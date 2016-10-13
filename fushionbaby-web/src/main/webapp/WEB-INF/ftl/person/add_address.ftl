
<!-- address -->
		<div class="address">
				<h3>确认收货地址</h3>
				<div class="add-ages-l fl width100">
				<input type="hidden" id="address_id" value="<#if gotoOrderDto?exists && gotoOrderDto.defaultAddressId?exists>${gotoOrderDto.defaultAddressId?c}</#if>" />
				<input type="hidden" id="area_code" value="<#if gotoOrderDto?exists && gotoOrderDto.province?exists>${gotoOrderDto.province}</#if>" />
					<ul class="fl width100" id="address-li">
					
					 <#if addressList??>
					   <#list addressList as tmp>
					   
						 <#if (tmp.isDefault=='y')><li class="choosed address-li" id="choosed_${tmp.address_id?c}" r="r"><#else><li class="address-li" id="choosed_${tmp.address_id?c}" r="r"></#if>
						 	<input type="hidden" name="address_id"  value="${tmp.address_id?c}" />
							<div class="add-select">
								<p class="name">${tmp.name!''}</p>
								<p><span class="province_name">${tmp.province_name!''}</span> <span class="city_name">${tmp.city_name!''}</span> <span class="district_name">${tmp.district_name!''}</span></p>
								<p class="phone">${tmp.phone!''}</p>
							</div>
							<div class="add-ages-r">
								<a class="add-r-ico rub" href="javascript:address_del(${tmp.address_id?c});" title="删除"></a>
								<a class="add-r-ico txt" href="javascript:address_edit(${tmp.address_id?c});" title="编辑"></a>
								<div class="new-addr" id="edit-addr-${tmp.address_id?c}">
									<div class="arrow"></div>
									<div class="new-addr-hd">
										<h3>编辑您的地址</h3>
										<a href="#" class="cancel"></a>
									</div>
									<ul class="new-addr-bd oh">
										<li>
											<span class="title">
											<label for="">所在地区</label>
											</span>
											<p>
											<select name="province_id" id="updateProvinceId${tmp.address_id?c}" class="address-prov myProvince" pvalue="${tmp.province_id}">
											</select>
											<select name="city_id" id="updateCityId${tmp.address_id?c}" class="address-city myCity" cvalue="${tmp.city_id}">
											</select>
											<select name="district_id" id="updateDistrictId${tmp.address_id?c}" class="address-area myDistrict" dvalue="${tmp.district_id}">
											</select>
											</p>
											<div class="error" id="error_PCD_${tmp.address_id?c}"><em>*</em><span id="">请选择省市县</span></div>
										</li>
										<li>
											<span class="title">
											<label for="deliverAddress${tmp.address_id?c}">详细地址</label>
											</span>
											<p>
											<textarea cols="30" rows="10" class="address-txt" id="deliverAddress${tmp.address_id?c}" onblur="if(this.value=='')this.value='请输入详细的地址';" onfocus="if(this.value=='请输入详细的地址')this.value='';" value="请输入详细的地址">${tmp.address_info!''}</textarea>
											</p>
										</li>
										<li>
											<div class="name-l">
												<span class="title">
												<label for="deliverName${tmp.address_id?c}">收件人</label>
												</span>
												<p>
												<input type="text" id="deliverName${tmp.address_id?c}"  placeholder="请输入姓名"  value="${tmp.name!''}" >
												</p>
											</div>
											<div class="name-r">
												<span class="title">
												<label for="phone${tmp.address_id?c}">手机</label>
												</span>
												<p>
												<input type="text" id="phone${tmp.address_id?c}" placeholder="请输入手机号" value="${tmp.phone!''}">
												</p>
											</div>
											
										</li>
										
									</ul>
									<div class="error" id="error_phone_${tmp.address_id?c}"><span></span></div>
									<div class="submitButton">
										<button onclick="address_editSubmit();">确定</button>
									</div>
								</div>
							</div>
						</li>
					   </#list>
					  </#if>	
						
						
						<li id="add-address-btn" style="margin-right:0;">
							<div class="add-address">
								<i></i> 
								<span class="add-tit">添加其他地址</span>
								<div class="new-addr" id="add-address" style="left:-77px;top:98px;">
									<div class="arrow"></div>
									<div class="new-addr-hd">
										<h3>编辑您的地址</h3>
										<a href="javascript:" class="cancel"></a>
									</div>
									<ul class="new-addr-bd">
										<li>
											<span class="title">
											<label for="">所在地区</label>
											</span>
											<p>
											<select  name="province_id" id="addProvinceId" class="address-prov myProvince">
												<option value="">上海</option>
											</select>
											<select name="city_id" id="addCityId" class="address-city myCity">
												<option value="">上海市</option>
											</select>
											<select  name="district_id" id="addDistrictId" class="address-area myDistrict">
												<option value="">闵行区</option>
											</select>
											</p>
											<div class="error" id="error_PCD_id"><em>*</em><span id="">请选择省市县</span></div>
										</li>
										<li>
											<span class="title">
											<label for="deliverAddress">详细地址</label>
											</span>
											<p>
											<textarea cols="30" rows="10" class="address-txt" id="deliverAddress" onblur="if(this.value=='')this.value='请输入详细的地址';" onfocus="if(this.value=='请输入详细的地址')this.value='';" value="请输入详细的地址"></textarea>
											<div class="error" id="error_deliverAddress_id"><em>*</em><span id="">请输入详细地址</span></div>
											</p>
										</li>
										<li>
											<div class="name-l">
												<span class="title">
													<label for="deliverName">收件人</label>
												</span>
												<p>
													<input type="text" id="deliverName"  placeholder="请输入姓名">
												</p>
												<div class="error" id="error_deliverName_id"><em>*</em><span id="">请输入姓名</span></div>
											</div>
											<div class="name-r">
												<span class="title">
													<label for="phone">手机</label>
												</span>
												<p>
													<input type="text" id="phone" placeholder="请输入手机号">
												</p>
												<div class="error" id="error_phone_id"><em>*</em><span id="">请输入正确的手机号</span></div>
											</div>
										</li>
									</ul>
									<div class="submitButton">
										<button  onclick="address_add();">确定</button>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>

	<!--<script type="text/javascript" src="${rc.contextPath}/web-js/address/address.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>-->