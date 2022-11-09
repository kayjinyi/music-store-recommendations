package com.company.app.controller;

import com.company.app.model.TrackRecommendation;
import com.company.app.repository.TrackRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {
    @MockBean
    private TrackRecommendationRepository trackRecommendationRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private TrackRecommendation inputTrackRecommended;
    private TrackRecommendation inputTrackRecommendedWithId;
    private String inputJson;
    private TrackRecommendation outputTrackRecommended;
    private TrackRecommendation outputTrackRecommended2;



    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        inputTrackRecommended = new TrackRecommendation(1,1, true);
        inputTrackRecommendedWithId = new TrackRecommendation(1,1,1, true);
        outputTrackRecommended = new TrackRecommendation(1,1,1, true);
        outputTrackRecommended2 = new TrackRecommendation(5,1,1, true);

    }
    @Test
    public void shouldReturn201AndOnPost() throws Exception{
        inputJson = mapper.writeValueAsString(inputTrackRecommended);
        String outputJson = mapper.writeValueAsString(outputTrackRecommended);
        when(trackRecommendationRepository.save(inputTrackRecommended)).thenReturn(outputTrackRecommended);

        mockMvc.perform(post("/trackRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndTrackRecommendedById() throws Exception{
        Integer id = 8;
        String outputJson = mapper.writeValueAsString(outputTrackRecommended2);
        when(trackRecommendationRepository.findById(id)).thenReturn(Optional.of(outputTrackRecommended2));

        mockMvc.perform(get("/trackRecommendation/8",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith404WhenGetByNotFoundIdthrowsException() throws Exception{
        Integer id = 888;

        when(trackRecommendationRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/trackRecommendation/8",id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200AndCoffeeListOnGetAll() throws Exception {

        List<TrackRecommendation> outputList = Arrays.asList(outputTrackRecommended,outputTrackRecommended2);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(trackRecommendationRepository).findAll();

        mockMvc.perform(get("/trackRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturn204OnDelete() throws Exception {

        Integer id = 365;
        String outputJson = mapper.writeValueAsString(outputTrackRecommended2);
        when(trackRecommendationRepository.findById(id)).thenReturn(Optional.of(outputTrackRecommended2));

        mockMvc.perform(delete("/trackRecommendation/{id}", 365))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        int id = 5;
        String inputJson = mapper.writeValueAsString(inputTrackRecommended);

        mockMvc.perform(put("/trackRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingTrackRecomendation() throws Exception {
        // Arrange
        doReturn(Optional.ofNullable(null)).when(trackRecommendationRepository).findById(134);

        // Act
        mockMvc.perform(
                        delete("/trackRecommendation/134"))
                .andDo(print())
                .andExpect(status().isNotFound()); // Assert return 404 NOT_FOUND
    }

}