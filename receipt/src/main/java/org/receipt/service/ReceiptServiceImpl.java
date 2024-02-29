package org.receipt.service;

import lombok.RequiredArgsConstructor;
import org.receipt.api.ReceiptService;
import org.receipt.model.Receipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    @Override
    public String print(Receipt receipt) {

        TemplateEngine templateEngine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("localDateTimes", receipt.getLocalDateTimes());
        context.setVariable("orderId", receipt.getOrderId());
        context.setVariable("items", receipt.getItems());
        context.setVariable("orderTotalCost", receipt.getOrderTotalCost());

        String formattedText = templateEngine.process("receipt_template", context);
        logger.info(formattedText);

        return formattedText;
    }
}
