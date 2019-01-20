概要:这是一个秒杀系统的项目,执行流程大致上就是显示秒杀商品,秒杀商品状态(也就是是否开启秒杀),秒杀结果返回(1.重复秒杀,2.秒杀结束等等状态)
也就是对应了下面几张图
![https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175054.png](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175054.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175111.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175125.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175134.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175145.png)
![这里本应该有个图但是丢失了](https://github.com/tomsajkdhsakjd/kill/blob/master/imgs/20190120175157.png)
这里就介绍完必了,这里我不会讲这个项目具体建立过程,这里我只是想测试一下这个项目的并发性,吞吐率,和一些优化等等,毕竟是秒杀项目.一些测试还是要
进行的。反正寒假无聊简单的测试一下应该没什么问题
压测工具:Jemeter
物理机:这里我是用的虚拟机,应该也行吧,不知道管他呢。1GB,1CPU
web环境:tomcat7 java7
