package com.pubmatic.crawler.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.inject.Inject;

import com.pubmatic.crawler.model.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.pubmatic.crawler.CrawlerIntegrationTest;
import com.pubmatic.crawler.model.PageTreeInfo;

@RunWith(SpringRunner.class)
@CrawlerIntegrationTest
public class CrawlerServiceImplTest {

    @Inject
    private CrawlerService crawlerService;

    @Test
    public void testDeepCrawl() {
        final PageTreeInfo info = crawlerService.deepCrawl("http://spring.io", 1, null);
        assertThat(info).isNotNull().satisfies(treeInfo -> {
            assertThat(treeInfo.getUrl()).contains("http://spring.io");
            assertThat(treeInfo.getChildUrl().size()).isGreaterThan(20);
        });
    }

    @Test
    public void testCrawl() {
        final Optional<PageInfo> info = crawlerService.crawl("http://ebay.com");
        assertThat(info).isPresent();
        assertThat(info.get().getUrl()).contains("http://ebay.com");
        assertThat(info.get().getLinks().size()).isGreaterThan(10);
    }

    @Test
    public void testCrawlPubmatic() {
        final Optional<PageInfo> info = crawlerService.crawl("http://pubmatic.com");
        assertThat(info).isPresent();
        assertThat(info.get().getUrl()).contains("http://pubmatic.com");
        assertThat(info.get().getLinks().size()).isGreaterThan(10);
    }

}
