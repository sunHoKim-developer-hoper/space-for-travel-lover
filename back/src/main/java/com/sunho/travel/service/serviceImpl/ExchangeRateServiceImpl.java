package com.sunho.travel.service.serviceImpl;

import static com.sunho.travel.util.DateFormatters.YYYYMMDD;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sunho.travel.domain.exchangerate.ExchangeRateResponse;
import com.sunho.travel.service.ExchangeRateService;
import com.sunho.travel.util.WebClientUtil;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Value("${exchange.rate.auth.key}")
    private String authKey;

    public Map<String, String> callExternalApi() {
        List<ExchangeRateResponse> exchangeRateExternalResponse = getExchangeRate();
        return exchangeRateExternalResponse.stream().collect(Collectors.toMap(ExchangeRateResponse::getCurrent,ExchangeRateResponse::getRate));
    }

    @Override
    @CachePut(value = "exchangeRateCache", key = "'exchangeRateKey'")
    public Map<String,String> updateExchangeRates() {
        return callExternalApi();
    }

    @Override
    @Cacheable(value = "exchangeRateCache", key = "'exchangeRateKey'")
    public Map<String,String> getExchangeRates() {
        return callExternalApi();
    }

    public List<ExchangeRateResponse> getExchangeRate() {
        String today = LocalDate.now().format(YYYYMMDD);
        WebClient webClient = WebClientUtil.getCustomizedWebClient("https://www.koreaexim.go.kr", 500);
        List<ExchangeRateResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/site/program/financial/exchangeJSON")
                        .queryParam("authkey", authKey)
                        .queryParam("searchdate", today)
                        .queryParam("data", "AP01")
                        .build())
                // 수신 받을 준비
                .retrieve()
                // ExchangeRateResponse 클래스 형태로 역질렬화해서 반환 아래는 List 형대로 받는 방법
                .bodyToMono(new ParameterizedTypeReference<List<ExchangeRateResponse>>() {
                }).block();
        return response;
    }
}
