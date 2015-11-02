package com.yao.volleyusage.response;

import java.util.List;

/**
 * 视频信息result
 * @author shc
 * @since 15/11/2
 */
public class VideoResponse {

    public String timestamp;


    public VideoData data;


    public static class VideoData{
        public List<Video> vlist;
    }


    public static class Video{
        public String shortTitle;
        public String vt;
    }


}
