
		window.onload=function(){
			var all=document.getElementById("all");// 获取全选的表单
			var allinput=document.getElementById("content").getElementsByTagName("input");
			// 获取content里面的所有的表单
			all.onclick=function(){ // 点击表单事件

				if(all.checked==true) // 首先判断改表单是否被选定
				{
					for(var i=0;i<=allinput.length-1;i++){ // 如果选定了全选表单，把下面所有的表单都选上
						allinput[i].checked=true;
					}
				}
				else{ // 否则 把content里面的表单都不选上

					for(var i=0;i<=allinput.length-1;i++){
						allinput[i].checked=false;
					}
				}


			}
		}
