package com.bhaskar.utils.crypt;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final CryptLib cryptLib;

    public GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter, CryptLib cryptLib) {
        this.gson = gson;
        this.adapter = adapter;
        this.cryptLib = cryptLib;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        if (cryptLib != null) {
            String out = buffer.readByteString().toString();
            String encrypted;
            try {
                encrypted = cryptLib.encryptPlainTextWithRandomIV(out, CryptLib.KEY);
            } catch (NoSuchAlgorithmException e) {
                throw new CrypeException(e.getMessage());
            } catch (InvalidKeySpecException e) {
                throw new CrypeException(e.getMessage());
            } catch (NoSuchPaddingException e) {
                throw new CrypeException(e.getMessage());
            } catch (InvalidAlgorithmParameterException e) {
                throw new CrypeException(e.getMessage());
            } catch (InvalidKeyException e) {
                throw new CrypeException(e.getMessage());
            } catch (BadPaddingException e) {
                throw new CrypeException(e.getMessage());
            } catch (IllegalBlockSizeException e) {
                throw new CrypeException(e.getMessage());
            } catch (Exception e) {
                throw new CrypeException(e.getMessage());
            }
            return RequestBody.create(MEDIA_TYPE, encrypted);
        } else
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());


    }
}