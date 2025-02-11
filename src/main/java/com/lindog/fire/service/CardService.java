package com.lindog.fire.service;

import java.util.List;

import com.lindog.fire.pojo.Card;

public interface CardService {
    // 获取卡片列表
    List<Card> list(String keyword);

    String create(Card card);

}
