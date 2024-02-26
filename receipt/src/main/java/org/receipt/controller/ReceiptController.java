package org.receipt.controller;

import org.modelmapper.ModelMapper;
import org.receipt.api.ReceiptService;
import org.receipt.dto.ReceiptDto;
import org.receipt.dto.ReceiptRequest;
import org.receipt.dto.ReceiptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiptController {
    @Autowired
    ReceiptService receiptService;

    @Autowired
    ModelMapper mapper;

    @PostMapping("/receipt")
    ReceiptResponse print(@RequestBody ReceiptRequest request) {
        ReceiptDto receiptDto = mapper.map(request, ReceiptDto.class);
        ReceiptDto rerutnedReceiptDto = receiptService.print(receiptDto);
        return mapper.map(rerutnedReceiptDto, ReceiptResponse.class);
    }

}
