package com.bhaskar.utils.crypt;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class GsonCryptConverterFactory extends Converter.Factory {
    private final Gson gson;
    private final CryptLib cryptLib;

    private GsonCryptConverterFactory(Gson gson, CryptLib cryptLib) {
        this.gson = gson;
        this.cryptLib = cryptLib;
    }

    public static GsonCryptConverterFactory create() {
        return new GsonCryptConverterFactory(new Gson(), null);
    }

    public static GsonCryptConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new GsonCryptConverterFactory(gson, null);
    }

    public static GsonCryptConverterFactory create(CryptLib cryptLib) {
        return new GsonCryptConverterFactory(new Gson(), cryptLib);
    }

    public static GsonCryptConverterFactory create(Gson gson, CryptLib cryptLib) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new GsonCryptConverterFactory(gson, cryptLib);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter, cryptLib);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter, cryptLib);
    }
}