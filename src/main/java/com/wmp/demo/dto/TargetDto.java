package com.wmp.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TargetDto {
    private String url;
    private String opt;
    private Integer outputUnit;
}
