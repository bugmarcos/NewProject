package com.foodapp.foodnearme;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.Toast;

import com.foodapp.foodnearme.utils.UserDataUtil;

import java.util.Map;


public class SplashActivity extends ActionBarActivity implements LoginFrag.OnFragmentInteractionListener {
    GestureDetector gestureDetector;
    Runnable myrunnable;
    Handler handler = new Handler();
    boolean shouldLogin = true;
    boolean shouldTouch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gestureDetector = new GestureDetector(SplashActivity.this, new GestureListener());
        if (savedInstanceState == null) {
            getSupportActionBar().hide();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        myrunnable = new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                if(shouldLogin)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFrag(getBaseContext())).commit();
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        handler.postDelayed(myrunnable, 4000);
        Map<String,String> map = (Map<String, String>) UserDataUtil.getAllData(SplashActivity.this);
        if(map.containsKey(UserDataUtil.USER_ID) && map.get(UserDataUtil.USER_ID)!=null)
        {

            shouldLogin = false;
            shouldTouch = true;
        }
        Toast.makeText(getApplicationContext(),map.size()+"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if(shouldTouch){
                if(shouldLogin)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFrag(getBaseContext())).commit();
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                handler.removeCallbacks(myrunnable);

            }


            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
            View rootView = inflater.inflate(R.layout.fragment_splash, container, false);


            return rootView;
        }


    }
}
