//户外活动详情
function outside_art(){
	 $.ajax({
       	type:"POST",
           data:{'activitiesId':$("#activitiesId").val(),
                 'activitiesName':$("#activitiesName").val(),
                 'number':$("#number  option:selected").text(),
                 'phone':$('#phone').val()
                },
           async:false,
           url:_ContextPath + '/activity/apply.do',
           dataType:"json",
           success: function (data) {
       		if (data.responseCode=="0") {
       		   alert("报名申请成功！");
       		} else {
       		   alert(data.msg);
       		}
           }
       });
}
     
  