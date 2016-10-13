package cn.b2m.eucp.sdkhttp.example;

public class TestSendMsg {

	public static void main(String[] args) {
		//testGetBalance();
		sendSMS("18321218140","【阿拉丁玛特】订单单号为：的宝贝正驶至您的家门口，快递公司为：，快递单号为：。");
	}

	/**
	 * 发送短信、可以发送定时和即时短信 sendSMS(String[] mobiles,String smsContent, String
	 * addSerial, int smsPriority) 1、mobiles 手机数组长度不能超过1000 2、smsContent
	 * 最多500个汉字或1000个纯英文
	 * 、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
	 * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
	 * 4、优先级范围1~5，数值越高优先级越高(相对于同一序列号) 5、其它短信发送请参考使用手册自己尝试使用
	 */
	public static void sendSMS(String mobile,String content) {
		try {
			int i = SingletonClient.getClient().sendSMS(new String[] { mobile }, content, "",5);// 带扩展码
			System.out.println("testSendSMS=====" + i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//余额方法
	// 获取软件序列号的余额
	public static void testGetBalance() {
		try {
			double a = SingletonClient.getClient().getBalance();
			System.out.println("testGetBalance:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
