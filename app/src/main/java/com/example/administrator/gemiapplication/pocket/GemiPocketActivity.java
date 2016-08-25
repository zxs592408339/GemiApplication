package com.example.administrator.gemiapplication.pocket;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.myrequest.UTF8StringRequest;
import com.android.volley.myvolleytool.RequestManager;
import com.android.volley.toolbox.ImageLoader;
import com.example.administrator.gemiapplication.R;
import com.example.administrator.gemiapplication.json.GemiNear;
import com.example.administrator.gemiapplication.json.Info;
import com.example.administrator.gemiapplication.json.MainKey;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GemiPocketActivity extends AppCompatActivity {
    private ListView mGemiPocketListView;
    private GemiPocketAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gemi_pocket);
        mGemiPocketListView = (ListView) findViewById(R.id.gemi_pocket_list_view);
        adapter = new GemiPocketAdapter(this);
        mGemiPocketListView.setAdapter(adapter);
        getJson();
    }

    public void getJson() {
        String url = "http://www.warmtel.com:8080/maininit";
        UTF8StringRequest utf8StringRequest = new UTF8StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                GemiNear gemiNear = gson.fromJson(response, GemiNear.class);
                Info info = gemiNear.getInfo();
                List<MainKey> mainKeyList = info.getMainKey();
                adapter.setMainKeyList(mainKeyList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestManager.addRequest(utf8StringRequest, this);
    }

    class GemiPocketAdapter extends BaseAdapter {
        List<MainKey> mainKeyList = new ArrayList<>();
        LayoutInflater layoutInflater;

        public GemiPocketAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setMainKeyList(List<MainKey> mainKeyList) {
            this.mainKeyList = mainKeyList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mainKeyList.size();
        }

        @Override
        public Object getItem(int i) {
            return mainKeyList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.layout_gemi_packet_item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.gemi_pocket_item_img);
                view.setTag(imageView);
            }
            MainKey mainKey = (MainKey) getItem(i);
            ImageView imageView = (ImageView) view.getTag();
            String httpUrl = mainKey.getPicUrl();
            ImageLoader imageLoader = RequestManager.getImageLoader();
            imageLoader.get(httpUrl, ImageLoader.getImageListener(imageView, android.R.drawable.btn_star, android.R.drawable.ic_delete));
            return view;
        }
    }
}
