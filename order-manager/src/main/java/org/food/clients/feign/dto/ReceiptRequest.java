package org.food.clients.feign.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReceiptRequest {
    private Integer orderId;
    private List<MealItemRequest> items;
    private BigDecimal orderTotalCost;

    @Data
    public static class MealItemRequest {
        private String name;
        private Integer count;
        private BigDecimal cost;
    }
}


