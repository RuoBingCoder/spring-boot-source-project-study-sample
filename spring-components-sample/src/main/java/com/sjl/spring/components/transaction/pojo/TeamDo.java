package com.sjl.spring.components.transaction.pojo;

import com.sjl.spring.components.mybatis.annoation.Table;
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