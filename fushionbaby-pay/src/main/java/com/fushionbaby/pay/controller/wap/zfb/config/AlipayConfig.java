package com.fushionbaby.pay.controller.wap.zfb.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088911777868192";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;
	// 商户的私钥
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALTpDh4IDJ+vNNxs95nqnKJBJNH6h8Ztixz6qdS3jvd3MDhiWEBktGgfly+FVMbxL3jaEEWWPoeJflS1sv2U6TqYvxkhv+waZk757TVrUOnuySsVLIaZyN4pDhPsUZuNiEz2uQMCT0fJaUZT4rQ8ab7TGn4qatnaWLi5ExLL2yajAgMBAAECgYAnclHDtpmaEAgEsDXk98Ujk/TFXE1StPwKW5XAbi3cQ77d7IO1tr+nnIvIrtfncRuazGGT6sjDoPVM3zmiFWAtKqhbTU2ON+LRx854OBJngAoS5Ghgs3gA5TwJnrJ638i33V2uAdfZK3QvcCNyBgADO+iI/zSwGb/ex9p1AoQ+EQJBAOW6NQrMBl50NZPkrUP9kTobmC1v6sB0xFTh0zuDZM2zSMf54IjjhFEV66saxIhuCdZZO7WzHdQO6cXDcFXpWp0CQQDJmZ5xldqsRQzGhFt2/6gz837wkFb85PO4WkcpnrZCy5l7STtZLV3e6pLMRZ9ZFXIz+NCGB2Uz0+uwwQTLaSI/AkARUA4tSSD1v1ZGVgaEyDhIo8m9KuELLgGZ0AcGRimzot5KS9eTGWpgXgGLvJGxInHVWJY6ZExMLe+KOuddYh+1AkBc5JIjmH9YXcDTQG282XSx64MUag4LHZxH2CFuRhmJkbM5IbddEhBtBP17bqFMNRuYYxACkinJxd6WZXpGGD2VAkEAiE5tT29arOhs6oz+HaHut0ArlkGsuvjnhRyXFzqYtYcUXb+lOXq0RG9S3yj9FfLcYpkt4bdu20jjhvizEUIDlg==";
	
	// 支付宝的公钥，无需修改该值
	//public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC06Q4eCAyfrzTcbPeZ6pyiQSTR+ofGbYsc+qnUt473dzA4YlhAZLRoH5cvhVTG8S942hBFlj6HiX5UtbL9lOk6mL8ZIb/sGmZO+e01a1Dp7skrFSyGmcjeKQ4T7FGbjYhM9rkDAk9HyWlGU+K0PGm+0xp+KmrZ2li4uRMSy9smowIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
