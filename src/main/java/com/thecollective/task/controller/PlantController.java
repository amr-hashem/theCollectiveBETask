package com.thecollective.task.controller;

import com.thecollective.task.bean.Plant;
import com.thecollective.task.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @GetMapping("/getTopPlants")
    public List<Plant> getTopPlants(
            @RequestParam(value = "limit", defaultValue = "5") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {

        limit = Math.min(limit, 500);

        PageRequest pageable = PageRequest.of(0, limit);

        return plantService.getTopPlants(pageable, direction);

    }
}
