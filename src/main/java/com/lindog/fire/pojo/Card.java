package com.lindog.fire.pojo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private Integer id;
    private String title;
    private String description;
    private String link;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
