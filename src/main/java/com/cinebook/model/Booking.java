package com.cinebook.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID bookingId;  

    @ManyToOne(optional = false)
    private ShowTime showTime;

    @ManyToOne(optional = false)
    private Customer customer;

    @Column(nullable = false)
    private Integer loyaltyPointsEarned;

    @ManyToOne
    private VoucherCode voucherApplied; // optional

    @ManyToOne
    private PromoCode promocodeUsed;   // optional

    @Column(nullable = false)
    private Float discountedAmount;

    @Column(nullable = false)
    private Float taxPercentage;

    @Column(nullable = false)
    private Float taxAmount;

    @Column(nullable = false)
    private Float totalPrice;
    
    @Lob
    private byte[] ticketPdf;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id")
    private BookingStatus status;   // now a reference to booking_status table

    @ManyToMany
    @JoinTable(
        name = "booking_seats",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

	public UUID getBookingId() {
		return bookingId;
	}

	public void setBookingId(UUID bookingId) {
		this.bookingId = bookingId;
	}

	public ShowTime getShowTime() {
		return showTime;
	}

	public void setShowTime(ShowTime showTime) {
		this.showTime = showTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getLoyaltyPointsEarned() {
		return loyaltyPointsEarned;
	}

	public void setLoyaltyPointsEarned(Integer loyaltyPointsEarned) {
		this.loyaltyPointsEarned = loyaltyPointsEarned;
	}

	public VoucherCode getVoucherApplied() {
		return voucherApplied;
	}

	public void setVoucherApplied(VoucherCode voucherApplied) {
		this.voucherApplied = voucherApplied;
	}

	public PromoCode getPromocodeUsed() {
		return promocodeUsed;
	}

	public void setPromocodeUsed(PromoCode promocodeUsed) {
		this.promocodeUsed = promocodeUsed;
	}

	public Float getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public Float getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Float taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public Float getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Float taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public LocalDateTime getCreatedAt() {
	    return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
	    this.createdAt = createdAt;
	}

	public LocalDateTime getModifiedAt() {
	    return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
	    this.modifiedAt = modifiedAt;
	}
	
	public void setTicketPdf(byte[] ticketPdf) {
	    this.ticketPdf = ticketPdf;
	}

	public byte[] getTicketPdf() {
	    return ticketPdf;
	}
	
}
