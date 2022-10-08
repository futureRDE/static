package com.example.demoelasticsearch1.entity.mapper;



import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products", createIndex = true)
public class Product {
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String title;
    @Field(type = FieldType.Float)
    private Double price;
    @Field(type = FieldType.Text)
    private String description;
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPrice() {
        return price;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
