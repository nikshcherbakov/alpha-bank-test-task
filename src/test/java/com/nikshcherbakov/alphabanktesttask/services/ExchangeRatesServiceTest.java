package com.nikshcherbakov.alphabanktesttask.services;

import com.nikshcherbakov.alphabanktesttask.clients.OpenExchangeRatesClient;
import com.nikshcherbakov.alphabanktesttask.exceptions.IllegalDateException;
import com.nikshcherbakov.alphabanktesttask.exceptions.NonSupportedCurrencyException;
import com.nikshcherbakov.alphabanktesttask.exceptions.ServiceIsNotAvailableException;
import com.nikshcherbakov.alphabanktesttask.utils.*;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeRatesServiceTest {

    @Value("${api.openexchangerates.appid}")
    private String appId;

    @Value("${api.openexchangerates.basecurrency}")
    private String base;

    @MockBean
    private OpenExchangeRatesClient exchangeRatesClient;

    @Autowired
    private IExchangeService exchangeService;

    @Test
    void simpleRequestReturnsSomething() {
        Map<String, Float> mockRates = new HashMap<>() {{
            put("RUB", 72.83f);
            put("EUR", 0.841818f);
        }};
        Mockito.when(exchangeRatesClient.requestRatesByDate(LocalDate.now().toString(), appId, base))
                .thenReturn(new OpenExchangeRatesResponse(mockRates));

        assertDoesNotThrow(() -> {
            float eur = exchangeService.getCrossRateTargetCurrencyToRubByDate("EUR", LocalDate.now());
            assertTrue(eur > 0);
        });
    }

    @Test
    void nonSupportedCurrencyThrowsException() {
        Map<String, Float> mockRates = new HashMap<>() {{
            put("RUB", 72.83f);
            put("EUR", 0.841818f);
        }};
        Mockito.when(exchangeRatesClient.requestRatesByDate(LocalDate.now().toString(), appId, base))
                .thenReturn(new OpenExchangeRatesResponse(mockRates));

        assertThrows(NonSupportedCurrencyException.class, () ->
                exchangeService.getCrossRateTargetCurrencyToRubByDate("asd", LocalDate.now()));
    }

    @Test
    void invalidDateThrowsIllegalDateException() {
        // Case 1 - Дата из будущего
        assertThrows(IllegalDateException.class, () ->
                exchangeService.getCrossRateTargetCurrencyToRubByDate("EUR", LocalDate.now().plusDays(1)));

        // Case 2 - Дата до 1 января 1999 года (т. к. сервис хранит информацию о курсах, начиная с этой даты)
        assertThrows(IllegalDateException.class, () ->
                exchangeService.getCrossRateTargetCurrencyToRubByDate("EUR",
                        LocalDate.of(1998, 12, 31)));
    }

    @Test
    void invalidResponseIsHandledCorrectly() {
        // Сервис недоступен (отсутствие интернет соединения, некорректный ApiKey)
        Mockito.when(exchangeRatesClient.requestRatesByDate(LocalDate.now().toString(), appId, base))
                .thenThrow(FeignException.class);
        assertThrows(ServiceIsNotAvailableException.class, () ->
                exchangeService.getCrossRateTargetCurrencyToRubByDate("EUR", LocalDate.now()));
    }
}