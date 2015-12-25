package io.jstack.sendcloud4j.mail;

import static org.junit.Assert.assertFalse;
import io.jstack.sendcloud.SendCloud;
import io.jstack.sendcloud.mail.Email;
import io.jstack.sendcloud.mail.Result;
import io.jstack.sendcloud.mail.SubStitutionVars;

import org.junit.Test;

public class TestMailWebApi {

	private String apiUser = "testApiUser";
	private String apiKey = "testApiKey";
	private String yourEmail = "test@qq.com";

	@Test
	public void testSendOut() {
		// 创建 API实例
		SendCloud webapi = SendCloud.createWebApi(apiUser, apiKey);

		// 构建模板邮件并替换模板变量
		Email email = Email
				.template("icontract_active_mail")
				.from("from@test.com")
				.fromName("denger")
				.substitutionVars(
						SubStitutionVars.sub().set("url",
								"http://www.baidu.com")).to(yourEmail);
		// 发送
		Result result = webapi.mail().sendOut(email);
		assertFalse(result.isSuccess());
	}

}
