package com.foodapp.foodnearme;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashActivity extends ActionBarActivity{

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private Context context;
        public ScreenSlidePagerAdapter(FragmentManager fm,Context c) {
            super(fm);
            context=c;
        }

        @Override
        public Fragment getItem(int position) {
            AccountManager am = AccountManager.get(context);
            Account[] accounts = am.getAccounts();
            String email="";
            for (Account ac : accounts) {
                if(ac.type.equals("com.google"))
                {
                    email=ac.name;
                }
            }
            Bundle bun=new Bundle();
            bun.putInt("img",imgIds[position]);
            bun.putString("email",email);
            ScreenSlide sc=new ScreenSlide();
            sc.setArguments(bun);
            return sc;
        }

        @Override
        public int getCount() {
            return imgIds.length;
        }
    }

    private static int[] imgIds=new int[]{R.drawable.anushka,R.drawable.karena,R.drawable.anushka,R.drawable.karena,R.drawable.anushka};
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),this.getBaseContext());
        mPager.setAdapter(mPagerAdapter);
    }

}
