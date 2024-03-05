package org.kitchen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.kitchen.dto.OrderMessage;
import org.kitchen.dto.KitchenResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KitchenService {

    private final KafkaTemplate<Integer, String> template;

    private final ObjectMapper objectMapper;

    private Integer time;

    @KafkaListener(topics = "order-message")
    public void listen(OrderMessage kitchenRequest) {
        Optional<Integer> integer = kitchenRequest.getItemList().stream()
                .map(OrderMessage.Item::getCookingTime)
                .max(Comparator.naturalOrder());
        integer.ifPresent(value -> time = value);
        KitchenResponse response = new KitchenResponse(kitchenRequest.getOrderId(), time);
        String s;
        try {
             s = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        template.send("kitchen-response", s);
    }
}
