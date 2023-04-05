package com.hostTask.pavelCherepanov.persistence.dao;

import com.hostTask.pavelCherepanov.persistence.model.entity.MarqueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarqueModelRepository extends JpaRepository<MarqueModel, Integer> {
    // SQL query to search for marque_modelid by marqueID and modelId(nativeQuery = true - query in pure SQL)
    @Query(nativeQuery = true, value = "SELECT marque_modelid FROM marque_model WHERE marqueID = :marque AND modelID = :model")
    Integer findByMarqueIDModelID(
            @Param("marque") int marque,
            @Param("model") int model
    );
}
