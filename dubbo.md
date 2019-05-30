

### Dubbo的简介

```
Dubbo是阿里巴巴公司开源的一个高性能优秀的服务框架，使得应用可通过高性能的 RPC 实现服务的输出和输入功能，可以和Spring框架无缝集成。
Dubbo是一款高性能、轻量级的开源Java RPC框架，它提供了三大核心能力：面向接口的远程方法调用，智能容错和负载均衡，以及服务自动注册和发现。
```

#### 网站架构的背景

```
随着互联网的发展，网站应用的规模不断扩大，常规的垂直应用架构已无法应对，分布式服务架构以及流动计算架构势在必行，亟需一个治理系统确保架构有条不紊的演进。
```

![image](http://dubbo.apache.org/docs/zh-cn/user/sources/images/dubbo-architecture-roadmap.jpg)

```
单一应用架构
当网站流量很小时，只需一个应用，将所有功能都部署在一起，以减少部署节点和成本。此时，用于简化增删改查工作量的数据访问框架(ORM)是关键。

垂直应用架构
当访问量逐渐增大，单一应用增加机器带来的加速度越来越小，将应用拆成互不相干的几个应用，以提升效率。此时，用于加速前端页面开发的Web框架(MVC)是关键。

分布式服务架构
当垂直应用越来越多，应用之间交互不可避免，将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，使前端应用能更快速的响应多变的市场需求。此时，用于提高业务复用及整合的分布式服务框架(RPC)是关键。

流动计算架构
当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。此时，用于提高机器利用率的资源调度和治理中心(SOA)是关键。
```

#### RPC简介

```
RPC（Remote Procedure Call）—远程过程调用，它是一种通过网络从远程计算机程序上请求服务，而不需要了解底层网络技术的协议。RPC协议假定某些传输协议的存在，如TCP或UDP，为通信程序之间携带信息数据。在OSI网络通信模型中，RPC跨越了传输层和应用层。RPC使得开发包括网络分布式多程序在内的应用程序更加容易。
```

#### Dubbo的工作原理

```
1.Provider：暴露服务方称之为"服务提供者"
2.Consumer:调用远程服务方称之为:"服务消费者"
3.Registry:服务注册与发现的中心目录服务称之为"服务注册中心"
4.Monitor:统计服务的调用次数和调用时间的日志服务称之为"服务监控中心"
5.Container：服务运行容器
```

#### Dubbo的调用关系

```
1.服务容器负责启动，加载，运行服务提供者。
2.服务提供者在启动时，向注册中心注册自己提供的服务。
3.服务消费者在启动时，向注册中心订阅自己所需的服务。
4.注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
5.服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
6.服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。
```

#### Dubbo的特点

```
(1) 连通性：
注册中心负责服务地址的注册与查找，相当于目录服务，服务提供者和消费者只在启动时与注册中心交互，注册中心不转发请求，压力较小
监控中心负责统计各服务调用次数，调用时间等，统计先在内存汇总后每分钟一次发送到监控中心服务器，并以报表展示
服务提供者向注册中心注册其提供的服务，并汇报调用时间到监控中心，此时间不包含网络开销
服务消费者向注册中心获取服务提供者地址列表，并根据负载算法直接调用提供者，同时汇报调用时间到监控中心，此时间包含网络开销
注册中心，服务提供者，服务消费者三者之间均为长连接，监控中心除外
注册中心通过长连接感知服务提供者的存在，服务提供者宕机，注册中心将立即推送事件通知消费者
注册中心和监控中心全部宕机，不影响已运行的提供者和消费者，消费者在本地缓存了提供者列表
注册中心和监控中心都是可选的，服务消费者可以直连服务提供者
(2) 健壮性：
监控中心宕掉不影响使用，只是丢失部分采样数据
数据库宕掉后，注册中心仍能通过缓存提供服务列表查询，但不能注册新服务
注册中心对等集群，任意一台宕掉后，将自动切换到另一台
注册中心全部宕掉后，服务提供者和服务消费者仍能通过本地缓存通讯
服务提供者无状态，任意一台宕掉后，不影响使用
服务提供者全部宕掉后，服务消费者应用将无法使用，并无限次重连等待服务提供者恢复
(3) 伸缩性：
注册中心为对等集群，可动态增加机器部署实例，所有客户端将自动发现新的注册中心
服务提供者无状态，可动态增加机器部署实例，注册中心将推送新的服务提供者信息给消费者
```

### 分布式介绍

```
分布式系统是由一组通过网络进行通信、为了完成共同的任务而协调工作的计算机节点组成的系统。分布式系统的出现是为了用廉价的、普通的机器完成单个计算机无法完成的计算、存储任务。其目的是利用更多的机器，处理更多的数据。
　（1）提升性能和并发，操作被分发到不同的分片，相互独立
　（2）提升系统的可用性，即使部分分片不能用，其他分片不会受到影响
可以理解为：将同一个项目的某一个模块部署到某一个服务器上，然后通过RPC来协调调用。
```

#### 分布式系统面临的问题

```
分布式系统需要大量机器协作，面临诸多的挑战如下面几个问题
```

##### 异构的机器与网络

```
　分布式系统中的机器，配置不一样，其上运行的服务也可能由不同的语言、架构实现，因此处理能力也不一样；节点间通过网络连接，而不同网络运营商提供的网络的带宽、延时、丢包率又不一样。怎么保证大家齐头并进，共同完成目标，这四个不小的挑战。
```

##### 普遍的节点故障

```
　虽然单个节点的故障概率较低，但节点数目达到一定规模，出故障的概率就变高了。分布式系统需要保证故障发生的时候，系统仍然是可用的，这就需要监控节点的状态，在节点故障的情况下将该节点负责的计算、存储任务转移到其他节点
```

##### 不可靠的网络

```
　节点间通过网络通信，而网络是不可靠的。可能的网络问题包括：网络分割、延时、丢包、乱序。
　相比单机过程调用，网络通信最让人头疼的是超时：节点A向节点B发出请求，在约定的时间内没有收到节点B的响应，那么B是否处理了请求，这个是不确定的，这个不确定会带来诸多问题，最简单的，是否要重试请求，节点B会不会多次处理同一个请求。
```

```
　总而言之，分布式的挑战来自不确定性，不确定计算机什么时候crash、断电，不确定磁盘什么时候损坏，不确定每次网络通信要延迟多久，也不确定通信对端是否处理了发送的消息。而分布式的规模放大了这个不确定性，不确定性是令人讨厌的，所以有诸多的分布式理论、协议来保证在这种不确定性的情况下，系统还能继续正常工作。
```

### 注册中心的简介

#### 为什么需要注册中心

```
因为服务增多以后，彼此之间频繁的调用，如果没有注册中心，A服务要记录B服务地址，那么B更改以后A的地址列表也要变。如果服务非常多，维护这些变化将会非常困难。
```

#### 上述问题的解决方案

```
就一个中间服务，将B，C，D....等服务器的地址写到这个中间服务中，那么即使B,C,D...的服务地址变了，A服务器也不需要关心，它只需要知道B,C,D的名称即可。如果B，C,D......这些服务崩溃，只需要切换到集群中其他的服务上即可，A也不需要关心。
```

#### 现实中的注册中心场景

```
学院内300个学生，每一个学生要求记录其余299个人的电话号码，这时可以将所有的电话号码给院长保存即可，以后打电话的时候，只需要请求院长。这时，整个架构中，院长是薄弱环节，不能崩溃，可以通过集群或者缓存来解决。
```

### 注册中心的安装

#### Zookeeper的下载安装和启动

##### Zookeeper下载

```
https://archive.apache.org/dist/zookeeper/zookeeper-3.4.10/zookeeper-3.4.10.tar.gz
备注：
1）下载的版本要和dubbo支持的版本相吻合或相差不大。
2）dubbo不需要下载，只需要依赖到pom.xml中即可使用
3）zookeeper是一个中间件，单独在命令行启动
4）下载后解压
```

##### Zookeeper的配置

```
复制conf/zoo_sample.cfg，并命名为zoo.cfg,打开该文件，修改如下配置：
```

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/zookeeper%E7%9A%84%E9%85%8D%E7%BD%AE.jpg?raw=true)

```
1.修改dataDir路径,上面是默认放大zookeeper目录下,如果使用默认的启动会报错
2.修改端口,端口根据实际情况修改
```

##### zookeeper的启动

```
双击bin/zkServer.cmd即可启动
```

### 构建项目思路

```
1.需要将哪些抽取成服务提供端？
服务提供端,其实就是被调用的一端,也就是业务逻辑层和数据访问层,即serviceImpl层和dao层
2.需要将哪些抽取成服务消费端?
服务消费端,其实就是调用的一端,也就是controller
3.服务的提供端,服务的消费端需要哪些共同的依赖?
Pojo实体类和service的接口,所以还需要将Pojo和service接口都抽取出来作为消费端和提供端共同的依赖。
4.需要创建哪些maven工程?
Pojo实体类,service接口,provider服务提供者,consumer服务消费者
```

### DubboProvider打包配置

```
service工程的pom.xml中的</project>上方，复制以下内容：
<!--MAVEN打包duboo可执行jar begin -->
	<build>
		<finalName>provider</finalName>
		<resources>
			<!-- 结合com.alibaba.dubbo.container.Main -->
			<resource>
				<targetPath>${project.build.directory}/classes/META-INF/spring</targetPath>
				<directory>src/main/resources</directory>
				<filtering>true</filtering>
				<includes>
					<include>**/*.xml</include>
				</includes>
			</resource>
		</resources>
		<pluginManagement>
			<plugins>
				<!-- 解决Maven插件在Eclipse内执行了一系列的生命周期引起冲突 -->
				<plugin>
					<groupId>org.eclipse.m2e</groupId>
					<artifactId>lifecycle-mapping</artifactId>
					<version>1.0.0</version>
					<configuration>
						<lifecycleMappingMetadata>
							<pluginExecutions>
								<pluginExecution>
									<pluginExecutionFilter>
										<groupId>org.apache.maven.plugins</groupId>
										<artifactId>maven-dependency-plugin</artifactId>
										<versionRange>[2.0,)</versionRange>
										<goals>
											<goal>copy-dependencies</goal>
										</goals>
									</pluginExecutionFilter>
									<action>
										<ignore />
									</action>
								</pluginExecution>
							</pluginExecutions>
						</lifecycleMappingMetadata>
					</configuration>
				</plugin>
			</plugins>
		</pluginManagement>
		<plugins>
			<!-- 打包jar文件时，配置manifest文件，加入lib包的jar依赖 -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<configuration>
					<classesDirectory>target/classes/</classesDirectory>
					<archive>
						<manifest>
							<mainClass>com.alibaba.dubbo.container.Main</mainClass>
							<!-- 打包时 MANIFEST.MF文件不记录的时间戳版本 -->
							<useUniqueVersions>false</useUniqueVersions>
							<addClasspath>true</addClasspath>
							<classpathPrefix>lib/</classpathPrefix>
						</manifest>
						<manifestEntries>
							<Class-Path>.</Class-Path>
						</manifestEntries>
					</archive>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-dependency-plugin</artifactId>
				<executions>
					<execution>
						<id>copy-dependencies</id>
						<phase>package</phase>
						<goals>
							<goal>copy-dependencies</goal>
						</goals>
						<configuration>
							<type>jar</type>
							<includeTypes>jar</includeTypes>
							<useUniqueVersions>false</useUniqueVersions>
							<outputDirectory>
								${project.build.directory}/lib
							</outputDirectory>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
	<!--MAVEN打包duboo可执行jar end -->
```

### Dubbo常用标签

**七大常用标签**

```
<dubbo:service/>用于配置服务提供者暴露自己的服务
<dubbo:reference/>用于配置服务消费者引用服务
<dubbo:protocol/>用于配置服务提供者的访问协议
<dubbo:registry/>用于配置注册中心
<dubbo:application/>用于配置应用信息
<dubbo:provider/>用于配置服务提供者的默认值,即设置<dubbo:service/>和<dubbo:protocol/>标签的默认值
<dubbo:consumer/>用于配置服务消费者的默认值,即<dubbo:reference/>标签的默认值
```

**七大常用标签的联系**

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/TIM%E5%9B%BE%E7%89%8720190529104611.png?raw=true)

**七大常用标签分类**

```
公共标签
	<dubbo:application/>
	<dubbo:registry/>
服务提供者标签
	<dubbo:provider/>
	<dubbo:protocol/>
	<dubbo:service/>
服务消费者标签
	<dubbo:consumer/>
	<dubbo:reference/>
```

### Dubbo内置容器

```
Dubbo默认内置了spring,jetty,log4j容器
```

 **spring容器**

```
spring容器是dubbo默认加载的容器，即不用任何配置，dubbo在初始化的时候就会加载spring容器。只有加载了spring容器整个服务才可以使用。即dubbo是基于spring的，没有spring的话dubbo无法运行。
spring容器默认的访问为在META-INF/spring/*.xml
```

```
配置如下：
dubbo.container=spring
dubbo.application.name=crmprovider
dubbo.application.owner=zhanglikun
dubbo.spring.config=classpath:META-INF/spring/spring.xml
dubbo.registry.address=zookeeper://127.0.0.1:2181
dubbo.protocol.name=dubbo
dubbo.protocol.port=20881
dubbo.service.shutdown.wait=15000
```

**log4j容器**

```
如果系统中配置了log4j容器，那就就可以不再使用log4j.properties文件
总之就是启动了一个log日志对象，用来记录日志信息。
```

```
配置如下：
dubbo.container=spring,log4j
dubbo.log4j.level=DEBUG
dubbo.log4j.file=provider.log
```

**jetty容器**

```
配置如下：
dubbo.container=spring,jetty
dubbo.jetty.port=8088
会启动jetty服务器
访问http://localhost:8088之后，如下：
```

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/wps6533.tmp.jpg?raw=true)

```
这是早先内置的一个dubbo-monitor，不建议在这里开启jetty容器。
```

### Dubbo注册中心

**Multicast注册中心**

```
Multicast注册中心的特点
1.不需要安装和启动任何中心节点,只要服务提供者与服务消费者的广播地址一样,就可以互相发现
2.组播地址段:224.0.0.0-239.255.255.255
3.组播只适合小规模应用或开发调试使用

如何配置？
--------》消费者和提供者的地址必须相同《——————
dubbo.registry.address=multicast://224.2.3.4:1234
```

**Zookeeper注册中心**

```
认识zookeeper
	Zookeeper:是一个高性能,分布式的,开源分布式应用协调服务
	dubbo官网推荐使用此注册中心
如何配置？
	(1)首先进入zookeeper的bin目录启动zookeeper
	(2)--------》消费者和提供者的地址必须相同《——————
		加入zookeeper的jar包
	   dubbo.registry.address=zookeeper://127.0.0.1:2181
```

**Redis注册中心**

```
如何配置？
	(1)首先进入redis的bin目录启动redis
	(2)--------》消费者和提供者的地址必须相同《——————
		加入redis的jar包
	   dubbo.registry.address=redis://127.0.0.1:6379
```

### Dubbo常用协议

**8种协议**

```
dubbo://
	Dubbo协议采用单一长连接和NIO异步通讯,适合于小数据量大并发的服务调用,以及服务消费者机器数远大于服务提供者机器数的情况。
	Dubbo协议底层默认使用的是netty ,性能非常优秀,官方推荐使用此协议
	Dubbo协议不适合传送大数据量的服务,比如传文件,传视频等,除非请求量很低
	dubbo.protocol.name=dubbo
	dubbo.protocol.port=20880
	dubbo协议默认端口20880
hessian://
	Hessian协议用于集成Hessian的服务, Hessian底层采用Http通讯,采用Servlet暴露服务, Dubbo缺省内嵌Jetty作为服务器实现。
	Hessian是Caucho开源的一个RPC框架: http://hessian.caucho.com ,其通讯效率高于WebService和Java自带的序列化
rmi://
http://
webservice://
thrift://
redis://
rest://
memcached://
以上不再一一描述一般使用dubbo协议
```

### Dubbo参数覆盖策略

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/ss.png?raw=true)

```
JVM启动-D参数优先，这样可以使用户在部署和启动时进行参数重写，比如在启动时需改变协议的端口。
XML次之，如果在XML中有配置，则dubbo.properties中的相应配置项无效。
Properties最后，相当于缺省值，只有XML没有配置时，dubbo.properties的相应配置项才会生效，通常用于共享公共配置，比如应用名。
```

### Dubbo启动时检查

```
Dubbo在没有配置此项时会在启动时检查依赖的服务是否可用，不可用时会抛出异常，阻止 Spring 初始化完成，以便上线时，能及早发现问题，默认 check="true"。

可以通过 check="false" 关闭检查，比如，测试时，有些服务不关心，或者出现了循环依赖，必须有一方先启动。

另外，如果你的 Spring 容器是懒加载的，或者通过 API 编程延迟引用服务，请关闭 check，否则服务临时不可用时，会抛出异常，拿到 null 引用，如果 check="false"，总是会返回引用，当服务恢复时，能自动连上。
```

```
通过 spring 配置文件
	关闭某个服务的启动时检查 (没有提供者时报错)：
	<dubbo:reference interface="com.foo.BarService" check="false" />
	关闭所有服务的启动时检查 (没有提供者时报错)：
	<dubbo:consumer check="false" />
	关闭注册中心启动时检查 (注册订阅失败时报错)：
	<dubbo:registry check="false" />
通过 dubbo.properties
	dubbo.reference.com.foo.BarService.check=false
    dubbo.reference.check=false
    dubbo.consumer.check=false
    dubbo.registry.check=false
通过 -D 参数
    java -Ddubbo.reference.com.foo.BarService.check=false
    java -Ddubbo.reference.check=false
    java -Ddubbo.consumer.check=false 
    java -Ddubbo.registry.check=false
通过注解
 	@Reference(check=false)
```

```
dubbo.reference.check=false，强制改变所有 reference 的 check 值，就算配置中有声明，也会被覆盖。

dubbo.consumer.check=false，是设置 check 的缺省值，如果配置中有显式的声明，如：<dubbo:reference check="true"/>，不会受影响。

dubbo.registry.check=false，前面两个都是指订阅注册中心成功，但提供者列表为空是否报错，这个是如果注册订阅失败时，也允许启动，需使用此选项，将在后台定时重试。
```

### Dubbo服务调用超时时间

```
timeout默认在consumer中是1000毫秒,consumer如果调用provider的时间大于1秒那么就会报错,时间超出
```

```
可以配置在consumer的全局、接口、方法3个级别上||||||||||||||可以配置在provider的全局、接口、方法3个级别上
如果consumer和provider的timeout值产生了冲突以consumer为准，但是在实际应用中，建议由服务提供方设置超时，因为一个方法需要执行多长时间，服务提供方更清楚，如果一个消费方同时引用多个服务，就不需要关心每个服务的超时设置
```

```
方法级优先，接口级次之，全局配置再次之。
如果级别一样，则消费方优先，提供方次之。
```

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/%E9%A3%92%E9%A3%92jpg.jpg?raw=true)

### Dubbo重试机制

```
可以配置在consumer的全局、接口、方法3个级别上||||||||||||||可以配置在provider的全局、接口、方法3个级别上优先级同于上图timeout一样
什么情况下会重试？
	timeout超时情况下会发起重试。
重试的默认次数？
	默认重试2次，加上第一次执行，总共执行3次。
重试可能引起的问题？
	由上节可知，重试可能引起业务的重复执行，例如偶尔发现数据库有几条相同的数据。
如何避免重试引起的问题？例如用户注册场景
	(1)设置重试次数为0
	(2)加大timeout的值
	(3)将用户名在数据库中设置成唯一
重试机制次数配置?
	dubbo.consumer.retries=5
```

### Dubbo多版本

```
当一个接口的实现类,出现不兼容升级时,可以用版本号过度,版本号不同的服务相互间不引用
可以按照以下的步骤进行版本迁移：
(1)在低压力时间段，先升级一半提供者为新版本
(2)再将所有消费者升级为新版本
(3)然后将剩下的一半提供者升级为新版本
```

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/TIM%E5%9B%BE%E7%89%8720190527201639.png?raw=true)

```
注解版配置
将UserServiceImpl的@Service（注意是dubbo的注解）改成@Service(version = "1.0.0")
将UserServiceImpl2的@Service（注意是dubbo的注解）改成@Service(version = "2.0.0")
消费者引用服务的时候将改成要使用的版本号就可以了(如下！！！！)
```

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/TIM%E5%9B%BE%E7%89%8720190527202011.png?raw=true)

```
若是将@Reference改成@Reference(version="*")那么就会随机访问一个版本的实现类
```

### Dubbo本地存根

```
本地存根可以简单理解为在调用提供者之前,会先调用本地的一个接口实现类,由这个实现类来进行一些业务逻辑操作,如果通过则调用服务,否者的话可以假装调用失败后伪造容错数据等等

实现流程,在api这个接口项目中添加接口对象的实现类（即本地存根）,如下方代码
```

```
public class UserServiceStub implements UserService {

    private final UserService userService;
    //接口必须给个有参构造,方法中的参数传入对于接口,这个接口是传入过来的
    public UserServiceStub(UserService userService) {
        this.userService = userService;
    }

    @Override
    public int InsertUser(User user) {
        if(user!=null){
            System.out.println("业务逻辑通过执行调用");
            int i = userService.InsertUser(user);
            return i;
        }else{
            System.out.println("业务逻辑不通过,伪造容错数据");
            return 99;
        }
    }
}
```

```
然后只需要在消费者配置文件中配置一个dubbo.consumer.stub=com.lvshihao.userApi.UserServiceStub即可
实现本地存根,提供者不需要做任何改变。
或者 @Reference(version = "2.0.0",stub = "com.lvshihao.userApi.UserServiceStub")
都可以,不过这两个就是作用域不同,一个是针对全部,一个是针对一个服务,建议使用第二个,还有其他配置方式....
```

### Dubbo结果缓存

```
结果缓存用于加速热门数据的访问速度，Dubbo 提供声明式缓存，以减少用户加缓存的工作量。
```

```
 首先添加fastjson的jar包,然后只需要在对于服务上面加上@Reference(version = "2.0.0",stub = "com.lvshihao.userApi.UserServiceStub",cache = "lru")中的cache标签即可,
他的可选值有以下几个！！！！！
(1)lru:基于最近最少使用原则删除多余缓存，保持最热的数据被缓存。
(3)threadlocal:当前线程缓存，比如一个页面渲染，用到很多 portal，每个 portal 都要去查用户信息，通过线程缓存，可以减少这种多余访问。
(3)jcache:与 JSR107 集成，可以桥接各种缓存实现。
```

### Dubbo直连提供者

```
什么是直连模式？
	直连模式，就是消费端直接连接服务端，而不同注册中心。在开发及测试环境下，经常需要绕过注册中心，只测试指定服务提供者，这时候可能需要点对点直连，点对点直连方式，将以服务接口为单位，忽略注册中心的提供者列表，A 接口配置点对点，不影响 B 接口从注册中心获取列表。
直连模式的实现如下方图片!!!!!
```

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/dubbo-directly.jpg?raw=true)

```
上面的IP就是provider的IP地址，是写死的IP地址。如果provider的IP地址变了，直连也就失效了。
如    @Reference(version = "2.0.0",stub = "com.lvshihao.userApi.UserServiceStub",timeout = 6000,url = "dubbo://localhost:20883")
直连存在的问题？
	不适合生产环境，只适合开发阶段
备注：
1）cosumer中有N多服务引用，某些引用可以走直连，另外的一些可以继续走注册中心，这并不矛盾
2）如果要走直连，只需要在引用中配置url属性即可
3）注意防火墙端口要打开
4）直连有的地方，也成为点对点。
5）provider使用的dubbo协议,cosumer也必须使用dubbo协议进行直连接
6）调用服务超时引起的异常->因为是直连所以提供者设置的timeout对服务者是不可见的,所以必须在服务消费者端设置超时时间才生效。
```

### Dubbo异步调用

```
什么是异步？
一个请求发出去，不必等待对方应答，就可以继续往下执行，这就是异步。最大的好处，就是让用户感觉整个页面执行的特别流畅。从程序执行角度来说，达到了一种类似于多线程的效果，但是比多线开销要小。

如何设置？
@Reference(version = "2.0.0",stub = "com.lvshihao.userApi.UserServiceStub",cache = "lru",async = true)
备注:级别可以根据实际情况设置,使用@Reference注解的话就是对当前服务的设置

备注：：
	(1)异步情况下，timeout设置任何值都不会超时。
	(2)异步情况下，无法获得方法的返回值，返回值为null，但是不意味着方法没有执行，即使在controller中休眠5秒也不能拿到返回值。
	(3)在方法之间有依赖关系的情况下不要使用异步(即第二个方法执行的前必须要第一个方法执行的结果)
```

**Dubbo异步获取返回值**

```
User findUser = userService.findUser("1002");

System.out.println(findUser);

//需要在这里调用dubbo的一个api来解决异步无法获取方法返回值的问题

Future<User> future = RpcContext.getContext().getFuture();

User user = future.get();

System.out.println(findUser)
```

### Dubbo令牌机制

```
What is a token?
	通过令牌验证在注册中心控制权限，以决定要不要下发令牌给消费者，可以防止消费者绕过注册中心访问提供者，另外通过注册中心可灵活改变授权方式，而不需修改或升级提供者
	可以看做是身份的一个标识或者认证，即拿到令牌的客户端就是合法的，没有令牌的客户端就是非法的，非法的客户端,provider可以拒绝为其服务。
```

```
令牌流程：
1)在Provider端设置令牌
2)Provider启动的时候将令牌一起注册到ZK
3)Consumer通过链接zk获取令牌
4)Consumer请求Provider为期服务
5)Provider去验证Consumer的令牌是否合法
6)如果合法就为其服务,如果不合法就拒绝服务
7)通过上述过程可知,Consumer必须通过zk才能拿到令牌,Consumer是无法通过直连的方式请求Provider的

如何设置？
在Provider的实现类上的@Service注解上标记token,有两种配置token的方式如下
<!--固定token令牌，相当于密码-->
@Service(version = "2.0.0",token = "123456")
<!--随机token令牌，使用UUID生成-->
@Service(version = "2.0.0",token = "true")

provider中设置令牌，consumer通过zk访问provider，无异常
provider中设置令牌，consumer通过直连访问provider，有异常
```

### Dubbo隐式参数

```
可以通过 RpcContext 上的 setAttachment 和 getAttachment 在服务消费方和提供方之间进行参数的隐式传递。
如:消费者使用直连调用提供者的时候,提供者设置了令牌机制,那么消费者会调用失败,那么真的就没有其他办法了吗？答肯定是可以的,通过隐式参数传入一个token,然后values是token密码,就可以啦,不过前提必须要知道token的密码。
```

```
RpcContext.getContext().setAttachment("token", "123456");
User user=new User("吕世昊","瘦肉","大");
int a = userService.InsertUser(user);
// 隐式传参，后面的远程调用都会隐式将这些参数发送到服务器端，类似cookie，用于框架集成，不建议常规业务使用
注意：path, group, version, dubbo, token, timeout 几个 key 是保留字段，请使用其它值。 
所以上面那种方法仅作为娱乐。
```

### Dubbo优雅停机

```
Dubbo 是通过 JDK 的 ShutdownHook 来完成优雅停机的，所以如果用户使用 kill -9 PID 等强制关闭指令，是不会执行优雅停机的，只有通过 kill PID 时，才会执行。
```

**服务提供方**

```
停止时，先标记为不接收新请求，新请求过来时直接报错，让客户端重试其它机器。
然后，检测线程池中的线程是否正在运行，如果有，等待所有线程执行完成，除非超时，则强制关闭。
```

**服务消费方**

```
停止时，不再发起新的调用请求，所有新的调用在客户端即报错。
然后，检测有没有请求的响应还没有返回，等待响应返回，除非超时，则强制关闭。
```

```
如何设置？
	设置优雅停机超时时间，缺省超时时间是 10 秒，如果超时则强制关闭。
	dubbo.service.shutdown.wait=15000
如果不是优雅关机，可能引起的问题?
	会引起请求的丢失。例如支付成功后，支付中心给消息中心发指令。。。。。
```

### Dubbo泛化引用

```
泛化接口调用方式主要用于客户端没有 API 接口及模型类元的情况，参数及返回值中的所有 POJO 均用 Map 表示，通常用于框架集成，比如：实现一个通用的服务测试框架，可通过 GenericService 调用所有服务实现。
```

**通过 API 方式使用泛化调用**

```
@RestController
public class UserController {
    @RequestMapping("/insertUser")
    public int insertUser() throws ExecutionException, InterruptedException {
        User user=new User();
        user.setId(1);
        user.setName("吕世昊");
        user.setMeat("最帅");
        user.setBig("帅");
        // 引⽤远程服务, 该实例⾥⾯封装了所有与注册中⼼及服务提供⽅连接，请缓存
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        // 弱类型接⼝名
        reference.setInterface("com.lvshihao.userApi.UserService");
        reference.setVersion("2.0.0");
        reference.setRetries(3);
        // 声明为泛化接⼝
        reference.setGeneric(true);
        // ⽤com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用⽤
        GenericService genericService = reference.get();
        // 基本类型以及Date,List,Map等不需要转换，直接调⽤
        int val = (int) genericService.$invoke("InsertUser", new String[] {"com.lvshihao.userEntity.User"}, new Object[] {user});
        System.out.println("result --> "+val);
        return val;

    }
}
```

```
使用了泛型引用之后相当于纯API调用服务的方法,可以用于测试
```

### Dubbo服务分组

```
当一个接口有多个实现类时,可以用group区分,服务分组是dubbo用来区分相同接口,但是方法具体实现不同的一种模式
@Reference(group = "environment",retries = 3,interfaceClass = UserService.class)
@Reference(group = "test",retries = 3,interfaceClass = UserService.class)
@Service(group = "test")
@Service(group = "environment")
随机调用分组,总是调用一个可用组的服务提供者的方法
@Reference(group = "*",retries = 3,interfaceClass = UserService.class)
```

### Dubbo拦截扩展

```
(1)内置拦截器
在Meta-inf/dubbo/internal/com.alibaba.dubbo.rpc.Filter中声明，例如下图：
```

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/wps3CBC.tmp.jpg?raw=true)

```
可以看到，它是以键值对的形式定义的拦截器，当我们在使用某个拦截器的时候，指定的类就是处理类。
(2)自定义过滤器
	在resources文件下创建一个名字叫com.alibaba.dubbo.rpc.Filter的文本文件
	然后再创建一个类并且实现了com.alibaba.dubbo.rpc.Filter接口
	com.alibaba.dubbo.rpc.Filter文件放置到工程的resources目录，并且将来打到META-INF/dubbo目录下
	键值对形式，其中key为firstFilter（可以自定义拦截器名称，,建议类名称），值为cn.buba.filter.FirstFilter(根据实现了com.alibaba.dubbo.rpc.Filter接口的类的位置设置)。
	在pom文件中添加打包配置
		<resources>
			<!-- 结合com.alibaba.dubbo.container.Main -->
			<resource>
				<targetPath>${project.build.directory}/classes/META-INF/dubbo</targetPath>
				<directory>src/main/resources</directory>
				<filtering>true</filtering>
				<includes>
					<include>**/*.Filter</include>
				</includes>
			</resource>
		</resources>
	必须将该文件放置到META-INF/dubbo目录下，因为这是约定好的。
	编写FirstFilter类
		public class firstFilter implements Filter {
            @Override
            public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
                System.out.println("进入了firstfiltter....");
                //如果返回值为null或者返回值为new RpcResult()那么请求就不会往下执行
                return invoker.invoke(invocation);
            }
        }
	配置spring.xml
	<dubbo:provider filter="firstFilter"/>firstFilter代表文件中自定义过滤器的key
	调用之后查看控制台provider端会输出"进入了firstfiltter...."这么一句话
	这个配置在提供者和消费者均可配置一样操作。
```

**多个拦截配置**

```
假如又添加了一个拦截器可以使用,号进行分隔<dubbo:provider filter="firstFilter,secondFilter"/>
备注：：：：：
过滤器执行的顺序跟定义的顺序有关，定义越靠前，越优先执行，这里也包括内置过滤器
如果某一个过滤器返回的是new RpcResult()则其后的过滤器不再执行
如果某一个过滤器返回的null则其后的过滤器不再执行并且重试该过滤器，重试次数就是spring.xml中定义的次数，也就是配置的重试机制次数。
如果某一个过滤器有异常则其后的过滤器不再执行并且重试该过滤器，跟返回null的情况一样。
```

**Dubbo拦截器的参数**

```
invocation参数：封装的是consumer端的请求参数
invoker参数：封装的是provider端的信息
```



### Dubbo与SpringBoot整合的三种方式

```
1)导入dubbo-starter,在application.prperties配置属性,使用@Service注解进行【暴露服务】,和使用@Reference注解进行【引用服务】,还需要在启动类上标注@EnableDubbo注解进行【开启Dubbo注解支持,可以理解为就是扫描dubbo所给的注解类】,
2)保留dubbo.xml配置文件
可以使用xml配置文件来配置dubbo,然后使用@ImportResource(locations="classpath:provider.xml")来导入dubbo的配置文件即可,那么这个@EnableDubbo注解就可以不要了
3)使用dubbo的API进行配置
```

### Dubbo的高可用

#### zookeeper宕机

```
现象:zookeeper注册中心宕机,还可以消费dubbo暴露的服务
```

原因:

```
健壮性
	(1)监控中心宕掉不影响使用,只是丢失部分采样数据
	(2)数据库宕掉后,注册中心仍能通过缓存提供服务列表查询,但不能注册新服务
	(3)注册中心对等集群,任意一台宕掉后,将直动切换到另一台
	(4)注册中心全部宕掉后,服务提供者和服务消费者仍能通过本地缓存通讯
	(5)服务提供者无状态,任意一台宕掉后,不影响便用
	(6)服务提供者全部宕掉后,服务消费者应用将无法使用,并无限次重连等待服务提供者恢复
```

```
高可用：通过设计，减少系统不能提供服务的时间
```

#### Dubbo负载均衡机制

```
在集群负载均衡时,Dubbo提供了多种均衡策略,缺省为random随机调用
```

**负载均衡策略**

```
Random LoadBalance
随机,按权重设置随机概率。
在一个截面上碰撞的概率高,但调用量越大分布越均句,而且按概率使用权重后也比较均匀,有利于动态调整提供者权重。
RoundRobin LoadBalance
轮循,按公约后的权重设置轮循比率。
存在慢的提供者累积请求的问题,比如:第二台机器很慢,但没挂,当请求调到第二台时就卡在那,久而久之,所有请求都卡在调到第二台上。
```

```
LeastActive LoadBalance
最少活跃调用数,相同活跃数的随机,活跃数指调用前后计数差。
使慢的提供者收到更少请求,因为越慢的提供者的调用前后计数差会越大。
ConsistentHash LoadBalance
一致性 Hash，相同参数的请求总是发到同一提供者。
当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。
算法参见：http://en.wikipedia.org/wiki/Consistent_hashing
缺省只对第一个参数 Hash，如果要修改，请配置 <dubbo:parameter key="hash.arguments" value="0,1" />
缺省用 160 份虚拟节点，如果要修改，请配置 <dubbo:parameter key="hash.nodes" value="320" />
```

```
如何设置？
	<dubbo:service interface="..." loadbalance="roundrobin" />其中loadbalance根据负载均衡机制设置对应的策略
	设置提供者的权重
	@Service(version = "2.0.0",token = "123456",weight = 100)其中weight代表服务权重
```

#### Dubbo服务降级

```
什么是服务降级？
当服务器压力剧增的情况下,根据实际业务情况及流量,对一些服务和页面有策略的不处理或换种简单的方式处理,从而释放服务器资源以保证核心交易正常运作或高效运作。
可以通过服务降级功能临时屏蔽某个出错的非关键服务,并定义降级后的返回策略。
向注册中心配置覆盖规则：
```

![](https://github.com/202252197/DubboDemo/blob/master/%E5%9B%BE%E7%89%87%E5%82%A8%E8%93%84/TIM%E5%9B%BE%E7%89%8720190528154319.png?raw=true)

```
其中：
(1)mock=force:return+null 表示消费方对该服务的方法调用都直接返回null值,不发起远程调用。用来屏蔽不重要服务不可用时对调用方的影响。(可以在dubbo-admin中将不重要的消费者屏蔽即可)
(2)还可以改为mock=fail:return+null 表示消费方对该服务的方法调用在失败后,再返回null值,不抛异常。用来容忍不重要服务不稳定时对调用方的影响。(可以在dubbo-admin中将不重要的消费者容错即可)
```

#### Dubbo集群容错

```
在集群调用失败时, Dubbo提供了多种容错方案,缺省为failover重试。
```

**集群容错模式**

```
@Service(version = "1.0.0",token = "123456",cluster = "failsafe")Failover Cluster
失败自动切,当出现失败,重试其它服务器、通常用于读操作,但重试会带来更长延迟。可通过 retries="2"来设置重试次数(不含第一次)。
重试次数配置如下:
	  @Reference(version = "2.0.0",retries = 3)
	  
Failfast Cluster
快速失败,只发起一次调用,失败立即报错。通常用于非幂等性的写操作,比如新增记录。
Failsafe Cluster
失败安全,出现异常时,直接忽略。通常用于写入审计日志等操作。
Failback Cluster
失败自动恢复,后台记录失败请求,定时重发。通常用于消息通知操作
Forking Cluster
并行调用多个服务器,只要一个成功即返回。通常用于实时性要求较高的读操作,但需要浪费更多服务资源。可通过forks="2"来设置最大并行数。
Broadcast Cluster
广播调用所有提供者,逐个调用,任意一台报错则报错。通常用于通知所有提供者更新缓存或日志等本地资源信息

集群模式配置
@Service(version = "1.0.0",token = "123456",cluster = "failsafe")
```

#### Dubbo整合hystrix进行熔断

```
Hystrix指在通过控制那些访问远程系统、服务和第三方库的节点,从而对延迟和故障提供更强大的容错能力。Hystrix具备拥有回退机制和断路器功能的线程和信号隔离,请求缓存和请求打包,以及监控和配置等功能.
```

**1.配置spring-cloud-starter-netflix-hystrix**

```
springboot官网提供了对hystrix的集成,直接在pom.xml里加入依赖
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    <version>1.4.4.RELEASE</version>
</dependency>
```

**然后在Application类上增加@EnableHystri来启用hystrix starter**

```
@EnableDubbo //开启基于dubbo注解的功能
@EnableHystrix注解Provider和Consumer的Application类上都需要加载 //开启Hystrix容器功能
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
@EnableHystrix注解Provider和Consumer的Application类上都需要加载
```

```
provider的接口实现类的方法上标注 @HystrixCommand注解即可
consumer的调用接口实现类的方法上标注  @HystrixCommand(fallbackMethod="insertUserHystrix")其中fallbackMethod属性中的值代表出现异常容错回调的方法名称,所以还需要写一个方法名字叫做insertUserHystrix如下
   public int insertUserHystrix()  {
        return 888;
    }
```

### Dubbo标签介绍

| 标签                   | 目的             | 介绍                                                         |
| :--------------------- | ---------------- | ------------------------------------------------------------ |
| `<dubbo:service/>`     | 服务出口         | 用于导出服务，定义服务元数据，使用多个协议导出服务，向多个注册表注册服务 |
| `<dubbo:reference/>`   | 服务参考         | 用于创建远程代理，订阅多个注册表                             |
| `<dubbo:protocol/>`    | 协议配置         | 在提供方面配置服务协议，消费者方面如下。                     |
| `<dubbo:application/>` | 应用程序配置     | 适用于提供者和消费者。                                       |
| `<dubbo:module/>`      | 模块配置         | 可选的。                                                     |
| `<dubbo:registry/>`    | 注册中心         | 注册表信息：地址，协议等                                     |
| `<dubbo:monitor/>`     | 监控中心         | 监控信息：地址，地址等。可选。                               |
| `<dubbo:provider/>`    | 提供商的默认配置 | ServiceConfigs的默认配置。可选的。                           |
| `<dubbo:consumer/>`    | 消费者的默认配置 | ReferenceConfigs的默认配置。可选的。                         |
| `<dubbo:method/>`      | 方法级别配置     | ServiceConfig和ReferenceConfig的方法级别配置。               |
| `<dubbo:argument/>`    | 参数配置         | 用于指定方法参数配置。                                       |

### Dubbo头协议

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.2.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.2.xsd
		http://code.alibabatech.com/schema/dubbo
		http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
	
</beans>
```

### 面试题

```
1.什么是RPC？RPC框架相对EJB调用的好处？
     RPC（Remote Procedure Call）—远程过程调用， ejb可以进行远程调用，但是不能够跨语言，ejb是同步调用，
2.什么是SOA?你知道哪些SOA解决方案？
    SOA：Service Oriented Architecture面向服务的架构。也就是把工程拆分成服务层、表现层两个工程。服务层中包含业务逻辑，只需要对外提供服务即可。表现层只需要处理和页面的交互，业务逻辑都是调用服务层的服务来实现。
    这样做的好处就是，系统之间的调用很方便，A系统要用到B系统，直接调用B系统的服务层就可以了。
3.什么是分布式部署？
   一个业务拆分成多个子系统，部署在不同的服务器上
4.什么是集群部署？
    同一个业务，部署在多个服务器上
5.描述一下基于注册中心的分布式架构？
   Provider: 暴露服务的服务提供方。
   Consumer: 调用远程服务的服务消费方。
   Registry: 服务注册与发现的注册中心。
   Monitor: 统计服务的调用次调和调用时间的监控中心。
   Container: 服务运行容器。
6.上述架构中注册中心的作用是什么？
    服务注册中心，服务自动发现: 基于注册中心目录服务，使服务消费方能动态的查找服务提供方， 使地址透明，使服务提供方可以平滑增加或减少机器。
7.provider在启动时都做了些什么？
   向注册中心注册自己提供的服务。
8.consumer在请求时都做了些什么？
  向注册中心订阅自己所需的服务。 
9.完整描述基于dubbo+zookeeper的请求应答过程？
  服务容器 Container 负责启动，加载，运行服务提供者。 
  服务提供者 Provider 在启动时，向注册中心注册自己提供的服务。 
  服务消费者 Consumer 在启动时，向注册中心订阅自己所需的服务。 
 注册中心 Registry 返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接 推送变更数据给消费者。 
 服务消费者 Consumer，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调 用，如果调用失败，再选另一台调用。
 服务消费者 Consumer 和提供者 Provider，在内存中累计调用次数和调用时间，定时每分钟 发送一次统计数据到监控中心 Monitor。

10.描述分布式开发中的子工程之间的依赖关系？
     web 依赖业务接口   业务接口依赖domian  业务依赖业务接口和dao
11.什么是长连接？长连接如何维持节点之间的通信？
  长连接就是指在基于tcp的通讯中，一直保持连接，不管当前是否发送或者接收数据。
长连接依靠心跳机制完成节点的状态维护，例如client端给zookeeper发心跳包以证明它还存在。
  为什么叫做“心跳”？ 因为它都是按照一定的频率发送的。
  12.什么是短连接？和长连接有什么区别？谈谈各自的好处？
 所谓短连接指建立SOCKET连接后发送后接收完数据后马上断开连接，一般银行都使用短连接
  长连接优点：节约TCP握手时间，可以保证高实时性，数据流向可以采用服务器端的主动推模式。
 长连接缺点：并发量不宜太高，持续占用服务端口（相对消耗资源）。
 13.长连接适合什么场景？短连接呢？
 短连接适合频率不是很高的情况，但是频率很高的情况下就不适合端连接了。
 长连接的适用情况跟短连接相反。
14.dubbo和zookeeper默认的端口号分别是多少？
20880 2181
15.dubbo支持哪些类型的注册中心？
  zookeeper Multicast Redis  Simple
16.dubbo支持哪些通信协议？
   dubbo http rmi hessian  webservice thrift
17.dubbo协议是基于什么协议的？
基于tcp协议的。netty是个框架。
18.dubbo协议适合传递体积较大的文件吗？
备注：dubbo协议不太适合体积比较大的数据，例如上传图片。
   不建议将上传操作放到provider端，因为provider端是无状态的，一般情况下它没有web容器的支持。
	无状态的组件特别适合扩展（一台机器不够了立即增加一台就能用，不必像tomcat一样需要进行session的管理。
	另外宕机后只需要重启即可，不必要求客户端重新登录。
19.如何启动zk?如何查看zk的状态？
20.dubbo提供了哪些可视化管理工具？
 dubbo-admin 　dubbo-monitor-simple 
21.dubbo使用哪一个标签来暴露一个服务？
<dubbo:service interface="com.qianfeng.p2p.sso.service.UserService" ref="userServiceImpl" timeout="600000"/>
22.dubbo使用哪一个标签来连接注册中心？
<dubbo:registry address="zookeeper://192.168.154.128:2181?backup=192.168.154.128:2182,192.168.154.128:2183"/>
23.dubbo使用哪一个标签来引用一个服务？
	<dubbo:reference interface="com.qianfeng.p2p.sso.service.UserService" id="userservice" timeout="600000"/>	
24.描述dubbo:provider标签的作用？
 提供方的缺省值，当ProtocolConfig和ServiceConfig某属性没有配置时，采用此缺省值，
25.描述dubbo:protocol的作用？
 协议配置，用于配置提供服务的协议信息，协议由提供方指定，消费方被动接受。
26.描述timeout的作用？一般声明在哪一端？
timeout是超时配置，如果consumer在规定的时间内没有收到provider的返回值就会超时。
一旦超时，就会重试，从而引起业务执行多次。一般在提供方
27.描述retries的作用？
dubbo在调用服务不成功时，会重试。
例如retries="3",最大重试3次。

28.描述dubbo:application标签name属性的作用？
<!-- 告诉别人我是谁,这只是一个别名 -->
29.dubbo.properties文件可以取代spring.xml配置吗？
可以
30.描述一下属性配置的优先级？
xml中的配置要大于properties.
consumer大于provider.
局部优于全局.
备注：
过滤器，如果在全局和service均设置了过滤器A和B，那么在执行方法的时候
会执行A、B两个过滤器，不会产生覆盖，并且先执行A再执行B。

--------------中高级
1.pojo中的类为什么必须实现序列化接口？
  1）使用dubbo进行通信，必须将对象转换成流，在网络上传输要用到io，必然要实现序列化接口
  2）当consumer收到流后要进行反序列化将流转成对象。
  3）要求双方必须是同一个类，名称、属性、包必须一模一样。
  dubbo2.6.0版本支持kryo序列化方式。
2.提供者是无状态的，描述一下什么是无状态？
    是指服务端对于客户端每次发送的请求都认为它是一个新的请求，上一次会话和下一次会话没有联系。
	备注：
	在dubbo体系中将provider做出是无状态的。
3.如果不用注册中心或者注册中心宕机服务还有没有办法继续？
	答：注册中心中任意一台机器宕机之后，可以切换到另一台主机上。如果所有的主机都宕机了，还可以依赖本地缓存进行通信。
	备注：直连
4.dubbo连接注册中心和直连的区别 ？
	答：直连速度快些啊,但直连不能动态增减提供者。
	1）通过直连必须知道provider的地址，例如dubbo://ip:20880
	2)直连无法获取provider的令牌，会导致拒绝服务
	3）直连无法支持集群
5.dubbo在安全方面可以用哪些方式解决？
	答：通过Token令牌防止用户绕过注册中心直连,然后在注册中心上管理授权
 		Dubbo还提供服务黑白名单，来控制服务所允许的调用方。
6.consumer的spring.xml中只配置了zookeeper,它并不知道provider在哪里,哪个provider为它服务。对吗？
	答：对，因为有注册中心做协调。
7.一个服务可以用多个协议暴露，一个服务也可以注册到多个注册中心。 
	答：对
8.当一个provider恢复服务的时候,会被自动注册注册到注册中心,
并且consumer端也会自动连接上该provider的dubbo服务，对吗？
	答：对
9.描述RPC同步调用和异步调用的区别？
	答：同步慢，异步快，timeout，异步不会超时，同步会。
	备注：
	异步执行情况拿不到方法的返回值。
	User u = userService.findUser();
	u.getId();----报空指针异常
	sysout.out(u);
	如果两个方法有依赖关系，不要异步执行。
10.多注册中心情况下，如何指定某一个服务使用特定的注册中心？
	答：在暴露接口的时候指定注册中心， registry="abc"
11.描述结果缓存，如何实现？
	答：<dubbo:reference id="cacheService" interface="org.bazinga.service.CacheService" cache="lru"/> 
12.描述注册中心缓存，如何实现？
	答：provider端的配置不做任何改变
	consumer端的注册中心使用file属性指定缓存位置。
	缓存文件中，存的是provider的地址。
13.如何传递隐式参数？注意事项？
	答：就是通过编程的方式，将参数传递给filter或者后天的provider。
	在第二次调用远程方法的时候，第一次附加的隐式参数会被丢弃。
	RpcContext.getContext().setAttachment("timeout","zhangsan")
14.如何控制服务端的线程并发处理量？
	答：<dubbo:service interface="com.foo.BarService" executions="10" />
16.如何控制消费端的线程并发请求量？
	答：<dubbo:reference interface="com.foo.BarService" actives="10" />
17.描述dubbo的令牌机制？
	答：防止消费者绕过注册中心访问提供者
                      在注册中心控制权限，以决定要不要下发令牌给消费者
18.假定知道令牌值，直连模式下如何突破令牌限制？
	答：使用隐式传值给他传一个token密码设置成令牌的值.
	
19.什么是优雅停机？
	服务提供方 
                      	停止时，先标记为不接受新的请求，新请求过来时直接报错，让客户端重试其他机器；
		然后，检测线程池中的线程是否正在运行，如果有，等待所有线程执行完成，除非超时，则强制关闭；
 	服务消费方 
		停止时，不在发起新的调用请求，所有新的调用在客户端即报错；
		然后，检测有没有请求的相应还没有返回，等待相应返回，，除非超时，则强制关闭；
20.如何实现优雅停机？
	答：在dubbo.properties文件中配置dubbo.service.shutdown.wait=15000
	com.alibaba.dubbo.rpc.container.Main通过它来启动。用户不使用”kill -9 PID”等强制关闭指令
21.dubbo服务容器有哪些？各个的作用？
	答：jetty,spring,log4j容器等
	spring容器是dubbo默认加载的容器，即不用任何配置，dubbo在初始化的时候就会加载spring容器。只有加载了spring容器整个服务才可以使用。即，dubbo是基于spring的，没有spring的话dubbo无法运行。
	如果系统中配置了log4j容器，那就就可以不再使用log4j.properties文件
	会启动jetty服务器.
22.如何在dubbo的provider中使用log4j?
	答：写一个properties文件
	dubbo.container=spring,log4j
	dubbo.log4j.level=DEBUG
	dubbo.log4j.file=provider.log
23.描述dubbo-monitor的作用？
	答：作为dubbo的监控中心用来监控调用的频率和信息.
	备注：
	monitor是一个提供者。如果monitor中间宕机了，业务系统不受影响。
23.以provider为例，描述dubbo集群的容错策略？
	答：<dubbo:service cluster="failover" />
24.以provider为例，集群的负载均衡策略？
	答：<dubbo:referenceinterface="..."loadbalance="random"/>
26.描述启动时检查的作用？
	答：如果开启则代表会检查注册中心是否连接成功,如果连接失败就会报异常
27.除了properties和xml配置dubbo外，你还知道哪些配置方式？
	答：yml  注解  api
28.如何使用dubbo中的自定义过滤器？
	答：1)声明一个com.alibaba.dubbo.rpc.Filter的文本文件
	2）声明一个类并且实现了com.alibaba.dubbo.rpc.Filter接口,filter文件放置到工程的resources目录，并且将来打到META-INF/dubbo目录下
	3)spirng.xml中配置filter属性
29.RPC 异常处理跟本地调用的异常处理有什么不同吗？
	答：RPC异常处理：服务端全方位进行拦截，如果出现异常后，把异常信息转换成字符串，然后把异常信息返回到客户端中：
	本地调用的异常处理：控制台直接显示异常
	备注：
	RPC的异常除了业务自身的异常外，还有网络异常。
30.RPC调用和本地调用的性能比较？哪些场景更适合RPC调用？
	答：rpc大于本地 
	即用于某个集群内部的网络通信 。
	备注：单纯将apc调用速度来说，本地调用高于rpc。那为什么还有RPC？
	分布式部署不光是为了追求运行效率，还追求开发、部署、维护的效率。
	从微观来说，RPC调用需要走网络，走网络需要花费时间远远小于业务处理时间的话，RPC就比较划算。
	
分布式，SOA，微服务：
1）原始社会：分布式指的是部署的方式，webservice,mq,ejb+特有的ejb服务器例如webloginc等，这些都是做分布式的技术。
2）SOA,根据服务进行拆分，比之前的传统的分布式更加细致。配合rpc框架。
3）微服务，直接将应用打包成一个可执行的程序进行开发或者发布

SOA解决方案：
dubbo
spring cloud
```

