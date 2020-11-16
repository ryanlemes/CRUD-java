package com.crud.crudjava;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String path = "/user";

    @Test
    public void testUserGetList() throws Exception {
        URI uri = new URI(path);

        ResultActions response = mockMvc
                .perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON));
        response
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .content()
                        .string(
                                "[{\"id\":1,\"name\":\"testUser\",\"email\":\"testuser@testmail.com\"," +
                                                "\"singleDigits\":[{\"id\":1,\"n\":\"92\",\"k\":3,\"result\":6}]}]"));
    }

    @Test
    public void testUserGetSingleUser() throws Exception {
        URI uri = new URI(path + "/1");

        ResultActions response = mockMvc
                .perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .content()
                        .string(
                                "{\"id\":1,\"name\":\"testUser\",\"email\":\"testuser@testmail.com\"," +
                                                "\"singleDigits\":[{\"id\":0,\"n\":\"92\",\"k\":3,\"result\":6}]}"));
    }

    @Test
    public void testUserGetSingleUserThatNotExist() throws Exception {
        URI uri = new URI(path + "/99");

        ResultActions response = mockMvc
                .perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void testUserInsertNewUserSuccess() throws Exception {
        URI uri = new URI(path);

        String jsonRequest = "{\"name\": \"Ryan\",\"email\": \"ryan@testmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void testUserUpdate() throws Exception {
        URI uri = new URI(path + "/1");

        String jsonRequest = "{\"name\": \"Ryan\",\"email\": \"newtestuser@testmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers
                    .content()
                    .string(
                            "{\"id\":1,\"name\":\"Ryan\",\"email\":\"newtestuser@testmail.com\"," +
                                    "\"singleDigits\":[{\"id\":1,\"n\":\"92\",\"k\":3,\"result\":6}]}"));

    }

    @Test
    public void testUserUpdateInvalidUser() throws Exception {
        URI uri = new URI(path + "/2");

        String jsonRequest = "{\"name\": \"Ryan\",\"email\": \"newtestuser@testmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void testUserRemove() throws Exception {
        URI uri = new URI(path + "/1");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testUserRemoveNonExist() throws Exception {
        URI uri = new URI(path + "/99");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

}
