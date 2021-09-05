package com.nikshcherbakov.alphabanktesttask.services;

import com.nikshcherbakov.alphabanktesttask.utils.GifTag;
import com.nikshcherbakov.alphabanktesttask.clients.GiphyClient;
import com.nikshcherbakov.alphabanktesttask.utils.GiphyResponse;
import com.nikshcherbakov.alphabanktesttask.exceptions.ServiceIsNotAvailableException;
import feign.FeignException;
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
    public String getRandomGifUrlByTag(GifTag tag) throws ServiceIsNotAvailableException {
        try {
            GiphyResponse response =
                    giphyClient.requestRandomGifByTag(tag == GifTag.RICH ? "rich" : "broke", apiKey);
            return response.getData().getImageUrl();
        } catch (FeignException e) {
            throw new ServiceIsNotAvailableException();
        }

    }
}
