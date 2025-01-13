package com.ohgiraffers.springdatajpa.common;

import org.springframework.data.domain.Page;

public class Pagenation {

    // 공통적으로 사용할 페이징 정보 기술
    // ex) 첫 페이지 번호, 페이지에 해당하는 데이터 수 등등
    public static PagingButton getPagingInfo(Page page) {

        // 현재 페이지에 대한 정보.
        // 더하기 1 하는 이유는 0 부터 시작하기 때문에
        // 사용자 시점에서 보기 편하게 더해준다.
        int currentPage = page.getNumber() + 1;

        // 페이지 버튼의 기본 갯수
        int defaultButtonCount = 10;

        // 현재 시작 페이지 계산
        int startPage = (int)(Math.ceil((double) currentPage / defaultButtonCount) -1)
                * defaultButtonCount + 1;

        int endPage = startPage + defaultButtonCount -1;

        // 실제 총 페이지가 endPage 보다 작은 경우 endPage 를 총 페이지 수로 지정
        if(page.getTotalPages() < endPage) {
            endPage = page.getTotalPages();
        }

        // 페이지가 0이면 끝 페이지를 시작 페이지로 설정
        if(page.getTotalPages() == 0 && endPage == 0) {
            endPage = startPage;
        }

        // 계산 된 정보들을 반환
        return new PagingButton(currentPage, startPage, endPage);
    }
}
