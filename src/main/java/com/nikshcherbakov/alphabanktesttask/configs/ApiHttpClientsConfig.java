package com.nikshcherbakov.alphabanktesttask.configs;

import com.nikshcherbakov.alphabanktesttask.utils.GiphyClient;
import com.nikshcherbakov.alphabanktesttask.utils.OpenExchangeRatesClient;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiHttpClientsConfig {

    @Value("${api.openexchangerates.base.url}")
    String openExchangeRatesApiBaseUrl;

    @Value("${api.giphy.base.url}")
    String giphyApiBaseUrl;

    @Bean
    public OpenExchangeRatesClient configureOpenExchangeRatesClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .target(OpenExchangeRatesClient.class, openExchangeRatesApiBaseUrl);
    }

    @Bean
    public GiphyClient configureGiphyApiClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .target(GiphyClient.class, giphyApiBaseUrl);
    }

}
