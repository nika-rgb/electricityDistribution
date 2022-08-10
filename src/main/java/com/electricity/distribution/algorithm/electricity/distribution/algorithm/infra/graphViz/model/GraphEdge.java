package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model;

import lombok.Data;

@Data
public class GraphEdge {
    private GraphNode start;
    private GraphNode end;
}
