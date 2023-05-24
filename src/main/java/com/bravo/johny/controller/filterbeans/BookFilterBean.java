package com.bravo.johny.controller.filterbeans;


import lombok.Data;

@Data
public class BookFilterBean {

    private String author;
    private String title;
    private String genre;
    private int offset;
    private int limit;
}
