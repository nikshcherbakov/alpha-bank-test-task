package com.nikshcherbakov.alphabanktesttask.services;

import com.nikshcherbakov.alphabanktesttask.utils.GifTag;
import com.nikshcherbakov.alphabanktesttask.utils.GiphyClient;
import com.nikshcherbakov.alphabanktesttask.utils.GiphyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class GifService implements IGifService {

    @Value("${api.giphy.apikey}")
    private String apiKey;

    private final GiphyClient giphyClient;

    public GifService(GiphyClient giphyClient) {
        this.giphyClient = giphyClient;
    }

    @Override
    public String getRandomGifUrlByTag(GifTag tag) {
        GiphyResponse response =
                giphyClient.requestRandomGifByTag(tag == GifTag.RICH ? "rich" : "broke", apiKey);
        return response.getData().getImageUrl();
    }
}
