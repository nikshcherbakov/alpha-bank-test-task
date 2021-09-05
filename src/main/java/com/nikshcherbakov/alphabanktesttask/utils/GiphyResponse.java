package com.nikshcherbakov.alphabanktesttask.utils;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GiphyResponse {

    public static class GiphyApiData {
        @SerializedName("image_url")
        private final String imageUrl;

        public GiphyApiData(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

    private final GiphyApiData data;

    public GiphyResponse(GiphyApiData data) {
        this.data = data;
    }

    public GiphyApiData getData() {
        return data;
    }
}
