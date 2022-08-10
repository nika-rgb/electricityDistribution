package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "edge")
@Getter
@Setter
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "start_vertice_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Vertice startVerticeId;

    @JoinColumn(name = "end_vertice_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Vertice endVerticeId;

}
