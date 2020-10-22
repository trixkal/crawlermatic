package com.pubmatic.crawler.service;

import java.util.List;
import java.util.Optional;

import com.pubmatic.crawler.model.PageInfo;
import com.pubmatic.crawler.model.PageTreeInfo;

public interface CrawlerService {

    /**
     * Deep crawl the page for provided depth but upto max
     *
     * @param url
     *            web page url to crawl
     * @param depth
     *            w.r.t base page url
     * @param processedUrls
     *            already processed urls to avoid loops
     * @return page info upto desired or max depth
     */
    PageTreeInfo deepCrawl(final String url, final int depth, List<String> processedUrls);

    /**
     * get page info for given url
     *
     * @param url
     *            web page url
     * @return optional page info - url, title and links on a web page
     */
    Optional<PageInfo> crawl(String url);
}
