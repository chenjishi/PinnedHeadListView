package com.demo.pinnedlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by chenjishi on 14-8-27.
 */
public class PinnedView extends LinearLayout implements View.OnClickListener {
    private static final int BASE_TAG_INDEX = 100;

    private static int[] COLORS = {
            0xFF009588,
            0xFFE91D63,
            0xFF4CAF51,
            0xFF3E51B5
    };

    private float mDensity;

    public PinnedView(Context context) {
        super(context);
        init(context);
    }

    public PinnedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mDensity = context.getResources().getDisplayMetrics().density;

        int height = (int) (39 * mDensity);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        setOrientation(HORIZONTAL);
        setLayoutParams(lp);
        setBackgroundColor(0xFFF1F1F1);
        setWeightSum(4.f);

        setupView();
    }

    private void setupView() {
        Context context = getContext();

        int len = COLORS.length;
        for (int i = 0; i < len; i++) {
            LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1.f;
            if (i < len - 1) layoutParams.rightMargin = (int) (6 * mDensity);

            Button button = new Button(context);
            button.setLayoutParams(layoutParams);
            button.setTag(BASE_TAG_INDEX + i);
            button.setText("TAB " + (i + 1));
            button.setBackgroundColor(COLORS[i]);
            button.setTextColor(0xFFFFFFFF);
            button.setOnClickListener(this);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.f);
            button.setGravity(Gravity.CENTER);

            addView(button);
        }
    }

    @Override
    public void onClick(View v) {
        int tag = (Integer) v.getTag();
        tag = tag - BASE_TAG_INDEX;

        Toast.makeText(getContext(), "clicked at " + tag, Toast.LENGTH_SHORT).show();
    }
}
