package com.nikshcherbakov.alphabanktesttask.utils;

import java.util.Map;

@SuppressWarnings("unused")
public class OpenExchangeRatesResponse {
    private final Map<String, Float> rates;

    public OpenExchangeRatesResponse(Map<String, Float> rates) {
        this.rates = rates;
    }

    public Map<String, Float> getRates() {
        return rates;
    }
}
