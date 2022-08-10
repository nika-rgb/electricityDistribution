package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response;

import java.util.List;

public record GraphNodeResponse(VerticeResponse verticeResponse, List<EdgeResponse> edges) {

}
