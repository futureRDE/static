package com.example.demoelasticsearch1.products;

import com.example.demoelasticsearch1.DemoElasticsearch1Application;
import com.example.demoelasticsearch1.DemoElasticsearch1ApplicationTests;
import com.example.demoelasticsearch1.entity.mapper.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

public class ElasticSearchOptionsTest extends DemoElasticsearch1ApplicationTests {
    private final ElasticsearchOperations elasticsearchOperations;
    @Autowired
    public ElasticSearchOptionsTest(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }
    @Test
    public void testIndex(){
            Product product = new Product();
            product.setId(1);
            product.getId();
            product.setPrice(2.2);
            product.getPrice();
            product.setTitle("潮牌服装");
            product.getTitle();
            product.setDescription("低价");
            product.getDescription();
            elasticsearchOperations.save(product);
    }
    @Test
    public void testSearch() {
        Product product = elasticsearchOperations.get("1",Product.class);
        System.out.println(product.getId()+" "+product.getPrice()+product.getTitle()+product.getDescription());
    }
    @Test
    public void testDelete() {
        Product product = new Product();
        product.setId(1);
        elasticsearchOperations.delete(product);
    }
    @Test
    public void testDeleteAll() {
        elasticsearchOperations.delete(Query.findAll(),Product.class);
    }
    @Test
    public void testFindAll() throws JsonProcessingException {
        SearchHits<Product> productSearchHits = elasticsearchOperations.search(Query.findAll(), Product.class);
        System.out.println("分数"+ productSearchHits.getMaxScore());
        System.out.println("符合条件"+productSearchHits.getTotalHits());
        for (SearchHit<Product> productSearchHit : productSearchHits) {
            System.out.println(new ObjectMapper().writeValueAsString(productSearchHit.getContent()));
        }
    }
}
