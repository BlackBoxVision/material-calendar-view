package io.blackbox_vision.materialcalendarview.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.blackbox_vision.materialcalendarview.R;

/**
 * @author jonatan.salas
 */
public class HeaderView extends RelativeLayout {
    private static final String LOG = HeaderView.class.getSimpleName();

    private TextView mMonthTitleView;
    private ImageView mNextButton;
    private ImageView mBackButton;

    private int currentMonthIndex = 0;

    public HeaderView(Context context) {
        this(context, null, 0);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleArr) {
        super(context, attrs, defStyleArr);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode()) {
                return;
            }
        }

        getAttributes(context, attrs);
        init(context);
    }

    private void getAttributes(Context context, AttributeSet attrs) {

    }

    private void init(Context context) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View root = inflater.inflate(R.layout.material_calendar_title, this, false);

        initBackButton(root);
        initMonthTitleView(root);
        initNextButton(root);
    }

    private void initMonthTitleView(View root) {
        if (null != root) {

        }
    }

    private void initBackButton(View root) {
        if (null != root) {

        }
    }

    private void initNextButton(View root) {
        if (null != root) {

        }
    }
}
