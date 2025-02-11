package com.lindog.fire.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lindog.fire.pojo.Card;

@Mapper
@Repository
public interface CardMapper {
    List<Card> list(String keyword);

    void create(Card card);
}
