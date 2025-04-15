package com.sunho.travel.domain.exchangerate;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class ExchangeRateResponse {
    @JsonAlias(value = "cur_unit")
    private String currency;

    @JsonAlias(value = "deal_bas_r")
    private String rate;

}
