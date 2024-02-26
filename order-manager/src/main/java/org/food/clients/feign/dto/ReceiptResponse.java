package org.food.clients.feign.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import org.food.clients.feign.dto.ReceiptRequest.Item;

@Data
public class ReceiptResponse {
    private Integer orderId;
    private List<Item> items;
    private BigDecimal orderTotalCost;
}

