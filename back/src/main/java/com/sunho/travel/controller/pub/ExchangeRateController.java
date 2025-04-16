package com.sunho.travel.controller.pub;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunho.travel.domain.exchangerate.ExchangeRateResponse;
import com.sunho.travel.service.ExchangeRateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/exchange")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/getRate")
    public ResponseEntity<List<ExchangeRateResponse>> getRate(@RequestParam(required = false) String currency) {
        List<ExchangeRateResponse> rates = exchangeRateService.getExchangeRates();

        if (rates.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        if (currency != null) {
            rates = rates.stream().filter(o -> Objects.equals(currency, o.getCurrency())).toList();
        }
        return ResponseEntity.ok(rates);
    }
}
