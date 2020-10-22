package com.pubmatic.crawler.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.inject.Inject;

import com.pubmatic.crawler.model.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.test.context.junit4.SpringRunner;

import com.pubmatic.crawler.CrawlerIntegrationTest;
import com.pubmatic.crawler.model.PageTreeInfo;

@RunWith(SpringRunner.class)
@CrawlerIntegrationTest
public class CrawlerServiceCacheTest {

    @Value("#{cacheManager.getCache('${spring.application.name}')}")
    private Cache applicationCache;

    @Inject
    private CrawlerService crawlerService;

    @Test
    public void testCacheOnDeepCrawl() {
        final PageTreeInfo info = crawlerService.deepCrawl("http://spring.io", 0, null);
        assertThat(info).isNotNull().satisfies(treeInfo -> {
            assertThat(treeInfo.getUrl()).contains("http://spring.io");
        });
        assertThat(applicationCache.getName()).isNotNull();
        assertThat(applicationCache.get(new SimpleKey("http://spring.io", 0, null))).isNotNull();
    }

    @Test
    public void testCacheOnCrawl() {
        final Optional<PageInfo> info = crawlerService.crawl("https://pubmatic.com");
        assertThat(info).isPresent();
        assertThat(info.get().getUrl()).contains("https://pubmatic.com");
        assertThat(info.get().getLinks().size()).isGreaterThan(20);

        assertThat(applicationCache.get("https://pubmatic.com")).isNotNull();
    }

}
