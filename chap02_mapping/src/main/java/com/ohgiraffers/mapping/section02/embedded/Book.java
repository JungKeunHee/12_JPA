package com.ohgiraffers.mapping.section02.embedded;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_book")
public class Book {

    @Id
    @Column(name = "book_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookNo;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "author")
    private String author; // 저자

    @Column(name = "pulisher")
    private String publisher; // 출판사

    @Column(name = "create_date")
    private LocalDate createDate; // 출판일

    @Embedded
    private Price price;

    public Book () {}

    public Book(String bookTitle, String author, String publisher, LocalDate createDate, Price price) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.createDate = createDate;
        this.price = price;
    }
}
