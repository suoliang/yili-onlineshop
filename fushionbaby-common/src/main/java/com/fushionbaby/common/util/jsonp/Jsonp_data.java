package com.fushionbaby.common.util.jsonp;



import java.util.HashMap;

import com.fushionbaby.common.constants.CommonConstant;


/**
 * Created by duxihu on 14-10-31.
 */
public class Jsonp_data {

    private java.util.Map<String, Object> jsonp;

    private Jsonp_data(){
        jsonp=new HashMap<String, Object>();
    }

    
    private Jsonp_data(String code, Object data, String msg){
        jsonp=new HashMap<String, Object>();
        setResponseCode(code);
        setData(data);
        setMsg(msg);
    }
    


    public String getResponseCode() {
        return (String)jsonp.get("response_code");
    }

    public Jsonp_data setResponseCode(String code) {
        if(null==code)
            jsonp.put("response_code","");
        else
            jsonp.put("response_code",code);
        return this;
    }

    public Object getData() {
        return jsonp.get("data");
    }
    
    public Jsonp_data setData(Object data) {
            jsonp.put("data",data);
        return this;
    }

    public String getMsg() {
        return (String)jsonp.get("msg");
    }

    public Jsonp_data setMsg(String msg) {
        if(null==msg)
            jsonp.put("msg","");
        else{
        	 jsonp.put("msg",msg);
        }
        return this;
    }

   


    
    public static Jsonp_data success(Object data){
        return newInstance(CommonConstant.CommonCode.SUCCESS_CODE,data,CommonConstant.CommonMessage.SUCCESS_MESSAGE);
    }

    public static Jsonp_data newInstance(String code,Object data,String msg){
        return new Jsonp_data(code,data,msg);
    }
    
}
