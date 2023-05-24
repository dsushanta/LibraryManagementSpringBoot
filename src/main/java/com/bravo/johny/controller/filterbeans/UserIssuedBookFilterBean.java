package com.bravo.johny.controller.filterbeans;

import lombok.Data;

@Data
public class UserIssuedBookFilterBean {

    private int bookId;
    private int offset;
    private int limit;
}
