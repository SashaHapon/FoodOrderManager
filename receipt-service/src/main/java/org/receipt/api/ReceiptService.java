package org.receipt.api;

import org.receipt.model.Receipt;
import org.receipt.payload.ReceiptResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService {
    ReceiptResponse print(Receipt receipt);
}
