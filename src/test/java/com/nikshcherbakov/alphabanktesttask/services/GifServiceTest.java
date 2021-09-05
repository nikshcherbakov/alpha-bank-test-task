package com.nikshcherbakov.alphabanktesttask.services;

import com.nikshcherbakov.alphabanktesttask.utils.GifTag;
import com.nikshcherbakov.alphabanktesttask.clients.GiphyClient;
import com.nikshcherbakov.alphabanktesttask.utils.GiphyResponse;
import com.nikshcherbakov.alphabanktesttask.exceptions.ServiceIsNotAvailableException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GifServiceTest {

    @Value("${api.giphy.apikey}")
    private String apiKey;

    @Autowired
    private IGifService gifService;

    @MockBean
    private GiphyClient giphyClient;

    @Test
    void simpleRequestReturnsSomething() throws ServiceIsNotAvailableException {
        String imageUrl = "https://media3.giphy.com/media/QAO5e1GbF9kLKDjqlI/giphy.gif";

        Mockito.when(giphyClient.requestRandomGifByTag("rich", apiKey))
                .thenReturn(new GiphyResponse(new GiphyResponse.GiphyApiData(imageUrl)));

        assertEquals(imageUrl, gifService.getRandomGifUrlByTag(GifTag.RICH));
    }

    @Test
    void invalidResponseIsHandledCorrectly() {
        // Сервис недоступен (отсутствие интернет соединения, некорректный ApiKey)
        Mockito.when(giphyClient.requestRandomGifByTag("rich", apiKey))
                .thenThrow(FeignException.class);

        assertThrows(ServiceIsNotAvailableException.class, () ->
                gifService.getRandomGifUrlByTag(GifTag.RICH));
    }
}