package org.kitchen.service;

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

    private final KafkaTemplate<Object, Object> template;

    private Integer time;

    @KafkaListener(topics = "order-message")
    public void listen(OrderMessage kitchenRequest) {
        Optional<Integer> integer = kitchenRequest.getItemList().stream()
                .map(OrderMessage.Item::getCookingTime)
                .max(Comparator.naturalOrder());
        integer.ifPresent(value -> time = value);

        template.send("kitchen-response", new KitchenResponse(kitchenRequest.getOrderId(), time));
    }
}
