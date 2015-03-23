package com.innblue.modernartui;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    //initial value for RED and GREEN for the tile , BLUE are driven by progress in onProgressChanged
    private final static Map<Integer, Integer> rectangleToStartingColorMapping = Collections.unmodifiableMap(new HashMap<Integer, Integer>() {{
        put(R.id.rectimage2, 0);
        put(R.id.rectimage3, 60);
        put(R.id.rectimage4, 120);
        put(R.id.rectimage5, 180);
    }});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        MoMaDialogFragment moMaDialogFragment = new MoMaDialogFragment();
        moMaDialogFragment.setRetainInstance(true);
        moMaDialogFragment.show(fm, "fragment_moma_dialog");

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //set initial colors
            setColors(rootView, 0);

            SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //for each rectangle (but white, which is not in the mapping) change color of  BLUE components to "progress" value
                    setColors(rootView, progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            return rootView;
        }

        //set colors (called initially and the on each progress change)
        private void setColors(View rootView, int progress) {
            for (Map.Entry<Integer, Integer> entry : rectangleToStartingColorMapping.entrySet()) {
                //find rectangle
                ImageView rectangle = (ImageView) rootView.findViewById(entry.getKey());
                //change color
                rectangle.setBackgroundColor(Color.rgb(entry.getValue(), entry.getValue(), progress));
            }
        }
    }

}
