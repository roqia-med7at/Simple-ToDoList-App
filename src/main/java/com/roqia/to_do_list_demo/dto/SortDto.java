package com.roqia.to_do_list_demo.dto;

import org.springframework.security.core.parameters.P;

public class SortDto {
    private String orderDirection;
    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }


}
