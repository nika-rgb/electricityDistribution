package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<GraphNode, List<GraphEdge>> graph;

    private Graph(Map<GraphNode, List<GraphEdge>> graph) {
        this.graph = graph;
    }

    public Map<GraphNode, List<GraphEdge>> getGraph() {
        return graph;
    }

    public static class Builder {
        Map<GraphNode, List<GraphEdge>> graph;

        public Builder() {
            graph = new HashMap<>();
        }

        public Builder addNode(GraphNode node) {
            graph.putIfAbsent(node, new ArrayList<>());
            return this;
        }

        public Builder addEdge(GraphEdge edge) {
            addNode(edge.getStart());
            addNode(edge.getEnd());
            graph.get(edge.getStart()).add(edge);
            return this;
        }

        public Graph build() {
            return new Graph(graph);
        }

    }

}
