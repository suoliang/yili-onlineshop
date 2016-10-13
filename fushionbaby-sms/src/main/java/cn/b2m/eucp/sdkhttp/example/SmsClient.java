package cn.b2m.eucp.sdkhttp.example;

import java.rmi.RemoteException;


/***
 * 短信类
 * @author King 索亮
 *
 */
public class SmsClient {
	/**
	 * 发送短信、可以发送定时和即时短信 sendSMS(String[] mobiles,String smsContent, String
	 * addSerial, int smsPriority) 1、mobiles 手机数组长度不能超过1000 2、smsContent
	 * 最多500个汉字或1000个纯英文
	 * 、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
	 * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
	 * 4、优先级范围1~5，数值越高优先级越高(相对于同一序列号) 5、其它短信发送请参考使用手册自己尝试使用
	 * @param mobile 手机号
	 * @param content 短信内容
	 * @throws RemoteException 
	 */
	public static int sendSMS(String mobile,String content) throws RemoteException {
		int i = SingletonClient.getClient().sendSMS(new String[] { mobile }, content, "",5);// 带扩展码
		return i;
	}
	/**
	 * 发送定时短信 sendScheduledSMSEx(String[] mobiles, String smsContent,String
	 * sendTime,String srcCharset) 1、mobiles 手机数组长度不能超过1000 2、smsContent
	 * 最多500个汉字或1000个纯英文
	 * 、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
	 * 3、sendTime 定时短信发送时间 定时时间格式为：年年年年月月日日时时分分秒秒，例如20090801123030
	 * 表示2009年8月1日12点30分30秒该条短信会发送到用户手机 4、srcCharset 字符编码，默认为"GBK"
	 * 5、其它定时短信发送请参考使用手册自己尝试使用
	 * @param mobile 手机号
	 * @param content 短信内容
	 * @param time 发送时间
	 * @throws RemoteException 
	 */
	public static int sendScheduledSMS(String mobile,String content,String time) throws RemoteException {
		int i = SingletonClient.getClient().sendScheduledSMSEx(new String[] { mobile },content, time, "GBK");
		return i;
	}
	/***
	 * 获取短信的余额
	 * @return
	 * @throws RemoteException 
	 */
	public static double getBalance() throws RemoteException {
		double a = SingletonClient.getClient().getBalance();
		return a;
	}
}
