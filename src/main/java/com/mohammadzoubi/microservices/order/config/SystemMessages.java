package com.mohammadzoubi.microservices.order.config;

public class SystemMessages {
    //Bad Requests Messages
    public static final String CUSTOMER_ID_MAX_LENGTH_MESSAGE = "Customer id max 10 digits";
    public static final String ORDER_EXCEED_ITEMS = "Order can't have more than "
            + SystemConfig.DEFAULT_ITEMS_NUMBER_PER_ORDER
            + " items";

}
