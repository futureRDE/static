package com.example.demoelasticsearch1.service;


import com.alibaba.fastjson.JSON;
import com.example.demoelasticsearch1.pojo.Content;
import com.example.demoelasticsearch1.utils.HtmlParseUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ContentService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

//    public static void main(String[] args) throws Exception{
//         new ContentService().praseContent("java");
//    }
    public Boolean praseContent(String keywords) throws Exception{
        List<Content> contents = new HtmlParseUtil().parseJD(keywords);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        for (int i = 0; i<contents.size();i++) {
            bulkRequest.add(
                    new IndexRequest("jd_goods")
                    .source(JSON.toJSONString(contents.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulk.hasFailures();
    }
    public List<Map<String,Object>> searchPage(String keyword, int pageNo, int pageSize) throws Exception{
        if(pageNo <= 1) {
            pageNo = 1;
        }
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title",keyword);
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for(SearchHit documentFields: searchResponse.getHits().getHits()) {
            list.add(documentFields.getSourceAsMap());
        }
        return list;
    }
}
