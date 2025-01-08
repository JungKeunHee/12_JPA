package com.ohgiraffers.mapping.section02.embedded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

@SpringBootTest
public class EmbeddedTest {

    @Autowired
    private BookService service;

    private static Stream<Arguments> getBook() {
        return Stream.of(
          Arguments.of(
                  "JPA의 정석",
                  "너구리",
                  "하이미디어출판",
                  LocalDate.now(),
                  35000,
                  0.1
          )
        );
    }

    @ParameterizedTest
    @DisplayName("임베디드 시 테스트")
    @MethodSource("getBook")
    void testEmbedded(String bookTitle, String author, String publisher,
                      LocalDate createDate, int regularPrice, double discountRate) {

        BookRegistDTO newBook = new BookRegistDTO(bookTitle, author, publisher, createDate, regularPrice, discountRate);

        Assertions.assertDoesNotThrow(
                () -> service.registBook(newBook)
        );

    }

}
