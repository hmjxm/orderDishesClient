package com.example.diancanclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getData {
	static List<Map<String,Object>> getdata() {
		List<Map<String,Object>> datalist=new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String,Object>();
        Map<String,Object> map2 = new HashMap<String,Object>();
        Map<String,Object> map3 = new HashMap<String,Object>();
        Map<String,Object> map4 = new HashMap<String,Object>();
        Map<String,Object> map5 = new HashMap<String,Object>();
        Map<String,Object> map6 = new HashMap<String,Object>();
        map1.put("text", "ÇëÑ¡Ôñ×ÀÌ¨ºÅ!");
        map2.put("text", "1ºÅ×À¡Œ");
        map3.put("text", "2ºÅ×À¡Œ");
        map4.put("text", "3ºÅ×À¡Œ");
        map5.put("text", "4ºÅ×À¡Œ");
        map6.put("text", "5ºÅ×À¡Œ");
        datalist.add(map1);
        datalist.add(map2);
        datalist.add(map3);
        datalist.add(map4);
        datalist.add(map5);
        datalist.add(map6);
        return datalist;
    }
}
