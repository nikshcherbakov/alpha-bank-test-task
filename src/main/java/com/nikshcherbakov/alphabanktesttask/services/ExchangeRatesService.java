package com.nikshcherbakov.alphabanktesttask.services;

import com.nikshcherbakov.alphabanktesttask.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRatesService implements IExchangeService {

    @Value("${api.openexchangerates.appid}")
    private String appId;

    @Value("${api.openexchangerates.basecurrency}")
    private String baseCurrency;

    private final OpenExchangeRatesClient exchangeRatesClient;

    public ExchangeRatesService(OpenExchangeRatesClient exchangeRatesClient) {
        this.exchangeRatesClient = exchangeRatesClient;
    }

    @Override
    public float getCrossRateTargetCurrencyToRubByDate(String currencyCode, LocalDate date)
            throws NonSupportedCurrencyException, IllegalDateException {
        if (date.isBefore(LocalDate.of(1999, 1, 1)) ||
                date.isAfter(LocalDate.now())) {
            throw new IllegalDateException();
        }

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("app_id", appId);
        requestParams.put("base", baseCurrency);

        OpenExchangeRatesResponse response = exchangeRatesClient.requestRatesByDate(date.toString(), requestParams);
        Map<String, Float> rates = response.getRates();
        for (Map.Entry<String, Float> rate : rates.entrySet()) {
            if (rate.getKey().equals(currencyCode)) {
                return rates.get("RUB") / rate.getValue();
            }
        }

        // Запрашиваемая валюта не найдена
        throw new NonSupportedCurrencyException();
    }
}
