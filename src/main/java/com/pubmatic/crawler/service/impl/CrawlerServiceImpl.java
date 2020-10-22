package com.pubmatic.crawler.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.cache.annotation.CacheResult;
import javax.inject.Named;

import com.pubmatic.crawler.configuration.AppProperties;
import com.pubmatic.crawler.model.PageInfo;
import com.pubmatic.crawler.service.CrawlerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;

import com.pubmatic.crawler.model.PageTreeInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named
public class CrawlerServiceImpl implements CrawlerService {

    @Value("#{appProperties.crawler}")
    private AppProperties.CrawlerProperties crawlerProperties;

    /*
     * recursive crawler to fetch child pages upto desired depth / max depth
     * (non-Javadoc)
     *
     * @see com.pubmatic.crawler.service.CrawlerService#deepCrawl(java.lang.String,
     * int)
     */
    @Override
    @CacheResult(cacheName = "web-crawlermatic-service")
    public PageTreeInfo deepCrawl(final String url, final int depth, final List<String> processedUrls) {

        log.debug("Starting crawler for url {} for depth {}", url, depth);
        if (depth < 0) {
            log.info("Maximum depth reached, backing out for url {}", url);
            return null;
        } else {
            final List<String> updatedProcessedUrls = Optional.ofNullable(processedUrls).orElse(new ArrayList<>());
            if (!updatedProcessedUrls.contains(url)) {
                updatedProcessedUrls.add(url);
                final PageTreeInfo pageTreeInfo = new PageTreeInfo(url);
                crawl(url).ifPresent(pageInfo -> {

                    log.info("Found {} links on the web page: {}", pageInfo.getLinks().size(), url);
                    pageInfo.getLinks().forEach(link -> {
                        pageTreeInfo.addChildUrlsItem(deepCrawl(link.attr("abs:href"), depth - 1,
                                updatedProcessedUrls));
                    });
                });
                return pageTreeInfo;
            } else {
                return null;
            }
        }

    }

    /*
     * Method to fetch web page content. Cache is used for better performance
     *
     * @see com.pubmatic.crawler.service.CrawlerService#crawl(java.lang.String)
     */
    @Override
    @CacheResult(cacheName = "web-crawlermatic-service")
    public Optional<PageInfo> crawl(final String url) {

        log.info("Fetching contents for url: {}", url);
        try {
            final Document doc = Jsoup.connect(url).timeout(crawlerProperties.getTimeOut())
                    .followRedirects(crawlerProperties.isFollowRedirects()).get();

            /** .select returns a list of links here **/
            final Elements links = doc.select("a[href]");

            log.debug("Fetched title: {}, links[{}] for url: {}", links.nextAll(), url);

            return Optional.of(new PageInfo(url, links));
        } catch (final IOException | IllegalArgumentException e) {

            log.info(String.format("A file was fetched getting contents of url %s", url));
            return Optional.empty();
        }

    }

}
