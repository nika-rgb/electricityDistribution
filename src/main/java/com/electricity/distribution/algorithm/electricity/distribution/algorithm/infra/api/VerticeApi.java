package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.api;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.facade.VerticeFacade;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.body.VerticeBody;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.VerticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/vertice")
@RequiredArgsConstructor
public class VerticeApi {
    private final VerticeFacade verticeFacade;

    @GetMapping(path = "/get-vertice")
    public ResponseEntity<VerticeResponse> getVerticeById(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(verticeFacade.getVerticeById(id));
    }

    // TODO Delete written for testing purposes
    @PostMapping("/add-new-vertice")
    public ResponseEntity<VerticeResponse> addVertice(@RequestBody VerticeBody verticeBody) {
        return ResponseEntity.ok(verticeFacade.addNewVertice(verticeBody));
    }

}
