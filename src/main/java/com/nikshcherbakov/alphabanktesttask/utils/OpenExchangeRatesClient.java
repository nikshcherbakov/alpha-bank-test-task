package com.nikshcherbakov.alphabanktesttask.utils;

import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface OpenExchangeRatesClient {
    @RequestLine("GET /historical/{date}.json")
    OpenExchangeRatesResponse requestRatesByDate(@Param("date") String date,
                                                 @QueryMap Map<String, String> params);
}
