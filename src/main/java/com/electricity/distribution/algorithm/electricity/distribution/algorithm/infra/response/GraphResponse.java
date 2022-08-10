package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response;

import lombok.Data;

import java.util.List;

@Data
public class GraphResponse {
    private List<GraphNodeResponse> graph;
}
