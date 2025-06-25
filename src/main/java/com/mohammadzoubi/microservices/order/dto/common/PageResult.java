package com.mohammadzoubi.microservices.order.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PageResult <T>{
    private T content;
    private int page;
    private long totalElements;
    private int totalPages;
}
