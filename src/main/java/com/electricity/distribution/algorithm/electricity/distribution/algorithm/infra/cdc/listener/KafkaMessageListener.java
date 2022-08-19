package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.cdc.listener;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.cdc.GraphNodeMessage;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Edge;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.model.Vertice;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.repository.EdgeRepository;
import com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.repository.VerticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final VerticeRepository verticeRepository;
    private final EdgeRepository edgeRepository;

    private final String TOPIC_NAME = "electricityDistributionTopic";

    private final String GROUP_ID = "group-electricityDatabase";

    @KafkaListener(topics = {TOPIC_NAME}, groupId = GROUP_ID)
    public void listenToDataChange(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload GraphNodeMessage message) {
        log.info("Message key {}", key);
        log.info("Message payload {}", message.toString());
        boolean isProvider = message.getIsProvider();
        List<Long> edges = message.getEdges();
        Vertice vertice = new Vertice();
        vertice.setProvider(isProvider);
        final Vertice addedVertice = verticeRepository.save(vertice);
        edges.forEach(endPoint -> {
            Optional<Vertice> endVerticeOptional = verticeRepository.findById(endPoint);
            if (!endPoint.equals(addedVertice.getId())) {
                try {
                    Vertice endVertice = endVerticeOptional.orElseThrow(Exception::new); // might change // produce message and say that something unexpected happened and also should fail message
                    Edge edge = new Edge();
                    edge.setStartVerticeId(addedVertice);
                    edge.setEndVerticeId(endVertice);
                    edgeRepository.save(edge);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
