package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response;

import lombok.Data;

import java.util.List;

@Data
public class NonReachableNodesResponse extends GraphResponse {

    public NonReachableNodesResponse(GraphResponse response, List<VerticeResponse> nonReachableNodes) {
        this.nonReachableNodes = nonReachableNodes;
        super.setGraph(response.getGraph());
    }

    private List<VerticeResponse> nonReachableNodes;
}
