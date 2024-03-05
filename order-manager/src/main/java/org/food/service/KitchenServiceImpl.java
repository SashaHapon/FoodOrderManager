package org.food.service;

import lombok.RequiredArgsConstructor;
import org.food.api.repository.OrderRepository;
import org.food.api.service.KitchenService;
import org.food.service.mapper.ItemMapper;
import org.food.dto.KitchenResponse;
import org.food.dto.OrderMessage;
import org.food.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class KitchenServiceImpl implements KitchenService {

    private final OrderRepository orderRepository;

    private final KafkaTemplate<Object, Object> template;

    private final ItemMapper mapper;
    @Value("${order-manager.kafka.topic.out}")
    private String topicOut;

    @Override
    public void sendToKitchen(Order order) {
        List<OrderMessage.Item> itemList = mapper.itemMapper(order.getMeals());
        OrderMessage orderMessage = new OrderMessage(order.getId(), itemList);
        template.send(topicOut, order.getId(), orderMessage);
    }

    @KafkaListener(topics = "${order-manager.kafka.topic.in}")
    public void listen(KitchenResponse response) {
        Order order = orderRepository.findById(response.getOrderId());
        order.setCookingTimeSum(response.getCookingTime());
        orderRepository.update(order);
    }
}
