package com.dbtest.dbkeshe.controller;

import com.dbtest.dbkeshe.entity.HistoryVideo;
import com.dbtest.dbkeshe.entity.Kitchen;
import com.dbtest.dbkeshe.entity.VideoInfo;
import com.dbtest.dbkeshe.entity.VideoLimtedQuery;
import com.dbtest.dbkeshe.repository.HistoryVideoRepository;
import com.dbtest.dbkeshe.repository.KitchenRepository;
import com.dbtest.dbkeshe.repository.VideoInfoRepository;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


//@RestController
@Controller
@RequestMapping("/historyvideo")
public class HistoryVideoHandler{

    @Autowired
    private HistoryVideoRepository historyVideoRepository;
    @Autowired
    private KitchenRepository kitchenRepository;
    @Autowired
    private VideoInfoRepository videoInfoRepository;


//    @GetMapping("/findAll")
//    public List<HistoryVideo> findAll(){
//        return historyVideoRepository.findAll();
//    }
    @GetMapping("/count")
    @ResponseBody
    public int test(){
        return videoInfoRepository.count();
    }

    @GetMapping("/findAll")
    @ResponseBody
    public List<VideoInfo> findAll(@PathParam(value = "pageIndex") int pageIndex, @PathParam(value = "pageSize") int pageSize){
        List<VideoInfo> videoInfos = null;
        PageHelper.startPage(pageIndex,pageSize);
        videoInfos= videoInfoRepository.findAll();
        return videoInfos;

    }

    @GetMapping("/findById/{id}")
    public HistoryVideo findByVideoId(@PathVariable("id") Long id){
        return historyVideoRepository.findByVideoId(id);
    }


    @PostMapping("/findByName")
    @ResponseBody
    public List<HistoryVideo> findByName( @RequestBody Map kName){
        Kitchen tmp = null;
        tmp=kitchenRepository.findByName((String) kName.get("kName"));
        if(tmp!=null){
            List<HistoryVideo> historyVideos = historyVideoRepository.findById(tmp.getId());
            return historyVideos;
        }else {
            System.out.println(".......................");
            System.out.println(kName.toString());
            return null;
        }
    }

    @PostMapping("/findByIdAndDate")
    @ResponseBody
    public List<HistoryVideo> findByNameAndDate( @RequestBody Map map) throws ParseException {
        VideoLimtedQuery videoLimtedQuery=new VideoLimtedQuery();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse((String) map.get("dateFrom"));
        Date date2 = simpleDateFormat.parse((String) map.get("dateTo"));
        videoLimtedQuery.setId((int)map.get("id"));
        videoLimtedQuery.setDateFrom(date1);
        videoLimtedQuery.setDateTo(date2);
        return historyVideoRepository.findByIdAndDate(videoLimtedQuery);
    }

    @GetMapping("/deleteByVideoId")
    @ResponseBody
    public int deleteByVideoId(@RequestParam(value = "videoId",required = false) Long id){
        try {
            historyVideoRepository.deleteByVideoId(id);
            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    @PutMapping("/save")
    @ResponseBody
    public int save(@RequestBody Map map) throws ParseException {
        Long id = new Long((String) map.get("id"));
        String videoURL= (String) map.get("videoURL");
        String date=(String)map.get("date");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1=simpleDateFormat.parse(date);
        HistoryVideo historyVideo = new HistoryVideo();
        historyVideo.setId(id);
        historyVideo.setVideoURL(videoURL);
        historyVideo.setDate(date1);
        try{
            historyVideoRepository.save(historyVideo);
            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    @PostMapping("/changeURL")
    @ResponseBody
    public int changeURL(@RequestBody HistoryVideo historyVideo){
        try {
            historyVideoRepository.changeURL(historyVideo);
            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }









}


