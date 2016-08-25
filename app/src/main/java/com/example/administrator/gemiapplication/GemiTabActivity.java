package com.example.administrator.gemiapplication;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.gemiapplication.chear.GemiCheapActivity;
import com.example.administrator.gemiapplication.more.GemiMoreActivity;
import com.example.administrator.gemiapplication.near.GemiNearActivity;
import com.example.administrator.gemiapplication.pocket.GemiPocketActivity;

public class GemiTabActivity extends TabActivity {
    private TabHost mTabHost;
    private ImageView mTabMenuImg;
    private TextView mTabMenuTxt;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gemi_tab);
        mTabHost = getTabHost();
        mInflater = LayoutInflater.from(this);
        setData();
    }

    public <T> void getMenuTabView(String tabName, int tabImg, Class<T> t) {
        View view = mInflater.inflate(R.layout.layout_tab_meun, null);
        mTabMenuTxt = (TextView) view.findViewById(R.id.tab_menu_txt);
        mTabMenuImg = (ImageView) view.findViewById(R.id.tab_menu_img);
        mTabMenuTxt.setText(tabName);
        mTabMenuImg.setImageResource(tabImg);
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec("tab");
        tabSpec.setIndicator(view);
        tabSpec.setContent(new Intent(this, t));
        mTabHost.addTab(tabSpec);
    }


    public void setData() {
        getMenuTabView("附近", R.drawable.layout_tab_near, GemiNearActivity.class);
        getMenuTabView("找便宜", R.drawable.layout_tab_cheap, GemiCheapActivity.class);
        getMenuTabView("优惠", R.drawable.tab_favor, GemiMainActivity.class);
        getMenuTabView("口袋", R.drawable.layout_tab_pocket, GemiPocketActivity.class);
        getMenuTabView("更多", R.drawable.layout_tab_more, GemiMoreActivity.class);
    }
}
