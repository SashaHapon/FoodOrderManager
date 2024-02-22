package controller;

import api.ReceiptService;
import org.food.clients.feign.ReceiptClient;
import org.food.clients.feign.dto.ReceiptRequest;
import org.food.clients.feign.dto.ReceiptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiptController {
    @Autowired
    ReceiptClient receiptClient;
    @Autowired
    ReceiptService receiptService;

    ReceiptResponse print(ReceiptRequest request){
       return receiptService.print(request);
    }
    
}
