package org.receipt.service;

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
public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger logger
            = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    @Override
    public String print(Receipt receipt) {

//        logger.info("============PAYMENT CHECK==============");
//        logger.info("Date: " + receiptDto.getLocalDateTimes());
//        logger.info("Printing check for Order ID: " + receiptDto.getOrderId());
//        logger.info("Items:");
//        for (MealItemRequest mealItemRequest : receiptDto.getItems()) {
//            logger.info("- " + mealItemRequest.getName() + ": $" + mealItemRequest.getCost());
//        }
//        logger.info("Total Cost: $" + receiptDto.getOrderTotalCost());
//        logger.info("----------------------------------------");
//        logger.info("");

        TemplateEngine templateEngine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(resolver);

        // Создайте контекст с данными для шаблона
        Context context = new Context();
        context.setVariable("localDateTimes", receipt.getLocalDateTimes());
        context.setVariable("orderId", receipt.getOrderId());
        context.setVariable("items", receipt.getItems());
        context.setVariable("orderTotalCost", receipt.getOrderTotalCost());

        // Обработайте шаблон с помощью Thymeleaf
        String formattedText = templateEngine.process("receipt_template", context);

        // Выведите отформатированный текст в консоль
        logger.info(formattedText);

        return formattedText;
    }
}
