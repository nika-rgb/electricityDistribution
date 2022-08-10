package com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.algorithm;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Edge;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Vertice;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReachableNodes {

    private void doDfs(Vertice current, Set<Long> visited) {
        visited.add(current.getId());
        List<Edge> edges = current.getStartVerticeEdges();
        edges.forEach(edge -> {
            if (!visited.contains(edge.getEndVerticeId().getId()))
                doDfs(edge.getEndVerticeId(), visited);
        });
    }

    public List<Vertice> getUnreachableNodes(List<Vertice> graph) {
        if (graph.isEmpty())
            return Collections.emptyList();
        List<Vertice> providerVertices = graph.stream().filter(Vertice::isProvider).toList();
        Set<Long> visited = new HashSet<>();
        providerVertices.forEach(vertice -> doDfs(vertice, visited));
        return graph.stream().filter(vertice -> !visited.contains(vertice.getId())).toList();
    }


}
