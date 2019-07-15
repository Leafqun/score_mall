package com.czbank.coding.api;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    private Integer id;

    private Integer userId;

    private String place;

    private Integer type;

    private String cardNum;

    private String currency;

    private Double money;

    private Double availableMoney;

}
