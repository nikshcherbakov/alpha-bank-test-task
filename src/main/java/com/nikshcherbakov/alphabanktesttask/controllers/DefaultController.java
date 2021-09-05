package com.nikshcherbakov.alphabanktesttask.controllers;

import com.nikshcherbakov.alphabanktesttask.services.IExchangeService;
import com.nikshcherbakov.alphabanktesttask.services.IGifService;
import com.nikshcherbakov.alphabanktesttask.utils.GifTag;
import com.nikshcherbakov.alphabanktesttask.exceptions.IllegalDateException;
import com.nikshcherbakov.alphabanktesttask.exceptions.ServiceIsNotAvailableException;
import com.nikshcherbakov.alphabanktesttask.exceptions.NonSupportedCurrencyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Controller
public class DefaultController {

    private final IExchangeService exchangeRatesService;
    private final IGifService gifService;

    public DefaultController(IExchangeService exchangeRatesService, IGifService gifService) {
        this.exchangeRatesService = exchangeRatesService;
        this.gifService = gifService;
    }

    @GetMapping("/{currencyCode}")
    public String handleRequest(@PathVariable String currencyCode, Model model) {
        if (currencyCode.equals("RUB")) {
            String errorDescription = "Пожалуйста, введите код валюты, отличный от RUB.";
            model.addAttribute("errorDescription", errorDescription);
            return "error";
        }

        LocalDate today = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        try {
            float todayCrossRate =
                    exchangeRatesService.getCrossRateTargetCurrencyToRubByDate(currencyCode, today);
            float yesterdayCrossRate =
                    exchangeRatesService.getCrossRateTargetCurrencyToRubByDate(currencyCode, yesterday);

            boolean rubleGrewUp = todayCrossRate > yesterdayCrossRate;

            // Загружаем гифку и возвращаем ее в виде массива байтов
            String gifUrl = gifService.getRandomGifUrlByTag(rubleGrewUp ? GifTag.RICH : GifTag.BROKE);

            model.addAttribute("gifUrl", gifUrl);
            model.addAttribute("rubleGrewUp", rubleGrewUp);
            return "gif-page";
        } catch (NonSupportedCurrencyException e) {
            String errorDescription = String.format("Извините, введенный код валюты (%s) не поддерживается " +
                    "системой. Пожалуйста, введите корректный курс валюты и используйте только заглавные " +
                    "латинские буквы.", currencyCode);
            model.addAttribute("errorDescription", errorDescription);
            return "error";

        } catch (IllegalDateException e) {
            String errorDescription = "Извините, на сервере произошла внутренняя ошибка. " +
                    "Пожалуйста, попробуйте позднее.";
            model.addAttribute("errorDescription", errorDescription);
            return "error";

        } catch (ServiceIsNotAvailableException e) {
            String errorDescription = "При соединении со сторонним сервисом на сервере произошла ошибка. " +
                    "Пожалуйста, попробуйте позднее.";
            model.addAttribute("errorDescription", errorDescription);
            return "error";

        }

    }

}
