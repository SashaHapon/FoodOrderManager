package org.food.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.food.api.repository.OrderRepository;
import org.food.api.service.KitchenService;
import org.food.api.service.OrderService;
import org.food.clients.kafka.service.ItemMapper;
import org.food.dto.KitchenResponse;
import org.food.dto.OrderDto;
import org.food.dto.OrderMessage;
import org.food.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final OrderRepository orderRepository;

    private final KafkaTemplate<Integer, String> template;

    private final ItemMapper mapper;

    private final ObjectMapper objectMapper;
    @Override
    public void sendToKitchen(Order order) {
        List<OrderMessage.Item> itemList = mapper.itemMapper(order.getMeals());
        OrderMessage orderMessage = new OrderMessage(order.getId(), itemList);
        String mappedOrder;
        try {
            mappedOrder = objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        template.send("order-message", order.getId(), mappedOrder);
    }

    @KafkaListener(topics = "kitchen-response")
    public void listen(String kitchenResponse) {
        KitchenResponse response;
        try {
             response = objectMapper.readValue(kitchenResponse, KitchenResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Order order = orderRepository.findById(response.getOrderId());
        order.setCookingTimeSum(response.getCookingTime());
        orderRepository.update(order);
    }
}
