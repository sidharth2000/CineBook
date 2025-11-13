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
import com.cinebook.model.Format;
import com.cinebook.model.Language;
import com.cinebook.model.RequestStatus;
import com.cinebook.model.Role;
import com.cinebook.repository.FormatRepository;
import com.cinebook.repository.LanguagesRepository;
import com.cinebook.repository.RequestStatusRepository;
import com.cinebook.repository.RoleRepository;
import com.cinebook.repository.UserRepository;

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
    }
    
}
