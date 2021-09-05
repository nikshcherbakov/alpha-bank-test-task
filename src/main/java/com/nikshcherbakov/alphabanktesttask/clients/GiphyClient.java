package com.nikshcherbakov.alphabanktesttask.clients;

import com.nikshcherbakov.alphabanktesttask.utils.GiphyResponse;
import feign.Param;
import feign.RequestLine;

public interface GiphyClient {
    @RequestLine("GET /random?api_key={apiKey}&tag={tag}")
    GiphyResponse requestRandomGifByTag(@Param("tag") String tag, @Param("apiKey") String apiKey);
}
