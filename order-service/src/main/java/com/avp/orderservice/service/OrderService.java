package com.avp.orderservice.service;

import com.avp.orderservice.dto.OrderRequest;

public interface OrderService {

    String placeOrder(OrderRequest orderRequest);
}
