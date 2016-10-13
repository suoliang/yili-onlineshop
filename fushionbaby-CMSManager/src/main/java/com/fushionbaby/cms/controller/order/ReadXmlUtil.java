package com.fushionbaby.cms.controller.order;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.fushionbaby.log.model.LogSendGoodsConfirm;

public class ReadXmlUtil {
	public static final String FILE = "<alipay><is_success>T</is_success><response><tradeBase><buyer_account>20881123295792010156</buyer_account><buyer_actions>[REFUND, CONFIRM_GOODS]</buyer_actions><buyer_login_email>15026782962</buyer_login_email><buyer_type>PRIVATE_ACCOUNT</buyer_type><buyer_user_id>2088112329579201</buyer_user_id><channel>interface/escrow</channel><create_time>2015-01-09 16:05:00</create_time><currency>156</currency><gathering_type>1</gathering_type><last_modified_time>2015-01-12 13:15:03</last_modified_time><operator_role>B</operator_role><out_trade_no>20150109160438441140</out_trade_no><partner_id>2088711896780205</partner_id><seller_account>20887118967802050156</seller_account><seller_actions>[EXTEND_TIMEOUT]</seller_actions><seller_login_email>dream.shen@fushionbaby.com</seller_login_email><seller_type>CORPORATE_ACCOUNT</seller_type><seller_user_id>2088711896780205</seller_user_id><service_fee>0.00</service_fee><service_fee_ratio>0.0</service_fee_ratio><stop_timeout>F</stop_timeout><total_fee>0.01</total_fee><trade_from>INST_PARTNER</trade_from><trade_no>2015010957571920</trade_no><trade_status>WAIT_BUYER_CONFIRM_GOODS</trade_status><trade_type>S</trade_type></tradeBase></response><sign>867c8aa71b45322912d4272ea2b22a83</sign><sign_type>MD5</sign_type></alipay>";

	public static Map<String, String> readXMLFile(String file) throws Exception {
		Document doc = null;
		doc = DocumentHelper.parseText(file);
		Element rootElt = doc.getRootElement(); // 获取根节点
		System.out.println("根节点：" + rootElt.getName());// 拿到根节点的名字
		@SuppressWarnings("unchecked")
		Iterator<Element> it = rootElt.elementIterator();
		Map<String, String> map = new HashMap<String, String>();
		while (it.hasNext()) {
			Element element = (Element) it.next();
			if(StringUtils.equals(element.getName(), "response") ){
				Iterator<?> its = element.elementIterator("tradeBase");
				while(its.hasNext()){
					Element  itemEle =  (Element)its.next();
					map.put("out_trade_no", itemEle.elementTextTrim("out_trade_no"));
					map.put("partner_id", itemEle.elementTextTrim("partner_id"));
					map.put("buyer_account", itemEle.elementTextTrim("buyer_account"));
					map.put("buyer_login_email", itemEle.elementTextTrim("buyer_login_email"));
					map.put("buyer_user_id", itemEle.elementTextTrim("buyer_user_id"));
					map.put("seller_account", itemEle.elementTextTrim("seller_account"));
					map.put("seller_login_email", itemEle.elementTextTrim("seller_login_email"));
					map.put("seller_type", itemEle.elementTextTrim("seller_type"));
					map.put("seller_user_id", itemEle.elementTextTrim("seller_user_id"));
					map.put("total_fee", itemEle.elementTextTrim("total_fee"));
					map.put("trade_status", itemEle.elementTextTrim("trade_status"));
					map.put("trade_type", itemEle.elementTextTrim("trade_type"));
				}
			}
			if(StringUtils.equals(element.getName(), "is_success") ){
				map.put(element.getName(), element.getStringValue());
			}
		}
		return map;

	}

	public static void main(String[] args) {
		
		
		try {
			Map<String, String> xmlMap = ReadXmlUtil.readXMLFile(FILE);
			LogSendGoodsConfirm log = new LogSendGoodsConfirm();
			log.setOutOrderNo(xmlMap.get("out_trade_no"));
			log.setPartnerId(xmlMap.get("partner_id"));
			log.setBuyerAccount(xmlMap.get("buyer_account"));
			log.setBuyerLoginEmail(xmlMap.get("buyer_login_email"));
			log.setBuyerUserId(xmlMap.get("buyer_user_id"));
			log.setSellerAccount(xmlMap.get("seller_account"));
			log.setSellerLoginEmail(xmlMap.get("seller_login_email"));
			log.setSellerType(xmlMap.get("seller_type"));
			log.setSellerUserId(xmlMap.get("seller_user_id"));
			log.setTotalFee(xmlMap.get("total_fee"));
			log.setTradeStatus(xmlMap.get("trade_status"));
			log.setTradeType(xmlMap.get("trade_type"));
			System.out.println(xmlMap.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
