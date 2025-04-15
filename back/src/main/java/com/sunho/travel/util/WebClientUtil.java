package com.sunho.travel.util;

import java.time.Duration;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class WebClientUtil {
    
    public static WebClient getCustomizedWebClient(String baseUrl, int timeout){
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).filter(errorHandlingFilter()).filter(timeoutFilter(Duration.ofSeconds(timeout)))
        .filter(retryFilter(3, Duration.ofSeconds(2))).build();
        return webClient;
    }
    

    private static ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
                return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new RuntimeException(
                        "HTTP Error " + response.statusCode() + ": " + body
                    )));
            }
            return Mono.just(response);
        });
    }

    private static ExchangeFilterFunction timeoutFilter(Duration timeout){
        return (request, next) -> next.exchange(request).timeout(timeout);
    }
    // 몇 초 간격으로 최대 몇 회 시도
    private static ExchangeFilterFunction retryFilter(long maxAttempts, Duration backoff) {
        return (request, next) -> next.exchange(request)
            .retryWhen(
                Retry.backoff(maxAttempts, backoff)
                    .filter(throwable -> throwable instanceof Exception)
                    .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                        new RuntimeException("Retry attempts exhausted", retrySignal.failure()))
            );
    }



    // 아래 설정을 해주면 SSL 인증서 검증을 하지 않게 된다.
    // public static WebClient getCustomizedWebClient(String baseUrl) {
    //     HttpClient httpClient = HttpClient.create()
    //             .secure(sslContextSpec ->
    //                     sslContextSpec.sslContext(
    //                             SslProvider
    //                                     .builder()
    //                                     .trustManager(InsecureTrustManagerFactory.INSTANCE)
    //                                     .build()
    //                     )
    //             );

    //     return WebClient.builder()
    //             .clientConnector(new ReactorClientHttpConnector(httpClient))
    //             .baseUrl(baseUrl)
    //             .build();
    // }

}
