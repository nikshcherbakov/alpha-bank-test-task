package com.nikshcherbakov.alphabanktesttask.utils;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GiphyResponse {

    public static class GiphyApiData {
        @SerializedName("image_url")
        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }
    }

    private GiphyApiData data;

    public GiphyApiData getData() {
        return data;
    }
}
