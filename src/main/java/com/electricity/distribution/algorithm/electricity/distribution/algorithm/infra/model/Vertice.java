package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "vertices")
@Getter
@Setter
public class Vertice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_provider", nullable = false)
    private boolean isProvider;

    @OneToMany(mappedBy = "startVerticeId", fetch = FetchType.LAZY)
    private List<Edge> startVerticeEdges;

    @OneToMany(mappedBy = "endVerticeId", fetch = FetchType.LAZY)
    private List<Edge> endVerticeEdges;
}
