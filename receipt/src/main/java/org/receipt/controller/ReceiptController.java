package org.receipt.controller;

import org.modelmapper.ModelMapper;
import org.receipt.api.ReceiptService;
import org.receipt.dto.OrderDto;
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
        OrderDto orderDto = mapper.map(request, OrderDto.class);
        OrderDto rerutnedOrderDto = receiptService.print(orderDto);
        return mapper.map(rerutnedOrderDto, ReceiptResponse.class);
    }

}
