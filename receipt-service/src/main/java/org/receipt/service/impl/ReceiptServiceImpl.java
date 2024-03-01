package org.receipt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.receipt.model.Receipt;
import org.receipt.service.ReceiptService;
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
    public Receipt print(Receipt receipt) {
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("localDateTimes", receipt.getLocalDateTimes());
        context.setVariable("orderId", receipt.getOrderId());
        context.setVariable("items", receipt.getItems());
        context.setVariable("orderTotalCost", receipt.getOrderTotalCost());

        String formattedText = templateEngine.process("receipt_template", context);
        log.info(formattedText);
        receipt.setText(formattedText);
        return receipt;
    }
}
