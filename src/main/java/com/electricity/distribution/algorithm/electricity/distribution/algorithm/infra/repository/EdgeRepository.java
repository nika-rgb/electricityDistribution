package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.repository;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {
    @Query("SELECT e FROM edge e WHERE (:endId IS null OR :endId = e.endVerticeId.id) " +
            "AND (:startId IS null OR :startId = e.startVerticeId.id)")
    List<Edge> getEdgesOf(@Param("startId") Long startId, @Param("endId") Long endId);
}
