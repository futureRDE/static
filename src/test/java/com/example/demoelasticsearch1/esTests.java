package com.example.demoelasticsearch1;


import com.alibaba.fastjson.JSON;
import com.example.demoelasticsearch1.entity.mapper.Product;
import com.example.demoelasticsearch1.entity.mapper.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;


//@RunWith(SpringRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext.xml")

//@RunWith(SpringJUnit4ClassRunner.class)
public class esTests extends DemoElasticsearch1ApplicationTests{
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Test
    public void testCreateIndex() throws IOException {
         CreateIndexRequest request = new CreateIndexRequest("hp_index");
        System.out.println(client);
        try {
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            System.out.println(response+"-----"+response.index());
        }catch (ElasticsearchStatusException e) {
            System.out.println("已经存在");
        }
    }
    @Test
    public void testCreate() throws IOException {
        IndexRequest indexRequest = new IndexRequest("products");
        indexRequest.id("2").source("{\n" +
                "  \"title\":\"商品\",\n" +
                "  \"price\":2.5,\n" +
                "  \"created_at\": \"2021-12-12\",\n" +
                "  \"description\": \"低价新品\"\n" +
                "}", XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.status());
        client.close();
    }
    @Test
    void testAddDocument() throws IOException {
        User user = new User("狂神说",3);
        IndexRequest request = new IndexRequest("hp_index");
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        request.source(JSON.toJSONString(user), XContentType.JSON);

        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }

    @Test
    void testIsExists() throws IOException {
        GetRequest getRequest = new GetRequest("hp_index", "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    @Test
    void testGetDocument() throws IOException{
        GetRequest getRequest = new GetRequest("hp_index","1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
        System.out.println(getResponse);
        System.out.println(getResponse);
    }
    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("hp_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name","hp");

    }
}
