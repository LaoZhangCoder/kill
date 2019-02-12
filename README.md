概要:这是一个秒杀系统的项目,执行流程大致上就是显示秒杀商品,秒杀商品状态(也就是是否开启秒杀),秒杀结果返回(1.重复秒杀,2.秒杀结束等等状态)
也就是对应了下面几张图
![https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175054.png](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175054.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175111.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175125.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175134.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175145.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175157.png)
这里就介绍完必了,这里我不会讲这个项目具体建立过程,这里我只是想测试一下这个项目的并发性,吞吐率,和一些优化等等,毕竟是秒杀项目.一些测试还是要
进行的。反正寒假无聊简单的测试一下应该没什么问题.

压测工具:Jmeter

物理机:这里我是用的虚拟机,应该也行吧,不知道管他呢。1GB,1CPU

web环境:tomcat7 java7。

先看一下压测设置的一些的参数
![https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120181411.png](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120181411.png)
也就是在0秒内开启1000个线程(当然不可能做到同时启动这1000线程,只是尽量多开启一点线程),这里我就直接来执行秒杀的接口来测试一下一个tomcat7的并发性了,由于一些网络问题我会讲每个结果反复测几十次然后区
平均值我们先来看第一次测试的结果

可以看出有些成功有些失败
![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120182632.png)

这里我们主要看下面这这图
![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/QQ%E6%88%AA%E5%9B%BE20190118154155.png)
这里我简单介绍一些具有重要参考价值的数值
 1. Label：每个 JMeter 的 element（例如 HTTP Request）都有一个 Name 属性，这里显示的就是 Name 属性的值 
2. #Samples：请求数——表示这次测试中一共发出了多少个请求，如果模拟10个用户，每个用户迭代10次，那么这里显示100 
3. Average：平均响应时间——默认情况下是单个 Request 的平均响应时间，当使用了 Transaction Controller 时，以Transaction 为单位显示平均响应时间 
6. Min：最小响应时间 
7. Max：最大响应时间 
8. Error%：错误率——错误请求数/请求总数 
9. Throughput：吞吐量——默认情况下表示每秒完成的请求数（Request per Second），当使用了 Transaction Controller 时，也可以表示类似 LoadRunner 的 Transaction per Second 数 
10. KB/Sec：每秒从服务器端接收到的数据量，相当于LoadRunner中的Throughput/Sec
一般而言，性能测试中我们需要重点关注的数据有： #Samples 请求数，Average 平均响应时间，Min 最小响应时间，Max 最大响应时间，Error% 错误率及Throughput 吞吐量。

当然一次测试结果不具有参考性,下面的结果是我经过多次次测试后一些数据的平均值,首先我们来看下默认的tomcat7测试的一些数据


![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/0190125132619.png)
这里我只统计了平均响应时间,错误率,和吞吐量这三个数据,可以看出没优化的tomcat7在大量线程的请求下,错误率还是蛮高的,为了减少错误率,我这里优化了一下tomcat7,这里我优化了三个地方,首先修改Server.xml文件,适当增加最大连接请求数(默认是200),第二点是使用了线程池。第三是优化tomcat运行模式,即将传统的bio模式改成nio模式这些都是我百度来的方法。最后一个就是设置JVM参数也就是修改jvm内存的一些参数。


如下

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/201901202349499.png)


查看修改前的jvm内存占用情况

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120235520.png)

可以看出默认的持久带占用的内存非常少


修改后,和我们所设置的参数一样了

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120234949.png)

我们再来看下优化后进行并发测试后的一些数据

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190125132641.png)

首先明显可以看出当我们增大了tomcat的最大请求线程连接数时错误率明显更少了,虽然有些也很高,但是大致趋势可以得出结论。要想具体看有多大提升,我们去掉数据当中一个各项数据最差的一个数据,然后对各项数据取平均值来进行比较

优化前

Average   Error%   Throughput

5168.0    8.76      79.3

优化后

Average   Error%   Throughput

5493.0      0        96.6

从数据可以看出优化后平均响应时间增加了,错误率降低了,吞吐率增加了

上面大部分是对Tomcat优化，接下来我们再来优化一下其他部分，我们都知道访问数据库的操作是慢的，毕竟是IO操作,因此我们可以将频繁发生数据库访问的操作加入缓存,毕竟访问内存的速度可要比访问磁盘文件快的多。这里我们来用redis来做缓存。接下来来分析那部分需要缓存。

![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175111.png)

很明显这里将会有大量用户访问,大量用户将会不停的刷新等待秒杀开启,这样在秒杀开启后就会有大量的请求到后端,数据库的压力就会加大。而这部分对应的过程就是秒杀开启时,向用户返回秒杀地址。

![https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212165909.png](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212165909.png)

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212170034.png)

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212170044.png)

可以看出我在对应的地方加了缓存部分的代码,并且减少了缓存雪崩和缓存击穿带来的影响,当然这是单机的redis测试,集群的以后再说.我们来看下效果

第一次查看这个商品时后台输出的结果
![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212171732.png)

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212171653.png)

第二次

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212171718.png)

这是我通过查看redis-cli。可以看出这个商品已经加入了缓存并且设置了过期时间
![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212172938.png)

我们都知道tomcat支持的并发数有限,而我们的秒杀又是高并发项目,为了进一步提高并发性,这里我搭建了tomcat集群,这里我只弄了2个,搭建过程并不难。要将服务分摊到不同的tomcat服务器上,这里我使用nginx来实现负载均衡。效果如下

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212202235.png)

![](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190212202246.png)

这里我采用的是默认的轮询式的负载均衡算法,也就是刷新一次换一个tomcat访问,当然这里我只弄了两个tomcat集群,你可以根据你的电脑配置来配置更多,当然nginx也可以弄集群。这里我也不弄了.后面我可能还会弄个数据库方面的分库分片,进一步提升并发性能
