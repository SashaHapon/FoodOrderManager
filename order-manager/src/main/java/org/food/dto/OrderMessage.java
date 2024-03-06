package org.food.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderMessage {
    private Integer orderId;
    private List<Item> itemList;

    public OrderMessage(Integer orderId, List<Item> itemList) {
        this.orderId = orderId;
        this.itemList = itemList;
    }

    @Data
    public static class Item {
        String name;
        Integer cookingTime;
    }
}
