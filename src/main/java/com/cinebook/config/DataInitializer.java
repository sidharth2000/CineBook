package com.cinebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cinebook.model.RequestStatus;
import com.cinebook.model.Role;
import com.cinebook.repository.RequestStatusRepository;
import com.cinebook.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
    private RequestStatusRepository requestStatusRepository;

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
    }
    
}
