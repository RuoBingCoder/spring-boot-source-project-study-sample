package com.sjl.spring.components.transaction.pojo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    private Integer teamId;

    private String teamName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}