package com.infosys.api;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.infosys.api.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTests {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}


	@Test
	public void SaveUserTest() throws Exception {
		User user = new User(377, "ADB", 38, "USA", "6.5", "78", "unknown");
		String jsonRequest = om.writeValueAsString(user);
		MvcResult result = mockMvc.perform(post("/users/signup").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is2xxSuccessful()).andReturn();
		String status = String.valueOf(result.getResponse().getStatus());
		assertEquals("201", status);
	}


	@Test
	public void getAllUserTest() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/users/fetchAll").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is2xxSuccessful()).andReturn();
		String status = String.valueOf(result.getResponse().getStatus());
		assertEquals("200", status);

	}

}

