package org.food.clients.feign.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReceiptRequest {
    private Integer orderId;
    private List<Item> items;
    private BigDecimal orderTotalCost;

    @Data
    public static class Item {
        private String name;
        private Integer count;
        private BigDecimal cost;
    }
}


