package org.receipt.api;

import org.receipt.dto.ReceiptDto;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService {
    ReceiptDto print(ReceiptDto receiptDto);
}
