package com.pratham.dse.utils;
/**
 * Created by Poonamchand Sahu 24 Jan 2020
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ImageUtil {

    public static void loadImage(String url, ImageView imageView) {
        loadImage(url, imageView, -1, -1, -1, -1, null);
    }

    public static void loadImage(String url, ImageView imageView, int placeholder) {
        loadImage(url, imageView, -1, -1, placeholder, -1, null);
    }

    public static void loadImage(String url, int errorHolder, ImageView imageView) {
        loadImage(url, imageView, -1, -1, -1, errorHolder, null);

    }

    public static void loadThumbImage(String url, ImageView imageView, int placeholder) {
        loadThumbImage(url, imageView, -1, -1, placeholder, -1, null);
    }

    public static void loadThumbImage(String url, int errorHolder, ImageView imageView) {
        loadThumbImage(url, imageView, -1, -1, -1, errorHolder, null);
    }

    public static void loadImage(String url, ImageView imageView, int placeholder, int width, int height) {
        loadImage(url, imageView, width, height, placeholder, -1, null);
    }

    public static void loadImage(String url, ImageView imageView, int width, int height) {
        loadImage(url, imageView, width, height, -1, -1, null);
    }

    public static void loadImage(String url, ImageView imageView, int width, int height, RequestOptions options) {
        loadImage(url, imageView, width, height, -1, -1, options);
    }

    public static void loadImage(String url, ImageView imageView, int placeholder, RequestOptions options) {
        loadImage(url, imageView, -1, -1, placeholder, -1, options);
    }

    public static void loadImage(int imageResId, ImageView imageView) {
        loadImage(imageResId, imageView, -1, -1, -1, -1, null);
    }

    public static void loadImage(int imageResId, ImageView imageView, RequestOptions options) {
        loadImage(imageResId, imageView, -1, -1, -1, -1, options);
    }

    public static void loadImage(int imageResId, ImageView imageView, int placeholder) {
        loadImage(imageResId, imageView, -1, -1, placeholder, -1, null);
    }

    private static void loadImage(String url, ImageView imageView, int width, int height,
                                  int placeholder, int error, RequestOptions options) {
        try {

            RequestOptions requestOptions = getRequestOptions(width, height, placeholder, error, options);
            Glide.with(imageView)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);

        } catch (Exception e) {
            Log.d("ImageUtil", "loadImage");
        }

    }

    /****
     *
     * @param url
     * @param imageView
     * @param width
     * @param height
     * @param placeholder
     * @param error
     * @param options
     */
    private static void loadThumbImage(String url, ImageView imageView, int width, int height,
                                       int placeholder, int error, RequestOptions options) {
        RequestOptions requestOptions = getRequestOptions(width, height, placeholder, error, options);
        Glide.with(imageView)
                .load(url)
                .thumbnail(1.0f)
                .apply(requestOptions)
                .into(imageView);
    }

    private static void loadImage(int imageResId, ImageView imageView, int width, int height,
                                  int placeholder, int error, RequestOptions options) {

        RequestOptions requestOptions = getRequestOptions(width, height, placeholder, error, options);

        Glide.with(imageView)
                .load(imageResId)
                .apply(requestOptions)
                .into(imageView);
    }

    @SuppressLint("CheckResult")
    @NonNull
    private static RequestOptions getRequestOptions(int width, int height, int placeholder, int error, RequestOptions options) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.skipMemoryCache(false);
        requestOptions.placeholder(placeholder);
        requestOptions.error(error);

        if (options != null) {
            requestOptions.apply(options);
        }

        if (width > 0 && height > 0) {
            requestOptions.override(width, height);
        }
        return requestOptions;
    }

    /**
     * This method will be used to load GIF image
     *
     * @param url
     * @param imageView
     * @param placeHolder
     */
    public static void loadGifImage(String url, ImageView imageView, int placeHolder) {
        try {
            RequestOptions requestOptions = getRequestOptions(-1, -1, placeHolder, -1, null);
            Glide.with(imageView)
                    .asGif()
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        } catch (Exception e) {
            Log.d("ImageUtil", "loadGifImage");

        }

    }

    /**
     * This method will be used to load GIF image
     *
     * @param url
     * @param errorHolder
     * @param imageView
     */
    public static void loadGifImage(String url, int errorHolder, ImageView imageView) {
        RequestOptions requestOptions = getRequestOptions(-1, -1, -1, errorHolder, null);
        Glide.with(imageView)
                .asGif()
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    public static Bitmap getBitmapFromBase64(String encodedImage) {
        try {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            if (decodedByte != null)
                return decodedByte;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public static Bitmap getBitmapFromUrl(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }*/


    public static Bitmap getBitMapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /***
     *
     * @param context
     * @param imageURL
     * @return
     */
    public static Bitmap getBitmap(@NonNull Context context, @NonNull String imageURL) {
        Bitmap bitmap = null;
        try {
            Drawable drawable = Glide.
                    with(context).
                    load(imageURL).
                    into(100, 100).
                    get();
            if (drawable instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof GifDrawable) {
                bitmap = ((GifDrawable) drawable).getFirstFrame();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String getFileToBase64(String filePath) {
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArray = baos.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
