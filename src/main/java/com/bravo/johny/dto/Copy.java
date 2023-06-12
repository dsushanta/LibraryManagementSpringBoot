package com.bravo.johny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Copy extends RepresentationModel<Copy> implements Serializable {

    private int copyId;
    private int bookId;
    private boolean isIssued;
}
