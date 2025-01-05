package org.example.expert.domain.common.util;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class ResponseMapper {

    public static <S, T> List<T> mapToList(List<S> entityList, Function<S, T> responseDto) {
        return entityList.stream().map(responseDto).toList();
    }

    public static <S, T> Page<T> mapToPage(Page<S> page, Function<S, T> responseDto) {
        return page.map(responseDto);
    }
}
