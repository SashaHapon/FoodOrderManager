package org.food.clients.feign.dto;

import org.food.model.Meal;
import org.food.model.Order;
import org.modelmapper.PropertyMap;

public class ItemMappingProperties extends PropertyMap<Meal, ReceiptRequest.Item> {
    @Override
    protected void configure() {
        map(source.getName(),destination.getName());
        map(source.getPrice(),destination.getCost());
        map(source.getTime(),destination.getCount());
    }
}
