package com.android.support.v8.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.support.v8.R;
import com.android.support.v8.util.CalendarUtility;

import java.util.Locale;

/**
 * @author jonatan.salas
 */
public class CalendarTitleLayout extends BaseLinearLayout {
    private static final String TYPEFACE_NOT_NULL_MESSAGE = "typeface can't be null!";
    private static final String SIZE_NOT_NULL_MESSAGE = "size can't be null!";

    private static final String DRAWABLE_NOT_NULL_MESSAGE = "drawable can't be null!";
    private static final String DRAWABLE_ID_NOT_ZERO_VALUE = "drawableId can't be zero value";

    private static final String COLOR_NOT_NULL_MESSAGE = "color can't be null!";
    private static final String COLOR_ID_NOT_ZERO_VALUE = "colorId can't be zero value";

    private ImageView nextButton;
    private ImageView backButton;
    private TextView dateTitle;

    private int monthIndex = 0;

    /**
     *
     * @param context
     */
    public CalendarTitleLayout(Context context) {
        super(context);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public CalendarTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CalendarTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public CalendarTitleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     *
     */
    private void init() {
        if (!isInEditMode()) {
            return;
        }

        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.header_view, this, false);

        backButton = (ImageView) view.findViewById(R.id.back_button);
        nextButton = (ImageView) view.findViewById(R.id.next_button);
        dateTitle = (TextView) view.findViewById(R.id.date_title);

        setDateTitleText(monthIndex);

        dateTitle.setEnabled(true);

        backButton.setEnabled(true);
        backButton.setClickable(true);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTitleText(monthIndex--);
                //TODO JS: add interface..
            }
        });

        nextButton.setEnabled(true);
        nextButton.setClickable(true);
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTitleText(monthIndex++);
                //TODO JS: add interface..
            }
        });
    }

    /**
     *
     * @param monthIndex
     */
    private void setDateTitleText(int monthIndex) {
        final String title = CalendarUtility.getDateTitle(monthIndex);
        final String upperCaseTitle = title.toUpperCase(Locale.getDefault());
        dateTitle.setText(upperCaseTitle);
    }

    /**
     *
     * @param typeface
     */
    public void setTitleTextTypeface(Typeface typeface) {
        setTitleTextTypeface(typeface, Typeface.NORMAL);
    }

    /**
     *
     * @param typeface
     * @param style
     */
    public void setTitleTextTypeface(Typeface typeface, int style) {
        if (null != typeface) {
            dateTitle.setTypeface(typeface, style);
        } else {
            throw new IllegalArgumentException(TYPEFACE_NOT_NULL_MESSAGE);
        }
    }

    /**
     *
     * @param size
     */
    public void setTitleTextSize(Float size) {
        if (null != size) {
            dateTitle.setTextSize(size);
            updateLayout();
        } else {
            throw new IllegalArgumentException(SIZE_NOT_NULL_MESSAGE);
        }
    }

    /**
     *
     * @param colorId
     */
    public void setTitleTextColor(@ColorRes int colorId) {
        if (0 != colorId) {
            final Integer textColor = getColor(colorId);
            setTitleTextColor(textColor);
        } else {
            throw new IllegalArgumentException(COLOR_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     *
     * @param color
     */
    public void setTitleTextColor(Integer color) {
        if (null != color) {
            dateTitle.setTextColor(color);
        } else {
            throw new IllegalArgumentException(COLOR_NOT_NULL_MESSAGE);
        }
    }

    /**
     *
     * @param drawableId
     */
    public void setNextButtonDrawable(@DrawableRes Integer drawableId) {
        if (null != drawableId) {
            final Drawable drawable = getDrawable(drawableId);
            setNextButtonDrawable(drawable);
        } else {
            throw new IllegalArgumentException(DRAWABLE_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     *
     * @param drawableId
     */
    public void setBackButtonDrawable(@DrawableRes Integer drawableId) {
        if (null != drawableId) {
            final Drawable drawable = getDrawable(drawableId);
            setBackButtonDrawable(drawable);
        } else {
            throw new IllegalArgumentException(DRAWABLE_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     *
     * @param drawable
     */
    public void setNextButtonDrawable(Drawable drawable) {
        if (null != drawable) {
            nextButton.setImageDrawable(drawable);
            updateLayout();
        } else {
            throw new IllegalArgumentException(DRAWABLE_NOT_NULL_MESSAGE);
        }
    }

    /**
     *
     * @param drawable
     */
    public void setBackButtonDrawable(Drawable drawable) {
        if (null != drawable) {
            backButton.setImageDrawable(drawable);
            updateLayout();
        } else {
            throw new IllegalArgumentException(DRAWABLE_NOT_NULL_MESSAGE);
        }
    }

    /**
     *
     * @param colorId
     */
    public void setNextButtonDrawableColor(@ColorRes int colorId) {
        if (0 != colorId) {
            final Integer color = getColor(colorId);
            setNextButtonDrawableColor(color);
        } else {
            throw new IllegalArgumentException(COLOR_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     *
     * @param colorId
     */
    public void setBackButtonDrawableColor(@ColorRes int colorId) {
        if (0 != colorId) {
            final Integer color = getColor(colorId);
            setBackButtonDrawableColor(color);
        } else {
            throw new IllegalArgumentException(COLOR_ID_NOT_ZERO_VALUE);
        }
    }

    /**
     *
     * @param color
     */
    public void setNextButtonDrawableColor(Integer color) {
        if (null != color) {
            backButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        } else {
            throw new IllegalArgumentException(COLOR_NOT_NULL_MESSAGE);
        }
    }

    /**
     *
     * @param color
     */
    public void setBackButtonDrawableColor(Integer color) {
        if (null != color) {
            backButton.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        } else {
            throw new IllegalArgumentException(COLOR_NOT_NULL_MESSAGE);
        }
    }
}
