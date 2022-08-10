package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.builder.GraphVizFormatBuilder;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model.Graph;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model.GraphEdge;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model.GraphNode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GraphGenerator {
    public byte[] generateGraph(Graph graph) {
        Map<GraphNode, List<GraphEdge>> structure = graph.getGraph();
        final GraphVizFormatBuilder formatBuilder = new GraphVizFormatBuilder();
        structure.keySet().forEach(formatBuilder::addNode);
        structure.forEach((key, value) -> value.forEach(formatBuilder::addEdge));
        return formatBuilder.build().getBytes();
    }

}
