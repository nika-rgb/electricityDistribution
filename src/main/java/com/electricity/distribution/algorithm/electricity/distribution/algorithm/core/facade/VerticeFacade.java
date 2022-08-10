package com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.facade;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.convert.VerticeMapper;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.body.VerticeBody;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Vertice;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.repository.VerticeRepository;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.response.VerticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VerticeFacade {
    private final VerticeRepository verticeRepository;

    public VerticeResponse getVerticeById(Long id) {
        Optional<Vertice> optionalVertice = verticeRepository.findById(id);
        Vertice vertice = optionalVertice.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vertice not found"));
        return VerticeMapper.getVertiveResponseFrom(vertice);
    }

    // TODO Delete written for testing purposes
    public VerticeResponse addNewVertice(VerticeBody verticeBody) {
        Vertice vertice = new Vertice();
        vertice.setProvider(verticeBody.isProvider());
        Vertice addedVertice = verticeRepository.save(vertice);
        return VerticeMapper.getVertiveResponseFrom(addedVertice);
    }
}
