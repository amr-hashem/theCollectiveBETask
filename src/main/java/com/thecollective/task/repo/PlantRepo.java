package com.thecollective.task.repo;

import com.thecollective.task.bean.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepo extends JpaRepository<Plant, Integer> {

}
