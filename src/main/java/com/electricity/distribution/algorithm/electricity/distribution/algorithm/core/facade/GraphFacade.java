package com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.facade;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.algorithm.ReachableNodes;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.convert.GraphMapper;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.convert.VerticeMapper;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.GraphGenerator;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.model.Graph;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Edge;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Vertice;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.repository.EdgeRepository;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.repository.VerticeRepository;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.GraphResponse;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.NonReachableNodesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GraphFacade {
    private final VerticeRepository verticeRepository;
    private final GraphGenerator generator;
    private final EdgeRepository edgeRepository;
    private final ReachableNodes reachableNodesAlgorithm;

    public GraphResponse constructGraph() {
        List<Vertice> vertices = verticeRepository.findAll();
        return GraphMapper.getGraphResponseFrom(vertices);
    }

    public byte[] generateGraphViz() {
        List<Vertice> vertices = verticeRepository.findAll();
        Graph graph = GraphMapper.getGraphFrom(vertices, null);
        return generator.generateGraph(graph);
    }


    public NonReachableNodesResponse getNonReachableNodes(Optional<Set<Long>> excludedVerticesOptional, Optional<Set<Long>> excludedEdgesOptional) {
        // TODO might return some exception if some node is not present(non valid value)
        Set<Long> excludedVertices = excludedVerticesOptional.orElse(Collections.emptySet());
        Set<Long> excludedEdges = excludedEdgesOptional.orElse(Collections.emptySet());
        Map<Long, List<Edge>> mappedEdges = modifyEdges(excludedEdges);
        List<Vertice> changedGraph = modify(verticeRepository.findAll(), excludedVertices, mappedEdges);
        List<Vertice> unreachableNodes = reachableNodesAlgorithm.getUnreachableNodes(changedGraph);
        GraphResponse graph = GraphMapper.getGraphResponseFrom(changedGraph);
        return new NonReachableNodesResponse(graph,
                unreachableNodes.stream().map(VerticeMapper::getVertiveResponseFrom).toList());
    }

    private List<Vertice> modify(List<Vertice> startGraph, Set<Long> excludedVertices, Map<Long, List<Edge>> mappedEdges) {
        return startGraph.stream()
                .filter(elem -> !excludedVertices.contains(elem.getId()))
                .peek(elem -> {
                    List<Edge> excludedEdgesFrom = mappedEdges.get(elem.getId());
                    if (excludedEdgesFrom != null)
                        elem.getStartVerticeEdges().removeAll(excludedEdgesFrom);
                    elem.setStartVerticeEdges(elem.getStartVerticeEdges()
                            .stream()
                            .filter(vertice -> !excludedVertices.contains(vertice.getEndVerticeId().getId()))
                            .toList());
                })
                .collect(Collectors.toList());
    }

    private Map<Long, List<Edge>> modifyEdges(Set<Long> excludedEdges) {
        // TODO might return some exception if edge is not present(non valid value)
        List<Edge> edges = edgeRepository.findAllById(excludedEdges);
        Map<Long, List<Edge>> mapped = edges.stream().
                collect(Collectors.groupingBy(edge -> edge.getStartVerticeId().getId(), Collectors.toList()));
        return mapped;
    }

    public byte[] generateGraphVizForUnreachableNodes(Optional<Set<Long>> excludedVerticesOptional, Optional<Set<Long>> excludedEdgesOptional) {
        Set<Long> excludedVertices = excludedVerticesOptional.orElse(Collections.emptySet());
        Set<Long> excludedEdges = excludedEdgesOptional.orElse(Collections.emptySet());
        Map<Long, List<Edge>> mappedEdges = modifyEdges(excludedEdges);
        List<Vertice> changedGraph = modify(verticeRepository.findAll(), excludedVertices, mappedEdges);
        List<Vertice> unreachableNodes = reachableNodesAlgorithm.getUnreachableNodes(changedGraph);
        Graph graph = GraphMapper.getGraphFrom(changedGraph, unreachableNodes);
        return generator.generateGraph(graph);
    }


}
