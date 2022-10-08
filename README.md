# vue-ts-elasticsearch
## 1.下载elasticsearch,ik分词器,kibana
<span style="font-size:20px">elasticsearch官网地址：</span>
[IOC] https://www.elastic.co/cn/downloads/elasticsearch
<span style="font-size:20px">kibana官方下载地址：</span>
[IOC] https://www.elastic.co/cn/downloads/kibana
<span style="font-size:20px">ik分词器下载地址</span>
[IOC] https://github.com/medcl/elasticsearch-analysis-ik
<span style="font-size:20px color:red">jdk8务必采用761版本</span>
### ik分词器安装教程
<span style="font-size:20px">下载完后，解压安装包到 ElasticSearch 所在文件夹中的plugins目录中：</span>
<span style="font-size:20px color:red">IK提供了两个分词算法：ik_smart和ik_max_word，其中ik smart为最少切分,ik_max_word为最细粒度划分!</span>
## 2.启动springboot即可访问
