package com.tensquare.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.io.Serializable;

@Document(indexName = "tensquare",type = "article")
public class Article implements Serializable {

    @Id
    private String id;
        //索引             使用ik分词器           搜索也是ik分词器                                 Text
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.Object)
    private String title;//文章正文
                                                                                                  //Text
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.Object)
    private String content;

    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
