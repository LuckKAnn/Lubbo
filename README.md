# Lizzie Dubbo (Lubbo)




##现阶段还差的问题
* 返回值，如果不固定为String怎么做
* 服务如何与group，timeout等相关联
* 提升抽象，提升拓展性，抽象通信方式和协议，抽象序列化方式，抽象注册中心，如何做到类似Dubbo的SPI机制
* ip地址写死了,为什么获取到的ip地址网卡不对劲
* 如何构建服务目录，现阶段每次都需要去ZK上获取
* 增加RPCException，如果出现异常怎么办
* 负载均衡，一致性hash和轮询？