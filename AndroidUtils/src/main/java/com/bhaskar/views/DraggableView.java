package com.bhaskar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DraggableView extends FrameLayout {
    private float dX;
    private float dY;
    private int lastAction;
    private boolean isDraggingEnabled = false;
    private View.OnClickListener clickListener;

    public DraggableView(@NonNull Context context) {
        super(context);
    }

    public DraggableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DraggableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean isDraggingEnabled() {
        return isDraggingEnabled;
    }

    public void setOnClickListener(@NonNull View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setDraggingEnabled(@NonNull final View parentView, boolean draggingEnabled) {
        isDraggingEnabled = draggingEnabled;
        if (isDraggingEnabled) {

            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            dX = view.getX() - event.getRawX();
                            dY = view.getY() - event.getRawY();
                            lastAction = MotionEvent.ACTION_DOWN;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            view.setY(event.getRawY() + dY);
                            view.setX(event.getRawX() + dX);
                            lastAction = MotionEvent.ACTION_MOVE;

                            /*final float dx = x1 - mLastTouchX;
                            final float dy = y1 - mLastTouchY;
                            // Make sure we will still be the in parent's container
                            Rect parent = new Rect(0, 0,
                                    parentView.getWidth(), parentView.getHeight());

                            int newLeft = (int) (view.getX() + dX),
                                    newTop = (int) (view.getY() + dY),
                                    newRight = newLeft + view.getWidth(),
                                    newBottom = newTop + view.getHeight();

                            if (parent.contains(newLeft, newTop, newRight, newBottom)) {
                                view.setY(event.getRawY() + dY);
                                view.setX(event.getRawX() + dX);
                            }*/
                            break;
                        case MotionEvent.ACTION_UP:
                            if (lastAction == MotionEvent.ACTION_DOWN)
                                if (clickListener != null) {
                                    clickListener.onClick(view);
                                }
                            break;
                        default:
                            return false;
                    }
                    return true;
                }
            });
        }
    }
}
