package org.receipt.api;

import org.receipt.model.Receipt;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService {
    String print(Receipt receipt);
}
