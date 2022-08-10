package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.enums.GraphVizAttributes;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GraphNode {
    private Map<GraphVizAttributes, Object> attributes;

    public GraphNode() {
        attributes = new HashMap<>();
    }

    public void addAttribute(GraphVizAttributes attribute, Object value) {
        attributes.put(attribute, value);
    }

    public void removeAttribute(GraphVizAttributes attribute) {
        attributes.remove(attribute);
    }

    public Object getAttribute(GraphVizAttributes attribute) {
        return attributes.get(attribute);
    }
}
