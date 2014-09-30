package com.example.profilersdkdemo;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.TextView;

import com.core42matters.android.profiler.AppIdMissingException;
import com.core42matters.android.profiler.Profile;
import com.core42matters.android.profiler.Profiler;


public class MainActivity extends PreferenceActivity implements LoaderManager.LoaderCallbacks<Profile> {

    TextView debugView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDebugView();
        addPreferencesFromResource(R.xml.settings);

        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    private void setupDebugView() {
        debugView = new TextView(this);
        debugView.setText("loading...");
        debugView.setTypeface(Typeface.MONOSPACE);
        final int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        debugView.setPadding(padding, padding, padding, padding);
        getListView().addFooterView(debugView);
    }

    @Override
    public Loader<Profile> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Profile>(this) {
            @Override
            public Profile loadInBackground() {
                try {
                    return Profiler.getProfileSync(MainActivity.this);
                } catch (AppIdMissingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Profile> profileLoader, Profile profile) {
        if (profile == null) {
            debugView.setText("Inference not available");
        } else {
            debugView.setText("Debug information:\n" + profile.getDebugInfo());
        }
    }

    @Override
    public void onLoaderReset(Loader<Profile> profileLoader) {

    }
}
