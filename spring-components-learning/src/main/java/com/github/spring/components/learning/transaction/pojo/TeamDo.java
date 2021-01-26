package com.github.spring.components.learning.transaction.pojo;

import com.github.spring.components.learning.mybatis.annoation.Table;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "team")
public class TeamDo {

    private Integer id;

    private String teamName;

    private String createTime;

    private String updateTime;
}