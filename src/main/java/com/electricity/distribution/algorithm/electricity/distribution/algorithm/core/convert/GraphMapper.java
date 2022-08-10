package com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.convert;


import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.enums.GraphVizAttributes;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.enums.GraphVizColor;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.enums.GraphVizStyleOptions;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model.Graph;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model.GraphEdge;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model.GraphNode;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Vertice;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.GraphNodeResponse;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.GraphResponse;

import java.util.List;

public class GraphMapper {

    public static GraphResponse getGraphResponseFrom(List<Vertice> vertices) {
        GraphResponse response = new GraphResponse();
        List<GraphNodeResponse> graph = vertices.stream().
                map(vertice -> new GraphNodeResponse(VerticeMapper.getVertiveResponseFrom(vertice), EdgeMapper.getEdgeResponsesFrom(vertice.getStartVerticeEdges())))
                .toList();
        response.setGraph(graph);
        return response;
    }

    private static GraphNode getNodeFrom(Vertice vertice) {
        GraphNode node = new GraphNode();
        node.addAttribute(GraphVizAttributes.LABEL, vertice.getId());
        node.addAttribute(GraphVizAttributes.FILLCOLOR, vertice.isProvider() ? GraphVizColor.BLUE.getName() : GraphVizColor.RED.getName());
        node.addAttribute(GraphVizAttributes.STYLE, GraphVizStyleOptions.FILLED.getStyleName());
        return node;
    }

    private static GraphNode getNodeFrom(Vertice vertice, List<Vertice> unreachableNodes) {
        GraphNode node = new GraphNode();
        node.addAttribute(GraphVizAttributes.LABEL, vertice.getId());
        node.addAttribute(GraphVizAttributes.FILLCOLOR, unreachableNodes.contains(vertice) ? GraphVizColor.GREEN.getName() : GraphVizColor.BLUE.getName());
        node.addAttribute(GraphVizAttributes.STYLE, GraphVizStyleOptions.FILLED.getStyleName());
        return node;
    }


    public static Graph getGraphFrom(List<Vertice> vertices, List<Vertice> unreachableNodes) {
        Graph.Builder builder = new Graph.Builder();
        vertices.forEach(vertice -> {
            GraphNode node = unreachableNodes == null ? getNodeFrom(vertice) : getNodeFrom(vertice, unreachableNodes);
            builder.addNode(node);
            vertice.getStartVerticeEdges().forEach(adjacent -> {
                GraphNode adjacentNode = (unreachableNodes == null ? getNodeFrom(adjacent.getEndVerticeId()) : getNodeFrom(adjacent.getEndVerticeId(), unreachableNodes));
                builder.addNode(adjacentNode);
                GraphEdge edge = new GraphEdge();
                edge.setStart(node);
                edge.setEnd(adjacentNode);
                builder.addEdge(edge);
            });
        });
        return builder.build();
    }
}
