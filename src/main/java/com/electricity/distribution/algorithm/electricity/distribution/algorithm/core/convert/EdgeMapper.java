package com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.convert;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Edge;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.EdgeResponse;

import java.util.List;

public class EdgeMapper {

    public static EdgeResponse getEdgeResponseFrom(Edge edge) {
        EdgeResponse response = new EdgeResponse();
        response.setId(edge.getId());
        response.setEndVerticeId(edge.getEndVerticeId().getId());
        response.setStartVerticeId(edge.getStartVerticeId().getId());
        return response;
    }

    public static List<EdgeResponse> getEdgeResponsesFrom(List<Edge> edges) {
        return edges.stream().map(EdgeMapper::getEdgeResponseFrom).toList();
    }

}
