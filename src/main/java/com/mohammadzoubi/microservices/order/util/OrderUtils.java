package com.mohammadzoubi.microservices.order.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderUtils {

    public static String generateOrderNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // e.g., 20250616
        int randomPart = new Random().nextInt(9999);
        return "ORD-" + datePart + "-" + String.format("%04d", randomPart);
    }
}
