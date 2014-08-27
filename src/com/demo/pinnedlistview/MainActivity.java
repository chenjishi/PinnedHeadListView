package com.demo.pinnedlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onListViewButtonClicked(View view) {
        startActivity(new Intent(this, PinnedListActivity.class));
    }

    public void onExpandableListViewButtonClicked(View view) {
        startActivity(new Intent(this, PinnedExpandableListActivity.class));
    }
}
