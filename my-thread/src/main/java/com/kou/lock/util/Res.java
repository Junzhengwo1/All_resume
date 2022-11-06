
package com.kou.lock.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpStatus;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Res extends HashMap<String, Object> {
	@Serial
	private static final long serialVersionUID = 1L;


	//利用fastJSon
	public <T> T getData(String key , TypeReference<T> typeReference){
		Object data = get(key);
		String s = JSON.toJSONString(data);
		return JSON.parseObject(s, typeReference);
	}

	public Res setData(Object data){
		put("data",data);
		return this;
	}

	public Res() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static Res error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static Res error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static Res error(int code, String msg) {
		Res r = new Res();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Res ok(String msg) {
		Res r = new Res();
		r.put("msg", msg);
		return r;
	}
	
	public static Res ok(Map<String, Object> map) {
		Res r = new Res();
		r.putAll(map);
		return r;
	}
	
	public static Res ok() {
		return new Res();
	}

	public Res put(String key, Object value) {
		super.put(key, value);
		return this;
	}


	public Integer getCode() {

		return (Integer) this.get("code");
	}

}
