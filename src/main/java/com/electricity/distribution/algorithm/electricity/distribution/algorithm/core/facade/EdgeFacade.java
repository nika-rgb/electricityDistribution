package com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.facade;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.convert.EdgeMapper;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.body.EdgeBody;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Edge;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Vertice;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.repository.EdgeRepository;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.repository.VerticeRepository;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.EdgeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EdgeFacade {
    private final EdgeRepository edgeRepository;
    private final VerticeRepository verticeRepository;

    public EdgeResponse getEdgeById(Long id) {
        Optional<Edge> optionalEdge = edgeRepository.findById(id);
        Edge edge = optionalEdge.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Edge not found"));
        return EdgeMapper.getEdgeResponseFrom(edge);
    }

    public List<EdgeResponse> getEdgesBy(Long startId, Long endId) {
        if (startId == null)
            checkVertice(endId);
        else if (endId == null)
            checkVertice(endId);
        else {
            checkVertice(startId);
            checkVertice(endId);
        }
        List<Edge> edges = edgeRepository.getEdgesOf(startId, endId);
        return EdgeMapper.getEdgeResponsesFrom(edges);
    }

    private void checkVertice(Long checkId) {
        if (checkId == null)
            return;
        Optional<Vertice> verticeOf = verticeRepository.findById(checkId);
        verticeOf.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vertice not found"));
    }

    public EdgeResponse addEdgeBetween(EdgeBody edgeBody) {
        try {
            Optional<Vertice> startVertice = verticeRepository.findById(edgeBody.startVerticeId());
            Optional<Vertice> endVertice = verticeRepository.findById(edgeBody.endVerticeId());
            Vertice start = startVertice.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vertice not found id: " + edgeBody.startVerticeId()));
            Vertice end = endVertice.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vertice not found id: " + edgeBody.endVerticeId()));
            List <Edge> edges = edgeRepository.getEdgesOf(start.getId(), end.getId());
            if (!edges.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Edge already found");
            Edge newEdge = new Edge();
            newEdge.setEndVerticeId(end);
            newEdge.setStartVerticeId(start);
            edgeRepository.save(newEdge);
            return EdgeMapper.getEdgeResponseFrom(newEdge);
        } catch (InvalidDataAccessApiUsageException ignore) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid argument null");
        }
    }
}
