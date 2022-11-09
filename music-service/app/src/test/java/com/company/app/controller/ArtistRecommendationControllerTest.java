package com.company.app.controller;

import com.company.app.model.ArtistRecommendation;
import com.company.app.repository.ArtistRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {
    @MockBean
    private ArtistRecommendationRepository artistRecommendationRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private ArtistRecommendation inputArtistRecommended;
    private ArtistRecommendation inputArtistRecommendedWithId;
    private String inputJson;
    private ArtistRecommendation outputArtistRecommended;
    private ArtistRecommendation outputArtistRecommended2;



    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        inputArtistRecommended = new ArtistRecommendation(1,1, true);
        inputArtistRecommendedWithId = new ArtistRecommendation(1,1,1, true);
        outputArtistRecommended = new ArtistRecommendation(1,1,1, true);
        outputArtistRecommended2 = new ArtistRecommendation(5,1,1, true);

    }
    @Test
    public void shouldReturn201AndOnPost() throws Exception{
        inputJson = mapper.writeValueAsString(inputArtistRecommended);
        String outputJson = mapper.writeValueAsString(outputArtistRecommended);
        when(artistRecommendationRepository.save(inputArtistRecommended)).thenReturn(outputArtistRecommended);

        mockMvc.perform(post("/artistRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndArtistRecommendedById() throws Exception{
        Integer id = 8;
        String outputJson = mapper.writeValueAsString(outputArtistRecommended2);
        when(artistRecommendationRepository.findById(id)).thenReturn(Optional.of(outputArtistRecommended2));

        mockMvc.perform(get("/artistRecommendation/8",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith404WhenGetByNotFoundIdthrowsException() throws Exception{
        Integer id = 888;

        when(artistRecommendationRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/artistRecommendation/8",id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200AndCoffeeListOnGetAll() throws Exception {

        List<ArtistRecommendation> outputList = Arrays.asList(outputArtistRecommended,outputArtistRecommended2);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(artistRecommendationRepository).findAll();

        mockMvc.perform(get("/artistRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturn204OnDelete() throws Exception {
        Integer id = 1;
        String outputJson = mapper.writeValueAsString(outputArtistRecommended2);
        when(artistRecommendationRepository.findById(id)).thenReturn(Optional.of(outputArtistRecommended2));

        mockMvc.perform(delete("/artistRecommendation/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        int id = 5;
        String inputJson = mapper.writeValueAsString(inputArtistRecommended);

        mockMvc.perform(put("/artistRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingArtistRecomendation() throws Exception {
        // Arrange
        doReturn(Optional.ofNullable(null)).when(artistRecommendationRepository).findById(134);

        // Act
        mockMvc.perform(
                        delete("/artistRecommendation/134"))
                .andDo(print())
                .andExpect(status().isNotFound()); // Assert return 404 NOT_FOUND
    }

}