package com.desafio.desafiointer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class SingleDigitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCalculateSuccess() throws Exception {
        URI uri = new URI("/singleDigit");

        String jsonRequest = "{\"n\":\"92\",\"k\":\"3\",\"user\":\"1\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string("6"));
    }

    @Test
    public void testCalculateNotSuccessMissingParameter() throws Exception {
        URI uri = new URI("/singleDigit");

        String jsonRequest = "{\"k\":\"3\",\"user\":\"1\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testCalculateNotSuccessWrongParameter() throws Exception {
        URI uri = new URI("/singleDigit");

        String jsonRequest = "{\"n\":\"92\",\"k\":\"avocado\",\"user\":\"1\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testListSingleDigitsFromUser() throws Exception {
        URI uri = new URI("/singleDigit/1");

        ResultActions response = mockMvc.perform(
                get(uri)
                        .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testInvalidParameterListSingleDigitsFromUser() throws Exception {
        URI uri = new URI("/singleDigit/99");

        ResultActions response = mockMvc.perform(
                get(uri)
                        .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

}
