package com.nikshcherbakov.alphabanktesttask.utils;

import feign.Param;
import feign.RequestLine;

public interface GiphyClient {
    @RequestLine("GET /random?api_key={apiKey}&tag={tag}")
    GiphyResponse requestRandomGifByTag(@Param("tag") String tag, @Param("apiKey") String apiKey);
}
