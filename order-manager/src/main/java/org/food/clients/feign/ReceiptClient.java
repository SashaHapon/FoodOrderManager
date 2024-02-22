package org.food.clients.feign;

import org.food.clients.feign.dto.ReceiptRequest;
import org.food.clients.feign.dto.ReceiptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "receipt")
public interface ReceiptClient {
    @GetMapping("/order/receipt/")
    ReceiptResponse print(ReceiptRequest request);
}