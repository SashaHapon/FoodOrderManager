package org.receipt.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.receipt.api.ReceiptService;
import org.receipt.model.Receipt;
import org.receipt.payload.ReceiptRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    private final ModelMapper mapper;

    @PostMapping("/receipt")
    String print(@RequestBody ReceiptRequest request) {
        Receipt receipt = mapper.map(request, Receipt.class);
        return receiptService.print(receipt);
    }

}
