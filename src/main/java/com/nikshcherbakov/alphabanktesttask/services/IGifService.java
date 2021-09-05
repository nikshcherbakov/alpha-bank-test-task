package com.nikshcherbakov.alphabanktesttask.services;

import com.nikshcherbakov.alphabanktesttask.utils.GifTag;

public interface IGifService {

    /**
     * Возвращает ссылку на случайную гифку по тегу
     * @param tag тег, по которому производится поиск гифок
     * @return ссылка на ресурс с гифкой
     */
    String getRandomGifUrlByTag(GifTag tag);

}
