/**
 * @author Abhaydev and Mathew
 * 
 * Description:
 * Unit test cases for Admin Controller API's
 */

package com.cinebook.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cinebook.dto.MovieRequest;
import com.cinebook.dto.OnboardingRequestResponse;
import com.cinebook.dto.StatusUpdateRequest;
import com.cinebook.model.Movie;
import com.cinebook.model.OnboardingRequest;
import com.cinebook.security.JwtAuthenticationFilter;
import com.cinebook.service.AdminService;
import com.cinebook.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService adminService;

	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@MockBean
	private JwtService jwtService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	// ✅ 1. Test: Get onboarding requests (Admin only)
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetOnboardingRequestsSuccess() throws Exception {
		List<OnboardingRequestResponse> mockList = Collections.emptyList();

		Mockito.when(adminService.getOnboardingRequests("all")).thenReturn(mockList);

		mockMvc.perform(get("/admin/onboarding-requests")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success")).andExpect(jsonPath("$.payload").isArray());
	}

	// ✅ 2. Test: Get onboarding request by ID
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetOnboardingRequestByIdSuccess() throws Exception {
		OnboardingRequest mockRequest = new OnboardingRequest();

		Mockito.when(adminService.getOnboardingRequestById(1L)).thenReturn(mockRequest);

		mockMvc.perform(get("/admin/onboarding-request/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"));
	}

	// ✅ 3. Test: Update onboarding request status
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testUpdateOnboardingRequestStatusSuccess() throws Exception {
		StatusUpdateRequest req = new StatusUpdateRequest();
		req.setAction("APPROVE");

		OnboardingRequest updated = new OnboardingRequest();

		Mockito.when(adminService.updateOnboardingRequestStatus(Mockito.any())).thenReturn(updated);

		mockMvc.perform(post("/admin/onboarding-request/approval").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"));
	}

	// ✅ 4. Test: Get formats
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetFormatsSuccess() throws Exception {
		Mockito.when(adminService.getAllFormats()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/admin/formats")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"));
	}

	// ✅ 5. Test: Get languages
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetLanguagesSuccess() throws Exception {
		Mockito.when(adminService.getAllLanguages()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/admin/languages")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"));
	}

	// ✅ 6. Test: Get genres
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetGenresSuccess() throws Exception {
		Mockito.when(adminService.getAllGenres()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/admin/genres")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"));
	}

	// ✅ 7. Test: Get certifications
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetCertificationsSuccess() throws Exception {
		Mockito.when(adminService.getAllCertifications()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/admin/certifications")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"));
	}

	// ✅ 8. Test: Add movie
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testAddMovieSuccess() throws Exception {
		MovieRequest req = new MovieRequest();
		Movie saved = new Movie();

		Mockito.when(adminService.addMovie(Mockito.any(), Mockito.anyString())).thenReturn(saved);

		mockMvc.perform(post("/admin/add-movie").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("Movie added successfully"));
	}
}