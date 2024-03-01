package org.receipt.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.receipt.controller.dto.ReceiptRequest;
import org.receipt.controller.dto.ReceiptResponse;
import org.receipt.model.Receipt;
import org.receipt.service.ReceiptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    private final ModelMapper mapper;

    @PostMapping("/receipt")
    ReceiptResponse print(@RequestBody ReceiptRequest request) {
        Receipt receipt = mapper.map(request, Receipt.class);
        Receipt printerdReceipt = receiptService.print(receipt);
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setReceipt(printerdReceipt.getText());
        return receiptResponse;
    }
}
