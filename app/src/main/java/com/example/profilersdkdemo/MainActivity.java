package com.example.profilersdkdemo;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.widget.TextView;

import com.core42matters.android.profiler.Profile;
import com.core42matters.android.profiler.Profiler;


public class MainActivity extends PreferenceActivity {

    TextView debugView;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Profile profile = Profiler.getProfile(MainActivity.this);
            if (profile != null) {
                String debugInfo = profile.getDebugInfo();
                if (debugInfo == null)
                    sendEmptyMessageDelayed(0, 1000);
                else
                    debugView.setText("Debug information:\n" + debugInfo);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        debugView = new TextView(this);
        debugView.setText("loading...");
        debugView.setTypeface(Typeface.MONOSPACE);
        final int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        debugView.setPadding(padding, padding, padding, padding);
        getListView().addFooterView(debugView);

        addPreferencesFromResource(R.xml.settings);

        handler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}
