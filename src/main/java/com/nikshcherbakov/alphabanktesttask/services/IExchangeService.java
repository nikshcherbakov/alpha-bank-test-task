package com.nikshcherbakov.alphabanktesttask.services;

import com.nikshcherbakov.alphabanktesttask.exceptions.IllegalDateException;
import com.nikshcherbakov.alphabanktesttask.exceptions.ServiceIsNotAvailableException;
import com.nikshcherbakov.alphabanktesttask.exceptions.NonSupportedCurrencyException;

import java.time.LocalDate;

public interface IExchangeService {
    /**
     * Возвращает значение текущего курса валюты относительно доллара США (USD).
     * В случае, если код валюты не обнаружен, выбрасывается исключение
     * NonSupportedCurrencyException.
     * @param currencyCode код валюты
     * @param date дата, по отношению к которой осуществляется поиск курса валют.
     * @return курс обмена выбранной валюты по отношению к доллару США.
     * @throws NonSupportedCurrencyException в случае, если выбранная валюта не
     * поддерживается системой
     * @throws IllegalDateException в случае, если текущая дата находится до 1
     * января 1999 года, либо после текущей даты
     */
    float getCrossRateTargetCurrencyToRubByDate(String currencyCode, LocalDate date)
            throws NonSupportedCurrencyException, IllegalDateException, ServiceIsNotAvailableException;
}
