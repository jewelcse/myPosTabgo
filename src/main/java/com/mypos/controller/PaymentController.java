package com.mypos.controller;

import com.mypos.model.CartItemRequest;
import com.mypos.model.PaymentRequest;
import com.mypos.myposcheckout.ipc.Cart;
import com.mypos.myposcheckout.ipc.CartItem;
import com.mypos.myposcheckout.ipc.Customer;
import com.mypos.myposcheckout.ipc.request.Purchase;
import com.mypos.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping("/")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi Im online now");
    }

    @RequestMapping("/ok")
    public ResponseEntity<String> ok(){
        return ResponseEntity.ok("OK from mypos");
    }

    @RequestMapping("/cancel")
    public ResponseEntity<String> cancel(){
        return ResponseEntity.ok("Cancel from mypos");
    }

    @RequestMapping("/notify")
    public ResponseEntity<String> notifyFrommypos(){
        return ResponseEntity.ok("Notify from mypos");
    }


    @RequestMapping(value = "/api/payment/confirm", method = RequestMethod.POST)
    public ResponseEntity<Purchase> confirmPayment(@RequestBody PaymentRequest request) throws Exception {

        var customerRequest=  request.getCustomerRequest();
        var cartItems = request.getCartItems();

        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());
        customer.setCountry(customerRequest.getCountry());
        customer.setAddress(customerRequest.getAddress());
        customer.setCity(customerRequest.getCity());
        customer.setZip(customerRequest.getZip());

        // Create Cart
        Cart cart = new Cart();
        for (CartItemRequest itemRequest : cartItems) {
            cart.addItem(new CartItem(itemRequest.getItemName(), itemRequest.getPrice(), itemRequest.getQuantity()));
        }

        return new ResponseEntity<>(paymentService.confirmPayment(customer, cart), HttpStatus.CREATED);
    }

}
