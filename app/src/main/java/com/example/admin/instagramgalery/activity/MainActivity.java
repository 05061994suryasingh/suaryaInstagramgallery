package com.example.admin.instagramgalery.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;
import com.example.admin.instagramgalery.R;
import com.example.admin.instagramgalery.adapter.ViewPagerAdapter;
import com.example.admin.instagramgalery.modules.PermissionModule;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class MainActivity extends AppCompatActivity {
     private   ViewPagerAdapter viewPagerAdapter ;
    @BindView(R.id.mMainTabLayout)     TabLayout mMainTabLayout;
    @BindView(R.id.viewpager)          ViewPager viewPager;
    @BindString(R.string.tab_gallery)  String _tabGallery;
    @BindString(R.string.tab_photo)    String _tabPhoto;
    @BindString(R.string.tab_video)    String _tabVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PermissionModule permissionModule = new PermissionModule(this);
        permissionModule.checkPermissions();
        viewPagerAdapter = new ViewPagerAdapter(this ,getSupportFragmentManager());
        Log.e("MainActivity",""+viewPager);
        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);
        mMainTabLayout.setupWithViewPager(viewPager);
        mMainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("","position"+tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
            }
}
