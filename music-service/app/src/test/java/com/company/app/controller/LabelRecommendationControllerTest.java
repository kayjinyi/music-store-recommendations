package com.company.app.controller;

import com.company.app.model.LabelRecommendation;
import com.company.app.repository.LabelRecommendationRepository;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {
    @MockBean
    private LabelRecommendationRepository labelRecommendationRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private LabelRecommendation inputLabelRecommended;
    private LabelRecommendation inputLabelRecommendedWithId;
    private String inputJson;
    private LabelRecommendation outputLabelRecommended;
    private LabelRecommendation outputLabelRecommended2;



    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        inputLabelRecommended = new LabelRecommendation(1,1, true);
        inputLabelRecommendedWithId = new LabelRecommendation(1,1,1, true);
        outputLabelRecommended = new LabelRecommendation(1,1,1, true);
        outputLabelRecommended2 = new LabelRecommendation(5,1,1, true);

    }
    @Test
    public void shouldReturn201AndOnPost() throws Exception{
        inputJson = mapper.writeValueAsString(inputLabelRecommended);
        String outputJson = mapper.writeValueAsString(outputLabelRecommended);
        when(labelRecommendationRepository.save(inputLabelRecommended)).thenReturn(outputLabelRecommended);

        mockMvc.perform(post("/labelRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn200AndLabelRecommendedById() throws Exception{
        Integer id = 8;
        String outputJson = mapper.writeValueAsString(outputLabelRecommended2);
        when(labelRecommendationRepository.findById(id)).thenReturn(Optional.of(outputLabelRecommended2));

        mockMvc.perform(get("/labelRecommendation/8",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith404WhenGetByNotFoundIdthrowsException() throws Exception{
        Integer id = 888;

        when(labelRecommendationRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/labelRecommendation/8",id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200AndCoffeeListOnGetAll() throws Exception {

        List<LabelRecommendation> outputList = Arrays.asList(outputLabelRecommended,outputLabelRecommended2);
        String outputJson = mapper.writeValueAsString(outputList);

        doReturn(outputList).when(labelRecommendationRepository).findAll();

        mockMvc.perform(get("/labelRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturn204OnDelete() throws Exception {
        Integer id = 8;
        String outputJson = mapper.writeValueAsString(outputLabelRecommended2);
        when(labelRecommendationRepository.findById(id)).thenReturn(Optional.of(outputLabelRecommended2));

        mockMvc.perform(delete("/labelRecommendation/{id}", 8))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturn204OnUpdate() throws Exception {
        int id = 5;
        String inputJson = mapper.writeValueAsString(inputLabelRecommended);

        mockMvc.perform(put("/labelRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingLabelRecomendation() throws Exception {
        // Arrange
        doReturn(Optional.ofNullable(null)).when(labelRecommendationRepository).findById(134);

        // Act
        mockMvc.perform(
                        delete("/labelRecommendation/134"))
                .andDo(print())
                .andExpect(status().isNotFound()); // Assert return 404 NOT_FOUND
    }

}