package service;

import api.ReceiptService;
import org.food.clients.feign.dto.ReceiptResponse;
import org.food.


public class ReceiptServiceImpl implements ReceiptService {
    @Override
    public ReceiptResponse print(ReceiptRequest request) {
        System.out.println("Order ID:" + request.getId() + "\n" +
                "Account ID:" + request.getAccount().getId() + "\n" +
                "Account Name:" + request.getAccount().getName() + "\n" +
                "Account Phone:" + request.getAccount().getPhoneNumber() + "\n" +
                "Meals:" + request.getMeals() + "\n" +
                "Cooking Time:" + request.getCookingTimeSum() + "\n" +
                "Price:" + request.getOrderSum() + "\n");
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setId(request.getId());
        return receiptResponse;
    }
}
