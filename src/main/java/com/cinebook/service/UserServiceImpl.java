package com.cinebook.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cinebook.builder.TheatreBuilder;
import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.LoginRequest;
import com.cinebook.dto.RegisterRequest;
import com.cinebook.dto.UpgradeToTheatreOwnerRequest;
import com.cinebook.model.Address;
import com.cinebook.model.Customer;
import com.cinebook.model.OnboardingRequest;
import com.cinebook.model.RequestStatus;
import com.cinebook.model.Role;
import com.cinebook.model.Theatre;
import com.cinebook.model.TheatreOwner;
import com.cinebook.model.User;
import com.cinebook.repository.RoleRepository;
import com.cinebook.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public User registerUser(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already registered");
		}

		// Create Address
		Address address = new Address();
		address.setAddressLine1(request.getAddressLine1());
		address.setAddressLine2(request.getAddressLine2());
		address.setCounty(request.getCounty());
		address.setEirCode(request.getEirCode());

		// Create Customer
		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		customer.setPhoneNumber(request.getPhoneNumber());
		customer.setAddress(address);

		// Assign default role
		Role defaultRole = roleRepository.findByRoleName("CUSTOMER");
		if (defaultRole == null) {
			defaultRole = new Role();
			defaultRole.setRoleName("CUSTOMER");
			roleRepository.save(defaultRole);
		}
		Set<Role> roles = new HashSet<>();
		roles.add(defaultRole);
		customer.setRoles(roles);

		return userRepository.save(customer);
	}

	@Override
	public String loginUser(LoginRequest request) {
		try {
			// Authenticate user
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			// Load UserDetails
			UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

			// Generate JWT token
			return jwtService.generateToken(userDetails);
		} catch (BadCredentialsException e) {
			throw new RuntimeException("Invalid email or password");
		}
	}

	@Override
	@Transactional
	public ApiResponse<Void> upgradeToTheatreOwner(String email, UpgradeToTheatreOwnerRequest request) {
		// 1. Find existing user
		User existingUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		// 2. Check if user is already a TheatreOwner
		String checkOwnerSql = "SELECT COUNT(*) FROM theatre_owner WHERE user_id = :userId";
		Number count = (Number) entityManager.createNativeQuery(checkOwnerSql)
				.setParameter("userId", existingUser.getUserId()).getSingleResult();
		if (count.intValue() > 0) {
			return new ApiResponse<>("failure", null, "User is already a TheatreOwner");
		}

		// 3. Insert into theatre_owner table using native query
		String insertSql = "INSERT INTO theatre_owner (user_id, business_name, business_id, tax_id, registered_date, modified_date) "
				+ "VALUES (:userId, :businessName, :businessId, :taxId, NOW(), NOW())";
		entityManager.createNativeQuery(insertSql).setParameter("userId", existingUser.getUserId())
				.setParameter("businessName", request.getBusinessName())
				.setParameter("businessId", request.getBusinessId()).setParameter("taxId", request.getTaxId())
				.executeUpdate();

		// 4. Assign THEATRE_OWNER role
		Role theatreOwnerRole = roleRepository.findByRoleName("THEATRE_OWNER");
		if (theatreOwnerRole == null) {
			theatreOwnerRole = new Role();
			theatreOwnerRole.setRoleName("THEATRE_OWNER");
			roleRepository.save(theatreOwnerRole);
		}

		// Keep existing roles and add theatre owner role
		Set<Role> roles = new HashSet<>(existingUser.getRoles());
		roles.add(theatreOwnerRole);
		existingUser.setRoles(roles);
		userRepository.save(existingUser); // update roles in user_roles table

		return new ApiResponse<>("success", null, "User upgraded to TheatreOwner successfully");
	}
	
	


}
