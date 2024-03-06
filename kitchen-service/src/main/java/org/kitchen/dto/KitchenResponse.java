package org.kitchen.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class KitchenResponse {
    private final Integer orderId;
    private final Integer cookingTime;
}
