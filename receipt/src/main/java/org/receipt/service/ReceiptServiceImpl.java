package org.receipt.service;

import org.receipt.api.ReceiptService;
import org.receipt.dto.ReceiptDto;
import org.receipt.dto.ReceiptRequest.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger logger
            = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    @Override
    public ReceiptDto print(ReceiptDto receiptDto) {

        logger.info("============PAYMENT CHECK==============");
        logger.info("Date: " + receiptDto.getLocalDateTimes());
        logger.info("Printing check for Order ID: " + receiptDto.getOrderId());
        logger.info("Items:");
        for (Item item : receiptDto.getItems()) {
            logger.info("- " + item.getName() + ": $" + item.getCost());
        }
        logger.info("Total Cost: $" + receiptDto.getOrderTotalCost());
        logger.info("----------------------------------------");
        logger.info("");
        return receiptDto;
    }
}
