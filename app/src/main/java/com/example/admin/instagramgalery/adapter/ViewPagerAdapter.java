package com.example.admin.instagramgalery.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.admin.instagramgalery.R;
import com.example.admin.instagramgalery.activity.MainActivity;
import com.example.admin.instagramgalery.fragment.FragementcaprureVideo;
import com.example.admin.instagramgalery.fragment.Fragementcaprureimage;
import com.example.admin.instagramgalery.fragment.Fragementgalery;

/**
 * Created by admin on 12/1/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
private  Context context ;
    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context ;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragementgalery tab1      = new Fragementgalery();
                return tab1;
            case 1:
                Fragementcaprureimage tab2 = new Fragementcaprureimage();
                return tab2;
            case 2:
                FragementcaprureVideo tab3 = new FragementcaprureVideo();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = context.getString(R.string.tab_gallery);
        }
        else if (position == 1)
        {
            title = context.getString(R.string.tab_photo);
        }
        else if (position == 2)
        {
            title = context.getString(R.string.tab_video);
        }
        return title;
    }
}
