package com.bhaskar.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.plattysoft.leonids.ParticleSystem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RainbowBlastAnimation {

    private WeakReference<Activity> context;
    private int[] width, height;
    private Timer timer;
    private Random random;

    private int SCREEN_DIVIDEND;

    private Drawable ranbowDrawable[];

    private List<ParticleSystem> particleSystems = new ArrayList<>();
    private final int TIME_TO_LIVE = 2500;

    private int[] colorArray = new int[]
            {R.color.name_ranbow_green,
                    R.color.name_ranbow_indigo,
                    R.color.name_ranbow_orange,
                    R.color.name_ranbow_red,
                    R.color.name_ranbow_violet,
                    R.color.name_ranbow_yellow,
                    R.color.name_ranbow_blue};

    /**
     * @param context
     */
    public RainbowBlastAnimation(Activity context) {
        this.context = new WeakReference<>(context);
        random = new Random();
        int widthPixel = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixel = context.getResources().getDisplayMetrics().heightPixels;

        SCREEN_DIVIDEND = colorArray.length;

        width = new int[SCREEN_DIVIDEND];
        height = new int[SCREEN_DIVIDEND];
        int dividend = SCREEN_DIVIDEND + 1;
        ranbowDrawable = new Drawable[SCREEN_DIVIDEND];


        for (int index = 0; index < SCREEN_DIVIDEND; index++) {

            width[index] = ((index + 1) * widthPixel) / dividend;
            height[index] = ((index + 1) * heightPixel) / dividend;

            ranbowDrawable[index] = ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.ic_star_black_12dp);
            ranbowDrawable[index].setTint(ContextCompat.getColor(context.getApplicationContext(), colorArray[index]));
        }


        /*width[0] = widthPixel / dividend;
        width[1] = 2 * widthPixel / dividend;
        width[2] = 3 * widthPixel / dividend;
        width[3] = 4 * widthPixel / dividend;
        width[4] = 5 * widthPixel / dividend;
        width[5] = 6 * widthPixel / dividend;
        width[6] = 7 * widthPixel / dividend;

        height[0] = heightPixel / dividend;
        height[1] = 2 * heightPixel / dividend;
        height[2] = 3 * heightPixel / dividend;
        height[3] = 4 * heightPixel / dividend;
        height[4] = 5 * heightPixel / dividend;
        height[5] = 6 * heightPixel / dividend;
        height[6] = 7 * heightPixel / dividend;


        ranbowDrawable[1] = ContextCompat.getDrawable(activityContext.getApplicationContext(), R.drawable.ic_star_black_12dp);
        ranbowDrawable[1].setTint(ContextCompat.getColor(activityContext.getApplicationContext(), R.color.name_ranbow_blue));

        ranbowDrawable[2] = ContextCompat.getDrawable(activityContext.getApplicationContext(), R.drawable.ic_star_black_12dp);
        ranbowDrawable[2].setTint(ContextCompat.getColor(activityContext.getApplicationContext(), R.color.name_ranbow_green));

        ranbowDrawable[3] = ContextCompat.getDrawable(activityContext.getApplicationContext(), R.drawable.ic_star_black_12dp);
        ranbowDrawable[3].setTint(ContextCompat.getColor(activityContext.getApplicationContext(), R.color.name_ranbow_indigo));

        ranbowDrawable[4] = ContextCompat.getDrawable(activityContext.getApplicationContext(), R.drawable.ic_star_black_12dp);
        ranbowDrawable[4].setTint(ContextCompat.getColor(activityContext.getApplicationContext(), R.color.name_ranbow_orange));

        ranbowDrawable[5] = ContextCompat.getDrawable(activityContext.getApplicationContext(), R.drawable.ic_star_black_12dp);
        ranbowDrawable[5].setTint(ContextCompat.getColor(activityContext.getApplicationContext(), R.color.name_ranbow_red));

        ranbowDrawable[6] = ContextCompat.getDrawable(activityContext.getApplicationContext(), R.drawable.ic_star_black_12dp);
        ranbowDrawable[6].setTint(ContextCompat.getColor(activityContext.getApplicationContext(), R.color.name_ranbow_yellow));*/

    }

    /**
     * Call this method with Delay
     */
    public void startAnimation() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    // Generate random integers in range 0 to 999
                    int rand_intW = random.nextInt(SCREEN_DIVIDEND);
                    int rand_intH = random.nextInt(SCREEN_DIVIDEND);
                    int randDrawble = random.nextInt(ranbowDrawable.length);
                    //showFirework(rand_intW, rand_intH, ranbowDrawable[randDrawble]);

                    int randDrawble2 = random.nextInt(ranbowDrawable.length);
                    ////showFirework(rand_intW, rand_intH, ranbowDrawable[randDrawble], ranbowDrawable[randDrawble2]);


                    int randDrawble3 = random.nextInt(ranbowDrawable.length);
                    showFirework(rand_intW, rand_intH, ranbowDrawable[randDrawble], ranbowDrawable[randDrawble2], ranbowDrawable[randDrawble3]);

                }
            }, 0, 1500);
        }
    }


    /***
     *
     * @param randomW
     * @param randomH
     */
    private void showFirework(final int randomW, final int randomH, final Drawable randDrawable) {
        if(context != null && context.get() != null) {
            context.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ParticleSystem particleSystem = new ParticleSystem(context.get(), 50, randDrawable, TIME_TO_LIVE);
                    particleSystem.setScaleRange(0.7f, 1.3f);
                    particleSystem.setSpeedRange(0.1f, 0.25f);
                    particleSystem.setRotationSpeedRange(90, 180);
                    particleSystem.setFadeOut(200, new AccelerateInterpolator());
                    particleSystem.oneShot(width[randomW], height[randomH], 100, new LinearInterpolator());

                    particleSystems.add(particleSystem);
                }
            });
        }
    }

    /***
     *
     * @param randomW
     * @param randomH
     */
    private void showFirework(final int randomW, final int randomH, final Drawable randDrawable1, final Drawable randDrawable2) {
        if(context != null && context.get() != null) {
            context.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ParticleSystem ps1 = new ParticleSystem(context.get(), 50, randDrawable1, TIME_TO_LIVE);
                ps1.setScaleRange(0.7f, 1.3f);
                ps1.setSpeedRange(0.1f, 0.25f);
                ps1.setRotationSpeedRange(90, 180);
                ps1.setFadeOut(200, new AccelerateInterpolator());
                ps1.oneShot(width[randomW], height[randomH], 100, new LinearInterpolator());

                ParticleSystem ps2 = new ParticleSystem(context.get(), 50, randDrawable2, TIME_TO_LIVE);
                ps2.setScaleRange(0.7f, 1.3f);
                ps2.setSpeedRange(0.1f, 0.25f);
                ps2.setRotationSpeedRange(90, 180);
                ps2.setFadeOut(200, new AccelerateInterpolator());
                ps2.oneShot(width[randomW], height[randomH], 100, new LinearInterpolator());

                particleSystems.add(ps1);
                particleSystems.add(ps2);

            }
        });}
    }

    /***
     *
     * @param randomW
     * @param randomH
     */
    private void showFirework(final int randomW, final int randomH, final Drawable randDrawable1, final Drawable randDrawable2, final Drawable randDrawable3) {
        if(context != null && context.get() != null) {
            context.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ParticleSystem ps1 = new ParticleSystem(context.get(), 50, randDrawable1, TIME_TO_LIVE);
                    ps1.setScaleRange(0.7f, 1.3f);
                    ps1.setSpeedRange(0.1f, 0.25f);
                    ps1.setRotationSpeedRange(90, 180);
                    ps1.setFadeOut(200, new AccelerateInterpolator());
                    ps1.oneShot(width[randomW], height[randomH], 100, new LinearInterpolator());

                    ParticleSystem ps2 = new ParticleSystem(context.get(), 50, randDrawable2, TIME_TO_LIVE);
                    ps2.setScaleRange(0.7f, 1.3f);
                    ps2.setSpeedRange(0.1f, 0.25f);
                    ps2.setRotationSpeedRange(90, 180);
                    ps2.setFadeOut(200, new AccelerateInterpolator());
                    ps2.oneShot(width[randomW], height[randomH], 100, new LinearInterpolator());


                    ParticleSystem ps3 = new ParticleSystem(context.get(), 50, randDrawable3, TIME_TO_LIVE);
                    ps3.setScaleRange(0.7f, 1.3f);
                    ps3.setSpeedRange(0.1f, 0.25f);
                    ps3.setRotationSpeedRange(90, 180);
                    ps3.setFadeOut(200, new AccelerateInterpolator());
                    ps3.oneShot(width[randomW], height[randomH], 100, new LinearInterpolator());

                    particleSystems.add(ps1);
                    particleSystems.add(ps2);
                    particleSystems.add(ps3);
                }
            });
        }
    }

    public void stopAnimation() {
        for (ParticleSystem ps : particleSystems) {
            ps.stopEmitting();
            ps.cancel();
        }
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
}
