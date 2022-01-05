package com.thecollective.task.service;

import com.thecollective.task.bean.Plant;
import com.thecollective.task.dto.PlantDTO;
import com.thecollective.task.repo.PlantRepo;
import com.thecollective.task.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class PlantService {

    private final static int BATCH_SIZE = 100;

    @Autowired
    private PlantRepo plantRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveInPatch(List<PlantDTO> plantDTOS) {
        for (int i = 0; i < plantDTOS.size(); i++) {
            if(i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            PlantDTO plantDTO = plantDTOS.get(i);
            Plant plant = Plant.builder()
                    .sequenceNumber(plantDTO.getSequenceNumber())
                    .year(plantDTO.getYear())
                    .plantState(plantDTO.getPlantState())
                    .plantName(plantDTO.getPlantName())
                    .generatorId(plantDTO.getGeneratorId())
                    .status(plantDTO.getStatus())
                    .generatorAnnualNetGeneration(plantDTO.getGeneratorAnnualNetGeneration())
                    .build();

            entityManager.merge(plant);
        }
    }

    public List<PlantDTO> getPlants()  {
        return CommonUtil.preloadExcelDataIntoBean();
    }

}
