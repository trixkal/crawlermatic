package com.pubmatic.crawler.configuration;

import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 *  Application properties for default configuration.
 */
@Data
@Named
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

    @Valid
    private final CrawlerProperties crawler = new CrawlerProperties();

    /**
     * App properties.
     */
    @Data
    @Validated
    public static class CrawlerProperties {

        /**
         * default depth for web crawler.
         */
        @Min(0)

        private int defaultDepth;

        /**
         * max depth allowed for a request based on service capability and SLAs to
         * prevent DOS
         */
        @Min(0)
        private int maxDepthAllowed;

        /**
         * timeout for http requests in seconds
         */
        @Min(5)
        private int timeOut;

        /**
         * follow redirects for the given url
         */
        private boolean followRedirects;

    }
}
