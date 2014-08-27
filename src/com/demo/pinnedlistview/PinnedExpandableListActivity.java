package com.demo.pinnedlistview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.demo.pinnedlistview.library.PinnedHeadExpandListView;

/**
 * Created by chenjishi on 14-8-27.
 */
public class PinnedExpandableListActivity extends Activity implements AbsListView.OnScrollListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expand_list_layout);

        MyExpandableListAdapter listAdapter = new MyExpandableListAdapter(this);

        PinnedHeadExpandListView pinnedExpandListView = (PinnedHeadExpandListView) findViewById(R.id.list_view);
        pinnedExpandListView.addHeaderView(getHeaderView(), null, false);
        pinnedExpandListView.setPinnedHeaderView(new PinnedView(this));
        pinnedExpandListView.setAdapter(listAdapter);
        pinnedExpandListView.setOnScrollListener(this);
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
        if (view instanceof PinnedHeadExpandListView) {
            ((PinnedHeadExpandListView) view).configureHeaderView(firstVisibleItem);
        }
    }

    private static class MyExpandableListAdapter extends BaseExpandableListAdapter {
        private static final int MAX_TYPE_COUNT = 2;

        private static final int TYPE_PINNED_VIEW = 0;
        private static final int TYPE_NORMAL_ITEM = 1;

        private Context mContext;
        private String[] groups = {"People Names", "Dog Names", "Cat Names", "Fish Names"};
        private String[][] children = {
                {"Arnold", "Barry", "Chuck", "David", "Stas", "Oleg", "Max", "Alex", "Romeo", "Adolf"},
                {"Ace", "Bandit", "Cha-Cha", "Deuce", "Nokki", "Baron", "Sharik", "Toshka", "SObaka", "Belka", "Strelka", "Zhuchka"},
                {"Fluffy", "Snuggles", "Cate", "Yasha", "Bars"},
                {"Goldy", "Bubbles", "Fluffy", "Snuggles", "Guffy", "Snoopy"}
        };

        private AbsListView.LayoutParams mGroupLayoutParams, mChildLayoutParams;

        public MyExpandableListAdapter(Context context) {
            mContext = context;

            float density = mContext.getResources().getDisplayMetrics().density;
            mGroupLayoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (44 * density));
            mChildLayoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (36 * density));
        }

        @Override
        public int getGroupType(int groupPosition) {
            return groupPosition == 0 ? TYPE_PINNED_VIEW : TYPE_NORMAL_ITEM;
        }

        @Override
        public int getGroupTypeCount() {
            return MAX_TYPE_COUNT;
        }

        @Override
        public int getGroupCount() {
            return groups.length + 1;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (groupPosition == 0) {
                return 0;
            } else {
                return children[groupPosition - 1].length;
            }
        }

        @Override
        public String getGroup(int groupPosition) {
            if (groupPosition == 0) {
                return "";
            } else {
                return groups[groupPosition - 1];
            }
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            if (groupPosition == 0) {
                return "";
            } else {
                return children[groupPosition - 1][childPosition];
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (getGroupType(groupPosition) == TYPE_PINNED_VIEW) {
                return new PinnedView(mContext);
            } else {
                TextView textView;

                String groupName = getGroup(groupPosition);

                if (null == convertView) {
                    textView = generateTextView(groupName, 18.f, 0xFF607D8B);
                } else {
                    textView = (TextView) convertView;
                }

                textView.setLayoutParams(mGroupLayoutParams);
                textView.setBackgroundColor(0xFFC9C9C9);
                textView.setPadding(24, 0, 0, 0);
                textView.setTypeface(null, Typeface.BOLD);

                return textView;
            }
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (groupPosition == 0) {
                return null;
            } else {
                TextView textView;

                String childName = getChild(groupPosition, childPosition);
                if (null == convertView) {
                    textView = generateTextView(childName, 16.f, 0xFF333333);
                } else {
                    textView = (TextView) convertView;
                }

                textView.setLayoutParams(mChildLayoutParams);
                textView.setPadding(48, 0, 0, 0);

                return textView;
            }
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
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
