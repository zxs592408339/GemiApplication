package com.example.administrator.gemiapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.myvolleytool.RequestManager;
import com.android.volley.toolbox.NetworkImageView;

public class GemiMainFragment extends Fragment {
    private String url;

    private NetworkImageView mNetworkImageView;

    public static Fragment newInstance(String url) {
        GemiMainFragment gemiMainFragment = new GemiMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MAIN_KEY", url);
        gemiMainFragment.setArguments(bundle);
        return gemiMainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments() == null ? null : getArguments().getString("MAIN_KEY");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gemi_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNetworkImageView = (NetworkImageView) getView().findViewById(R.id.gemi_main_fragment_img);
        getJson();
    }

    public void getJson() {
        mNetworkImageView.setImageUrl(url, RequestManager.getImageLoader());
    }
}
