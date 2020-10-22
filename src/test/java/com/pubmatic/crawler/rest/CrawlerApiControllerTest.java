package com.pubmatic.crawler.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

import javax.inject.Inject;

import com.pubmatic.crawler.model.PageInfo;
import com.pubmatic.crawler.service.CrawlerService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.common.io.Resources;

import com.pubmatic.crawler.CrawlerIntegrationTest;
import com.pubmatic.crawler.model.PageTreeInfo;

@RunWith(SpringRunner.class)
@CrawlerIntegrationTest
public class CrawlerApiControllerTest {

    @Inject
    private MockMvc mockMvc;
    private String htmlPayload;
    private PageTreeInfo pageTreeInfo;
    private PageInfo pageInfo;

    @MockBean
    private CrawlerService crawlerService;

    @Test
    public void testGetWebPageTreeInfo() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(
                "/crawler").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.when(crawlerService.crawl(Mockito.anyString())).thenReturn(Optional.of(pageInfo));
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/crawler?url=something&depth=5")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();

        Mockito.when(crawlerService.deepCrawl(Mockito.anyString(), Mockito.anyInt(), Mockito.anyList()))
                .thenReturn(pageTreeInfo);
        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/crawler?url=something&depth=5")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty().contains("something");

    }

    public static String readContentAsString(final String uri) throws IOException {
        return Resources.toString(Resources.getResource(uri), Charset.defaultCharset());
    }

}
