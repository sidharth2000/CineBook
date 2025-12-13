/**
 * @author Mathew and Abhaydev
 * 
 * Description:
 * Unit test cases for User Controller API's
 */

package com.cinebook.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.LoginRequest;
import com.cinebook.dto.RegisterRequest;
import com.cinebook.dto.UpgradeToTheatreOwnerRequest;
import com.cinebook.security.JwtAuthenticationFilter;
import com.cinebook.service.JwtService;
import com.cinebook.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({})
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@MockBean
	private JwtService jwtService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void testRegisterUserSuccess() throws Exception {
		RegisterRequest request = new RegisterRequest();
		request.setEmail("mathew@example.com");
		request.setPassword("test123");

		Mockito.doAnswer(invocation -> null).when(userService).registerUser(Mockito.any());

		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("User registered successfully"));
	}

	@Test
	void testLoginUserSuccess() throws Exception {
		LoginRequest request = new LoginRequest();
		request.setEmail("mathew@example.com");
		request.setPassword("test123");

		Mockito.when(userService.loginUser(Mockito.any())).thenReturn("mocked-token");

		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("Login successful"))
				.andExpect(jsonPath("$.payload.token").value("mocked-token")); // ✅ FIXED
	}

	@Test
	@WithMockUser(username = "mathew@example.com")
	void testUpgradeToTheatreOwnerSuccess() throws Exception {
		UpgradeToTheatreOwnerRequest request = new UpgradeToTheatreOwnerRequest();
		request.setBusinessName("CineMax");
		request.setBusinessId("BIZ123");
		request.setTaxId("TAX456");

		ApiResponse<Void> response = new ApiResponse<>("success", null, "Upgraded successfully");

		Mockito.when(userService.upgradeToTheatreOwner(Mockito.anyString(), Mockito.any())).thenReturn(response);

		mockMvc.perform(post("/user/upgrade-to-theatre-owner").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("Upgraded successfully"));
	}
}