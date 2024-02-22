package org.receipt.api;

import org.receipt.dto.ReceiptRequest;
import org.receipt.dto.ReceiptResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService {
    ReceiptResponse print(ReceiptRequest request);
}
