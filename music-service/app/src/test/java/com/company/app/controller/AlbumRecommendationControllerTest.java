package com.company.app.controller;

import com.company.app.model.AlbumRecommendation;
import com.company.app.repository.AlbumRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {
    @MockBean
    private AlbumRecommendationRepository albumRecommendationRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private AlbumRecommendation inputAlbumRecommended;
    private AlbumRecommendation inputAlbumRecommendedWithId;
    private String inputJson;
    private AlbumRecommendation outputAlbumRecommended;
    private AlbumRecommendation outputAlbumRecommended2;



    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        inputAlbumRecommended = new AlbumRecommendation(1,1, true);
        inputAlbumRecommendedWithId = new AlbumRecommendation(1,1,1, true);
        outputAlbumRecommended = new AlbumRecommendation(1,1,1, true);
        outputAlbumRecommended2 = new AlbumRecommendation(5,1,1, true);

    }
    @Test
    public void shouldReturn201AndOnPost() throws Exception{
        inputJson = mapper.writeValueAsString(inputAlbumRecommended);
        String outputJson = mapper.writeValueAsString(outputAlbumRecommended);
        when(albumRecommendationRepository.save(inputAlbumRecommended)).thenReturn(outputAlbumRecommended);

        mockMvc.perform(post("/albumRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndAlbumRecommendedById() throws Exception{
        Integer id = 8;
        String outputJson = mapper.writeValueAsString(outputAlbumRecommended2);
        when(albumRecommendationRepository.findById(id)).thenReturn(Optional.of(outputAlbumRecommended2));

        mockMvc.perform(get("/albumRecommendation/8",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith404WhenGetByNotFoundIdthrowsException() throws Exception{
        Integer id = 888;

        when(albumRecommendationRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/albumRecommendation/8",id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200AndCoffeeListOnGetAll() throws Exception {

        List<AlbumRecommendation> outputList = Arrays.asList(outputAlbumRecommended,outputAlbumRecommended2);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(albumRecommendationRepository).findAll();

        mockMvc.perform(get("/albumRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturn204OnDelete() throws Exception {
        Integer id = 123;
        String outputJson = mapper.writeValueAsString(outputAlbumRecommended2);
        when(albumRecommendationRepository.findById(id)).thenReturn(Optional.of(outputAlbumRecommended2));
        mockMvc.perform(delete("/albumRecommendation/{id}", 123))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        int id = 5;
        String inputJson = mapper.writeValueAsString(inputAlbumRecommended);

        mockMvc.perform(put("/albumRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingAlbumRecomendation() throws Exception {
        // Arrange
        doReturn(Optional.ofNullable(null)).when(albumRecommendationRepository).findById(134);

        // Act
        mockMvc.perform(
                        delete("/albumRecommendation/134"))
                .andDo(print())
                .andExpect(status().isNotFound()); // Assert return 404 NOT_FOUND
    }

}