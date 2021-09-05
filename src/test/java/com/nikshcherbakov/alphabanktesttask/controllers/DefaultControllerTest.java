package com.nikshcherbakov.alphabanktesttask.controllers;

import com.nikshcherbakov.alphabanktesttask.exceptions.NonSupportedCurrencyException;
import com.nikshcherbakov.alphabanktesttask.exceptions.ServiceIsNotAvailableException;
import com.nikshcherbakov.alphabanktesttask.utils.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nikshcherbakov.alphabanktesttask.services.IExchangeService;
import com.nikshcherbakov.alphabanktesttask.services.IGifService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
@AutoConfigureMockMvc
class DefaultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IExchangeService exchangeService;

    @MockBean
    private IGifService gifService;

    private final LocalDate today = LocalDate.now();
    private final LocalDate yesterday = LocalDate.now().minusDays(1);

    @Test
    void simpleGetRequestIsHandledCorrectly()
            throws Exception {
        Mockito.when(exchangeService.getCrossRateTargetCurrencyToRubByDate("USD", today))
                .thenReturn(72.89f);

        Mockito.when(exchangeService.getCrossRateTargetCurrencyToRubByDate("USD", yesterday))
                .thenReturn(73.45f);

        String imageUrl = "https://media3.giphy.com/media/QAO5e1GbF9kLKDjqlI/giphy.gif";
        Mockito.when(gifService.getRandomGifUrlByTag(GifTag.RICH)).thenReturn(imageUrl);

        mockMvc.perform(get("/USD"))
                .andExpect(view().name("gif-page"));
    }

    @Test
    void nonExistingCurrencyIsHandledCorrectly() throws Exception {
        Mockito.when(exchangeService.getCrossRateTargetCurrencyToRubByDate("qwe", today))
                .thenThrow(NonSupportedCurrencyException.class);

        mockMvc.perform(get("/qwe"))
                .andExpect(view().name("error"));
    }

    @Test
    void servicesConnectionProblemsAreHandled() throws Exception {
        // Case 1 - Проблема с сервисом обмена валюты
        Mockito.when(exchangeService.getCrossRateTargetCurrencyToRubByDate("EUR", today))
                .thenThrow(ServiceIsNotAvailableException.class);

        mockMvc.perform(get("/EUR"))
                .andExpect(view().name("error"));

        // Case 2 - Проблема с сервисом гифок
        Mockito.when(gifService.getRandomGifUrlByTag(GifTag.RICH))
                .thenThrow(ServiceIsNotAvailableException.class);

        mockMvc.perform(get("/EUR"))
                .andExpect(view().name("error"));
    }

    @Test
    void whenCurrencyCodeIsRubErrorPageIsShown() throws Exception {
        // Если пользователь переходит по /RUB, то выводится ошибка
        mockMvc.perform(get("/RUB"))
                .andExpect(view().name("error"));
    }
}