package com.sparta.gimbap_heaven.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasketRequestDto {


    @NotNull
    private Long menu_id;

    @NotNull
    @Min(value = 1, message = "최소 한 개 이상 주문해야 합니다.")
    private int count;

}
