package com.thecollective.task.controller;

import com.thecollective.task.bean.GeneratorStatus;
import com.thecollective.task.bean.Plant;
import com.thecollective.task.config.security.PasswordConfig;
import com.thecollective.task.service.PlantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlantController.class)
@Import(value = {PasswordConfig.class})
class PlantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantService plantService;


    @Test
    void itShouldGetTop2PlantsAsc() throws Exception {
        List<Plant> plants = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            Plant plant = Plant.builder()
                    .sequenceNumber(i)
                    .year(2000 + i)
                    .plantName("generatedPlantNum " + i)
                    .plantState("AP")
                    .status(GeneratorStatus.IP)
                    .generatorAnnualNetGeneration(165156l+ i)
                    .build();

            plants.add(plant);
        }

        given(plantService.getTopPlants(PageRequest.of(0, 2), "asc"))
                .willReturn(plants);

        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/v1/plants/getTopPlants")
                .param("limit", "2")
                .param("direction", "asc")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("sana", "sana"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].sequenceNumber").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].sequenceNumber").value(1));
    }

    @Test
    void itShouldGetPlantDetails() throws Exception {

        Plant plant = Plant.builder()
                .sequenceNumber(1)
                .plantName("test")
                .build();

        given(plantService.getPlantDetails(1))
                .willReturn(plant);

        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/v1/plants/{plantId}", 1)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("sana", "sana"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sequenceNumber").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.plantName").value("test"));
    }
}