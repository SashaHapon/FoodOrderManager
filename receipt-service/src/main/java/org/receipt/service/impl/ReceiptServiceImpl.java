package org.receipt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.receipt.model.Receipt;
import org.receipt.service.ReceiptService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private final SpringTemplateEngine springTemplateEngine;

    @Override
    public Receipt print(Receipt receipt) {
        Context context = new Context();
        context.setVariable("localDateTimes", receipt.getLocalDateTimes());
        context.setVariable("orderId", receipt.getOrderId());
        context.setVariable("items", receipt.getItems());
        context.setVariable("orderTotalCost", receipt.getOrderTotalCost());

        String formattedText = springTemplateEngine.process("receipt_template", context);
        log.info(formattedText);
        receipt.setText(formattedText);
        return receipt;
    }
}
