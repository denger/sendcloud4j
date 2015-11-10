# SendCloud Java SDK

[![Travis CI Build Status](https://travis-ci.org/denger/sendcloud-javasdk.svg)](https://travis-ci.org/denger/sendcloud-javasdk)
[![Coverage Status](https://coveralls.io/repos/denger/sendcloud-javasdk/badge.svg?branch=master&service=github)](https://coveralls.io/github/denger/sendcloud-javasdk?branch=master)
[![Landscape Status](https://landscape.io/github/denger/sendcloud-javasdk/master/landscape.svg?style=flat)](https://landscape.io/github/denger/sendcloud-javasdk)
[![Coverage Status](https://coveralls.io/repos/denger/sendcloud-javasdk/badge.svg?branch=master&service=github)](https://coveralls.io/github/denger/sendcloud-javasdk?branch=master)

[SendCloud](http://sendcloud.sohu.com) API SDK For Java

## Quick Start
```xml
<dependency>
	<groupId>com.sohu</groupId>
	<artifactId>sendcloud-javasdk</artifactId>
	<version>0.1-SNAPSHOT</version>
<dependency>
```

##### 发送 HTML 邮件
```java
// 通过 sendcloud 后台获取 api_user 和 api_key
private String apiUser = "testApiUser";
private String apiKey = "testApiKey";

// 创建 API实例
SendCloud webapi = SendCloud.createWebApi(apiUser, apiKey);

// 构建邮件内容
Email email = Email.general()
            .from("from@test.com")
            .fromName("denger")
            .subject("this is subject")
            .html("test html")
            .to("denger.it@gmail.com");
// 发送
webapi.mail().send(email);
```

##### 发送模板邮件
```java
// 通过 sendcloud 后台获取 api_user 和 api_key
private String apiUser = "testApiUser";
private String apiKey = "testApiKey";

// 创建 API实例
SendCloud webapi = SendCloud.createWebApi(apiUser, apiKey);

// 构建模板邮件并替换模板变量
Email email = Email.template("template_name")
            .from("from@test.com")
            .fromName("denger")
            .substitutionVars(SubStitutionVars.sub().set("url", "http://www.baidu.com"))
            .to("denger.it@gmail.com");
// 发送
webapi.mail().send(email);
```


