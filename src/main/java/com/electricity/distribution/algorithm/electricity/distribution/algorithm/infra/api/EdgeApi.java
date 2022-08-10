package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.api;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.facade.EdgeFacade;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.body.EdgeBody;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.EdgeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/edge")
@RequiredArgsConstructor
public class EdgeApi {
    private final EdgeFacade edgeFacade;

    @GetMapping(path = "/get-edge")
    public ResponseEntity<EdgeResponse> getEdgeById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(edgeFacade.getEdgeById(id));
    }

    @GetMapping(path = "/get-by-start-id")
    public ResponseEntity<List<EdgeResponse>> getAllByStartId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(edgeFacade.getEdgesBy(id, null));
    }

    @GetMapping(path = "/get-by-end-id")
    public ResponseEntity<List<EdgeResponse>> getAllByEndId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(edgeFacade.getEdgesBy(null, id));
    }

    @GetMapping(path = "/get-edge-between")
    public ResponseEntity<List<EdgeResponse>> getEdgeBetween(@RequestParam("startId") Long startId, @RequestParam("endId") Long endId) {
        return ResponseEntity.ok(edgeFacade.getEdgesBy(startId, endId));
    }

    @PostMapping("/add-edge-between")
    public ResponseEntity<EdgeResponse> addEdgeBetween(@RequestBody EdgeBody edgeBody) {
        return ResponseEntity.ok(edgeFacade.addEdgeBetween(edgeBody));
    }

}
