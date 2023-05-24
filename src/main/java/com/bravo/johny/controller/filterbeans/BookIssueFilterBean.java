package com.bravo.johny.controller.filterbeans;

import lombok.Data;

@Data
public class BookIssueFilterBean {

    private int bookId;
    private String username;
    private int offset;
    private int limit;
}
