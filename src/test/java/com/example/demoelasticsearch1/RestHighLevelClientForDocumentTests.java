package com.example.demoelasticsearch1;

import com.example.demoelasticsearch1.DemoElasticsearch1Application;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
public class RestHighLevelClientForDocumentTests extends DemoElasticsearch1ApplicationTests {

//    private final RestHighLevelClient restHighLevelClient;
//
//    @Autowired
//    public RestHighLevelClientForDocumentTests(RestHighLevelClient restHighLevelClient) {
//        this.restHighLevelClient = restHighLevelClient;
//    }
@Autowired
@Qualifier("restHighLevelClient")
private RestHighLevelClient restHighLevelClient;

    @Test
    public void testCreate() throws IOException {
       IndexRequest indexRequest = new IndexRequest("products");
       indexRequest.id("2").source("{\n" +
               "  \"title\":\"商品\",\n" +
               "  \"price\":2.5,\n" +
               "  \"created_at\": \"2021-12-12\",\n" +
               "  \"description\": \"低价新品\"\n" +
               "}", XContentType.JSON);
       IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.status());
        restHighLevelClient.close();
    }
    @Test
    public void testUpdate() throws IOException{
        UpdateRequest updateRequest = new UpdateRequest("products", "2");
        updateRequest.doc(" {\"price\": 1.3}", XContentType.JSON);
       UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }
    @Test
    public void testQueryById() throws IOException{
        GetRequest getRequest = new GetRequest("products", "1");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.isExists());
        System.out.println(getResponse.getId());
        System.out.println(getResponse.getSourceAsString());
   }
   @Test
   public void testMatchAll() throws IOException{
       SearchRequest searchRequest = new SearchRequest("products");
       SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
       searchSourceBuilder.query(QueryBuilders.matchAllQuery());
       searchRequest.source(searchSourceBuilder);
       SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
       System.out.println("符合条件:"+searchResponse.getHits().getTotalHits());
       System.out.println("最大得分："+searchResponse.getHits().getMaxScore());

   }

   @Test
   public void testQuery() throws IOException{
       // termQuery查不到数据
       // Query(QueryBuilders.termQuery("description", "低价"));
       //Query(QueryBuilders.matchQuery("description","新品"));
       //指定参数
       Search(QueryBuilders.matchAllQuery());
   }

    public void Query(QueryBuilder queryBuilder) throws IOException{
       SearchRequest searchRequest = new SearchRequest("products");
       SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
       //searchSourceBuilder.query(QueryBuilders.termQuery("description","新品"));

       searchSourceBuilder.query(queryBuilder);
       searchRequest.source(searchSourceBuilder);

       SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println("请求状态:" + searchResponse.status());
       System.out.println("符合条件总数:"+searchResponse.getHits().getTotalHits().value);
       System.out.println("获取文档最大分数:"+searchResponse.getHits().getMaxScore());

       SearchHit[] hits = searchResponse.getHits().getHits();
       for (SearchHit hit: hits) {
           System.out.println("id:"+hit.getId()+"source"+hit.getSourceAsString());
       }
   }

    public void Search(QueryBuilder queryBuilder) throws IOException{
       SearchRequest searchRequest = new SearchRequest("products");
       SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
       searchSourceBuilder.query(queryBuilder)
               .from(0).size(10).sort("price", SortOrder.ASC)
               .fetchSource(new String[]{},new String[]{"created_at"});
       searchRequest.source(searchSourceBuilder);
       SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("当前状态:" + searchResponse.status());
       System.out.println("符合条件总数:"+searchResponse.getHits().getTotalHits().value);
        System.out.println("获取文档最大分数:"+searchResponse.getHits().getMaxScore());

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit: hits) {
            System.out.println("id:"+hit.getId()+"source"+hit.getSourceAsString());
        }
   }

}
