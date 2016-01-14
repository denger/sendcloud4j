# SendCloud SDK For Java [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.jstack/sendcloud4j/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/io.jstack/sendcloud4j)

[![Travis CI Build Status](https://travis-ci.org/denger/sendcloud4j.svg)](https://travis-ci.org/denger/sendcloud4j)
[![Coverage Status](https://coveralls.io/repos/denger/sendcloud4j/badge.svg?branch=master&service=github)](https://coveralls.io/github/denger/sendcloud4j?branch=master)


[SendCloud](http://sendcloud.sohu.com) SDK For Java

* 支持 [邮箱API v2](http://sendcloud.sohu.com/doc/email_v2/send_email/#_2) 普通发送和模板发送
* 支持批量发送(模块批量变量替换)
* 支持添加附件发送



## Quick Start

##### Maven

```xml
<dependency>
	<groupId>io.jstack</groupId>
	<artifactId>sendcloud4j</artifactId>
	<version>0.0.4</version>
<dependency>
```

##### Gradle

```groovy
compile 'io.jstack:sendcloud4j:0.0.4'
```

##### 代码示例

1. 初始化 API，通过 SendCloud 后台获取 apiUser 和 apiKey，创建 `SendCloud` 实例
    ```java
    private String apiUser = "testApiUser";
    private String apiKey = "testApiKey";
    SendCloud webapi = SendCloud.createWebApi(apiUser, apiKey);
    ```

1. 创建邮件实例，支持普通邮件和模板邮件。

   普通邮件，邮件内容支持 HTML 或文本:
    ```java
    Email email = Email.general()
        .from("support@jstack.io")
        .fromName("JStack Support")
        .html("<b>Hello World!</b>")          // or .plain()
        .subject("mail title")
        .attachment(new File("att.png"))      // 添加附件 (File || byte[])
        .to("denger.it@gmail.com");
    ```
    模块邮件，使用 `Substitution.sub()` 替换变量值:
    ```java
    Email email = Email.template("template_order_customer")
        .from("support@jstack.io")
        .fromName("JStack Support")
        .substitutionVars(Substitution.sub()  // 模板变量替换
                .set("product", "iPhone 6S")
                .set("name", "denger"))
        .attachment(new File("att.png"))      // 添加附件
        .to("denger.it@gmail.com");
    ```

1. 执行发送
    ```java
    Result result = webapi.mail().send(email);
    ```

1. 处理发送结果
    ```java
    result.isSuccess();      //API 请求是否成功
    result.getStatusCode();  //API 返回码
    result.getMessage();     //API 返回码的中文解释
    ```

*如果你有新需求或问题欢迎提 issues 或 fork :)*

