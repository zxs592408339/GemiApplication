package com.example.administrator.gemiapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.myrequest.GsonRequest;
import com.android.volley.myvolleytool.RequestManager;
import com.example.administrator.gemiapplication.json.GemiNear;
import com.example.administrator.gemiapplication.json.Info;
import com.example.administrator.gemiapplication.json.MainKey;

import java.util.ArrayList;
import java.util.List;

public class GemiMainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mGemiMainViewPager;
    private ImageView mGemiMainTenImg,mGemiMainOneImg;
    private GemiMainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gemi_main);
        mGemiMainViewPager = (ViewPager) findViewById(R.id.gemi_main_viewpager);
        mGemiMainTenImg = (ImageView) findViewById(R.id.gemi_main_ten_img);
        mGemiMainOneImg = (ImageView) findViewById(R.id.gemi_main_one_img);
        mGemiMainViewPager.addOnPageChangeListener(this);
        adapter = new GemiMainAdapter(getSupportFragmentManager());
        mGemiMainViewPager.setAdapter(adapter);
        getJson();
    }

    public void getJson() {
        String url = "http://www.warmtel.com:8080/maininit";
        GsonRequest<GemiNear> gsonRequest = new GsonRequest<>(url, GemiNear.class, new Response.Listener<GemiNear>() {
            @Override
            public void onResponse(GemiNear response) {
                Info info = response.getInfo();
                List<MainKey> mainKeyList = info.getMainKey();
                adapter.setData(mainKeyList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestManager.addRequest(gsonRequest, this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int number = position+1;
        mGemiMainTenImg.setImageResource(setNoImage(number/10));
        mGemiMainOneImg.setImageResource(setNoImage(number%10));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int setNoImage(int number){
        int image = 0;
        switch (number){
            case 0:
                image = R.drawable.favor_page_0;
                break;
            case 1:
                image = R.drawable.favor_page_1;
                break;
            case 2:
                image = R.drawable.favor_page_2;
                break;
            case 3:
                image = R.drawable.favor_page_3;
                break;
            case 4:
                image = R.drawable.favor_page_4;
                break;
            case 5:
                image = R.drawable.favor_page_5;
                break;
            case 6:
                image = R.drawable.favor_page_6;
                break;
            case 7:
                image = R.drawable.favor_page_7;
                break;
            case 8:
                image = R.drawable.favor_page_8;
                break;
            case 9:
                image = R.drawable.favor_page_9;
                break;
        }
        return image;
    }

    class GemiMainAdapter extends FragmentStatePagerAdapter {
        private List<MainKey> mainKeyList = new ArrayList<>();

        public GemiMainAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(List<MainKey> mainKeyList) {
            this.mainKeyList = mainKeyList;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            MainKey mainKey = mainKeyList.get(position);
            String nrl = mainKey.getBigpicUrl();
            return GemiMainFragment.newInstance(nrl);
        }

        @Override
        public int getCount() {
            return mainKeyList.size();
        }
    }
}
