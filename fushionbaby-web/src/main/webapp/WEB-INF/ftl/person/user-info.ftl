<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 母婴用品【基础信息】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<#include "/common/link.ftl"/>
</head>
<body>
	<div class="user-info clearfix">
		<h3 class="center-title">我的基本信息</h3>
		<form action="">
			<div class="user-info-l">
				<div class="info-list clearfix">
					<div class="info-list-l user-name">登录名：</div>
					<div class="info-list-r" id="get_userName"></div>
				</div>
				<div class="info-list clearfix">
					<div class="info-list-l user-name">您的昵称：</div>
					<div class="info-list-r">
						<div class="pet">
						<#if userExtraInfo?exists>
							<input id="nick_name" type="text" class="pet-info" placeholder="输入文本" value="${userExtraInfo.nickname}"/>
						</#if>	
						</div>
						<div id="show_nick_name" class="error"><em>*</em><em id="check_nick_name"></em></div>
					</div>
				</div>
				<div class="info-list clearfix">
					<div class="info-list-l genera">您是：</div>
					<div class="info-list-r">
						<div class="select">
							<#if userExtraInfo?exists>
								<#if userExtraInfo.baby_fm == 2>
									<input type="radio" name="parent" value="1" id="dad"/>
								<#elseif userExtraInfo.baby_fm == 1>
									<input type="radio" name="parent" value="1" id="dad" checked="checked"/>
								</#if>
							</#if>
							<label for="dad">
								<span>宝爸</span>
								<i class="dad"></i>
							</label>
						</div>
						<div class="select">
							<#if userExtraInfo?exists>
								<#if userExtraInfo.baby_fm == 2>
									<input type="radio" name="parent" value="2" id="mom" checked="checked"/>
								<#elseif userExtraInfo.baby_fm == 1>
									<input type="radio" name="parent" value="2" id="mom" />
								</#if>
							</#if>
							<label for="mom">
								<span>宝妈</span>
								<i class="mom"></i>
							</label>
						</div>
					</div>
				</div>
				
				<div class="info-list clearfix">
					<div class="info-list-l genera">宝宝性别：</div>
					<div class="info-list-r">
						<div class="select">
							<#if userExtraInfo?exists>
								<#if userExtraInfo.baby_gender == 1>
									<input type="radio" name="children" value="1" id="prince" checked="checked"/>
								<#elseif userExtraInfo.baby_gender != 1>
									<input type="radio" name="children" value="1" id="prince" />
								</#if>
							</#if>
							<label for="prince">
								<span>王子</span>
							</label>
						</div>
						<div class="select">
							<#if userExtraInfo?exists>
								<#if userExtraInfo.baby_gender == 2>
									<input type="radio" name="children" value="2" id="princess" checked="checked"/>
								<#elseif userExtraInfo.baby_gender != 2>
									<input type="radio" name="children" value="2" id="princess" />
								</#if>
							</#if>
							<label for="princess">
								<span>公主</span>
							</label>
						</div>
						<div class="select">
							<#if userExtraInfo?exists>
								<#if userExtraInfo.baby_gender == 3>
									<input type="radio" name="children" value="3" id="twins-m" checked="checked"/>
								<#elseif userExtraInfo.baby_gender != 3>
									<input type="radio" name="children" value="3" id="twins-m" />
								</#if>
							</#if>
							<label for="twins-m">
								<span>双胞胎男</span>
							</label>
						</div>
						<div class="select">
							<#if userExtraInfo?exists>
								<#if userExtraInfo.baby_gender == 4>
									<input type="radio" name="children" value="4" id="twins-f" checked="checked"/>
								<#elseif userExtraInfo.baby_gender != 4>
									<input type="radio" name="children" value="4" id="twins-f" />
								</#if>
							</#if>
							<label for="twins-f">
								<span>双胞胎女</span>
							</label>
						</div>
						<div class="select">
							<#if userExtraInfo?exists>
								<#if userExtraInfo.baby_gender == 5>
									<input type="radio" name="children" value="5"  id="twins-mf" checked="checked"/>
								<#elseif userExtraInfo.baby_gender != 5>
									<input type="radio" name="children" value="5"  id="twins-mf" />
								</#if>
							</#if>
							<label for="twins-mf">
								<span>龙凤胎</span>
							</label>
						</div>
					</div>
				</div>
				<div class="info-list clearfix">
					<div class="info-list-l genera">宝宝生日：</div>
					<div class="info-list-r">
						<#if userExtraInfo?exists>
							<input type="text" class="pet-info" id="baby_birthday" onclick="WdatePicker();" value="${userExtraInfo.baby_birthday}" />
						</#if>
					</div>
				</div>
				<div class="info-list clearfix user-infor-weixin">
					<div class="info-list-l genera">您的微信号：</div>
					<div class="info-list-r">
						<#if userExtraInfo?exists>
							<input id="weixin_no" type="text" class="pet-info" placeholder="输入微信号" value="${userExtraInfo.weixin_no!''}"/>
						</#if>	
						<div id="show_weixin_no" class="error"><em>*</em><em id="check_weixin_no"></em></div>
					</div>
				</div>
				<div class="info-list clearfix">
					<input type="button" class="mod-btn info-desc fl" onclick="update_userInfo();" value="确认修改"/>
					<span class="complete-information">完善资料将有赠品赠送</span>
				</div> 
			
			</div>
			<div class="user-info-r">
				<div class="u-photo">
					<#if userExtraInfo?exists>
						<img  id="show_image" alt="" src="#" height="104px;" width="104px;">
						<input type="hidden" value="${userExtraInfo.img_path}" id="image_path"/>
					<#elseif !userExtraInfo?exists>
						<img  id="show_image" alt="" src="#" height="104px;" width="104px;" style="display: none">
					</#if>
				</div>
				<#if userExtraInfo?exists>
					<input type="button" class="mod-btn info-desc" value="修改头像">
				<#elseif !userExtraInfo?exists>
					<input type="button" class="mod-btn info-desc" value="修改头像" disabled="disabled">
				</#if>
			</div>
			<div class="upload-pic">
				<div class="close"></div>
				<div class="file-box">
				<!--<input type='text' name='textfield' id='textfield' class='txt' /> 
				 	<input type='button' class='btn mod-btn info-desc' value='浏览...' />   -->
				 	<input type="file" id="fileField" class="file_upload" /> 
				 	<input type="hidden" name="imgUrl" id="file_photo" value="" />
				<!--<input type="file" name="fileField" class="file file_upload" id="fileField" size="28" onchange="document.getElementById('textfield').value=this.value" />
					 <button class="btn mod-btn info-desc c-upload">确定</button>  -->			    	
				</div>
			</div>
		</form>
	</div>
	<!-- 自定义 -->
	<script type="text/javascript" src="${rc.contextPath}/web-js/My97DatePicker/WdatePicker.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/web-js/membercenter/user-info.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/web-js/uploadify/swfobject.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/web-js/uploadify/jquery.uploadify.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" >
		 var _ContextPath = "${rc.contextPath}";
	</script>
</body>
</html>