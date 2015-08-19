package com.yao.volleyusage.response;

import java.util.List;

/**
 * Created by yao on 15/8/19.
 */
public class JokeResponse {

    public List<Joke> jokes;


    public static class Joke{

        public String id;
        public String title;
        public String content;
        public String tag;
        public String img;
        public String publishDate;
        public String url;
        public String commentUrl;
        public String source;

        @Override
        public String toString() {
            return "Joke{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
