package org.receipt.payload;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReceiptResponse {
    private Integer orderId;
    private List<MealItemResponse> items;
    private BigDecimal orderTotalCost;

    @Data
    public static class MealItemResponse {
        private String name;
        private Integer count;
        private BigDecimal cost;
    }
}

