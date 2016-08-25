package com.example.administrator.gemiapplication.chear;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.myrequest.UTF8StringRequest;
import com.android.volley.myvolleytool.RequestManager;
import com.example.administrator.gemiapplication.R;
import com.example.administrator.gemiapplication.chear.json.AdvertisingKey;
import com.example.administrator.gemiapplication.chear.json.Cheap;
import com.example.administrator.gemiapplication.chear.json.CheapInfo;
import com.example.administrator.gemiapplication.chear.json.Hotkey;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

public class GemiCheapActivity extends AppCompatActivity {
    private GridView mGemiCheapGridView;
    private List<AdvertisingKey> advertisingKeyList;
    private List<Hotkey> hotkeyList;
    private GemiCheapAdapter adapter;
    private SliderLayout mSliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gemi_cheap);
        mGemiCheapGridView = (GridView) findViewById(R.id.gemi_cheap_Grid_view);
        mSliderLayout = (SliderLayout) findViewById(R.id.slider_layout);

        getJson();
        adapter = new GemiCheapAdapter(this);
        mGemiCheapGridView.setAdapter(adapter);
    }

    public void setHeaderView() {
        if (mSliderLayout != null) {
            mSliderLayout.removeAllSliders();
        }
        for (AdvertisingKey advertisingKey : advertisingKeyList) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .image(advertisingKey.getPicUrl())
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mSliderLayout.addSlider(textSliderView);
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom); //指示器位置

    }

    public void getJson() {
        String url = "http://www.warmtel.com:8080/keyConfig";
        UTF8StringRequest utf8StringRequest = new UTF8StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Cheap cheap = gson.fromJson(response, Cheap.class);
                CheapInfo info = cheap.getInfo();
                advertisingKeyList = info.getAdvertisingKey();
                hotkeyList = info.getHotkey();
                adapter.setHotkeyList(hotkeyList);
                setHeaderView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestManager.addRequest(utf8StringRequest, this);
    }

    class GemiCheapAdapter extends BaseAdapter {
        List<Hotkey> hotkeyList = new ArrayList<>();
        LayoutInflater layoutInflater;

        public GemiCheapAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setHotkeyList(List<Hotkey> hotkeyList) {
            this.hotkeyList = hotkeyList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return hotkeyList.size();
        }

        @Override
        public Object getItem(int i) {
            return hotkeyList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 1;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.layout_gemi_cheap_item, null);
                TextView textView = (TextView) view.findViewById(R.id.gemi_cheap_item);
                view.setTag(textView);
            }
            Hotkey item = (Hotkey) getItem(i);
            TextView textView = (TextView) view.getTag();
            textView.setText(item.getValue());
            return view;
        }
    }
}
