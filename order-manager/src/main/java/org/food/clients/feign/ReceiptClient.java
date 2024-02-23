package org.food.clients.feign;

import org.food.clients.feign.dto.ReceiptRequest;
import org.food.clients.feign.dto.ReceiptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "receipt", url = "https://localhost:8090/")
public interface ReceiptClient {
    @GetMapping("/order/receipt/")
    ReceiptResponse print(@RequestBody ReceiptRequest request);
}