package org.receipt.service;

import org.receipt.api.ReceiptService;
import org.receipt.dto.OrderDto;
import org.receipt.dto.ReceiptRequest.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger logger
            = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    @Override
    public OrderDto print(OrderDto orderDto) {


        //todo date
        logger.info("Date: " + orderDto.getLocalDateTime());
        logger.info("Printing check for Order ID: " + orderDto.getOrderId());
        logger.info("Items:");
        for (Item item : orderDto.getItems()) {
            logger.info("- " + item.getName() + ": $" + item.getCost());
        }
        logger.info("Total Cost: $" + orderDto.getOrderTotalCost());
        return orderDto;
    }
}
