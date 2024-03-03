package org.food.service;

import lombok.RequiredArgsConstructor;
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
public abstract class KitchenServiceImpl implements KitchenService {

    private final OrderService orderService;

    private final KafkaTemplate<Object, Object> template;

    private final ItemMapper mapper;
    @Override
    public void sendToKitchen(Order order) {
        List<OrderMessage.Item> itemList = mapper.itemMapper(order.getMeals());
        OrderMessage orderMessage = new OrderMessage(order.getId(), itemList);
        template.send("kitchen", order.getId(), order);
    }

    @KafkaListener(topics = "kitchenResponse")
    public void listen(KitchenResponse kitchenResponse) {
        OrderDto orderDto = orderService.getOrder(kitchenResponse.getOrderId());
        orderDto.setCookingTimeSum(kitchenResponse.getCookingTime());
        orderService.updateOrder(orderDto);
    }
}
