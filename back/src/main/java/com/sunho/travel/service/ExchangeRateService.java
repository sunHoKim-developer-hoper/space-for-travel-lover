package com.sunho.travel.service;

import java.util.List;

import com.sunho.travel.domain.exchangerate.ExchangeRateResponse;

public interface ExchangeRateService {
    public List<ExchangeRateResponse> updateExchangeRates();
    public List<ExchangeRateResponse> getExchangeRates();
}
