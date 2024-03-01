package org.receipt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.receipt.api.ReceiptService;
import org.receipt.model.Receipt;
import org.receipt.payload.ReceiptResponse;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final TemplateEngine templateEngine;

    @Override
    public ReceiptResponse print(Receipt receipt) {
        StringTemplateResolver resolver = new StringTemplateResolver();
        ReceiptResponse response = new ReceiptResponse();
        resolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("localDateTimes", receipt.getLocalDateTimes());
        context.setVariable("orderId", receipt.getOrderId());
        context.setVariable("items", receipt.getItems());
        context.setVariable("orderTotalCost", receipt.getOrderTotalCost());

        String formattedText = templateEngine.process("receipt_template", context);
        log.info(formattedText);
        response.setReceipt(formattedText);
        return response;
    }
}
