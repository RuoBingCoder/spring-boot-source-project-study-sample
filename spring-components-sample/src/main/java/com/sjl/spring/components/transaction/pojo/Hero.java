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
public class Hero {
    private Integer id;

    private String name;

    private String type;

    private Integer money;

    private LocalDateTime createTime;
}