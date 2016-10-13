package com.aladingshop.wap.controller.ruyibao;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.model.AlabaoIncomeRecord;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.aladingshop.alabao.service.AlabaoIncomeRecordService;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.alabao.vo.AlabaoBillDto;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.date.DateFormat;

/**
 * @description 如意宝详情记录查询
 * @author 索亮
 * @date 2015年9月10日上午11:48:40
 */
@Controller
@RequestMapping("/ruyibao")
public class AlabaoDetailController {
	private static final Log LOGGER = LogFactory
			.getLog(AlabaoDetailController.class);

	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> shiftToRecordService;
	@Autowired
	private AlabaoIncomeRecordService<AlabaoIncomeRecord> alabaoIncomeRecordService;
	@Autowired
	private AlabaoConsumeRecordService<AlabaoConsumeRecord> alabaoConsumeRecordService;
	@Autowired
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;

	@RequestMapping("getAllRecord")
	public ModelAndView getAllRecordRequest(HttpServletRequest request) {
		String alabaoSid = CookieUtil.getCookieValue(request,
				CookieConstant.ALABAO_SID);
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache
				.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return new ModelAndView("redirect:/login/index");
		}

		ModelAndView modelAndView = new ModelAndView("ruyibao/spe-ruyi-detail");
		/** 获取转入列表 */
		modelAndView.addObject("shiftToRecord",
				this.shiftToRecord(alabaoUserDto));
		/** 获取收益列表 */
		modelAndView
				.addObject("incomeRecord", this.incomeRecord(alabaoUserDto));
		/** 获取消费列表 */
		modelAndView.addObject("consumeRecord",
				this.consumeRecord(alabaoUserDto));
		/** 获取转出列表 */
		modelAndView.addObject("rollOffRecord",
				this.rollOffRecord(alabaoUserDto));

		return modelAndView;
	}

	/***
	 * 转入记录
	 * 
	 * @param alabaoSid
	 * @return
	 */
	private List<AlabaoBillDto> shiftToRecord(AlabaoUserDto alabaoUserDto) {
		try {
			List<AlabaoShiftToRecord> shiftToRecord = shiftToRecordService
					.findAllByMemberId(alabaoUserDto.getMemberId());
			List<AlabaoBillDto> recordDtolist = new ArrayList<AlabaoBillDto>();
			for (AlabaoShiftToRecord alabaoShiftToRecord : shiftToRecord) {
				AlabaoBillDto alabaoBillDto = new AlabaoBillDto();
				alabaoBillDto.setCreateTime(DateFormat
						.dateToString(alabaoShiftToRecord.getCreateTime()));
				alabaoBillDto.setTradeMoney(NumberFormatUtil
						.numberFormat(alabaoShiftToRecord.getTransferMoney()));
				recordDtolist.add(alabaoBillDto);
			}

			return recordDtolist;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("转入记录查询出错", e);
			return null;
		}
	}

	/***
	 * 收益记录
	 * 
	 * @param alabaoSid
	 * @return
	 */
	private List<AlabaoBillDto> incomeRecord(AlabaoUserDto alabaoUserDto) {
		try {
			List<AlabaoIncomeRecord> incomeRecord = alabaoIncomeRecordService
					.findAllByMemberId(alabaoUserDto.getMemberId());
			List<AlabaoBillDto> recordDtoList = new ArrayList<AlabaoBillDto>();
			for (AlabaoIncomeRecord alabaoIncomeRecord : incomeRecord) {
				AlabaoBillDto incomeRecordDto = new AlabaoBillDto();
				incomeRecordDto.setCreateTime(DateFormat
						.dateToString(alabaoIncomeRecord.getCreateTime()));
				incomeRecordDto.setTradeMoney(NumberFormatUtil
						.numberFormat(alabaoIncomeRecord.getIncomeMoney()));
				recordDtoList.add(incomeRecordDto);
			}
			return recordDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("收益记录查询出错", e);
			return null;
		}
	}

	/***
	 * 消费记录
	 * 
	 * @param alabaoSid
	 * @return
	 */
	private List<AlabaoBillDto> consumeRecord(AlabaoUserDto alabaoUserDto) {
		try {
			List<AlabaoConsumeRecord> consumeRecord = alabaoConsumeRecordService
					.findAllByMemberId(alabaoUserDto.getMemberId());
			List<AlabaoBillDto> consumeRecordDtoList = new ArrayList<AlabaoBillDto>();
			for (AlabaoConsumeRecord alabaoConsumeRecord : consumeRecord) {
				AlabaoBillDto consumeRecordDto = new AlabaoBillDto();
				consumeRecordDto.setCreateTime(DateFormat
						.dateToString(alabaoConsumeRecord.getCreateTime()));
				consumeRecordDto.setTradeMoney(NumberFormatUtil
						.numberFormat(alabaoConsumeRecord.getConsumeMoney()));
				consumeRecordDtoList.add(consumeRecordDto);
			}
			return consumeRecordDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("消费记录查询出错", e);
			return null;
		}
	}

	/**
	 * 转出记录
	 * 
	 * @param alabaoSid
	 * @return
	 */
	private List<AlabaoBillDto> rollOffRecord(AlabaoUserDto alabaoUserDto) {
		try {
			List<AlabaoRollOffRecord> list = alabaoRollOffRecordService
					.findAllByMemberId(alabaoUserDto.getMemberId());
			List<AlabaoBillDto> offRecordList = new ArrayList<AlabaoBillDto>();
			for (AlabaoRollOffRecord alabaoRollOffRecord : list) {
				AlabaoBillDto rollOffRecordDto = new AlabaoBillDto();
				rollOffRecordDto.setCreateTime(DateFormat
						.dateToString(alabaoRollOffRecord.getCreateTime()));
				rollOffRecordDto.setTradeMoney(NumberFormatUtil
						.numberFormat(alabaoRollOffRecord.getTransferMoney()));
				offRecordList.add(rollOffRecordDto);
			}
			return offRecordList;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("转出记录查询出错", e);
			return null;
		}
	}

}
