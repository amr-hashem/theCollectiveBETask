package com.thecollective.task.repo;

import com.thecollective.task.bean.Plant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepo extends JpaRepository<Plant, Integer> {

    @Query(value = "SELECT p from Plant p ORDER BY sequenceNumber ASC")
    List<Plant> getTopPlants(Pageable pageable);
}
