package com.sunho.travel.service;

import java.util.Map;

public interface ExchangeRateService {
    public Map<String,String> updateExchangeRates();
    public Map<String,String> getExchangeRates();
}
