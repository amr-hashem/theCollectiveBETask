package com.thecollective.task;

import com.thecollective.task.dto.PlantDTO;
import com.thecollective.task.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TheCollectiveTaskApplication implements ApplicationRunner {

	@Autowired
	private PlantService plantService;

	public static void main(String[] args) {
		SpringApplication.run(TheCollectiveTaskApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		List<PlantDTO> plantDTOList = plantService.getPlants();

		plantService.saveInPatch(plantDTOList);
	}
}
