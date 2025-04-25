package com.mypos.service;


import com.mypos.config.MyPosConfig;
import com.mypos.myposcheckout.ipc.Cart;
import com.mypos.myposcheckout.ipc.Customer;
import com.mypos.myposcheckout.ipc.enumerable.CardTokenRequest;
import com.mypos.myposcheckout.ipc.enumerable.Currency;
import com.mypos.myposcheckout.ipc.enumerable.PaymentParametersRequired;
import com.mypos.myposcheckout.ipc.request.Purchase;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.UUID;

@Service
public class PaymentService {

    private MyPosConfig config;

    public PaymentService(MyPosConfig config) {
        this.config = config;
    }

    public Purchase confirmPayment(Customer customer, Cart cart) throws Exception {
        Purchase purchase = new Purchase(config);
        purchase.setOrderId(UUID.randomUUID().toString()); // Some unique ID for the order
        purchase.setCurrency(Currency.EUR);
        purchase.setNote("Some note");
        purchase.setCardTokenRequest(CardTokenRequest.DO_NOT_REQUEST_TOKEN);
        purchase.setParametersRequired(PaymentParametersRequired.FULL_REQUEST);
        purchase.setCustomer(customer);
        purchase.setCart(cart);


        purchase.setCancelUrl(new URL("https://17b9-2400-3240-7006-331f-b972-a8f5-94d5-5b0.ngrok-free.app/cancel"));
        purchase.setSuccessUrl(new URL("https://17b9-2400-3240-7006-331f-b972-a8f5-94d5-5b0.ngrok-free.app/ok"));
        purchase.setNotifyUrl(new URL("https://17b9-2400-3240-7006-331f-b972-a8f5-94d5-5b0.ngrok-free.app/notify"));

        purchase.process();

        return purchase;
    }
}
