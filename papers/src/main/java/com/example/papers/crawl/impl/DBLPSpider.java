package com.example.papers.crawl.impl;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import com.example.papers.crawl.Spider;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Component
public class DBLPSpider implements Spider {
    private String url;
    private String latestExecURL = "No Content";
    private List<String> publish;
    private List<String> years;
    private List<String> titles;
    private List<String> pages;
    private List<String> authorList;
    private Date version;

    public DBLPSpider() {}

    public DBLPSpider(String url) {
        this.url = url;
    }

    @Override
    public void exec() {
        latestExecURL = url;

        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        version = new Date();
        Console.log("Spider Launched on " + dateFormat.format(version));
        String listContent = HttpUtil.get(url);
        publish = ReUtil.findAll("https://dblp.org/db/(.*?)/(.*?)/(.*?).html", url, 3);
        years = ReUtil.findAll("<meta itemprop=\"datePublished\" content=\"(.*?)\" />", listContent, 1);
        titles = ReUtil.findAll("<span class=\"title\" itemprop=\"name\">(.*?)</span>", listContent, 1);
        pages = ReUtil.findAll("<span itemprop=\"pagination\">(.*?)</span>", listContent, 1);
        authorList = new ArrayList<>();
        List<String> citeBlocks = ReUtil.findAll("<cite class=\"data tts-content\" itemprop=\"headline\">(.*?)</cite>", listContent, 1);
        for (String cite : citeBlocks) {
            List<String> authors = ReUtil.findAll("<span itemprop=\"name\" title=\"(.*?)\">(.*?)</span>", cite, 1);
            if (authors.size() != 0) {
                authorList.add(createStr(authors));
            }
        }
    }

    private static String createStr(List<String> strIter) {
        StringBuilder sb = new StringBuilder();
        for (String str: strIter) {
            if (Character.isDigit(str.charAt(str.length() - 1))){
                str = str.substring(0, str.length() - 5);
            }
            sb.append(str);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
