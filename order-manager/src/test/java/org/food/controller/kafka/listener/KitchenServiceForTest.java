package org.food.controller.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@Profile("h2")
@TestComponent
@RequiredArgsConstructor
public class KitchenServiceForTest {

    private final KafkaTemplate<Integer, String> template;

    private final ObjectMapper objectMapper;

    private Integer time = 15;

    @KafkaListener(topics = "order-message", groupId = "my-group-id")
    public void listen(String kitchenRequest) throws JsonProcessingException {
        OrderMessage orderMessage = objectMapper.convertValue(kitchenRequest, OrderMessage.class);
        KitchenResponse response = new KitchenResponse(orderMessage.getOrderId(), time);
        String re = objectMapper.writeValueAsString(response);
        template.send("kitchen-response", re);
    }
}
