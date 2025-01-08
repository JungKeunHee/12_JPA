package com.ohgiraffers.mapping.section02.embedded;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookRegistDTO {

    private String bookTitle;
    private String author;
    private String publisher;
    private LocalDate createDate;
    private int regularPrice;
    private double discountRate;

}
