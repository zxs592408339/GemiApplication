package com.example.administrator.gemiapplication.near.screenjson;

import com.warmtel.expandtab.KeyValueBean;

import java.util.List;

public class Infos {
    List<AreaKey> areaKey;
    List<KeyValueBean> distanceKey;
    List<KeyValueBean> industryKey;
    List<KeyValueBean> sortKey;

    public List<AreaKey> getAreaKey() {
        return areaKey;
    }

    public List<KeyValueBean> getDistanceKey() {
        return distanceKey;
    }

    public List<KeyValueBean> getIndustryKey() {
        return industryKey;
    }

    public List<KeyValueBean> getSortKey() {
        return sortKey;
    }
}
