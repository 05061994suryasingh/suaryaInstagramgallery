package com.example.admin.instagramgalery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.instagramgalery.R;

import butterknife.ButterKnife;


public class FragementcaprureVideo extends Fragment {
    public static FragementcaprureVideo newInstance() {
        return new FragementcaprureVideo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_fragementcaprurevideo, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

}
