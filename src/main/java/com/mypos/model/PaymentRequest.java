package com.mypos.model;

import java.util.List;

public class PaymentRequest {

    CustomerRequest customerRequest;
    List<CartItemRequest> cartItems;

    public PaymentRequest(CustomerRequest customerRequest, List<CartItemRequest> cartItems) {
        this.customerRequest = customerRequest;
        this.cartItems = cartItems;
    }



    public CustomerRequest getCustomerRequest() {
        return customerRequest;
    }

    public void setCustomerRequest(CustomerRequest customerRequest) {
        this.customerRequest = customerRequest;
    }

    public List<CartItemRequest> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }
}
