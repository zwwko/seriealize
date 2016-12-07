4种方式序列做比较(序列化时间：845  156 55  644)
1.ObjectMapper(json字符串)
2.ObjectOutputStream(java原生)
3.Kryo
4.avro

3种反序列化做比较(反序列化时间105   135 35)
1.ObjectMapper(json字符串)
2.ObjectOutputStream(java原生)
3.Kryo


总结
Kryo最优(没有做字节数测试,但相关资料显示kryo占用字节数很小)


具体测试请运行main.java

参考资料
http://avro.apache.org/docs/1.8.1/
http://thrift.apache.org/
https://github.com/google/protobuf
http://www.iteye.com/topic/1128881
https://www.oschina.net/question/12_91812
http://colobu.com/2014/08/26/java-serializer-comparison/
http://bijian1013.iteye.com/blog/2232335
https://github.com/yongfenghao/serializable_Demo
http://blog.csdn.net/xiao__gui/article/details/36643949
