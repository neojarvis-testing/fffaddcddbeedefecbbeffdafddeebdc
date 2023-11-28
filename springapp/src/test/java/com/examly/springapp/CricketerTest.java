package com.examly.springapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.examly.springapp.configurations.ApplSecurityConfig;
import com.examly.springapp.entities.Player;
import com.examly.springapp.entities.Team;
import com.examly.springapp.entities.User;

@SpringBootTest
@AutoConfigureMockMvc
public class CricketerTest {
	
	private void assertFieldExists(Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			assertNotNull(field);
		} catch (NoSuchFieldException e) {
			fail("Field '" + fieldName + "' does not exist in the " + clazz.getSimpleName() + " class.");
		}
	}

	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	public void TeamClass() {

		String filePath = "src/main/java/com/examly/springapp/entities/Team.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
		
		Class<Team> teamclass = Team.class;

		assertFieldExists(teamclass, "id");
		assertFieldExists(teamclass, "name");
		assertFieldExists(teamclass, "maximumBudget");
	}
	
	@Test
	public void PlayerClass() {

		String filePath = "src/main/java/com/examly/springapp/entities/Player.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
		
		Class<Player> playerclass = Player.class;

		assertFieldExists(playerclass, "id");
		assertFieldExists(playerclass, "name");
		assertFieldExists(playerclass, "age");
		assertFieldExists(playerclass, "category");
		assertFieldExists(playerclass, "biddingPrice");
		assertFieldExists(playerclass, "sold");
		assertFieldExists(playerclass, "email");
	}
	
	@Test
	public void UserClass() {

		String filePath = "src/main/java/com/examly/springapp/entities/User.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
		
		Class<User> teamclass = User.class;

		assertFieldExists(teamclass, "id");
		assertFieldExists(teamclass, "username");
		assertFieldExists(teamclass, "password");
		assertFieldExists(teamclass, "role");
	}
	
	@Test
	public void TeamClassRepo() {

		String filePath = "src/main/java/com/examly/springapp/repositories/TeamRepository.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}
	
	@Test
	public void PlayerClassRepo() {

		String filePath = "src/main/java/com/examly/springapp/repositories/PlayerRepository.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}
	
	@Test
	public void UserClassRepo() {

		String filePath = "src/main/java/com/examly/springapp/repositories/UserRepository.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}
	
	@Test
	public void Controllerfolder() {

		String directoryPath = "src/main/java/com/examly/springapp/controllers"; // Replace with the path to your
		File directory = new File(directoryPath);
		assertTrue(directory.exists() && directory.isDirectory());
	}

	@Test
	public void AdminControllerFile() {
		String filePath = "src/main/java/com/examly/springapp/controllers/AdminController.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}
	
	@Test
	public void UserControllerFile() {
		String filePath = "src/main/java/com/examly/springapp/controllers/UserController.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}
	
	@Test
	public void TeamControllerFile() {
		String filePath = "src/main/java/com/examly/springapp/controllers/OrganizerController.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
	}
	
	@Test
	void Teamadd() throws Exception {
		String st = "{\"id\": 1000,\"name\": \"Demo\",\"maximumBudget\":15000}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/teams").contentType(MediaType.APPLICATION_JSON).content(st)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(MockMvcResultMatchers.content().string("true")) // Correct way to
				// validate boolean response
				.andReturn();
	}

	@Test
	void GetallTeam() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/teams").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andReturn();
	}
	

	@Test
	void Playeradd() throws Exception {
		String st = "{\"id\": 1000,\"name\": \"Demo\", \"age\": 24,\"category\": \"Seniorteam\",\"biddingPrice\":15000,\"sold\":false,\"email\": \"Viratdemo@gmail.com\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/players").contentType(MediaType.APPLICATION_JSON).content(st)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(MockMvcResultMatchers.content().string("true")) // Correct way to
				// validate boolean response
				.andReturn();
	}

	
	@Test
	void GetallPlayerWithTeam() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/players").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andReturn();
	}

	@Test
	public void TestFieldExistence() {
		Class<Player> player = Player.class;

		assertFieldExists(player, "team");
	}
	
	@Test
	void GetallPlayerList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/organizer/player-list").param("teamId", "1000").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andReturn();
	}

	
	@Test
	void GetallUnsoldPlayer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/organizer/unsold-players").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andReturn();
	}
	
	@Test
	void GetallSoldPlayer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/organizer/sold-players").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andReturn();
	}
	
	
	@Test
	public void Configfolder() {

		String directoryPath = "src/main/java/com/examly/springapp/configurations"; // Replace with the path to your
		File directory = new File(directoryPath);
		assertTrue(directory.exists() && directory.isDirectory());
	}
	
	
	
	@Test
	public void SpringSecurity() {
		String filePath = "src/main/java/com/examly/springapp/configurations/ApplSecurityConfig.java";
		File file = new File(filePath);
		assertTrue(file.exists() && file.isFile());
		Class<ApplSecurityConfig> config = ApplSecurityConfig.class;

		try {
			Method method1 = config.getDeclaredMethod("securityFilterChain",HttpSecurity.class);
			Method method2 = config.getDeclaredMethod("userDetailsService");

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void Springssecurity() throws Exception {
		String st = "{\"username\": \"admin\",\"password\": \"admin123\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(st).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				// .andExpect(MockMvcResultMatchers.content().string("true")) // Correct way to
				// validate boolean response
				.andReturn();
	}

	
	@Test
	void SpringRegisterUser() throws Exception {
		String st = "{\"id\": 1000,\"username\": \"admin\",\"password\": \"admin123\",\"role\": \"ADMIN\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/register").contentType(MediaType.APPLICATION_JSON).content(st)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
				// .andExpect(MockMvcResultMatchers.content().string("true")) // Correct way to
				// validate boolean response
				.andReturn();
	}

}
