package com.sparta.gimbap_heaven.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasketRequestDto {

    @NotBlank
    private Long menu_id;

    @NotBlank
    @Size(min = 1, message = "최소 한 개 이상 주문해야 합니다.")
    private int count;

}
