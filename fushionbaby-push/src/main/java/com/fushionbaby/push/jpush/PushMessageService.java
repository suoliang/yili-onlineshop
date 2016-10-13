package com.fushionbaby.push.jpush;

import java.util.Map;
import java.util.Set;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.fushionbaby.common.dto.sysmgr.JpushDto;


/***
 * 
 * @author xupeijun
 * Platform  设备
 * Audience  对象（听众）
 * Notification 通知信息
 * Message  消息 ios
 * Option 推送可选项
 */


public class PushMessageService {
	/***
	 * 給所有的设备发送消息
	 * @param jpush
	 * @return
	 */
	public static  PushPayload sendMessageToAll(JpushDto jpush) {
		return PushPayload.alertAll(jpush.getContent());
	}
	/***
	 * 给所有的别名发送通知
	 * @param jpush
	 * @param map
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static PushPayload pushMessageToAllAlias(JpushDto jpush,Map<String, String> map) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(jpush.getAlias()))
				.setNotification(Notification.alert(jpush.getTitle())
						                     .ios(jpush.getContent(), map)
						                     .android(jpush.getContent(), jpush.getTitle(), map))
				.setOptions(Options.newBuilder().setApnsProduction(true).build())
				.build();
	}
	/***
	 * 给所有的标签下发送通知
	 * @param jpush
	 * @param map
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static PushPayload pushMessageToAllTag(JpushDto jpush,Map<String, String> map) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.tag(jpush.getTag()))
				.setNotification(Notification.alert(jpush.getTitle())
						                     .ios(jpush.getContent(), map)
						                     .android(jpush.getContent(), jpush.getTitle(), map))
				.build();
	}
	/***
	 * 给所有标签和tag下的用户发送通知
	 * @param jpush
	 * @param map
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static PushPayload pushMessageToAllTagAndAlias(JpushDto jpush,Map<String, String> map) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.tag(jpush.getTag())
						             .alias(jpush.getAlias()))
				.setNotification(Notification.alert(jpush.getTitle())
						              .ios(jpush.getContent(), map)
						              .android(jpush.getContent(), jpush.getTitle(), map))
				.build();
	}

	/***
	 * 给安卓用户发送通知
	 * @param jpush
	 * @param map
	 * @return
	 */
	public static PushPayload pushMessageToAndroid(JpushDto jpush,Map<String, String> map) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.all())
				.setNotification(Notification.android(jpush.getContent(), jpush.getTitle(), map))
				.build();
	}

	/***
	 * 给ios用户发送通知
	 * @param jpush
	 * @param map
	 * @return
	 */
	public static PushPayload pushMessageToIOS(JpushDto jpush, Map<String, String> map) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.all())
				.setNotification(Notification.ios(jpush.getContent(), map))
				.setMessage(Message.newBuilder().setMsgContent(jpush.getContent()).setTitle(jpush.getTitle()).build())
		     	.setOptions(Options.newBuilder().setApnsProduction(true).build())
				.build();
	}
	/***
	 * 给所有的用户发送通知（安卓和ios）
	 * @param jpush
	 * @return
	 */
	public static PushPayload pushMessageToIosAndAndroid(JpushDto jpush) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.all())
				.setNotification(Notification.newBuilder()
								.setAlert(jpush.getContent())
								.addPlatformNotification(AndroidNotification.newBuilder()
												.setTitle(jpush.getTitle())
												.addExtra("url",jpush.getUrl())
												.addExtra("type",jpush.getAccessType())
												.addExtra("data",jpush.getData())
												.build())
								.addPlatformNotification(IosNotification.newBuilder()
												.incrBadge(1)
												.setAlert(jpush.getTitle())
												.addExtra("url", jpush.getUrl())
												.addExtra("type",jpush.getAccessType())
												.addExtra("data",jpush.getData())
												.build())
								.build()).build();
	}
	
	/***
	 * 根据注册id发送消息（发货通知，物流通知等）
	 * @param code
	 * @param registrationId
	 * @param message
	 * @return
	 */
	public static PushPayload pushMessageByRegistrationId(String code,Set<String> registrationIdList,String message) {
		return PushPayload.newBuilder()
				.setAudience(Audience.registrationId(registrationIdList))
				.setPlatform(Platform.all())
				.setMessage(Message.content(message+""+code))
				.build();
	}
	
	
}
