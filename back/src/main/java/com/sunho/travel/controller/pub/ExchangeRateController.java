package com.sunho.travel.controller.pub;

import java.util.ArrayList;
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

    @GetMapping(path = "getRate")
    public ResponseEntity<List<ExchangeRateResponse>> signUp(@RequestParam(required = false) String currency) { // body가 없으면 Void를 주면 된다.
            List<ExchangeRateResponse> rates = exchangeRateService.getExchangeRates();
            if(rates.isEmpty()){
                return ResponseEntity.ok().body(new ArrayList<ExchangeRateResponse>());
            }
            if(currency != null){
                List<ExchangeRateResponse> rate = rates.stream().filter(o -> Objects.equals(o.getCurrency(), currency)).toList();
                return ResponseEntity.ok().body(rate);
            }
        return ResponseEntity.ok().body(rates);
    }
}
