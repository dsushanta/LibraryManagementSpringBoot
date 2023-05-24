package com.bravo.johny.controller.filterbeans;

import lombok.Data;

@Data
public class UserFilterBean {

    private String lastName;
    private int offset;
    private int limit;
}
