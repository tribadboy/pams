package com.nju.pams.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"classpath:spring-mvc-servlet.xml",
        "classpath*:applicationContext.xml"
})
public class UserControllerTest {
	 private MockMvc mockMvc;

	 @Autowired
	 private WebApplicationContext webApplicationContext;

	 @Before
	 public void setUp() throws Exception {
	     mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	 }

	 @Test
	 public void testUserController() throws Exception {
		 final MvcResult result = mockMvc.perform(post("/test/showUser")
	                         .param("userId", "1")
	                         .header("Content-type", "application/text;charset=UTF-8"))
	         .andExpect(status().isOk())
	         .andDo(print()).andReturn();
	     System.out.println(result.getResponse().getContentAsString());  
	 }
}
