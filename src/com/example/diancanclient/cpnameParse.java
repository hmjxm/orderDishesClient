package com.example.diancanclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class cpnameParse {
	private static final String urlPath = "http://10.169.162.122:8080/cbb+book/getcpServlet";  
	public static List<Order> getcpname() throws Exception {  
        List<Order> mlists = new ArrayList<Order>();  
        byte[] data = readParse(urlPath);  
        JSONArray array = new JSONArray(new String(data));  
        for (int i = 0; i < array.length(); i++) {  
        	JSONObject item = array.getJSONObject(i);  
            String dname = item.getString("dname");  
            String dtype = item.getString("dtype");
            Integer dprice = item.getInt("dprice");
            String durl = item.getString("dpicture");
            String dintro=item.getString("dintro");
            String dflag=item.getString("dflag");
			Bitmap bitmap=null;
            Integer dnum=item.getInt("dnum");
            Integer did=i;
            mlists.add(new Order(did,dname,dtype,dprice,durl,dnum,bitmap,dintro,dflag));   
        }  
        return mlists;  
    }  

    public static byte[] readParse(String urlPath) throws Exception {  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[1024*32];  
        int len = 0;  
        URL url = new URL(urlPath);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        InputStream inStream = conn.getInputStream();  
        while ((len = inStream.read(data)) != -1) {  
            outStream.write(data, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
  
    }  
}


