package org.receipt.service;

import org.receipt.model.Receipt;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService {
    Receipt print(Receipt receipt);
}
