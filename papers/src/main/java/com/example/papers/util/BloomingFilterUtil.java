package com.example.papers.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component("blooming-filter")
@Scope(value = "singleton")
@Slf4j
public class BloomingFilterUtil {
    public static final BloomFilter<String> keywordBloomingFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("ASCII")),  256, 0.0001);

    public static BloomFilter instance() {
        return keywordBloomingFilter;
    }

    /**
     * init blooming filter
     * @param s string
     */
    public static void init(String s) {
        int n = s.length();
        for (int i = 0; i < n; i++) {
            // log.info(s.substring(i, i + 1));
            keywordBloomingFilter.put(s.substring(i, i + 1));
        }
    }

    /**
     * judge if blooming filter contains string elements
     * @param s string
     * @return boolean value
     */
    public static boolean contains(String s) {
        int n = s.length();
        boolean result = true;
        for (int i = 0; i < n; i++) {
            // log.info(s.substring(i, i + 1));
            if (!keywordBloomingFilter.mightContain(s.substring(i, i + 1))) { return false; }
        }
        return true;
    }
}
