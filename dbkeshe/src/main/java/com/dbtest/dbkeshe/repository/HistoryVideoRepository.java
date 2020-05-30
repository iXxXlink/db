package com.dbtest.dbkeshe.repository;

import com.dbtest.dbkeshe.entity.HistoryVideo;

public interface HistoryVideoRepository {
    public void save(HistoryVideo historyVideo);
    public HistoryVideo findById(Long videoId);
    public void deleteById(Long videoId);
}
