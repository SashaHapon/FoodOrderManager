package org.receipt.api;

import org.receipt.dto.OrderDto;
import org.receipt.dto.ReceiptRequest;
import org.receipt.dto.ReceiptResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService {
    OrderDto print(OrderDto orderDto);
}
