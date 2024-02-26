package org.food.clients.feign.dto;

import org.food.model.Order;
import org.modelmapper.PropertyMap;

public class OrderMappingProperties extends PropertyMap<Order, ReceiptRequest> {

        @Override
        protected void configure() {
            map(source.getId(),destination.getOrderId());
            map(source.getMeals(), destination.getItems());
            map(source.getCookingTimeSum(), destination.getOrderTotalCost());
        }
}
