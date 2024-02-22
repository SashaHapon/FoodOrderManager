package org.food.clients.feign;

import org.food.clients.feign.dto.ReceiptRequest;
import org.food.clients.feign.dto.ReceiptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "receipt", url = "http://localhost:8080")
public interface ReceiptClient {
    @GetMapping("/orders/remote_receipt/{id}")
    ReceiptResponse print(ReceiptRequest request);
}