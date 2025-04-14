package com.sunho.travel.domain.exchangerate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExchangeRateResponse {
    @JsonProperty(value = "cur_unit")
    private String current;

    @JsonProperty(value = "deal_bas_r")
    private String rate;

}
