package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.cdc;

import lombok.Data;

import java.util.List;
@Data
public class GraphNodeMessage {
    private Boolean isProvider;
    private List<Long> edges;
}
