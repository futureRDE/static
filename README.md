# vue-ts-elasticsearch
## 1.下载elasticsearch,ik分词器,kibana
### elasticsearch官网地址：
### https://www.elastic.co/cn/downloads/elasticsearch
kibana官方下载地址：
https://www.elastic.co/cn/downloads/kibana
ik分词器下载地址
https://github.com/medcl/elasticsearch-analysis-ik
jdk8务必采用761版本
### ik分词器安装教程
下载完后，解压安装包到 ElasticSearch 所在文件夹中的plugins目录中
IK提供了两个分词算法：ik_smart和ik_max_word，其中ik smart为最少切分,ik_max_word为最细粒度划分!
## 2.启动springboot即可访问
## 3.小bug
搜索需要点击两下启动，需要配合首页搜索使用
