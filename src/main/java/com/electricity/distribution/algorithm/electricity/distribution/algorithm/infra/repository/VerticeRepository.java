package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.repository;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Vertice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerticeRepository extends JpaRepository<Vertice, Long> {


}
