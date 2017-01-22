package io.blackbox_vision.materialcalendarview.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

        nextButton.setOnClickListener(v -> {
            if (null != onNextButtonClickListener) {
                onNextButtonClickListener.onNextButtonClick(v);
            }
        });

        backButton.setOnClickListener(v -> {
            if (null != onBackButtonClickListener) {
                onBackButtonClickListener.onBackButtonClick(v);
            }
        });

        titleTextView.setOnClickListener(v -> {
            if (null != onTitleClickListener) {
                onTitleClickListener.onTitleClick();
            }
        });
    }

    public HeaderView setTitle(@Nullable String text) {
        titleTextView.setText(text);
        invalidate();
        return this;
    }

    public HeaderView setTitleColor(int color) {
        titleTextView.setTextColor(color);
        invalidate();
        return this;
    }

    public HeaderView setTypeface(@Nullable Typeface typeface) {
        if (null != typeface) {
            titleTextView.setTypeface(typeface);
        }

        invalidate();
        return this;
    }

    public HeaderView setNextButtonColor(int color) {
        nextButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        invalidate();
        return this;
    }

    public HeaderView setBackButtonColor(int color) {
        backButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        invalidate();
        return this;
    }

    public HeaderView setNextButtonDrawable(@DrawableRes int drawable) {
        nextButton.setImageDrawable(ContextCompat.getDrawable(getContext(), drawable));
        return this;
    }

    public HeaderView setBackButtonDrawable(@DrawableRes int drawable) {
        backButton.setImageDrawable(ContextCompat.getDrawable(getContext(), drawable));
        return this;
    }

    public HeaderView setNextButtonDrawable(@NonNull Drawable drawable) {
        nextButton.setImageDrawable(drawable);
        return this;
    }

    public HeaderView setBackButtonDrawable(@NonNull Drawable drawable) {
        backButton.setImageDrawable(drawable);
        return this;
    }

    public HeaderView setOnTitleClickListener(@Nullable OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
        invalidate();
        return this;
    }

    public HeaderView setOnNextButtonClickListener(@Nullable OnNextButtonClickListener onNextButtonClickListener) {
        this.onNextButtonClickListener = onNextButtonClickListener;
        invalidate();
        return this;
    }

    public HeaderView setOnBackButtonClickListener(@Nullable OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
        invalidate();
        return this;
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
