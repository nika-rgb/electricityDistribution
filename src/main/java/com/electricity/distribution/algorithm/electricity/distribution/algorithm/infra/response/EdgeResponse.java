package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response;

import lombok.Data;


@Data
public class EdgeResponse {
    private Long id;
    private Long startVerticeId;
    private Long endVerticeId;
}
