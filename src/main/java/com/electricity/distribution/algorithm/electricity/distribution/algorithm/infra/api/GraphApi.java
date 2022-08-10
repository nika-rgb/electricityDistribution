package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.api;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.facade.GraphFacade;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.GraphResponse;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.NonReachableNodesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/graph")
@RequiredArgsConstructor
public class GraphApi {
    private final GraphFacade graphFacade;
    private static final String FILE_NAME = "graph";
    private static final String EXTENSION = "gv";
    private static final String FULL_NAME = FILE_NAME.concat(".").concat(EXTENSION);

    private static final String HEADER_VALUE = "attachment; filename=".concat(FULL_NAME);

    @GetMapping("/get")
    public ResponseEntity<GraphResponse> getGraph() {
        return ResponseEntity.ok(graphFacade.constructGraph());
    }

    @GetMapping("/generate")
    public ResponseEntity<Void> generateGraph(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, HEADER_VALUE);
        byte[] graphVizResponse = graphFacade.generateGraphViz();
        try (ServletOutputStream out = response.getOutputStream()) {
            out.write(graphVizResponse);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't generate graph");
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/get-unreachable")
    public ResponseEntity<NonReachableNodesResponse> excludeComponents(@RequestParam("excludedVertices") Optional<Set<Long>> excludedVertices, @RequestParam("excludedEdges") Optional<Set<Long>> excludedEdges) {
        return ResponseEntity.ok(graphFacade.getNonReachableNodes(excludedVertices, excludedEdges));
    }

    @GetMapping("/generate-unreachable")
    public ResponseEntity<Void> generateForUnreachable(@RequestParam("excludedVertices") Optional<Set<Long>> excludedVertices, @RequestParam("excludedEdges") Optional<Set<Long>> excludedEdges, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, HEADER_VALUE);
        byte[] graphVizResponse = graphFacade.generateGraphVizForUnreachableNodes(excludedVertices, excludedEdges);
        try (ServletOutputStream out = response.getOutputStream()) {
            out.write(graphVizResponse);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't generate graph");
        }
        return ResponseEntity.ok().body(null);
    }

}
