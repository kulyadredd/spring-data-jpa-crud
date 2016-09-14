package com.app.controller;

import com.app.SpringDataJpaApplication;
import com.app.models.jpa.User;
import com.app.service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Roman Kulinenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataJpaApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserService userService;

    @Before
    public void populate() throws Exception{
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        User user1 = new User();
        User user2 = new User();

        user1.setName("User-1");
        user2.setName("User-2");

        userService.insertUser(user1);
        userService.insertUser(user2);
    }

    @After
    public void erase() throws Exception{
        userService.deleteAllUsers();
    }

    @Test
    public void getUsersTest() throws Exception{
        mvc
            .perform(get("/api/users").param("page", "1").param("perPage", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$numberOfElements", is(2)));
    }

    @Test
    public void insertUserTest() throws Exception{
        mvc
            .perform(post("/api/users").content("{\"name\": \"User-3\"}").contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated());
        mvc
            .perform(get("/api/users").param("page", "1").param("perPage", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$numberOfElements", is(3)));
    }

    @Test
    public void deleteUserTest() throws Exception{
        Page<User> usersBeforeDelete = userService.getUsers(1, 10);
        assertNotNull(usersBeforeDelete);
        assertEquals(usersBeforeDelete.getNumberOfElements(), 2);
        mvc
            .perform(delete("/api/users/1"))
            .andExpect(status().isOk());
        Page<User> usersAfterDelete = userService.getUsers(1, 10);
        assertNotNull(usersAfterDelete);
        assertEquals(usersAfterDelete.getNumberOfElements(), 1);
    }

    @Test
    public void updateUserTest() throws Exception{
        mvc
            .perform(put("/api/users").content("{\"id\":1, \"name\":\"Updated User\"}").contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
        mvc
            .perform(get("/api/users").param("page", "1").param("perPage", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$numberOfElements", is(2)));

        User updatedUser = userService.getUserById(1);
        assertNotNull(updatedUser);
        assertEquals(updatedUser.getId().longValue(), 1L);
        assertEquals(updatedUser.getName(), "Updated User");
    }

    @Test
    public void getPages() throws Exception{
        mvc
            .perform(get("/api/users").param("page", "1").param("perPage", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$size", is(1)));
        mvc
            .perform(get("/api/users").param("page", "2").param("perPage", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$size", is(1)));
    }
}
