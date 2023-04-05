package com.hostTask.pavelCherepanov.persistence.dao;

import com.hostTask.pavelCherepanov.persistence.model.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EngineRepository extends JpaRepository<Engine, Integer> {
    // SQL query to search engine ID by engine name and power
    @Query("SELECT engineID FROM Engine u WHERE u.engine = :engine AND u.enginePowerBhp = :enginePowerBhp")
    Integer findByEngineAndEnginePowerBhp(
            @Param("engine") String engine,
            @Param("enginePowerBhp") Integer enginePowerBhp
    );
}
