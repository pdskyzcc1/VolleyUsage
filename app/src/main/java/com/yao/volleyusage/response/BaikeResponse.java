package com.yao.volleyusage.response;

import java.util.List;

/**
 *
 * Created by yao on 15/8/19.
 */
public class BaikeResponse {

    public List<Baike> card;


    @Override
    public String toString() {
        return "BaikeResponse{" +
                "card=" + card +
                '}';
    }

    public static class Baike {

        public String key;
        public String name;
        public List<String> value;
        public List<String> format;

        @Override
        public String toString() {
            return "Baike{" +
                    "key='" + key + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
