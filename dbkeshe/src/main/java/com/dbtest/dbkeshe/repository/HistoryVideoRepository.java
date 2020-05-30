package com.dbtest.dbkeshe.repository;

import com.dbtest.dbkeshe.entity.HistoryVideo;

import java.util.List;

public interface HistoryVideoRepository {
    public List<HistoryVideo> findAll();
    public void save(HistoryVideo historyVideo);
    public HistoryVideo findByVideoId(Long videoId);
    public HistoryVideo findById(Long id);
}
