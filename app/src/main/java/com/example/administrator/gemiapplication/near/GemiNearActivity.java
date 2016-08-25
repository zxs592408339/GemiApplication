package com.example.administrator.gemiapplication.near;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.myrequest.UTF8StringRequest;
import com.android.volley.myvolleytool.RequestManager;
import com.android.volley.toolbox.ImageLoader;
import com.example.administrator.gemiapplication.R;
import com.example.administrator.gemiapplication.near.json.Around;
import com.example.administrator.gemiapplication.near.json.Info;
import com.example.administrator.gemiapplication.near.json.Merchant;
import com.example.administrator.gemiapplication.near.screenjson.AreaKey;
import com.example.administrator.gemiapplication.near.screenjson.Infos;
import com.example.administrator.gemiapplication.near.screenjson.Screen;
import com.google.gson.Gson;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopOneListView;
import com.warmtel.expandtab.PopTwoListView;

import java.util.ArrayList;
import java.util.List;

public class GemiNearActivity extends AppCompatActivity {
    private ListView mGemiNearListView;
    private ExpandPopTabView mExpandPopTabView;
    private GemiNearAdapter adapter;
    List<KeyValueBean> areaKeyList = new ArrayList<>();
    List<ArrayList<KeyValueBean>> circlesBeanList = new ArrayList<>();
    List<KeyValueBean> distanceKeyList;
    List<KeyValueBean> industryKeyList;
    List<KeyValueBean> sortKeyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gemi_near);
        mGemiNearListView = (ListView) findViewById(R.id.gemi_near_list_view);
        mExpandPopTabView = (ExpandPopTabView) findViewById(R.id.expand_tab_view);
        adapter = new GemiNearAdapter(this);
        mGemiNearListView.setAdapter(adapter);
        getScreenJson();
        getJson();
    }

    public void getScreenJson() {
        String screenUrl = "http://www.warmtel.com:8080/configs";
        UTF8StringRequest utf8StringRequest = new UTF8StringRequest(Request.Method.GET, screenUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        utf8StringRequest.setShouldCache(false);
        RequestManager.addRequest(utf8StringRequest, this);
    }

    public void setJson(String response) {
        Gson gson = new Gson();
        Screen screen = gson.fromJson(response, Screen.class);
        Infos info = screen.getInfo();
        distanceKeyList = info.getDistanceKey();
        industryKeyList = info.getIndustryKey();
        sortKeyList = info.getSortKey();

        List<AreaKey> areaKeyList = info.getAreaKey();
        for (AreaKey configAreaDTO : areaKeyList) {
            KeyValueBean keyValueBean = new KeyValueBean();
            keyValueBean.setKey(configAreaDTO.getKey());
            keyValueBean.setValue(configAreaDTO.getValue());
            GemiNearActivity.this.areaKeyList.add(keyValueBean);

            ArrayList<KeyValueBean> childrenLists = new ArrayList<>();
            for (KeyValueBean keyValueBean1 : configAreaDTO.getCircles()) {
                childrenLists.add(keyValueBean1);
            }
            circlesBeanList.add(childrenLists);
        }

        addItem(mExpandPopTabView, GemiNearActivity.this.areaKeyList, circlesBeanList, "四川", "成都", "全部区域");
        addItem(mExpandPopTabView, distanceKeyList, distanceKeyList.get(0).getKey(), distanceKeyList.get(0).getValue());
        addItem(mExpandPopTabView, industryKeyList, industryKeyList.get(0).getKey(), industryKeyList.get(0).getValue());
        addItem(mExpandPopTabView, sortKeyList, sortKeyList.get(0).getKey(), sortKeyList.get(0).getValue());
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                Toast.makeText(GemiNearActivity.this, key + " " + value, Toast.LENGTH_SHORT).show();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> parentLists,
                        List<ArrayList<KeyValueBean>> childrenListLists, String defaultParentSelect, String defaultChildSelect, String defaultShowText) {
        PopTwoListView popTwoListView = new PopTwoListView(this);
        popTwoListView.setDefaultSelectByValue(defaultParentSelect, defaultChildSelect);
        popTwoListView.setCallBackAndData(expandTabView, parentLists, childrenListLists, new PopTwoListView.OnSelectListener() {
            @Override
            public void getValue(String showText, String parentKey, String childrenKey) {
                Toast.makeText(GemiNearActivity.this, showText + " " + parentKey + " " + childrenKey, Toast.LENGTH_SHORT).show();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popTwoListView);
    }

    public void getJson() {
        String url = "http://www.warmtel.com:8080/around";
        UTF8StringRequest utf8String = new UTF8StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Around around = gson.fromJson(response, Around.class);
                Info info = around.getInfo();
                List<Merchant> merchantList = info.getMerchants();
                adapter.setList(merchantList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestManager.addRequest(utf8String, this);
    }

    class GemiNearAdapter extends BaseAdapter {
        List<Merchant> list = new ArrayList<>();
        LayoutInflater layoutInflater;

        public GemiNearAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setList(List<Merchant> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.layout_gemi_near, null);
                ImageView internetImg = (ImageView) convertView.findViewById(R.id.internet_img);
                ImageView nearGroupImg = (ImageView) convertView.findViewById(R.id.near_group_img);
                ImageView nearCardImg = (ImageView) convertView.findViewById(R.id.near_card_img);
                ImageView nearTicketImg = (ImageView) convertView.findViewById(R.id.near_ticket_img);
                TextView titleView = (TextView) convertView.findViewById(R.id.internet_title);
                TextView periodView = (TextView) convertView.findViewById(R.id.internet_period);
                TextView addressView = (TextView) convertView.findViewById(R.id.internet_address);
                TextView infoMapView = (TextView) convertView.findViewById(R.id.info_map_txt);
                MerchantKeyBean merchantKeyBean = new MerchantKeyBean();
                merchantKeyBean.internetImg = internetImg;
                merchantKeyBean.nearGroupImg = nearGroupImg;
                merchantKeyBean.nearCardImg = nearCardImg;
                merchantKeyBean.nearTicketImg = nearTicketImg;
                merchantKeyBean.titleView = titleView;
                merchantKeyBean.periodView = periodView;
                merchantKeyBean.addressView = addressView;
                merchantKeyBean.infoMapView = infoMapView;
                convertView.setTag(merchantKeyBean);
            }
            Merchant merchantKey = (Merchant) getItem(position);
            MerchantKeyBean merchantKeyBean = (MerchantKeyBean) convertView.getTag();

            String httpUrl = merchantKey.getPicUrl();
            ImageLoader imageLoader = RequestManager.getImageLoader();
            imageLoader.get(httpUrl, ImageLoader.getImageListener(merchantKeyBean.internetImg, android.R.drawable.btn_star, android.R.drawable.ic_delete));

            merchantKeyBean.nearGroupImg.setImageBitmap(merchantKey.getGroupType().equals("YES") ? BitmapFactory.decodeResource(getResources(), R.drawable.near_group) : null);
            merchantKeyBean.nearCardImg.setImageBitmap(merchantKey.getCardType().equals("YES") ? BitmapFactory.decodeResource(getResources(), R.drawable.near_card) : null);
            merchantKeyBean.nearTicketImg.setImageBitmap(merchantKey.getCouponType().equals("YES") ? BitmapFactory.decodeResource(getResources(), R.drawable.near_ticket) : null);
            merchantKeyBean.titleView.setText(merchantKey.getName());
            Log.e("", merchantKey.getName());
            merchantKeyBean.periodView.setText(merchantKey.getCoupon());
            merchantKeyBean.addressView.setText(merchantKey.getLocation());
            merchantKeyBean.infoMapView.setText(merchantKey.getDistance());
            return convertView;
        }

    }

    class MerchantKeyBean {
        ImageView internetImg, nearGroupImg, nearCardImg, nearTicketImg;
        TextView titleView, periodView, addressView, infoMapView;
    }
}
