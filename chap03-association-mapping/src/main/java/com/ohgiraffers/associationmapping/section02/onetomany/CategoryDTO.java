package com.ohgiraffers.associationmapping.section02.onetomany;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {

    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;
    private List<MenuDTO> menuList;
}
