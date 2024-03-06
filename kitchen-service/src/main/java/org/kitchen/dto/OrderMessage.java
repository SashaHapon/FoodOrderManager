package org.kitchen.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderMessage {
    Integer orderId;
    List<Item> itemList;

    @Data
    public static class Item {
        String name;
        Integer cookingTime;
    }
}
