package com.aladingshop.wap.controller.sku;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.WapConstant;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.util.EmptyValidUtils;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.model.SkuLabel;
import com.fushionbaby.sku.service.SkuLabelService;
import com.google.common.base.Objects;

@Controller
@RequestMapping("/skuLabel")
public class SkuLabelController {
	private static final Log LOGGER = LogFactory
			.getLog(SkuLabelController.class);
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;

	private List<SkuDto> allSku;

	/**
	 * 根据不同的标签。获取到标签下的商品列表
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param model
	 * @return
	 */
	@RequestMapping("skuList")
	public String initSkuDetail(@RequestParam("labelCode") String labelCode,
			HttpServletRequest request, ModelMap map) {
		if (Objects.equal(labelCode, null)) {
			LOGGER.error("initSkuDetail:labelCode is null");
			map.addAttribute("errorMsg", "请求出错，请重试。");
			return "error";
		}

		SkuLabel label = skuLabelService.getByCode(labelCode);
		if (Objects.equal(label, null)) {
			LOGGER.error("initSkuDetail:label is not found by code:"
					+ labelCode);
			map.addAttribute("errorMsg", "请求出错，请重试。");
			return "error";
		}

		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();
		queryCondition.setLabelCode(labelCode);
		queryCondition.setDisabled(CommonConstant.YES);
		queryCondition
				.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_APP_640);
		allSku = skuFacade.getLabelSkuList(queryCondition);

		/** 算总页数 */
		Integer allPage = (allSku.size() - 1) / WapConstant.WAP_LIST_COUNT + 1;

		map.addAttribute("result", getPageList(WapConstant.WAP_DEFAULT_PAGE));
		map.addAttribute("currentPage", WapConstant.WAP_DEFAULT_PAGE);
		map.addAttribute("allPage", allPage);
		map.addAttribute("labelName", label.getName());

		return "sku/labellist";
	}

	@ResponseBody
	@RequestMapping("updateList")
	public Object loginSetNewPwd(@RequestParam("currentPage") String curPage) {
		try {
			if (StringUtils.isBlank(curPage)) {
				return Jsonp.error("请求异常，请重试");
			}

			Integer page = Integer.parseInt(curPage) + 1;

			if (EmptyValidUtils.arrayIsEmpty(allSku)) {
				return Jsonp_data.success(new ArrayList<SkuDto>());
			}

			return Jsonp_data.success(getPageList(page));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("SkuLabelController更新列表异常：" + e);
			return Jsonp.error("刷新列表页失败，请重试。");
		}
	}

	private List<SkuDto> getPageList(Integer curPage) {
		List<SkuDto> results = new ArrayList<SkuDto>();
		Integer start = curPage * WapConstant.WAP_LIST_COUNT;
		Integer end = (curPage + 1) * WapConstant.WAP_LIST_COUNT;

		if (start > allSku.size()) {
			return results;
		} else if (end > allSku.size()) {
			end = allSku.size();
		}

		results = allSku.subList(start, end);

		return results;
	}
}
