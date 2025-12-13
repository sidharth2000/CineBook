/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.observers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.cinebook.model.Booking;
import com.cinebook.model.Movie;
import com.cinebook.model.Screen;
import com.cinebook.model.ShowTime;
import com.cinebook.repository.BookingRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class TicketPdfGeneratorObserver implements BookingObserver {
	
	private final BookingRepository bookingRepository;

	public TicketPdfGeneratorObserver(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
	
	@Override
	public void update(Booking booking) {

	    String status = booking.getStatus().getStatusName().toUpperCase();

	    try {
	        switch (status) {

	            case "SUCCESS":
	                // Generate PDF ONLY for successful bookings
	                byte[] qrBytes = generateQrCode(booking.getBookingId().toString());
	                byte[] pdfBytes = generatePdf(booking, qrBytes);
	                booking.setTicketPdf(pdfBytes);
	                
	                System.out.println("Ticket PDF generated for booking: " + booking.getBookingId());
	                break;

	            case "CANCELLED":
	                // Remove stored PDF if booking is cancelled
	                booking.setTicketPdf(null);
	                System.out.println("Ticket PDF deleted for cancelled booking: " + booking.getBookingId());
	                break;

	            default:
	                // Do nothing for intermediate states (LOCKED, FAILED, PENDING, etc.)
	                System.out.println("No PDF action for status: " + status);
	        }
	        
	        bookingRepository.save(booking);

	    } catch (Exception e) {
	        System.out.println("PDF operation failed: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


    private byte[] generateQrCode(String text) throws Exception {

        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);

        BufferedImage qrImage = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < 250; x++) {
            for (int y = 0; y < 250; y++) {
                int grayValue = (bitMatrix.get(x, y) ? 0 : 255);
                qrImage.setRGB(x, y, (grayValue << 16) | (grayValue << 8) | grayValue);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);
        return baos.toByteArray();
    }


    private byte[] generatePdf(Booking booking, byte[] qrCode) throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document, output);
        document.open();

        ShowTime showTime = booking.getShowTime();
        Movie movie = showTime.getMovie();
        Screen screen = showTime.getScreen();

        // Title
        document.add(new Paragraph("CINEBOOK - MOVIE TICKET"));
        document.add(new Paragraph(" ")); // empty line

        // Table for ticket details
        PdfPTable table = new PdfPTable(2);

        table.addCell("Booking ID");
        table.addCell(booking.getBookingId().toString());

        table.addCell("Movie");
        table.addCell(movie.getMovieTitle());

        table.addCell("Language");
        table.addCell(showTime.getLanguage().getLanguage());

        table.addCell("Format");
        table.addCell(showTime.getFormat().getFormat());

        table.addCell("Theatre");
        table.addCell(screen.getScreenName());

        table.addCell("Show Time");
        table.addCell(showTime.getStartTime().toString());

        table.addCell("Seats");
        String seats = booking.getSeats()
                .stream()
                .map(seat -> seat.getSeatName())
                .collect(Collectors.joining(", "));
        table.addCell(seats);
        

        table.addCell("Final Price");
        table.addCell(String.valueOf(booking.getTotalPrice()));

        document.add(table);

        document.add(new Paragraph(" ")); // space

        // QR IMAGE
        Image qrImg = Image.getInstance(qrCode);
        qrImg.scaleAbsolute(150, 150);
        qrImg.setAlignment(Image.ALIGN_CENTER);
        document.add(qrImg);

        document.close();
        return output.toByteArray();
    }
}
