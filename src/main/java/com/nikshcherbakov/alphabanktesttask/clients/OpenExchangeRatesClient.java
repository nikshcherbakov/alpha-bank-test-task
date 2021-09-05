package com.nikshcherbakov.alphabanktesttask.clients;

import com.nikshcherbakov.alphabanktesttask.utils.OpenExchangeRatesResponse;
import feign.Param;
import feign.RequestLine;


public interface OpenExchangeRatesClient {
    @RequestLine("GET /historical/{date}.json?app_id={appId}&base={base}")
    OpenExchangeRatesResponse requestRatesByDate(@Param("date") String date, @Param("appId") String appId,
                                                 @Param("base") String base);
}
