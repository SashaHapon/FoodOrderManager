package api;

import org.food.clients.feign.dto.ReceiptRequest;
import org.food.clients.feign.dto.ReceiptResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService {
    ReceiptResponse print(ReceiptRequest request);
}
