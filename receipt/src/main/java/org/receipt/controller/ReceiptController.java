package org.receipt.controller;

import org.receipt.api.ReceiptService;
import org.receipt.dto.ReceiptRequest;
import org.receipt.dto.ReceiptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiptController {
    @Autowired
    ReceiptService receiptService;

    @GetMapping("/order/receipt/")
    ReceiptResponse print(ReceiptRequest request) {
        return receiptService.print(request);
    }

}
