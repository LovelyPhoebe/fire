package com.lindog.fire.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lindog.fire.mapper.CardMapper;
import com.lindog.fire.pojo.Card;
import com.lindog.fire.service.CardService;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class CardServiceImpl implements CardService {
    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY_PREFIX = "card:list:";
    private static final Long CACHE_TIMEOUT = 30L;

    @Override
    public List<Card> list(String keyword) {
        String cacheKey = CACHE_KEY_PREFIX + (keyword != null ? keyword : "all");

        @SuppressWarnings("unchecked")
        List<Card> cacheResult = (List<Card>) redisTemplate.opsForValue().get(cacheKey);
        if (cacheResult != null) {
            log.info("从缓存中获取数据, key: {}", cacheKey);
            return cacheResult;
        }
        List<Card> dbResult = cardMapper.list(keyword);
        redisTemplate.opsForValue().set(cacheKey, dbResult, CACHE_TIMEOUT, TimeUnit.MINUTES);
        log.info("将数据存入缓存, key: {}", cacheKey);
        return dbResult;
    }

    @Override
    public String create(Card card) {
        // 校验字段是否为空
        if (!StringUtils.hasLength(card.getTitle()) ||
                !StringUtils.hasLength(card.getDescription()) ||
                !StringUtils.hasLength(card.getLink())) {
            return "缺少必要字段";
        }

        // 设置时间
        card.setCreateTime(LocalDateTime.now());
        card.setUpdateTime(LocalDateTime.now());

        try {
            cardMapper.create(card);
            // 清除缓存
            String cacheKey = CACHE_KEY_PREFIX + "all";
            redisTemplate.delete(cacheKey);
            log.info("清除缓存, key: {}", cacheKey);
            return "创建成功";
        } catch (Exception e) {
            // 记录日志
             log.error("创建卡片失败", e);
            return "创建卡片失败，系统错误";
        }
    }



}
