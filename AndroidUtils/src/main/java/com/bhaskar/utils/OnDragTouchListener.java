package com.bhaskar.utils;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public final class OnDragTouchListener implements View.OnTouchListener {

    private static final String TAG = OnDragTouchListener.class.getSimpleName();

    /**
     * Callback used to indicate when the drag is finished
     */
    public interface OnDragActionListener {
        /**
         * Called when drag event is started
         *
         * @param view The view dragged
         */
        void onDragStart(View view);

        /**
         * Called when drag event is completed
         *
         * @param view The view dragged
         */
        void onDragEnd(View view);
    }

    private View mView;
    private View mParent;
    private boolean isDragging;
    private boolean isInitialized = false;

    private int width;
    private float xWhenAttached;
    private float maxLeft;
    private float maxRight;
    private float dX;

    private float dXMove;
    private float dYMove;
    private float dXDown;
    private float dYDown;

    private int height;
    private float yWhenAttached;
    private float maxTop;
    private float maxBottom;
    private float dY;

    private OnDragActionListener mOnDragActionListener;
    private View.OnClickListener onDragViewClickListener;
    private int lastAction;

    /**
     * @param view
     */
    public OnDragTouchListener(@NonNull View view) {
        this(view, (View) view.getParent(), null);
    }

    /**
     * @param view
     * @param parent
     */
    public OnDragTouchListener(@NonNull View view, @NonNull View parent) {
        this(view, parent, null);
    }

    /***
     *
     * @param view
     * @param onDragActionListener
     */
    public OnDragTouchListener(@NonNull View view, @NonNull OnDragActionListener onDragActionListener) {
        this(view, (View) view.getParent(), onDragActionListener);
    }

    /**
     * @param view
     * @param parent
     * @param onDragActionListener
     */
    public OnDragTouchListener(@NonNull View view, @NonNull View parent, @NonNull OnDragActionListener onDragActionListener) {
        initListener(view, parent);
        setOnDragActionListener(onDragActionListener);
    }

    /**
     * @param onDragActionListener
     */
    public void setOnDragActionListener(OnDragActionListener onDragActionListener) {
        mOnDragActionListener = onDragActionListener;
    }

    /***
     *
     * @param onDragViewClickListener
     */
    public void setOnDragViewClickListener(View.OnClickListener onDragViewClickListener) {
        this.onDragViewClickListener = onDragViewClickListener;
    }

    private void initListener(View view, View parent) {
        mView = view;
        mParent = parent;
        isDragging = false;
        isInitialized = false;
    }

    private void updateBounds() {
        updateViewBounds();
        updateParentBounds();
        isInitialized = true;
    }

    private void updateViewBounds() {
        width = mView.getWidth();
        xWhenAttached = mView.getX();
        dX = 0;

        height = mView.getHeight();
        yWhenAttached = mView.getY();
        dY = 0;
    }

    public void updateParentBounds() {
        maxLeft = 0;
        maxRight = maxLeft + mParent.getWidth();

        maxTop = 0;
        maxBottom = maxTop + mParent.getHeight();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isDragging) {
            float[] bounds = new float[4];
            // LEFT
            bounds[0] = event.getRawX() + dX;
            if (bounds[0] < maxLeft) {
                bounds[0] = maxLeft;
            }
            // RIGHT
            bounds[2] = bounds[0] + width;
            if (bounds[2] > maxRight) {
                bounds[2] = maxRight;
                bounds[0] = bounds[2] - width;
            }
            // TOP
            bounds[1] = event.getRawY() + dY;
            if (bounds[1] < maxTop) {
                bounds[1] = maxTop;
            }
            // BOTTOM
            bounds[3] = bounds[1] + height;
            if (bounds[3] > maxBottom) {
                bounds[3] = maxBottom;
                bounds[1] = bounds[3] - height;
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_CANCEL:
                    lastAction = MotionEvent.ACTION_CANCEL;
                    Log.d(TAG, "Last Action,dXMove,dYMove: " + MotionEvent.ACTION_CANCEL + "," + dXMove + "," + dYMove);
                    onDragFinish();
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "Last Action,dXMove,dYMove: " + MotionEvent.ACTION_UP + "," + dXMove + "," + dYMove);

                    if (onDragViewClickListener != null) {
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            onDragViewClickListener.onClick(v);
                        } else if (Math.abs(dXMove - dXDown) < 10 && Math.abs(dYMove - dYDown) < 10) {
                            onDragViewClickListener.onClick(v);
                        }
                    }
                    lastAction = MotionEvent.ACTION_UP;
                    onDragFinish();
                    break;
                case MotionEvent.ACTION_MOVE:
                    lastAction = MotionEvent.ACTION_MOVE;
                    dXMove = event.getRawX();
                    dYMove = event.getRawY();

                    float bound0 = bounds[0];
                    float bound1 = bounds[1];
                    ////Log.d(TAG, "Last Action,bound0,bound1: " + lastAction + "," + bound0 + "," + bound1);
                    mView.animate().x(bound0).y(bound1).setDuration(0).start();
                    break;
            }
            return true;
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isDragging = true;
                if (!isInitialized) {
                    updateBounds();
                }

                dXDown = event.getRawX();
                dYDown = event.getRawY();

                dX = v.getX() - event.getRawX();
                dY = v.getY() - event.getRawY();

                Log.d(TAG, "Last Action,dXDown,dYDown: " + MotionEvent.ACTION_DOWN + "," + dXDown + "," + dYDown);
                if (mOnDragActionListener != null) {
                    mOnDragActionListener.onDragStart(mView);
                }
                lastAction = MotionEvent.ACTION_DOWN;
                return true;
            }
        }
        return false;
    }

    private void onDragFinish() {
        Log.d(TAG, "onDragFinish()");
        if (mOnDragActionListener != null) {
            mOnDragActionListener.onDragEnd(mView);
        }

        dX = 0;
        dY = 0;
        dXDown = 0;
        dYDown = 0;
        dXMove = 0;
        dYMove = 0;
        isDragging = false;
    }
}
