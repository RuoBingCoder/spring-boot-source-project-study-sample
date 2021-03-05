package com.github.multiple.datasource.sample.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Long id;

    private String city;

    private String name;

    private static final long serialVersionUID = 1L;
}