package org.receipt.dto;

import lombok.Data;
import org.receipt.dto.ReceiptRequest.Item;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReceiptResponse {
    private Integer orderId;
    private List<Item> items;
    private BigDecimal orderTotalCost;
}

