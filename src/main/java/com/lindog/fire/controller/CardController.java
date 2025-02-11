package com.lindog.fire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lindog.fire.pojo.Card;
import com.lindog.fire.pojo.Result;
import com.lindog.fire.service.CardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("/cards")
    public Result list(String keyword) {
        List<Card> cardList = cardService.list(keyword);
        return Result.success(cardList);
    }

    @PostMapping("/cards")
    public Result create(@RequestBody Card card) {
        String result = cardService.create(card);
        if ("创建成功".equals(result)) {
            return Result.success();
        } else {
            return Result.error("创建失败");
        }
    }


}
