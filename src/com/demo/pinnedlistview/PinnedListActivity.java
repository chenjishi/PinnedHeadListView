package com.demo.pinnedlistview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.demo.pinnedlistview.library.PinnedHeadListView;

/**
 * Created by chenjishi on 14-8-27.
 */
public class PinnedListActivity extends Activity implements AbsListView.OnScrollListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        MyListAdapter listAdapter = new MyListAdapter(this);

        PinnedHeadListView pinnedHeadListView = (PinnedHeadListView) findViewById(R.id.list_view);
        pinnedHeadListView.addHeaderView(getHeaderView());
        pinnedHeadListView.setPinnedHeaderView(new PinnedView(this));
        pinnedHeadListView.setAdapter(listAdapter);

        //set scroll listener should be after set adapter
        pinnedHeadListView.setOnScrollListener(this);
    }

    private ImageView getHeaderView() {
        float density = getResources().getDisplayMetrics().density;

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (200 * density)));

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.head);

        return imageView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view instanceof PinnedHeadListView) {
            ((PinnedHeadListView) view).configureHeaderView(firstVisibleItem);
        }
    }

    private static class MyListAdapter extends BaseAdapter {
        private static final int MAX_TYPE_COUNT = 2;

        private static final int TYPE_PINNED_VIEW = 0;
        private static final int TYPE_NORMAL_ITEM = 1;

        private Context mContext;
        private AbsListView.LayoutParams mLayoutParams;

        private String[] mNames = {
                "Arnold", "Barry", "Chuck", "David", "Stas", "Oleg", "Max", "Alex", "Romeo", "Adolf",
                "Ace", "Bandit", "Cha-Cha", "Deuce", "Nokki", "Baron", "Sharik", "Toshka", "SObaka", "Belka", "Strelka", "Zhuchka"};

        public MyListAdapter(Context context) {
            mContext = context;
            float density = context.getResources().getDisplayMetrics().density;

            mLayoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (36 * density));
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? TYPE_PINNED_VIEW : TYPE_NORMAL_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return MAX_TYPE_COUNT;
        }

        @Override
        public int getCount() {
            return mNames.length + 1;
        }

        @Override
        public String getItem(int position) {
            return position == 0 ? " " : mNames[position - 1];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (getItemViewType(position) == TYPE_PINNED_VIEW) {
                return new PinnedView(mContext);
            } else {
                TextView textView;
                String name = getItem(position);
                if (null == convertView) {
                    textView = generateTextView(name, 16.f, 0xFF333333);
                } else {
                    textView = (TextView) convertView;
                }

                textView.setLayoutParams(mLayoutParams);
                textView.setPadding(24, 0, 0, 0);

                return textView;
            }
        }

        private TextView generateTextView(String text, float size, int color) {
            TextView textView = new TextView(mContext);
            textView.setText(text);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            textView.setTextColor(color);
            textView.setGravity(Gravity.CENTER_VERTICAL);

            return textView;
        }
    }
}
