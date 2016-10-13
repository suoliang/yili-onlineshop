package com.fushionbaby.web.controller.activities;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.sysmgr.model.SysmgrProductCompare;
import com.fushionbaby.sysmgr.service.SysmgrProductCompareService;
import com.fushionbaby.web.controller.AbstractMainController;

/**
 * 投票活动
 * 
 * @author 张明亮
 */
@Controller
@RequestMapping("/vote")
public class VoteController extends AbstractMainController {
	/** 记录日志 */
	private static final Log logg= LogFactory.getLog(VoteController.class);
	@Autowired
	private SysmgrProductCompareService sysmgrProductService;

	/**
	 * 投票数量更新
	 * 
	 * @param sku_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update_vote")
	public Object updateVote(String sku_id) {
		if (StringUtils.isBlank(sku_id)) {
			return Jsonp.paramError();
		}
		Long skuId = null;
		try {
			skuId = Long.valueOf(sku_id);
		} catch (NumberFormatException ne) {
			logg.error("VoteController.updateVote投票sku_id参数出错!" + ne);
		}
		try {
			SysmgrProductCompare sysmgrProduct = sysmgrProductService
					.getSysmgrProductCompareBySkuId(skuId);
			if (sysmgrProduct != null) {
				SysmgrProductCompare sysmgrProductTmp=	setClickNumAndAddToProductCompare(sysmgrProduct,skuId);
				sysmgrProductService.update(sysmgrProductTmp);
			}
		} catch (Exception e) {
			logg.error("VoteController.updateVote投票异常!" + e);
			return Jsonp.error();
		}
		return Jsonp.success();
	}

	/***
	 * 设置投票的数量，然后加入产品比较
	 * @param sysmgrProduct
	 * @param skuId
	 * @return
	 */
	private SysmgrProductCompare setClickNumAndAddToProductCompare(
			SysmgrProductCompare sysmgrProduct, Long skuId) {
		Long clickNum = setVoteClickNum(sysmgrProduct);
		SysmgrProductCompare sysmgrProductTmp=addToProductCompare(clickNum,skuId);
		return sysmgrProductTmp;
	}

	/***
	 * 加入产品比较
	 * @param clickNum
	 * @param skuId
	 * @return
	 */
	private SysmgrProductCompare addToProductCompare(Long clickNum, Long skuId) {
		SysmgrProductCompare sysmgrProductTmp = new SysmgrProductCompare();
		sysmgrProductTmp.setSkuId(skuId);
		sysmgrProductTmp.setClickNum(clickNum);
		return sysmgrProductTmp;
	}

	/***
	 * 设置投票的点击次数
	 * @param sysmgrProduct
	 * @return
	 */
	private Long setVoteClickNum(SysmgrProductCompare sysmgrProduct) {
		Long oldClickNum = sysmgrProduct.getClickNum();
		oldClickNum = oldClickNum == null ? 0 : oldClickNum;
		Long clickNum = oldClickNum + 1;
		return clickNum;
	}

	/**
	 * 查询投票
	 * 
	 * @param sku_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("query_vote")
	public Object queryVote() {
		try {
			List<SysmgrProductCompare> list = sysmgrProductService.findAll();
			if (CollectionUtils.isEmpty(list)) {
				return Jsonp_data.success(Collections.EMPTY_LIST);
			}
			return Jsonp_data.success(list);
		} catch (Exception e) {
			logg.error("VoteController.queryVote投票查询异常!" + e);
			return Jsonp.error();
		}
	}
}
