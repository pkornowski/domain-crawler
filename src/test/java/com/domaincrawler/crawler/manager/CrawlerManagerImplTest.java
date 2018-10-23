package com.domaincrawler.crawler.manager;

import com.domaincrawler.crawler.model.Page;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;

import static com.domaincrawler.crawler.CrawlerTestFixtures.prepareExpectedPageResult;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class CrawlerManagerImplTest {

    private CrawlerManager crawlerManager;

    @Before
    public void setUp() {
        crawlerManager = new CrawlerManagerImpl();
    }

    @Test
    public void shouldStartCrawlingFromPoperMainPage() throws IOException {
        //given
        final Page expectedResult = prepareExpectedPageResult();
        //when
        final Page page = crawlerManager.listAllDetectedUrls(new URL("http://www.example.com"));
        //then
        assertEquals(expectedResult, page);
    }
}
