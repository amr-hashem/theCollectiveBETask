package com.thecollective.task.util;

import com.thecollective.task.dto.PlantDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

class CommonUtilTest {

    List<PlantDTO> underTest;

    final static int PLANTS_IN_CA_STATE = 3374;

    @BeforeEach
    void setup() {
        underTest = CommonUtil.preloadExcelDataIntoBean();
    }

    @Test
    void TestPreloadExcelDataIntoBeanNotEmpty() {
        assertThat(underTest).isNotEmpty();
    }

    @Test
    void validateBeanLoadedData() {
        List<PlantDTO> filteredPlants= underTest.stream()
                .filter(plantDTO -> plantDTO.getPlantState().equals("CA"))
                .collect(Collectors.toList());

        assertThat(PLANTS_IN_CA_STATE).isEqualTo(filteredPlants.size());
    }

}