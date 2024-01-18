package com.supermaket.GMarket.responses;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseBodyResponse<T> {

    private String company;

    private String description;
    private T result;
}
