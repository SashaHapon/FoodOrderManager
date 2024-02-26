package org.receipt.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Integer orderId;
    private List<ReceiptRequest.Item> items;
    private BigDecimal orderTotalCost;
    private final LocalDateTime localDateTime;

    public OrderDto(){
        localDateTime = LocalDateTime.now();
    }
    @Data
    public static class Item {
        private String name;
        private Integer count;
        private BigDecimal cost;
    }
}
