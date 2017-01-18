package io.blackbox_vision.materialcalendarview.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.blackbox_vision.materialcalendarview.R;


public final class HeaderView extends RelativeLayout {
    private ImageView nextButton;
    private ImageView backButton;

    private TextView titleTextView;

    @Nullable
    private OnTitleClickListener onTitleClickListener;

    @Nullable
    private OnNextButtonClickListener onNextButtonClickListener;

    @Nullable
    private OnBackButtonClickListener onBackButtonClickListener;

    public HeaderView(Context context) {
        this(context, null, 0);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleArr) {
        super(context, attrs, defStyleArr);
        drawHeaderView();
    }

    private void drawHeaderView() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.header_view, this, true);

        nextButton = (ImageView) view.findViewById(R.id.right_button);
        backButton = (ImageView) view.findViewById(R.id.left_button);

        titleTextView = (TextView) view.findViewById(R.id.date_title);

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onNextButtonClickListener) {
                    onNextButtonClickListener.onNextButtonClick(v);
                }
            }
        });

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onBackButtonClickListener) {
                    onBackButtonClickListener.onBackButtonClick(v);
                }
            }
        });

        titleTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onTitleClickListener) {
                    onTitleClickListener.onTitleClick();
                }
            }
        });
    }

    public void setTitle(@Nullable String text) {
        titleTextView.setText(text);
        invalidate();
    }

    public void setTitleColor(int color) {
        titleTextView.setTextColor(color);
        invalidate();
    }

    public void setTypeface(@Nullable Typeface typeface) {
        if (null != typeface) {
            titleTextView.setTypeface(typeface);
        }

        invalidate();
    }

    public void setNextButtonColor(int color) {
        nextButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        invalidate();
    }

    public void setBackButtonColor(int color) {
        backButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        invalidate();
    }

    public void setNextButtonDrawable(@NonNull Drawable drawable) {
        nextButton.setImageDrawable(drawable);
    }

    public void setBackButtonDrawable(@NonNull Drawable drawable) {
        backButton.setImageDrawable(drawable);
    }

    public void setOnTitleClickListener(@Nullable OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
        invalidate();
    }

    public void setOnNextButtonClickListener(@Nullable OnNextButtonClickListener onNextButtonClickListener) {
        this.onNextButtonClickListener = onNextButtonClickListener;
        invalidate();
    }

    public void setOnBackButtonClickListener(@Nullable OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
        invalidate();
    }

    public interface OnTitleClickListener {

        void onTitleClick();
    }

    public interface OnNextButtonClickListener {

        void onNextButtonClick(@NonNull View v);
    }

    public interface OnBackButtonClickListener {

        void onBackButtonClick(@NonNull View v);
    }
}
