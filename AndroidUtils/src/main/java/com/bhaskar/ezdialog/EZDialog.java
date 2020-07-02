package com.bhaskar.ezdialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.bhaskar.utils.R;

public final class EZDialog {

    public static class Builder {

        private String
                title,
                message,
                positiveBtnText,
                negativeBtnText,
                neutralBtnText;

        private int
                backgroundColor,
                headerColor,
                titleDividerColor,
                buttonTextColor,
                titleTextColor,
                messageTextColor,
                fontId, titleImgResId, titleTxtResId;

        private boolean
                cancelOnTouchOutside = false,
                showTitleDivider = true,
                cancelable = false;

        private Activity context;

        private EZDialogListener
                positiveListener,
                negativeListener,
                neutralListener;

        //private Animation animation ;//= Animation.UP; // Default Animation Type. Set null if Animation is not required
        private Animation animation = Animation.UP; // Default Animation Type. Set null if Animation is not required

        public Builder(@NonNull Activity context) {
            this.context = context;
        }

        public Builder setAnimation(Animation animation) {
            this.animation = animation;
            return this;
        }

        public Builder setCancelableOnTouchOutside(boolean cancelOnTouchOutside) {
            this.cancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        /**
         * @param imgResId
         */
        public Builder setTitleImgResId(@DrawableRes int imgResId) {
            this.titleImgResId = imgResId;
            return this;
        }

        /**
         * @param titleTxtStartResId
         */
        public Builder setTitleTxtStartResId(@DrawableRes int titleTxtStartResId) {
            this.titleTxtResId = titleTxtStartResId;
            return this;
        }

        /***
         *
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCustomFont(int fontId) {
            this.fontId = fontId;
            return this;
        }

        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(@NonNull String message) {
            this.message = message;
            return this;
        }

        public Builder showTitleDivider(boolean showTitleDivider) {
            this.showTitleDivider = showTitleDivider;
            return this;
        }

        public Builder setTitleDividerLineColor(int titleDividerColor) {
            this.titleDividerColor = titleDividerColor;
            return this;
        }

        public Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder setMessageTextColor(int messageTextColor) {
            this.messageTextColor = messageTextColor;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setHeaderColor(int headerColor) {
            this.headerColor = headerColor;
            return this;
        }

        public Builder setButtonTextColor(int buttonTextColor) {
            this.buttonTextColor = buttonTextColor;
            return this;
        }

        public Builder setPositiveBtnText(@NonNull String positiveBtnText) {
            this.positiveBtnText = positiveBtnText;
            return this;
        }

        public Builder setNegativeBtnText(@NonNull String negativeBtnText) {
            this.negativeBtnText = negativeBtnText;
            return this;
        }

        public Builder setNeutralBtnText(@NonNull String neutralBtnText) {
            this.neutralBtnText = neutralBtnText;
            return this;
        }

        public Builder OnPositiveClicked(@NonNull EZDialogListener positiveListener) {
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder OnNegativeClicked(@NonNull EZDialogListener negativeListener) {
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder OnNeutralClicked(@NonNull EZDialogListener neutralListener) {
            this.neutralListener = neutralListener;
            return this;
        }

        public void build() {

            try {

                if (context == null || context.isFinishing()) {
                    return;
                }

                int style;

                if (animation == Animation.UP) style = R.style.EzDialogUpTheme;
                else if (animation == Animation.DOWN) style = R.style.EzDialogDownTheme;
                else style = 0;

                final AlertDialog dialog = style != 0 ?
                        new AlertDialog.Builder(context, style).create() :
                        new AlertDialog.Builder(context).create();

                dialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
                dialog.setCancelable(cancelable);

                @SuppressLint("InflateParams")
                View v = LayoutInflater.from(context).inflate(R.layout.ez_dialog_layout_constraint, null);
                dialog.setView(v);

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                }

                TextView tvTitle = v.findViewById(R.id.title),
                        tvMessage = v.findViewById(R.id.message);

                Button btnNegative = v.findViewById(R.id.cancel),
                        btnPositive = v.findViewById(R.id.confirm),
                        btnNeutral = v.findViewById(R.id.neutral);

                ConstraintLayout llRoot = v.findViewById(R.id.ll_dialog_sub_view);

                View titleDivider = v.findViewById(R.id.title_divider);

                titleDivider.setVisibility(showTitleDivider ? View.VISIBLE : View.INVISIBLE);

                if (fontId != 0) {
                    Typeface tf = ResourcesCompat.getFont(context, fontId);
                    tvTitle.setTypeface(tf);
                    tvMessage.setTypeface(tf);
                    btnPositive.setTypeface(tf);
                    btnNegative.setTypeface(tf);
                    btnNeutral.setTypeface(tf);
                }

                if (titleImgResId != 0) {
                    tvTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(titleImgResId, 0, 0, 0);
                }

                tvTitle.setText(title);
                tvMessage.setText(message);

                if (backgroundColor != 0) {
                    llRoot.setBackgroundColor(backgroundColor);
                }

                if (headerColor != 0) {
                    tvTitle.setBackgroundColor(headerColor);
                }

                if (titleTextColor != 0) {
                    tvTitle.setTextColor(titleTextColor);
                }

                if (messageTextColor != 0) {
                    tvMessage.setTextColor(messageTextColor);
                }

                if (titleDividerColor != 0) {
                    titleDivider.setBackgroundColor(titleDividerColor);
                }

                if (buttonTextColor != 0) {
                    btnPositive.setTextColor(buttonTextColor);
                    btnNegative.setTextColor(buttonTextColor);
                    btnNeutral.setTextColor(buttonTextColor);
                }

                if (positiveBtnText != null) {
                    btnPositive.setText(positiveBtnText);
                }

                if (negativeBtnText != null) {
                    btnNegative.setText(negativeBtnText);
                }

                if (neutralBtnText != null) {
                    btnNeutral.setText(neutralBtnText);
                }

                if (positiveListener != null) {
                    btnPositive.setVisibility(View.VISIBLE);
                    btnPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!context.isFinishing()) {
                                dialog.dismiss();
                            }
                            positiveListener.OnClick();
                        }
                    });

                } else {
                    btnPositive.setVisibility(View.GONE);
                }

                if (negativeListener != null) {
                    btnNegative.setVisibility(View.VISIBLE);
                    btnNegative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!context.isFinishing()) {
                                dialog.dismiss();
                            }
                            negativeListener.OnClick();
                        }
                    });
                } else {
                    btnNegative.setVisibility(View.GONE);
                }

                if (neutralListener != null) {
                    btnNeutral.setVisibility(View.VISIBLE);
                    btnNeutral.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!context.isFinishing()) {
                                dialog.dismiss();
                            }
                            neutralListener.OnClick();
                        }
                    });
                } else {
                    btnNeutral.setVisibility(View.GONE);
                }
                if (!context.isFinishing()) {
                    dialog.show();
                }
            } catch (Exception e) {
                Log.e("EZDialog", e.getMessage() + "");
            }
        }
    }
}
