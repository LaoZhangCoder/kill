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
也就是在0秒内开启1000个线程,这里我就直接来执行秒杀的接口来测试一下一个tomcat7的并发性了,由于一些网络问题我会讲每个结果反复测几十次然后区
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

当然一次测试结果不具有参考性,下面的结果是我经过多次次测试后一些数据的平均值
