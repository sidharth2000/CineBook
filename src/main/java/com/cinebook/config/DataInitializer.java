package com.cinebook.config;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cinebook.model.Admin;
import com.cinebook.model.BookingStatus;
import com.cinebook.model.Format;
import com.cinebook.model.GlobalConfig;
import com.cinebook.model.Language;
import com.cinebook.model.PromoCode;
import com.cinebook.model.RequestStatus;
import com.cinebook.model.Role;
import com.cinebook.model.VoucherType;
import com.cinebook.repository.BookingStatusRepository;
import com.cinebook.repository.FormatRepository;
import com.cinebook.repository.GlobalConfigRepository;
import com.cinebook.repository.LanguagesRepository;
import com.cinebook.repository.PromoCodeRepository;
import com.cinebook.repository.RequestStatusRepository;
import com.cinebook.repository.RoleRepository;
import com.cinebook.repository.UserRepository;
import com.cinebook.repository.VoucherTypeRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
    private RequestStatusRepository requestStatusRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private LanguagesRepository languageRepository;
	
	@Autowired
	private FormatRepository formatRepository;
	
	@Autowired
	private VoucherTypeRepository voucherTypeRepository;
	
	@Autowired
	private BookingStatusRepository bookingStatusRepository;
	
	@Autowired
	private PromoCodeRepository promoCodeRepository;
	
	@Autowired
	private GlobalConfigRepository globalConfigRepository;
	

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) { // only if table is empty
            Role admin = new Role();
            admin.setRoleName("ADMIN");

            Role customer = new Role();
            customer.setRoleName("CUSTOMER");

            Role theatreOwner = new Role();
            theatreOwner.setRoleName("THEATRE_OWNER");

            roleRepository.save(admin);
            roleRepository.save(customer);
            roleRepository.save(theatreOwner);

            System.out.println("Roles initialized!");
        }
        
        
        if (requestStatusRepository.count() == 0) {
            RequestStatus pending = new RequestStatus("PENDING");
            RequestStatus approved = new RequestStatus("APPROVED");
            RequestStatus rejected = new RequestStatus("REJECTED");

            requestStatusRepository.save(pending);
            requestStatusRepository.save(approved);
            requestStatusRepository.save(rejected);

            System.out.println("Request statuses initialized!");
        }
        
        
        if (!userRepository.findByEmail("admin@cinebook.com").isPresent()) {
            Admin admin = new Admin();
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setEmail("admin@cinebook.com");
            admin.setPasswordHash(passwordEncoder.encode("Admin@123")); // default password
            admin.setRegisteredDate(LocalDateTime.now());
            admin.setModifiedDate(LocalDateTime.now());

            Role adminRole = roleRepository.findByRoleName("ADMIN");
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);
            System.out.println("Admin user initialized!");
        }

        // Languages
        if (languageRepository.count() == 0) {
            List<String> languages = Arrays.asList(
                "English", "Irish", "Tamil", "Malayalam", "Chinese", "Hindi", "Swedish"
            );

            for (int i = 0; i < languages.size(); i++) {
                Language lang = new Language();
                lang.setLanguage(languages.get(i));
                languageRepository.save(lang);
            }

            System.out.println("Languages initialized!");
        }

        // Formats
        if (formatRepository.count() == 0) {
            List<String> formats = Arrays.asList("2D", "3D", "IMAX", "4DX", "ScreenX");
            for (int i = 0; i < formats.size(); i++) {
                Format f = new Format();
                f.setFormat(formats.get(i));
                formatRepository.save(f);
            }
            System.out.println("Formats initialized!");
        }
        
        
     // Voucher Types
        if (voucherTypeRepository.count() == 0) {

            VoucherType v1 = new VoucherType();
            v1.setVoucherName("LOYAL100");
            v1.setLoyaltyPointsCost(100);
            v1.setDiscountPercentage(5f);
            v1.setMaxDiscountAmount(5f);
            v1.setValidityPeriodDays(30);
            v1.setIsActive(true);

            VoucherType v2 = new VoucherType();
            v2.setVoucherName("LOYAL250");
            v2.setLoyaltyPointsCost(250);
            v2.setDiscountPercentage(10f);
            v2.setMaxDiscountAmount(10f);
            v2.setValidityPeriodDays(45);
            v2.setIsActive(true);

            VoucherType v3 = new VoucherType();
            v3.setVoucherName("LOYAL500");
            v3.setLoyaltyPointsCost(500);
            v3.setDiscountPercentage(15f);
            v3.setMaxDiscountAmount(15f);
            v3.setValidityPeriodDays(60);
            v3.setIsActive(true);

            VoucherType v4 = new VoucherType();
            v4.setVoucherName("LOYAL1000");
            v4.setLoyaltyPointsCost(1000);
            v4.setDiscountPercentage(20f);
            v4.setMaxDiscountAmount(25f);
            v4.setValidityPeriodDays(75);
            v4.setIsActive(true);

            VoucherType v5 = new VoucherType();
            v5.setVoucherName("LOYAL1500");
            v5.setLoyaltyPointsCost(1500);
            v5.setDiscountPercentage(25f);
            v5.setMaxDiscountAmount(40f);
            v5.setValidityPeriodDays(90);
            v5.setIsActive(true);

            voucherTypeRepository.saveAll(Arrays.asList(v1, v2, v3, v4, v5));
            System.out.println("Voucher Types initialized!");
        }
        
     // Booking Status Initialization
        if (bookingStatusRepository.count() == 0) {

            BookingStatus pending = new BookingStatus();
            pending.setStatusName("PENDING");
            pending.setDescription("Booking initiated but not completed");

            BookingStatus success = new BookingStatus();
            success.setStatusName("SUCCESS");
            success.setDescription("Booking completed and payment successful");

            BookingStatus failure = new BookingStatus();
            failure.setStatusName("FAILURE");
            failure.setDescription("Booking failed due to payment/session issues");

            BookingStatus cancelled = new BookingStatus();
            cancelled.setStatusName("CANCELLED");
            cancelled.setDescription("Booking cancelled by customer");

            bookingStatusRepository.saveAll(
                Arrays.asList(pending, success, failure, cancelled)
            );

            System.out.println("Booking statuses initialized!");
        }
        
     // Promo Codes
        if (promoCodeRepository.count() == 0) {

            PromoCode promo = new PromoCode();
            promo.setCode("CHRISTMAS2025");
            promo.setDiscountPercentage(20f);
            promo.setMaxDiscountAmount(50f);
            promo.setStartDate(LocalDateTime.now().minusDays(1));
            promo.setExpiryDate(LocalDateTime.now().plusDays(30));
            promo.setMaxUsePerCustomer(3);

            promoCodeRepository.save(promo);

            System.out.println("Promo Code initialized!");
        }
        
        if (globalConfigRepository.count() == 0) { // only if table is empty
            GlobalConfig taxConfig = new GlobalConfig();
            taxConfig.setConfigKey("TAX_PERCENTAGE");
            taxConfig.setConfigValue("10.0"); // example: 10%
            taxConfig.setDescription("Tax percentage applied on bookings");

            GlobalConfig loyaltyConfig = new GlobalConfig();
            loyaltyConfig.setConfigKey("LOYALTY_FACTOR");
            loyaltyConfig.setConfigValue("0.1"); // example: 10% of spent money = loyalty points
            loyaltyConfig.setDescription("Factor to calculate loyalty points from spent amount");

            globalConfigRepository.save(taxConfig);
            globalConfigRepository.save(loyaltyConfig);

            System.out.println("Global config initialized!");
        }
        
    }
    
}
