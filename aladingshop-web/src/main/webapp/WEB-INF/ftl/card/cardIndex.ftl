<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>益多宝卡</title>
         <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
        <script>
        function modify(num){
           $("#totalMoney").html('总金额：'+num+'.00 元')
        }
        </script>
    </head>
    <body id="card">
 <!-- 右侧悬浮菜单 开始-->
        <#include "/common/rightMenu.ftl" />
        <!-- 右侧悬浮菜单 结束-->
        <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->
        <!-- 头部搜索版块 开始 -->
        <#include "/common/header.ftl" />
        <!-- 头部搜索版块 结束 -->
        <!-- 菜单 开始 -->
        <#include "/common/menu.ftl" />
        
		<div class="wp100 oh">
            <div class="card-wrap container mT20">
            <div class="detail-box">
                <div id="detail-l">
                    <div class="detail-m-img">
                        <a href="javascript:void(0)">
                            <img src="${rc.contextPath}/static/shop/images/card-s.jpg">
                            <img src="${rc.contextPath}/static/shop/images/card-ds.jpg" style="display:none;">
                            <img src="${rc.contextPath}/static/shop/images/card-y.jpg" style="display:none;">
                        </a>
                    </div>
                </div>

                <div id="detail-r">
                    <div class="classify">
                        <span class="wp100 size18">卡类：</span>
                        <div class="classify-wrap">
                            <#if memberConfigList?exists>
                                <#list memberConfigList as cardConfig>
                                <#if cardConfig_index==0>
                                  <a class="active" href="javascript:void(0)">
                                  <#else>
                                  <a class="" href="javascript:void(0)">
                                  </#if>
	                                <i></i>
	                                <img src="<#if cardConfig.imgUrl?exists>${imgPath}${cardConfig.imgUrl}<#else>${rc.contextPath}/static/shop/images/card.png</#if>" alt="">
	                                <p>${cardConfig.typeName}</p>
	                                <input type="hidden"  value="${cardConfig.id}">
                                    <input name="lowestMoney" type="hidden"  value="${cardConfig.lowestMoney}">
                                   </a>
                                </#list>
                            </#if>
                        </div>
                    </div>
                    <div class="card-count">
                        <span class="wp100 textL size18">面值：</span>
                        <div class="reduce">-</div>
                        <input class="num" value="1000"  onkeyup="this.value=this.value.replace(/\D/g,'')" 
   							onafterpaste="this.value=this.value.replace(/\D/g,'')"  onblur="modify(this.value)">
                        <div class="add">+</div>
                    </div>
                    <div class="money fl wp100 mT50">
                        <span class="wp100 textL size18 red" id="totalMoney">总金额：1000.00 元</span>
                    </div>
                    <div class="detail-r-btn">
                        <button class="buy-now">立即购买</button>
                    </div>
                </div>
            </div>
            	<!--<input name="configId" id="configId" value="1" type="hidden">
				<input name="faceValue" id="faceValue" value="1000" type="hidden">-->
			<form method="post" id="subForm" action="${rc.contextPath}/card/goPay">
            	<input name="orderCode" id="orderCode"  type="hidden" value="">
				<input name="sid" id="sid"  type="hidden" value="">
				<input name="payWay" id="payWay"  type="hidden" value="">
			</form>
            <div class="detail-main">
                <section class="mB30 card-section1">
                    <img src="${rc.contextPath}/static/shop/images/card-2.png">
                    <p>1.点击购买益多宝储值卡。</p>
                    <p>2.请选择您需要的卡，季卡，双季卡或者年卡。</p>
                    <p>3.选择您需要的面值，也可以直接输入您需要的面值。</p>
                    <p>4.点击“立即购买”按钮，选择您需要的支付方式，确认支付即可。</p>
                    <p>5.购买成功，即可使用益多宝储值卡购买阿拉丁商城的商品。</p>
                    <p>6.在您提交商品订单的时候勾选使用益多宝赠券。</p>
                </section>
                <section class="card-section2 oh">
                    <p><img src="${rc.contextPath}/static/shop/images/card-3.png"></p>
                    <img src="${rc.contextPath}/static/shop/images/card-s.jpg" height="223" width="359" alt="" class="fl card-section2-img">
                    <div class="card-section2-p oh fl">
                        <p>益多宝储值卡分季卡、半年卡和年卡三种</p>
                        <p>1.季    卡：期限3个月，月返利率0.83%，年化率  10%，100元起投</p>
                        <p>2.半年卡：期限6个月，月返利率 1.00%，年化率  12%，100元起投</p>
                        <p>3.年  卡：期限12个月，月返利率 1.25%，年化率 15%，100元起投</p>
                        <p>期限内不可消费本金部分，但可以使用赠券购买阿拉丁玛特商城商品，享九折优惠。赠券在购卡结束后的公历每月10日，自动放入您的益多宝账户中</p>
                        <p>益多宝账户可以提现，提现请提前3日拨打</p>
                        <p>客服电话：4000-021-060</p>
                    </div>
                </section>
            </div>
        </div>
</div>
        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
     <script>
            $(function(){
                /*选择卡种*/
                $('.classify-wrap').find('a').click(function(event) {
                    $(this).addClass('active').siblings().removeClass('active');
                    var thisIndex = $(this).index();
                    $('.detail-m-img').find('img').eq(thisIndex).show().siblings().hide();
                    var thisSrc = $(this).find('img').attr('src');
                    $('.card-section2-img').attr('src', thisSrc);
                    
                    var lowestMoney=$('.active input[name="lowestMoney"]').val();
                       $('.card-count').find('.num').val(lowestMoney);
                       $("#totalMoney").html('总金额：'+lowestMoney+'.00 元')
                       
                });
                /*输入面值*/
                var $num = $('.card-count').find('.num');
                var $add = $('.card-count').find('.add');
                var $reduce = $('.card-count').find('.reduce');
                $add.click(function(event) {
                    var num = parseInt($num.val());
                    num += 1000;
                    $num.val(num);
                    $("#totalMoney").html('总金额：'+num+'.00 元')
                });
                $reduce.click(function(event) {
                    var num = parseInt($num.val());
                    var lowestNum=$('.active input[name="lowestMoney"]').val();
                    if (num>lowestNum) {
                        num -= 1000;
                        if(num<lowestNum)
                        num=lowestNum;
                        $num.val(num);
                    $("#totalMoney").html('总金额：'+num+'.00 元')
                    };
                });
                $()
                /*点击购买按钮*/
                $('.buy-now').click(function(event) {
                    var num = parseInt($num.val());
                    if (num<1000) {
                        openAddCartModal(0,'购买金额需不低于1000元',2000);
                    } else {
                        /*购买操作*/
                        $("#faceValue").val(num);
                        var faceValue=num;
                        var configId=$(".active input").val();
                        
                        $.post("${rc.contextPath}/card/createCardOrder",{faceValue:faceValue,time:new Date().getTime(),configId:configId},function(data){
                            if(data.responseCode==0){
                               $("#orderCode").val(data.data.orderCode);
                               $("#sid").val(data.data.sid);
                               $("#payWay").val(data.data.payWay);
                               $("#subForm").submit();
                            }else if(data.responseCode==201){
                                alert(data.msg);
                                window.location.href="${rc.contextPath}/login/index?"+new Date().getTime();
                            }else if(data.responseCode==300){
                                  alert(data.msg);
                            }else{
                                window.location.href="${rc.contextPath}/login/index?"+new Date().getTime();
                            }
                        });
                        
                        
                    }
                });
            });
        </script>


    </body>
</html>
