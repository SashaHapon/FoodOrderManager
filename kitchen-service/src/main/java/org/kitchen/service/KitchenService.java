package org.kitchen.service;

import lombok.RequiredArgsConstructor;
import org.kitchen.config.KitchenProperties;
import org.kitchen.dto.KitchenResponse;
import org.kitchen.dto.OrderMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KitchenService {
    private final KafkaTemplate<Object, Object> template;
    private final KitchenProperties kitchenProperties;

    @KafkaListener(topics = "${kitchen.kafka.topic.in}")
    public void listen(OrderMessage kitchenRequest) {
        Optional<Integer> integer = kitchenRequest.getItemList().stream()
                .map(OrderMessage.Item::getCookingTime)
                .max(Comparator.naturalOrder());
        KitchenResponse response = new KitchenResponse(kitchenRequest.getOrderId(), integer.orElse(null));
        template.send(kitchenProperties.getKafka().getTopic().getOut(), response);
    }
}
