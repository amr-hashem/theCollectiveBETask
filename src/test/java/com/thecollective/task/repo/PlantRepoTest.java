package com.thecollective.task.repo;

import com.thecollective.task.bean.GeneratorStatus;
import com.thecollective.task.bean.Plant;
import com.thecollective.task.service.PlantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PlantRepoTest {

    final int pageNumber = 0;
    final int pageSize = 10;

    List<Plant> generatedPlants = new ArrayList<>();
    List<Plant> allPlantsFromDB;

    @MockBean
    private PlantService plantService;

    @Autowired
    private PlantRepo underTest;


    @BeforeEach
    void setup() {

        generatedPlants.clear();

        for (int i = 1; i <= 10; i++) {
            Plant plant = Plant.builder()
                    .sequenceNumber(i)
                    .year(2000 + i)
                    .plantName("generatedPlantNum " + i)
                    .plantState("AP")
                    .status(GeneratorStatus.IP)
                    .generatorAnnualNetGeneration(165156l+ i)
                    .build();

            generatedPlants.add(plant);
        }
        underTest.saveAll(generatedPlants);
        allPlantsFromDB = underTest.findAll();
    }


    @Test
    void itShouldGetTop2Plants() {

        List<Plant> fetchedPlants =
                underTest.getTopPlants(PageRequest.of(0, 2));


        List<Plant> sortedPlants = allPlantsFromDB.stream()
                .sorted(Comparator.comparingInt(Plant::getSequenceNumber))
                .collect(Collectors.toList());

        plantAssertions(fetchedPlants, sortedPlants, 2);

    }

    @Test
    void itShouldGetBottom3Plants() {

        List<Plant> fetchedPlants =
                underTest.getBottomPlants(PageRequest.of(0, 3));


        List<Plant> sortedPlants = allPlantsFromDB.stream()
                .sorted(Comparator.comparingInt(Plant::getSequenceNumber).reversed())
                .collect(Collectors.toList());

        plantAssertions(fetchedPlants, sortedPlants, 3);
    }

    void plantAssertions(List<Plant> fetchedPlants, List<Plant> sortedPlants, int size) {
        assertThat(fetchedPlants.size()).isEqualTo(size);
        assertThat(fetchedPlants.get(0).getSequenceNumber()).isEqualTo(sortedPlants.get(0).getSequenceNumber());
        assertThat(fetchedPlants.get(1).getSequenceNumber()).isEqualTo(sortedPlants.get(1).getSequenceNumber());
    }
    @Test
    void itShouldFindAllByPlantState() {
        List<Plant> fetchedPlants =
                underTest.findAllByPlantState("AP", PageRequest.of(pageNumber, pageSize));

        assertThat(fetchedPlants.size()).isEqualTo(generatedPlants.size());
    }

    @Test
    void itShouldFindZeroResultByPlantState() {
        List<Plant> fetchedPlants =
                underTest.findAllByPlantState("AS", PageRequest.of(pageNumber, pageSize));
        assertThat(fetchedPlants.size()).isZero();
    }

    @Test
    void itShouldFindBySequenceNumber() {
        Optional<Plant> fetchedPlant =
                underTest.findBySequenceNumber(allPlantsFromDB.get(0).getSequenceNumber());

        Plant plant = new Plant();

        if(fetchedPlant.isPresent()) {
            plant = fetchedPlant.get();
        }

        assertThat(plant).isEqualTo(allPlantsFromDB.get(0));

    }

}