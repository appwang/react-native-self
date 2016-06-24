package com.chemao.base.api;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * User: LiYajun
 * Date: 2015-11-13
 * Describe: 图片加载
 */

public class CMImageLoader {

    public static void loadImage(Activity activity, String imgURL, int emptyImg, ImageView imageView) {
        Glide.with(activity).load(imgURL).placeholder(emptyImg).into(imageView);
    }

    public static void loadImage(Activity activity, String imgURL, int emptyImg, ImageView imageView, BitmapTransformation... transformations) {
        Glide.with(activity).load(imgURL).transform(transformations).placeholder(emptyImg).into(imageView);
    }

    public static void loadImage(Context context, String imgURL, int emptyImg, ImageView imageView) {
        Glide.with(context).load(imgURL).crossFade().placeholder(emptyImg).into(imageView);
    }

    public static void loadImage(Context context, String imgURL, int emptyImg, ImageView imageView, BitmapTransformation... transformations) {
        Glide.with(context).load(imgURL).transform(transformations).crossFade().placeholder(emptyImg).into(imageView);
    }

}
