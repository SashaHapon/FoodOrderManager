package org.receipt.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class Receipt {
    private Integer orderId;
    private List<Item> items;
    private BigDecimal orderTotalCost;
    private final String localDateTimes;
    private String text;

    public Receipt(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        localDateTimes = localDateTime.format(formatter);
    }
    @Data
    public static class Item {
        private String name;
        private Integer count;
        private BigDecimal cost;
    }
}
