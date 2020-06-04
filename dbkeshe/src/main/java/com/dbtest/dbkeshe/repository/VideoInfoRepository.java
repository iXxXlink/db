package com.dbtest.dbkeshe.repository;

import com.dbtest.dbkeshe.entity.Kitchen;
import com.dbtest.dbkeshe.entity.VideoInfo;

import java.util.List;

public interface VideoInfoRepository {
    public List<VideoInfo> findAll();
    public int count();
}
