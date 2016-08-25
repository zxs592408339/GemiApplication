package com.example.administrator.gemiapplication.more;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.gemiapplication.R;

import java.util.ArrayList;
import java.util.List;

public class GemiMoreActivity extends AppCompatActivity {
    private GridView mGemiMoreGridView;
    private List<MoreChoose> moreChoosesList = new ArrayList<>();
    private GemiMoreAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gemi_more);
        mGemiMoreGridView = (GridView) findViewById(R.id.gemi_more_grid_view);
        setData();
        adapter = new GemiMoreAdapter(this);
        mGemiMoreGridView.setAdapter(adapter);
        adapter.setMoreChoosesList(moreChoosesList);
    }

    public void setData() {
        inintAdd(R.drawable.personal_center1, "个人中心");
        inintAdd(R.drawable.my_watchlist, "我的关注");
        inintAdd(R.drawable.recently_viewed, "最近浏览");
        inintAdd(R.drawable.about_us, "关于我们");
        inintAdd(R.drawable.system_messages, "系统消息");
        inintAdd(R.drawable.choose_set, "选项设置");
        inintAdd(R.drawable.businesses_publish, "商家发布");
        inintAdd(R.drawable.sugest_icon1, "意见反馈");
    }

    public void inintAdd(int moreChooseImg, String moreChooseTxt){
        MoreChoose moreChoose = new MoreChoose();
        moreChoose.setMoreChooseImg(moreChooseImg);
        moreChoose.setMoreChooseTxt(moreChooseTxt);
        moreChoosesList.add(moreChoose);
    }

    class GemiMoreAdapter extends BaseAdapter {
        private List<MoreChoose> moreChoosesList = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public GemiMoreAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setMoreChoosesList(List<MoreChoose> moreChoosesList){
            this.moreChoosesList = moreChoosesList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return moreChoosesList.size();
        }

        @Override
        public Object getItem(int i) {
            return moreChoosesList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = layoutInflater.inflate(R.layout.layout_gemi_more_item,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.gemi_more_item_img);
                TextView textView = (TextView) view.findViewById(R.id.gemi_more_item_txt);
                MoreChooseBean moreChooseBean = new MoreChooseBean();
                moreChooseBean.imageView = imageView;
                moreChooseBean.textView = textView;
                view.setTag(moreChooseBean);
            }
            MoreChoose moreChoose = (MoreChoose) getItem(i);
            MoreChooseBean moreChooseBean = (MoreChooseBean) view.getTag();
            moreChooseBean.imageView.setImageResource(moreChoose.getMoreChooseImg());
            moreChooseBean.textView.setText(moreChoose.getMoreChooseTxt());
            return view;
        }
    }


    class MoreChooseBean{
        ImageView imageView;
        TextView textView;
    }
}
