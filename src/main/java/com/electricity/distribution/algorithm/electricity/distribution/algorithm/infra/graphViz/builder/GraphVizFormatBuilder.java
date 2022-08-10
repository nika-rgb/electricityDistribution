package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.builder;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.enums.GraphVizAttributes;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model.GraphEdge;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model.GraphNode;

import java.util.Map;
import java.util.stream.Collectors;

public class GraphVizFormatBuilder {
    private static final String DIGRAPH = "digraph";
    private static final String GRAPH_NAME = "graph";
    private static final String EDGE_DELIMITER = "->";
    private static final Character START_BRACKET = '{';
    private static final Character END_BRACKET = '}';
    private static final Character START_BRACK = '[';
    private static final Character END_BRACK = ']';
    private static final Character EQUALS_SIGN = '=';

    private static final String ATTRIBUTE_DELIMITER = " ,";
    private static final Character NEW_LINE = '\n';
    private static final String DIGRAPH_TEMPLATE = DIGRAPH + " \"{GRAPH_NAME}\" " + START_BRACKET + "\n";

    private final StringBuilder builder;


    public GraphVizFormatBuilder() {
        builder = new StringBuilder();
        String template = DIGRAPH_TEMPLATE.replaceAll("\\{GRAPH_NAME\\}", GRAPH_NAME);
        builder.append(template);
    }

    public GraphVizFormatBuilder addNode(GraphNode node) {
        Map<GraphVizAttributes, Object> attributes = node.getAttributes();
        Object label = attributes.get(GraphVizAttributes.LABEL);
        builder.append(label.toString())
                .append(" ")
                .append(START_BRACK);
        String nodeDescription = attributes.entrySet().stream()
                .map(entry -> entry.getKey().getAttributeName() + EQUALS_SIGN + entry.getValue().toString())
                .collect(Collectors.joining(ATTRIBUTE_DELIMITER));
        builder.append(nodeDescription);
        builder.append(END_BRACK);
        builder.append(NEW_LINE);
        return this;
    }

    public GraphVizFormatBuilder addEdge(GraphEdge edge) {
        GraphNode start = edge.getStart();
        GraphNode end = edge.getEnd();
        Object startLabel = start.getAttribute(GraphVizAttributes.LABEL);
        Object endLabel = end.getAttribute(GraphVizAttributes.LABEL);
        builder.append(startLabel)
                .append(EDGE_DELIMITER)
                .append(endLabel)
                .append(NEW_LINE);
        return this;
    }


    public String build() {
        builder.append(END_BRACKET);
        return builder.toString();
    }

}
