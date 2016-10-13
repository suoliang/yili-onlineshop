package cn.b2m.eucp.sdkhttp.example;


import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.fushionbaby.common.util.ConfigUtil;




public class SingletonClient {
	private static Client client=null;
	private SingletonClient(){
	}
//	public synchronized static Client getClient(String softwareSerialNo,String key){
//		if(client==null){
//			try {
//				client=new Client(softwareSerialNo,key);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return client;
//	}
//	public synchronized static Client getClient(){
//		ResourceBundle bundle=PropertyResourceBundle.getBundle("config");
//		if(client==null){
//			try {
//				client=new Client(bundle.getString("softwareSerialNo"),bundle.getString("key"));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return client;
//	}
	
	
	
	public synchronized static Client getClient(){
		//ResourceBundle bundle=PropertyResourceBundle.getBundle("config");
		if(client==null){
			try {
				System.out.println("ConfigUtil.getSmsSoftwareSerialNo()--->"+ConfigUtil.getSmsSoftwareSerialNo()+"ConfigUtil.getSmsKey()-->"+ConfigUtil.getSmsKey());
				client=new Client(ConfigUtil.getSmsSoftwareSerialNo(),ConfigUtil.getSmsKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	
}
