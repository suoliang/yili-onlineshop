package com.fushionbaby.web.controller.sku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuProduct;
import com.fushionbaby.sku.service.FindSkuService;
import com.fushionbaby.sku.service.SkuProductService;
import com.fushionbaby.sku.service.SkuService;
import com.fushionbaby.web.controller.AbstractMainController;

@Controller
public abstract class AbstractSkuController extends AbstractMainController {

	@Autowired
	protected SkuProductService<SkuProduct> skuProductService;
	@Autowired
	protected FindSkuService findSkuService;
	@Autowired
	protected SkuService<Sku> skuService;
}
