package com.example.schedulerapp.dto.scheduleDto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageScheduleResponseDto<T> {

    /**
     * content: 페이지에 포함된 데이터 List
     * page: 현재 페이지 번호
     * size: 페이지별로 보여줄 데이터 수
     * totalElements: 전체 데이터 수
     * totalPages: 전체 페이지 수
     * last: 마지막 페이지인지 확인
     */
    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean last;

    public PageScheduleResponseDto(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }
}