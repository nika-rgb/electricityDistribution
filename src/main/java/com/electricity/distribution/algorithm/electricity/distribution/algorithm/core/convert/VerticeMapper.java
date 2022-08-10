package com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.convert;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Vertice;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.VerticeResponse;

public class VerticeMapper {

    public static VerticeResponse getVertiveResponseFrom(Vertice vertice) {
        VerticeResponse response = new VerticeResponse();
        response.setId(vertice.getId());
        response.setProvider(vertice.isProvider());
        return response;
    }

}
