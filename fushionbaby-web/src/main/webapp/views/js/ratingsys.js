

var sMax;	// 最大数量的星星即最大评分值
var holder; // 鼠标停留的评分控件
var preSet; // 保存了评分值（通过单击来进行评分）
var rated; //是否评分过，并保存了结果（注意此值一旦设为空，就不能再评分）

// 鼠标停留事件
function rating(num,id,sMaxs,sStart){
	//num--
	//id---第几个评价
	//sMaxs--最大的评价分
	//sStart--开始评价分
	sMax = 0;	// 默认值为0
	for(n=0; n<num.parentNode.childNodes.length; n++){
		if(num.parentNode.childNodes[n].nodeName == "A"){
			sMax++;
		}
	}
	//alert(sMax);
	if(!rated){
		s = num.id.replace("_", ''); // 获取选中的星星的索引，这里使用_1,_2,_3,_4,_5来做为评分控件的ID，当然也有其他的方式。
		//alert(s);
		a = 0;
		for(i=sStart; i<=sMaxs; i++){
		//alert(sStart);
			if(i<=s){
				document.getElementById("_"+i).className = "on";

				holder = a+1;
				a++;
			}else{
				document.getElementById("_"+i).className = "";
			}
		}
	}
}

// 离开事件
function off(me,num,sMaxs,sStart2){
	//me--
	//num---第几个评价
	//sMaxs--最大的评价分
	//sStart2--开始评价分
	if(!rated){
		if(!preSet){
			for(i=sStart2; i<=sMaxs; i++){
				document.getElementById("_"+i).className = "";

			}
		}else{
			rating(preSet);
			//document.getElementById("rateStatus").innerHTML = document.getElementById("ratingSaved").innerHTML;
		}
	}
}

// 点击进行评分
function rateIt(me,num,sMax,sStart){
	if(!rated){
		//document.getElementById("ratingSaved").innerHTML + " :: "+
		preSet = me;
		//rated=1;  //设为1以后，就变成了最终结果，不能再修改评分结果
		sendRate(me);
		rating(me);
	}
}

//使用Ajax或其他方式发送评分结果
function sendRate(sel){
	//alert("评分结果: "+sel.title);
	//alert("score: "+sel.id);
	document.getElementById("score").value = sel.id.replace("_", '');
}

