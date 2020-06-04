package com.dbtest.dbkeshe.repository;

import com.dbtest.dbkeshe.entity.HistoryVideo;
import com.dbtest.dbkeshe.entity.VideoLimtedQuery;

import java.util.List;

public interface HistoryVideoRepository {
    public List<HistoryVideo> findAll();
    public void save(HistoryVideo historyVideo);
    public HistoryVideo findByVideoId(Long videoId);
    public List<HistoryVideo> findById(Long id);
    public List<HistoryVideo> findByIdAndDate(VideoLimtedQuery videoLimtedQuery);
    public void deleteByVideoId(Long videoId);
    public void changeURL(HistoryVideo historyVideo);
}
