package com.thecollective.task.service;

import com.thecollective.task.bean.GeneratorStatus;
import com.thecollective.task.bean.Plant;
import com.thecollective.task.repo.PlantRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


class PlantServiceTest {

    List<Plant> plants;

    final int totalPlantsSize = 10;

    @InjectMocks
    private PlantService underTest;

    @Mock
    private PlantRepo plantRepo;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        plants = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
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
    }

    @Test
    void itShouldGetPlantByState() {

        given(plantRepo.findAllByPlantState("AP", PageRequest.of(0,5)))
                .willReturn(plants);

        List<Plant> fetchedPlants = underTest.getPlantByState("AP", PageRequest.of(0, 5));

        assertThat(totalPlantsSize).isEqualTo(fetchedPlants.size());

    }

    @Test
    void itShouldGetTop2PlantsAsc() {

        List<Plant> mockedPlants = plants.stream()
                .filter(p -> p.getSequenceNumber() < plants.get(0).getSequenceNumber()+2).collect(Collectors.toList());

        given(plantRepo.getTopPlants(PageRequest.of(0, 2)))
                .willReturn(mockedPlants);

        List<Plant> fetchedPlants = underTest.getTopPlants(PageRequest.of(0,2), "asc");

        plantAssertions(fetchedPlants, mockedPlants);
    }

    @Test
    void itShouldGetTop2PlantsDesc() {

        List<Plant> mockedPlants = plants.stream()
                .filter(p -> p.getSequenceNumber() < plants.get(0).getSequenceNumber()+2)
                .sorted(Comparator.comparingInt(Plant::getSequenceNumber).reversed())
                .collect(Collectors.toList());;

        given(plantRepo.getTopPlants(PageRequest.of(0, 2)))
                .willReturn(mockedPlants);

        List<Plant> fetchedPlants = underTest.getTopPlants(PageRequest.of(0,2), "desc");

        plantAssertions(fetchedPlants, mockedPlants);
    }

    @Test
    void itShouldGetBottom2PlantsAsc() {

        List<Plant> mockedPlants = plants.stream()
                .filter(p -> p.getSequenceNumber() > plants.get(plants.size()-1).getSequenceNumber()-2)
                .collect(Collectors.toList());;

        given(plantRepo.getBottomPlants(PageRequest.of(0, 2)))
                .willReturn(mockedPlants);

        List<Plant> fetchedPlants = underTest.getBottomPlants(PageRequest.of(0,2), "asc");

        plantAssertions(fetchedPlants, mockedPlants);
    }

    @Test
    void itShouldGetBottom2PlantsDesc() {

        List<Plant> mockedPlants = plants.stream()
                .filter(p -> p.getSequenceNumber() > plants.get(plants.size()-1).getSequenceNumber()-2)
                .sorted(Comparator.comparingInt(Plant::getSequenceNumber).reversed())
                .collect(Collectors.toList());;

        given(plantRepo.getBottomPlants(PageRequest.of(0, 2)))
                .willReturn(mockedPlants);

        List<Plant> fetchedPlants = underTest.getBottomPlants(PageRequest.of(0,2), "desc");

        plantAssertions(fetchedPlants, mockedPlants);
    }



    void plantAssertions(List<Plant> fetchedPlants, List<Plant> filteredPlants) {
        assertThat(fetchedPlants.size()).isEqualTo(filteredPlants.size());
        assertThat(fetchedPlants.get(0).getSequenceNumber()).isEqualTo(filteredPlants.get(0).getSequenceNumber());
        assertThat(fetchedPlants.get(1).getSequenceNumber()).isEqualTo(filteredPlants.get(1).getSequenceNumber());
    }

    @Test
    void itShouldGetPlantDetails() {

        Plant plant = plants.get(0);

        given(plantRepo.findBySequenceNumber(plant.getSequenceNumber()))
                .willReturn(Optional.of(plant));

        Plant detailedPlant= underTest.getPlantDetails(plant.getSequenceNumber());

        assertThat(detailedPlant).isEqualTo(plant);
    }
}