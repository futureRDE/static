package com.example.demoelasticsearch1.products;

import com.example.demoelasticsearch1.DemoElasticsearch1Application;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class RestHighLevelClientTests extends DemoElasticsearch1Application {
    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public RestHighLevelClientTests(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }
    @Test
    public void testDeleteIndex() throws IOException{
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(new DeleteIndexRequest("products"),RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());
       // restHighLevelClient.close();
    }

    @Test
    public void testIndexAndMapping() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("products");
        createIndexRequest.mapping("{\n" +
                "    \"properties\": {\n" +
                "      \"title\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"price\": {\n" +
                "        \"type\": \"double\"\n" +
                "      },\n" +
                "      \"created_at\":{\n" +
                "        \"type\": \"date\"\n" +
                "      },\n" +
                "      \"description\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"ik_max_word\"\n" +
                "      }\n" +
                "    }\n" +
                "  }", XContentType.JSON);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println("创建状态"+ createIndexResponse.isAcknowledged());
        restHighLevelClient.close();
    }
}
