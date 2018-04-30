package com.example.diancanclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Spinner;


public class mainActivity extends Activity {
	private ListView mListView;  
    private List<Map<String,Object>> datalist; 
    private RadioGroup rg;
    private RadioButton item1;
    private RadioButton item2;
    private RadioButton item3;
    private RadioButton item4;
    private TextView txtv;
    private Spinner spinner;
    private SimpleAdapter adapter;
    private MyAdapter myadapter;
    private SlideMenu slideMenu;
    private  List<HashMap<String, Object>> listitems;
    public  static Order order;
    public static List<Order> cporder;
    
    public TextView tv_allprice; 
    public TextView tv_selectnum; 
    public TextView subtitle; 
    public TextView tv_submit;
    public CheckBox check_box;
    public TextView tv_desk;
    public static int totalPrice=0;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
    	tv_allprice=(TextView)findViewById(R.id.tv_allprice);
    	tv_selectnum=(TextView)findViewById(R.id.tv_selectnum);
    	check_box=(CheckBox)findViewById(R.id.check_box);
        subtitle=(TextView)findViewById(R.id.subtitle);
    	tv_submit=(TextView)findViewById(R.id.tv_submit);
        
        spinner = (Spinner) findViewById(R.id.spinner);
        //数据
        datalist = getData.getdata();
        System.out.println("datalist为"+datalist);
        //适配器
        adapter = new SimpleAdapter(this,datalist, R.layout.spinneritem, new String[]{"text"}, new int[]{R.id.text1});
        //设置样式
        adapter.setDropDownViewResource(R.layout.spinneritem);
        //加载适配器
        spinner.setAdapter(adapter);
        
        LayoutInflater layout=this.getLayoutInflater();
        View view=layout.inflate(R.layout.spinneritem, null);
        tv_desk=(TextView)view.findViewById(R.id.text1);
        
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id){
            	Object deskid= adapter.getItem(position);
            	String desk=deskid.toString();
            	System.out.println("桌台号为："+desk);
            	tv_desk.setText(desk);
            }

            @Override
            public void onNothingSelected(AdapterView parent) {
            	System.out.println("none!");
            }
        });
        
        mListView = (ListView) findViewById(R.id.listview2);  
        txtv=(TextView)findViewById(R.id.txtv);
        txtv.setTextColor(android.graphics.Color.BLACK);
        rg=(RadioGroup)findViewById(R.id.rg);
        item1=(RadioButton)findViewById(R.id.item1);
        item2=(RadioButton)findViewById(R.id.item2);
        item3=(RadioButton)findViewById(R.id.item3);
        item4=(RadioButton)findViewById(R.id.item4);
        
        try {
        	final Handler fHandler=new Handler(){
    			public void handleMessage(Message msg){
    				Object response;
    				response=msg.obj;
    				cporder =(List<Order>) response;
    				final ListView cpListview=(ListView)findViewById(R.id.listview3);
    				slideMenu = (SlideMenu)findViewById(R.id.slide_menu);
    				ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
    				menuImg.setOnClickListener(new OnClickListener() {
    			    public void onClick(View v) {
    				switch (v.getId()) {
    				case R.id.title_bar_menu_btn:
    					if (slideMenu.isMainScreenShowing()) {
    						slideMenu.openMenu();
    						check_box.setChecked(false);
    				    	tv_selectnum.setText("已选" + 0 + "类菜品");
    				  	    tv_allprice.setText("￥" + 0 + "");
    				  	    tv_submit.setText("提交订单");
    		    		    subtitle.setText("编辑");
    				  	    
    						
    						final Handler smenuHandler=new Handler(){
    							public void handleMessage(Message msg){
    								Object response=msg.obj;
    								listitems=(List<HashMap<String, Object>>)response;
    								shopcartAdapter shopadapter = new shopcartAdapter(mainActivity.this ,listitems); //创建适配器  
    	    						cpListview.setAdapter(shopadapter);
    								}
    				        };
    						new Thread(new Runnable(){
    							public void run(){
    								List<HashMap<String,Object>> cplist=new ArrayList<HashMap<String,Object>>();
    								Bitmap bitmap = null;
    	    						for(int j=0;j< cporder.size();j++)
    	    				        {
    	    							HashMap<String,Object> cpmap=new HashMap<String,Object>();
    	    							if(cporder.get(j).getQuantity()!=0)
    	    							{	
    	    								Integer cpnum=cporder.get(j).getQuantity();
    	    							    String cpname=cporder.get(j).getDname();
    	    							    String cptype=cporder.get(j).getDtype();
    	    							    Integer dprice=cporder.get(j).getDprice();
    	    							    Integer cpprice=dprice*cpnum;
    	    							    Integer cpid=cporder.get(j).getDid();
    	    							    bitmap=cporder.get(j).getDbitmap();
    										cpmap.put("cpid", cpid);
    										cpmap.put("cpname", cpname); 
											cpmap.put("cpprice", cpprice);
											cpmap.put("cppicture",bitmap);
			        	    	            cpmap.put("cpnum", cpnum);
			        	    	            cplist.add(cpmap); 
	    	        	    	                
	    	    							}
    	    				        }
	    	    						Message msg=new Message();
	    	    	    				msg.obj=cplist;
	    	    	    				smenuHandler.sendMessage(msg);
	    	    						System.out.println(cplist);		 
    					}}).start();
    				    } 
    					else {
    						slideMenu.closeMenu();
    						if(item1.isChecked())
    	        	        {
    							final String name=item1.getText().toString();
    	        	            txtv.setText(name);
    	        	            final Handler myHandler=new Handler(){
    	        	    			public void handleMessage(Message msg){
    	        	    				Object response;
    	        	    				response=msg.obj;
    	        	    				listitems=(List<HashMap<String, Object>>) response;
    	        	    				if(response.equals(""))
    	        	    				{
    	        	    					Toast.makeText(mainActivity.this, "解析失败！", Toast.LENGTH_LONG).show();
    	        	    				}
    	        	    				else
    	        	    				{
    	        	    				    myadapter = new MyAdapter(mainActivity.this ,listitems); //创建适配器  
    	        	    					mListView.setAdapter(myadapter); 
    	        	    			        mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
    	        		                		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	        		                		{
    	        		                			switch (parent.getId())
    	        		                			{
    	        		                			case R.id.listview2:
    	        		                				expressitemClick(position);//position 代表你点的哪一个
    	        		                				break;
    	        		                             }
    	        		                         }
    	        		                    public void expressitemClick(int postion){
    	        		                	Intent itemintent = new Intent(mainActivity.this,itemActivity.class);
    	        		                    //item内的数据保存在map中
    	        		                    @SuppressWarnings("unchecked")
    	        							Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
    	        		                    
    	        		                   //需要的数据在intent中
    	        		                    itemintent.putExtra("dname", (String)map.get("dname"));
    	        		                    itemintent.putExtra("dprice", (String)map.get("dprice"));
    	        		                    itemintent.putExtra("dintro", (String)map.get("dintro"));
    	        		                    itemintent.putExtra("bitmap",(String)map.get("durl") );
    	        		                    //跳转页面
    	        		                    startActivity(itemintent);
    	        		                }});
    	        	    				}
    	        	    			}
    	        	    		};
    	        	    		new Thread(new Runnable(){

    	        					public void run(){
    	        	    				try {
    	        	    					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
    	    	    	                    for(int j=0;j< cporder.size();j++)
    	    	    	                    {
    	    	    	                    	HashMap<String, Object> map = new HashMap<String, Object>(); 
    	    	    	                    	if(cporder.get(j).getDtype().equals(name)&&cporder.get(j).getDflag().equals("有"))
    	    	    	                    	{
    	    	    	                    		String dname=cporder.get(j).getDname();
    	    	    	                    		String dprice="¥"+cporder.get(j).getDprice()+"/份";
    	    	    	                    		Integer number=cporder.get(j).getQuantity();
    	    	    	                    		map.put("number", number);
    	    	    	                    		 map.put("dname",dname);  
    	    	        	    	                 map.put("dprice", dprice); 
    	    	        	    	                  String durl=cporder.get(j).getDpicture();
    	    	        	    	                  map.put("durl", durl);
    	    	        	    	                  String dintro=cporder.get(j).getDintro();
    	    	        	    	                  map.put("dintro",dintro);
    	    	        	    	                 Bitmap bitmap=cporder.get(j).getDbitmap();
    	    	        	    	                map.put("dpicture",bitmap);
    	    	        	    	                 data.add(map); 
    	    	    	                    	}
    	    	    	                    }
    	    	    	                    System.out.println(data);
    	    	    					Message msg=new Message();
    	    	            			msg.obj=data;
    	    	            			myHandler.sendMessage(msg);
    	        	    					}
    	        	            				
    	        	    				 catch (Exception e) {
    	        	    					// TODO Auto-generated catch block
    	        	    					e.printStackTrace();
    	        	    					
    	        	    				}
    	        	    			}
    	        	    		}).start();
    	        	        }
    						if(item2.isChecked()){ 
    							final String name=item2.getText().toString();
    	        	            txtv.setText(name);
    	        	            final Handler myHandler=new Handler(){
    	        	    			public void handleMessage(Message msg){
    	        	    				Object response;
    	        	    				response=msg.obj;
    	        	    				listitems=(List<HashMap<String, Object>>) response;
    	        	    				if(response.equals(""))
    	        	    				{
    	        	    					Toast.makeText(mainActivity.this, "解析失败！", Toast.LENGTH_LONG).show();
    	        	    				}
    	        	    				else
    	        	    				{
    	        	    				    myadapter = new MyAdapter(mainActivity.this ,listitems); //创建适配器  
    	        	    					mListView.setAdapter(myadapter); 
    	        	    			        mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
    	        		                		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	        		                		{
    	        		                			switch (parent.getId())
    	        		                			{
    	        		                			case R.id.listview2:
    	        		                				expressitemClick(position);//position 代表你点的哪一个
    	        		                				break;
    	        		                             }
    	        		                         }
    	        		                    public void expressitemClick(int postion){
    	        		                	Intent itemintent = new Intent(mainActivity.this,itemActivity.class);
    	        		                    //item内的数据保存在map中
    	        		                    @SuppressWarnings("unchecked")
    	        							Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
    	        		                    System.out.println(map);
    	        		                   //需要的数据在intent中
    	        		                    itemintent.putExtra("dname", (String)map.get("dname"));
    	        		                    itemintent.putExtra("dprice", (String)map.get("dprice"));
    	        		                    itemintent.putExtra("dintro", (String)map.get("dintro"));
    	        		                    itemintent.putExtra("bitmap",(String)map.get("durl"));
    	        		                    //跳转页面
    	        		                    startActivity(itemintent);
    	        		                }});
    	        	    				}
    	        	    			}
    	        	    		};
    	        	    		new Thread(new Runnable(){

    	        					public void run(){
    	        	    				try {
    	        	    					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
    	    	    	                    for(int j=0;j< cporder.size();j++)
    	    	    	                    {
    	    	    	                    	HashMap<String, Object> map = new HashMap<String, Object>(); 
    	    	    	                    	if(cporder.get(j).getDtype().equals(name)&&cporder.get(j).getDflag().equals("有"))
    	    	    	                    	{
    	    	    	                    		String dname=cporder.get(j).getDname();
    	    	    	                    		String dprice="¥"+cporder.get(j).getDprice()+"/份";
    	    	    	                    		Integer number=cporder.get(j).getQuantity();
    	    	    	                    		map.put("number", number);
    	    	    	                    		 map.put("dname",dname);  
    	    	        	    	                 map.put("dprice", dprice); 
    	    	        	    	                  String durl=cporder.get(j).getDpicture();
    	    	        	    	                  map.put("durl", durl);
    	    	        	    	                  String dintro=cporder.get(j).getDintro();
    	    	        	    	                  map.put("dintro",dintro);
    	    	        	    	                 Bitmap bitmap=cporder.get(j).getDbitmap();
    	    	        	    	                map.put("dpicture",bitmap);
    	    	        	    	                 data.add(map); 
    	    	    	                    	}
    	    	    	                    }
    	    	    	                    System.out.println(data);
    	    	    					Message msg=new Message();
    	    	            			msg.obj=data;
    	    	            			myHandler.sendMessage(msg);
    	        	    					}
    	        	            				
    	        	    				 catch (Exception e) {
    	        	    					// TODO Auto-generated catch block
    	        	    					e.printStackTrace();
    	        	    					
    	        	    				}
    	        	    			}
    	        	    		}).start();
        	                } 
    						if(item3.isChecked()){ 
    							final String name=item3.getText().toString();
    	        	            txtv.setText(name);
    	        	            final Handler myHandler=new Handler(){
    	        	    			public void handleMessage(Message msg){
    	        	    				Object response;
    	        	    				response=msg.obj;
    	        	    				listitems=(List<HashMap<String, Object>>) response;
    	        	    				if(response.equals(""))
    	        	    				{
    	        	    					Toast.makeText(mainActivity.this, "解析失败！", Toast.LENGTH_LONG).show();
    	        	    				}
    	        	    				else
    	        	    				{
    	        	    				    myadapter = new MyAdapter(mainActivity.this ,listitems); //创建适配器  
    	        	    					mListView.setAdapter(myadapter); 
    	        	    			        mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
    	        		                		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	        		                		{
    	        		                			switch (parent.getId())
    	        		                			{
    	        		                			case R.id.listview2:
    	        		                				expressitemClick(position);//position 代表你点的哪一个
    	        		                				break;
    	        		                             }
    	        		                         }
    	        		                    public void expressitemClick(int postion){
    	        		                	Intent itemintent = new Intent(mainActivity.this,itemActivity.class);
    	        		                    //item内的数据保存在map中
    	        		                    @SuppressWarnings("unchecked")
    	        							Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
    	        		                    System.out.println(map);
    	        		                   //需要的数据在intent中
    	        		                    itemintent.putExtra("dname", (String)map.get("dname"));
    	        		                    itemintent.putExtra("dprice", (String)map.get("dprice"));
    	        		                    itemintent.putExtra("dintro", (String)map.get("dintro"));
    	        		                    itemintent.putExtra("bitmap",(String)map.get("durl") );
    	        		                    //跳转页面
    	        		                    startActivity(itemintent);
    	        		                }});
    	        	    				}
    	        	    			}
    	        	    		};
    	        	    		new Thread(new Runnable(){

    	        					public void run(){
    	        	    				try {
    	        	    					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
    	    	    	                    for(int j=0;j< cporder.size();j++)
    	    	    	                    {
    	    	    	                    	HashMap<String, Object> map = new HashMap<String, Object>(); 
    	    	    	                    	if(cporder.get(j).getDtype().equals(name)&&cporder.get(j).getDflag().equals("有"))
    	    	    	                    	{
    	    	    	                    		String dname=cporder.get(j).getDname();
    	    	    	                    		String dprice="¥"+cporder.get(j).getDprice()+"/份";
    	    	    	                    		Integer number=cporder.get(j).getQuantity();
    	    	    	                    		map.put("number", number);
    	    	    	                    		 map.put("dname",dname);  
    	    	        	    	                 map.put("dprice", dprice); 
    	    	        	    	                  String durl=cporder.get(j).getDpicture();
    	    	        	    	                  map.put("durl", durl);
    	    	        	    	                  String dintro=cporder.get(j).getDintro();
    	    	        	    	                  map.put("dintro",dintro);
    	    	        	    	                 Bitmap bitmap=cporder.get(j).getDbitmap();
    	    	        	    	                map.put("dpicture",bitmap);
    	    	        	    	                 data.add(map); 
    	    	    	                    	}
    	    	    	                    }
    	    	    	                    System.out.println(data);
    	    	    					Message msg=new Message();
    	    	            			msg.obj=data;
    	    	            			myHandler.sendMessage(msg);
    	        	    					}
    	        	            				
    	        	    				 catch (Exception e) {
    	        	    					// TODO Auto-generated catch block
    	        	    					e.printStackTrace();
    	        	    					
    	        	    				}
    	        	    			}
    	        	    		}).start();
        	                }
    						if(item4.isChecked()) { 
    							final String name=item4.getText().toString();
    	        	            txtv.setText(name);
    	        	            final Handler myHandler=new Handler(){
    	        	    			public void handleMessage(Message msg){
    	        	    				Object response;
    	        	    				response=msg.obj;
    	        	    				listitems=(List<HashMap<String, Object>>) response;
    	        	    				if(response.equals(""))
    	        	    				{
    	        	    					Toast.makeText(mainActivity.this, "解析失败！", Toast.LENGTH_LONG).show();
    	        	    				}
    	        	    				else
    	        	    				{
    	        	    				    myadapter = new MyAdapter(mainActivity.this ,listitems); //创建适配器  
    	        	    					mListView.setAdapter(myadapter); 
    	        	    			        mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
    	        		                		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	        		                		{
    	        		                			switch (parent.getId())
    	        		                			{
    	        		                			case R.id.listview2:
    	        		                				expressitemClick(position);//position 代表你点的哪一个
    	        		                				break;
    	        		                             }
    	        		                         }
    	        		                    public void expressitemClick(int postion){
    	        		                	Intent itemintent = new Intent(mainActivity.this,itemActivity.class);
    	        		                    //item内的数据保存在map中
    	        		                    @SuppressWarnings("unchecked")
    	        							Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
    	        		                    System.out.println(map);
    	        		                   //需要的数据在intent中
    	        		                    itemintent.putExtra("dname", (String)map.get("dname"));
    	        		                    itemintent.putExtra("dprice", (String)map.get("dprice"));
    	        		                    itemintent.putExtra("dintro", (String)map.get("dintro"));
    	        		                    itemintent.putExtra("bitmap",(String)map.get("durl") );
    	        		                    //跳转页面
    	        		                    startActivity(itemintent);
    	        		                }});
    	        	    				}
    	        	    			}
    	        	    		};
    	        	    		new Thread(new Runnable(){

    	        					public void run(){
    	        	    				try {
    	        	    					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
    	    	    	                    for(int j=0;j< cporder.size();j++)
    	    	    	                    {
    	    	    	                    	HashMap<String, Object> map = new HashMap<String, Object>(); 
    	    	    	                    	if(cporder.get(j).getDtype().equals(name)&&cporder.get(j).getDflag().equals("有"))
    	    	    	                    	{
    	    	    	                    		String dname=cporder.get(j).getDname();
    	    	    	                    		String dprice="¥"+cporder.get(j).getDprice()+"/份";
    	    	    	                    		Integer number=cporder.get(j).getQuantity();
    	    	    	                    		map.put("number", number);
    	    	    	                    		 map.put("dname",dname);  
    	    	        	    	                 map.put("dprice", dprice); 
    	    	        	    	                  String durl=cporder.get(j).getDpicture();
    	    	        	    	                  map.put("durl", durl);
    	    	        	    	                  String dintro=cporder.get(j).getDintro();
    	    	        	    	                  map.put("dintro",dintro);
    	    	        	    	                 Bitmap bitmap=cporder.get(j).getDbitmap();
    	    	        	    	                map.put("dpicture",bitmap);
    	    	        	    	                 data.add(map); 
    	    	    	                    	}
    	    	    	                    }
    	    	    	                    System.out.println(data);
    	    	    					Message msg=new Message();
    	    	            			msg.obj=data;
    	    	            			myHandler.sendMessage(msg);
    	        	    					}
    	        	            				
    	        	    				 catch (Exception e) {
    	        	    					// TODO Auto-generated catch block
    	        	    					e.printStackTrace();
    	        	    					
    	        	    				}
    	        	    			}
    	        	    		}).start();
        	                }
    						
    						
    						
    					}
    					break;
    				}
    			}
    				});

    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				if(item1.isChecked())
        	        {
    					final String name=item1.getText().toString();
        	            txtv.setText(name);
        	            final Handler myHandler=new Handler(){
        	    			public void handleMessage(Message msg){
        	    				Object response;
        	    				response=msg.obj;
        	    				listitems=(List<HashMap<String, Object>>) response;
        	    				if(response.equals(""))
        	    				{
        	    					Toast.makeText(mainActivity.this, "解析失败！", Toast.LENGTH_LONG).show();
        	    				}
        	    				else
        	    				{
        	    				    myadapter = new MyAdapter(mainActivity.this ,listitems); //创建适配器  
        	    					mListView.setAdapter(myadapter); 
        	    			        mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
        		                		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        		                		{
        		                			switch (parent.getId())
        		                			{
        		                			case R.id.listview2:
        		                				expressitemClick(position);//position 代表你点的哪一个
        		                				break;
        		                             }
        		                         }
        		                    public void expressitemClick(int postion){
        		                	Intent itemintent = new Intent(mainActivity.this,itemActivity.class);
        		                    //item内的数据保存在map中
        		                    @SuppressWarnings("unchecked")
        							Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
        		                    System.out.println(map);
        		                   //需要的数据在intent中
        		                    itemintent.putExtra("dname", (String)map.get("dname"));
        		                    itemintent.putExtra("dprice", (String)map.get("dprice"));
        		                    itemintent.putExtra("dintro", (String)map.get("dintro"));
        		                    itemintent.putExtra("bitmap",(String)map.get("durl") );
        		                    //跳转页面
        		                    startActivity(itemintent);
        		                }});
        	    				}
        	    			}
        	    		};
        	    		new Thread(new Runnable(){

        					public void run(){
        	    				try {
        	    					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
    	    	                    for(int j=0;j< cporder.size();j++)
    	    	                    {
    	    	                    	HashMap<String, Object> map = new HashMap<String, Object>(); 
    	    	                    	if(cporder.get(j).getDtype().equals(name)&&cporder.get(j).getDflag().equals("有"))
    	    	                    	{
    	    	                    		String dname=cporder.get(j).getDname();
    	    	                    		String dprice="¥"+cporder.get(j).getDprice()+"/份";
    	    	                    		Integer number=cporder.get(j).getQuantity();
    	    	                    		map.put("number", number);
    	    	                    		 map.put("dname",dname);  
    	        	    	                 map.put("dprice", dprice); 
    	        	    	                  String durl=cporder.get(j).getDpicture();
    	        	    	                  map.put("durl", durl);
    	        	    	                  String dintro=cporder.get(j).getDintro();
    	        	    	                  map.put("dintro",dintro);
    	        	    	                  if(cporder.get(j).getDbitmap()==null)
    	        	    	                  {
    	        	    	                	  String url="http://10.169.162.122:8080/cbb+book/"+durl;
      	        	    	                	byte[] dpicture=cpnameParse.readParse(url);
      	        	    	                    InputStream is = new ByteArrayInputStream(dpicture); 
      	        	    	                    BitmapFactory.Options options=new BitmapFactory.Options();
      											options.inJustDecodeBounds = false;
      											options.inSampleSize =1; 
      											Bitmap bitmap=BitmapFactory.decodeStream(is,null,options);
      	        	    	                    map.put("dpicture",bitmap);
      	        	    	                    cporder.get(j).setDbitmap(bitmap);
      	        	    	                    System.out.println("图片为："+cporder.get(j).getDbitmap());
    	        	    	                  }
    	        	    	                  else
    	        	    	                  {
    	        	    	                	  Bitmap bitmap=cporder.get(j).getDbitmap();
    	        	    	                	  map.put("dpicture",bitmap);
    	        	    	                  }  
    	        	    	                 data.add(map); 
    	    	                    	}
    	    	                    }
    	    	                    System.out.println(data);
    	    					Message msg=new Message();
    	            			msg.obj=data;
    	            			myHandler.sendMessage(msg);
        	    					}
        	            				
        	    				 catch (Exception e) {
        	    					// TODO Auto-generated catch block
        	    					e.printStackTrace();
        	    					
        	    				}
        	    			}
        	    		}).start();
        	        }
        			
        			
        			  
        	        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { 
        	            @Override 
        	            public void onCheckedChanged(RadioGroup group, int checkedId) { 
        	                // TODO Auto-generated method stub 
        	                if(checkedId==item1.getId()){ 
        	                	final String name=item1.getText().toString();
                	            txtv.setText(name);
                	            final Handler myHandler=new Handler(){
                	    			public void handleMessage(Message msg){
                	    				Object response;
                	    				response=msg.obj;
                	    				listitems=(List<HashMap<String, Object>>) response;
                	    				if(response.equals(""))
                	    				{
                	    					Toast.makeText(mainActivity.this, "解析失败！", Toast.LENGTH_LONG).show();
                	    				}
                	    				else
                	    				{
                	    				    myadapter = new MyAdapter(mainActivity.this ,listitems); //创建适配器  
                	    					mListView.setAdapter(myadapter); 
                	    			        mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
                		                		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                		                		{
                		                			switch (parent.getId())
                		                			{
                		                			case R.id.listview2:
                		                				expressitemClick(position);//position 代表你点的哪一个
                		                				break;
                		                             }
                		                         }
                		                    public void expressitemClick(int postion){
                		                	Intent itemintent = new Intent(mainActivity.this,itemActivity.class);
                		                    //item内的数据保存在map中
                		                    @SuppressWarnings("unchecked")
                							Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
                		                    System.out.println(map);
                		                   //需要的数据在intent中
                		                    itemintent.putExtra("dname", (String)map.get("dname"));
                		                    itemintent.putExtra("dprice", (String)map.get("dprice"));
                		                    itemintent.putExtra("dintro", (String)map.get("dintro"));
                		                    itemintent.putExtra("bitmap",(String)map.get("durl"));
                		                    //跳转页面
                		                    startActivity(itemintent);
                		                }});
                	    				}
                	    			}
                	    		};
                	    		new Thread(new Runnable(){

                					public void run(){
                	    				try {
                	    					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
            	    	                    for(int j=0;j< cporder.size();j++)
            	    	                    {
            	    	                    	HashMap<String, Object> map = new HashMap<String, Object>(); 
            	    	                    	if(cporder.get(j).getDtype().equals(name)&&cporder.get(j).getDflag().equals("有"))
            	    	                    	{
            	    	                    		String dname=cporder.get(j).getDname();
            	    	                    		String dprice="¥"+cporder.get(j).getDprice()+"/份";
            	    	                    		Integer number=cporder.get(j).getQuantity();
            	    	                    		map.put("number", number);
            	    	                    		 map.put("dname",dname);  
            	        	    	                 map.put("dprice", dprice); 
            	        	    	                  String durl=cporder.get(j).getDpicture();
            	        	    	                  map.put("durl", durl);
            	        	    	                  String dintro=cporder.get(j).getDintro();
            	        	    	                  map.put("dintro",dintro);
            	        	    	                  if(cporder.get(j).getDbitmap()==null)
            	        	    	                  {
            	        	    	                	String url="http://10.169.162.122:8080/cbb+book/"+durl;
              	        	    	                	byte[] dpicture=cpnameParse.readParse(url);
              	        	    	                    InputStream is = new ByteArrayInputStream(dpicture); 
              	        	    	                    BitmapFactory.Options options=new BitmapFactory.Options();
              											options.inJustDecodeBounds = false;
              											options.inSampleSize =1; 
              											Bitmap bitmap=BitmapFactory.decodeStream(is,null,options);
              	        	    	                    map.put("dpicture",bitmap);
              	        	    	                    cporder.get(j).setDbitmap(bitmap);
              	        	    	                    System.out.println("图片为："+cporder.get(j).getDbitmap());
            	        	    	                  }
            	        	    	                  else
            	        	    	                  {
            	        	    	                	  Bitmap bitmap=cporder.get(j).getDbitmap();
            	        	    	                	  map.put("dpicture",bitmap);
            	        	    	                  }  
            	        	    	                 data.add(map); 
            	    	                    	}
            	    	                    }
            	    	                    System.out.println(data);
            	    					Message msg=new Message();
            	            			msg.obj=data;
            	            			myHandler.sendMessage(msg);
                	    					}
                	            				
                	    				 catch (Exception e) {
                	    					// TODO Auto-generated catch block
                	    					e.printStackTrace();
                	    					
                	    				}
                	    			}
                	    		}).start();
        	                }else if(checkedId==item2.getId()){ 
        	                	final String name=item2.getText().toString();
                	            txtv.setText(name);
                	            final Handler myHandler=new Handler(){
                	    			public void handleMessage(Message msg){
                	    				Object response;
                	    				response=msg.obj;
                	    				listitems=(List<HashMap<String, Object>>) response;
                	    				if(response.equals(""))
                	    				{
                	    					Toast.makeText(mainActivity.this, "解析失败！", Toast.LENGTH_LONG).show();
                	    				}
                	    				else
                	    				{
                	    				    myadapter = new MyAdapter(mainActivity.this ,listitems); //创建适配器  
                	    					mListView.setAdapter(myadapter); 
                	    			        mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
                		                		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                		                		{
                		                			switch (parent.getId())
                		                			{
                		                			case R.id.listview2:
                		                				expressitemClick(position);//position 代表你点的哪一个
                		                				break;
                		                             }
                		                         }
                		                    public void expressitemClick(int postion){
                		                	Intent itemintent = new Intent(mainActivity.this,itemActivity.class);
                		                    //item内的数据保存在map中
                		                    @SuppressWarnings("unchecked")
                							Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
                		                    System.out.println(map);
                		                   //需要的数据在intent中
                		                    itemintent.putExtra("dname", (String)map.get("dname"));
                		                    itemintent.putExtra("dprice", (String)map.get("dprice"));
                		                    itemintent.putExtra("dintro", (String)map.get("dintro"));
                		                    itemintent.putExtra("bitmap",(String)map.get("durl") );
                		                    //跳转页面
                		                    startActivity(itemintent);
                		                }});
                	    				}
                	    			}
                	    		};
                	    		new Thread(new Runnable(){

                					public void run(){
                	    				try {
                	    					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
            	    	                    for(int j=0;j< cporder.size();j++)
            	    	                    {
            	    	                    	HashMap<String, Object> map = new HashMap<String, Object>(); 
            	    	                    	if(cporder.get(j).getDtype().equals(name)&&cporder.get(j).getDflag().equals("有"))
            	    	                    	{
            	    	                    		String dname=cporder.get(j).getDname();
            	    	                    		String dprice="¥"+cporder.get(j).getDprice()+"/份";
            	    	                    		Integer number=cporder.get(j).getQuantity();
            	    	                    		map.put("number", number);
            	    	                    		 map.put("dname",dname);  
            	        	    	                 map.put("dprice", dprice); 
            	        	    	                  String durl=cporder.get(j).getDpicture();
            	        	    	                  map.put("durl", durl);
            	        	    	                  String dintro=cporder.get(j).getDintro();
            	        	    	                  map.put("dintro",dintro);
            	        	    	                  if(cporder.get(j).getDbitmap()==null&&dname!="凉拌海带")
            	        	    	                  {
            	        	    	                	 String url="http://10.169.162.122:8080/cbb+book/"+durl;
              	        	    	                	byte[] dpicture=cpnameParse.readParse(url);
              	        	    	                    InputStream is = new ByteArrayInputStream(dpicture); 
              	        	    	                    BitmapFactory.Options options=new BitmapFactory.Options();
              											options.inJustDecodeBounds = false;
              											options.inSampleSize =1; 
              											Bitmap bitmap=BitmapFactory.decodeStream(is,null,options);
              	        	    	                    map.put("dpicture",bitmap);
              	        	    	                    cporder.get(j).setDbitmap(bitmap);
              	        	    	                    System.out.println("图片为："+cporder.get(j).getDbitmap());
            	        	    	                  }
            	        	    	              
            	        	    	                  else
            	        	    	                  {
            	        	    	                	  Bitmap bitmap=cporder.get(j).getDbitmap();
            	        	    	                	  map.put("dpicture",bitmap);
            	        	    	                  }  
            	        	    	                 data.add(map); 
            	    	                    	}
            	    	                    }
            	    	                    System.out.println(data);
            	    					Message msg=new Message();
            	            			msg.obj=data;
            	            			myHandler.sendMessage(msg);
                	    					}
                	            				
                	    				 catch (Exception e) {
                	    					// TODO Auto-generated catch block
                	    					e.printStackTrace();
                	    					
                	    				}
                	    			}
                	    		}).start();
        	                } 
        	                else if(checkedId==item3.getId()){ 
        	                	final String name=item3.getText().toString();
                	            txtv.setText(name);
                	            final Handler myHandler=new Handler(){
                	    			public void handleMessage(Message msg){
                	    				Object response;
                	    				response=msg.obj;
                	    				listitems=(List<HashMap<String, Object>>) response;
                	    				if(response.equals(""))
                	    				{
                	    					Toast.makeText(mainActivity.this, "解析失败！", Toast.LENGTH_LONG).show();
                	    				}
                	    				else
                	    				{
                	    				    myadapter = new MyAdapter(mainActivity.this ,listitems); //创建适配器  
                	    					mListView.setAdapter(myadapter); 
                	    			        mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
                		                		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                		                		{
                		                			switch (parent.getId())
                		                			{
                		                			case R.id.listview2:
                		                				expressitemClick(position);//position 代表你点的哪一个
                		                				break;
                		                             }
                		                         }
                		                    public void expressitemClick(int postion){
                		                	Intent itemintent = new Intent(mainActivity.this,itemActivity.class);
                		                    //item内的数据保存在map中
                		                    @SuppressWarnings("unchecked")
                							Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
                		                    System.out.println(map);
                		                   //需要的数据在intent中
                		                    itemintent.putExtra("dname", (String)map.get("dname"));
                		                    itemintent.putExtra("dprice", (String)map.get("dprice"));
                		                    itemintent.putExtra("dintro", (String)map.get("dintro"));
                		                    itemintent.putExtra("bitmap",(String)map.get("durl") );
                		                    //跳转页面
                		                    startActivity(itemintent);
                		                }});
                	    				}
                	    			}
                	    		};
                	    		new Thread(new Runnable(){

                					public void run(){
                	    				try {
                	    					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
            	    	                    for(int j=0;j< cporder.size();j++)
            	    	                    {
            	    	                    	HashMap<String, Object> map = new HashMap<String, Object>(); 
            	    	                    	if(cporder.get(j).getDtype().equals(name)&&cporder.get(j).getDflag().equals("有"))
            	    	                    	{
            	    	                    		String dname=cporder.get(j).getDname();
            	    	                    		String dprice="¥"+cporder.get(j).getDprice()+"/份";
            	    	                    		Integer number=cporder.get(j).getQuantity();
            	    	                    		map.put("number", number);
            	    	                    		 map.put("dname",dname);  
            	        	    	                 map.put("dprice", dprice); 
            	        	    	                  String durl=cporder.get(j).getDpicture();
            	        	    	                  map.put("durl", durl);
            	        	    	                  String dintro=cporder.get(j).getDintro();
            	        	    	                  map.put("dintro",dintro);
            	        	    	                  if(cporder.get(j).getDbitmap()==null)
            	        	    	                  {
            	        	    	                	  String url="http://10.169.162.122:8080/cbb+book/"+durl;
              	        	    	                	byte[] dpicture=cpnameParse.readParse(url);
              	        	    	                    InputStream is = new ByteArrayInputStream(dpicture); 
              	        	    	                    BitmapFactory.Options options=new BitmapFactory.Options();
              											options.inJustDecodeBounds = false;
              											options.inSampleSize =1; 
              											Bitmap bitmap=BitmapFactory.decodeStream(is,null,options);
              	        	    	                    map.put("dpicture",bitmap);
              	        	    	                    cporder.get(j).setDbitmap(bitmap);
              	        	    	                    System.out.println("图片为："+cporder.get(j).getDbitmap());
            	        	    	                  }
            	        	    	                  else
            	        	    	                  {
            	        	    	                	  Bitmap bitmap=cporder.get(j).getDbitmap();
            	        	    	                	  map.put("dpicture",bitmap);
            	        	    	                  }  
            	        	    	                 data.add(map); 
            	    	                    	}
            	    	                    }
            	    	                    System.out.println(data);
            	    					Message msg=new Message();
            	            			msg.obj=data;
            	            			myHandler.sendMessage(msg);
                	    					}
                	            				
                	    				 catch (Exception e) {
                	    					// TODO Auto-generated catch block
                	    					e.printStackTrace();
                	    					
                	    				}
                	    			}
                	    		}).start();
        	                }
        	                else { 
        	                	final String name=item4.getText().toString();
                	            txtv.setText(name);
                	            final Handler myHandler=new Handler(){
                	    			public void handleMessage(Message msg){
                	    				Object response;
                	    				response=msg.obj;
                	    				listitems=(List<HashMap<String, Object>>) response;
                	    				if(response.equals(""))
                	    				{
                	    					Toast.makeText(mainActivity.this, "解析失败！", Toast.LENGTH_LONG).show();
                	    				}
                	    				else
                	    				{
                	    				    myadapter = new MyAdapter(mainActivity.this ,listitems); //创建适配器  
                	    					mListView.setAdapter(myadapter); 
                	    			        mListView.setOnItemClickListener(new OnItemClickListener(){   	                  	               
                		                		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                		                		{
                		                			switch (parent.getId())
                		                			{
                		                			case R.id.listview2:
                		                				expressitemClick(position);//position 代表你点的哪一个
                		                				break;
                		                             }
                		                         }
                		                    public void expressitemClick(int postion){
                		                	Intent itemintent = new Intent(mainActivity.this,itemActivity.class);
                		                    //item内的数据保存在map中
                		                    @SuppressWarnings("unchecked")
                							Map<String, Object> map = (Map<String, Object>)myadapter.getItem(postion);	                    
                		                    System.out.println(map);
                		                   //需要的数据在intent中
                		                    itemintent.putExtra("dname", (String)map.get("dname"));
                		                    itemintent.putExtra("dprice", (String)map.get("dprice"));
                		                    itemintent.putExtra("dintro", (String)map.get("dintro"));
                		                    itemintent.putExtra("bitmap",(String)map.get("durl") );
                		                    //跳转页面
                		                    startActivity(itemintent);
                		                }});
                	    				}
                	    			}
                	    		};
                	    		new Thread(new Runnable(){

                					public void run(){
                	    				try {
                	    					List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
            	    	                    for(int j=0;j< cporder.size();j++)
            	    	                    {
            	    	                    	HashMap<String, Object> map = new HashMap<String, Object>(); 
            	    	                    	if(cporder.get(j).getDtype().equals(name)&&cporder.get(j).getDflag().equals("有"))
            	    	                    	{
            	    	                    		String dname=cporder.get(j).getDname();
            	    	                    		String dprice="¥"+cporder.get(j).getDprice()+"/份";
            	    	                    		Integer number=cporder.get(j).getQuantity();
            	    	                    		map.put("number", number);
            	    	                    		 map.put("dname",dname);  
            	        	    	                 map.put("dprice", dprice); 
            	        	    	                  String durl=cporder.get(j).getDpicture();
            	        	    	                  map.put("durl", durl);
            	        	    	                  String dintro=cporder.get(j).getDintro();
            	        	    	                  map.put("dintro",dintro);
            	        	    	                  if(cporder.get(j).getDbitmap()==null)
            	        	    	                  {
            	        	    	                	String url="http://10.169.162.122:8080/cbb+book/"+durl;
              	        	    	                	byte[] dpicture=cpnameParse.readParse(url);
              	        	    	                    InputStream is = new ByteArrayInputStream(dpicture); 
              	        	    	                    BitmapFactory.Options options=new BitmapFactory.Options();
              											options.inJustDecodeBounds = false;
              											options.inSampleSize =1; 
              											Bitmap bitmap=BitmapFactory.decodeStream(is,null,options);
              	        	    	                    map.put("dpicture",bitmap);
              	        	    	                    cporder.get(j).setDbitmap(bitmap);
              	        	    	                    System.out.println("图片为："+cporder.get(j).getDbitmap());
            	        	    	                  }
            	        	    	                  else
            	        	    	                  {
            	        	    	                	  Bitmap bitmap=cporder.get(j).getDbitmap();
            	        	    	                	  map.put("dpicture",bitmap);
            	        	    	                  }  
            	        	    	                 data.add(map); 
            	    	                    	}
            	    	                    }
            	    	                    System.out.println(data);
            	    					Message msg=new Message();
            	            			msg.obj=data;
            	            			myHandler.sendMessage(msg);
                	    					}
                	            				
                	    				 catch (Exception e) {
                	    					// TODO Auto-generated catch block
                	    					e.printStackTrace();
                	    					
                	    				}
                	    			}
                	    		}).start();
        	                }
        	            } 
        	        });
    				
    				
    			}
    				};
    			
    			
			
    	
    		new Thread(new Runnable(){
				public void run(){
    				try { 
    					List<Order> cp=cpnameParse.getcpname();
    					for(int j=0;j<cp.size();j++)
    					{
    						System.out.println(cp.get(j).getDid());
    						System.out.println(cp.get(j).getDname());
    						System.out.println(cp.get(j).getDtype());
    						System.out.println(cp.get(j).getDprice());
    						System.out.println(cp.get(j).getDpicture());
    						System.out.println(cp.get(j).getQuantity());
    						System.out.println(cp.get(j).getDbitmap());
    					}
    					
    					Message msg=new Message();
            			msg.obj=cp;
            			fHandler.sendMessage(msg);
    					}
            				
    				 catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    					
    				}
    			}
    		}).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
        
    }
  
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			AlertDialog.Builder build = new AlertDialog.Builder(this);
			build.setTitle("系统提示").setMessage("确定要退出吗？");
			build.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							 moveTaskToBack(false);  
				        	 finish();
						}
					});
			build.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
    
    public class shopcartAdapter extends BaseAdapter
    {
    	private class ListItemView
        {                //自定义控件集合    
    		    public CheckBox  cb1; 
                public ImageView imageview1;    
                public TextView tv3;    
                public TextView tv4;
                public ImageView tv_reduce1;
                public TextView tv_num1;
                public ImageView tv_add1;    
        }   
    	private Context context;                        //运行上下文  
        private List<HashMap<String, Object>> listItems;    //商品信息集合  
        private LayoutInflater listContainer;           //视图容器                
        private ListItemView  listItemView ;
        private SparseArray<Boolean> selectstate = new SparseArray<Boolean>(); 
        public   int len=0;
        private boolean isBatchModel;// 是否可删除模式
        
        public shopcartAdapter(Context context, List<HashMap<String, Object>> listitems) {  
            this.context = context;           
            listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文  
            this.listItems = listitems;  
        }  

    	public int getCount() {  
            // TODO Auto-generated method stub  
            return listItems.size();  
        }  

        public Object getItem(int position) {  
            // TODO Auto-generated method stub  
            return listItems.get(position);  
        }  

        public long getItemId(int position) {  
            // TODO Auto-generated method stub  
            return position;  
        }  
        private void refreshListView()
    	{
    		
    			this.notifyDataSetChanged();
    	}
        
        
        private void doDelete(List<Integer> ids)
    	{
    		for (int i = 0; i < listItems.size(); i++)
    		{
    			Integer dataId = (Integer) listItems.get(i).get("cpid");
    			Integer cpprice=(Integer) listItems.get(i).get("cpprice");
    			for (int j = 0; j < ids.size(); j++)
    			{
    				int deleteId = ids.get(j);
    				if (dataId == deleteId)
    				{
    					for(int k=0;k< mainActivity.cporder.size();k++)
    		            {
    		            	if(mainActivity.cporder.get(k).getDid().equals(dataId))
    		            	{
    		            		mainActivity.cporder.get(k).setQuantity(0);
    		            	}
    		            }
    					listItems.remove(i);
    					i--;
    					ids.remove(j);
    					j--;
    					len--;
    					totalPrice -= cpprice;
                		tv_allprice.setText("￥" + totalPrice + "");
          		        tv_selectnum.setText("已选" + len + "类菜品");
    				}
    				
    			}
    		}
    		if(listItems.size()==0)
    		{
    			check_box.setChecked(false);
    		}
    		refreshListView();

    	}

    	private final List<Integer> getSelectedIds()
    	{
    		ArrayList<Integer> selectedIds = new ArrayList<Integer>();
    		for (int index = 0; index < selectstate.size(); index++)
    		{
    			if (selectstate.valueAt(index))
    			{
    				selectedIds.add(selectstate.keyAt(index));
    			}
    		}
    		return selectedIds;
    	}
    	
        public View getView( int position, View convertView, ViewGroup parent) {  
            // TODO Auto-generated method stub  
            //自定义视图  
            if (convertView == null) {  
                listItemView = new ListItemView();   
                //获取list_item布局文件的视图  
                convertView = listContainer.inflate(R.layout.cartlist_item, null);  
                //获取控件对象  
                listItemView.cb1 = (CheckBox)convertView.findViewById(R.id.cb1);  
                listItemView.imageview1 = (ImageView)convertView.findViewById(R.id.imageview1);  
                listItemView.tv3 = (TextView)convertView.findViewById(R.id.tv3);  
                listItemView.tv4 = (TextView)convertView.findViewById(R.id.tv4);
                listItemView.tv_reduce1 = (ImageView)convertView.findViewById(R.id.tv_reduce1);
                listItemView.tv_num1 = (TextView)convertView.findViewById(R.id.tv_num1);
                listItemView.tv_add1 = (ImageView)convertView.findViewById(R.id.tv_add1);
                //设置控件集到convertView  
                convertView.setTag(listItemView);  
            }else {  
                listItemView = (ListItemView)convertView.getTag();  
            }  

            listItemView.imageview1.setImageBitmap((Bitmap)listItems.get(  
                    position).get("cppicture"));  
            listItemView.tv3.setText((String) listItems.get(position)  
                    .get("cpname"));  
            listItemView.tv4.setText("￥"+listItems.get(position)  
                    .get("cpprice"));  
            String cpnum= String.valueOf(listItems.get(position)  
                    .get("cpnum"));
            int _id=(Integer) listItems.get(position).get("cpid");  
            boolean selected = selectstate.get(_id,false);
            listItemView.cb1.setChecked(selected);
            
            listItemView.tv_num1.setText(cpnum);
            listItemView.tv_add1.setTag(position);
            listItemView.tv_reduce1.setTag(position);
            listItemView.tv_add1.setOnClickListener(new numOnClickListener(position));
            listItemView.tv_reduce1.setOnClickListener(new numOnClickListener(position)); 
            listItemView.cb1.setOnClickListener(new numOnClickListener(position));
            check_box.setOnClickListener(new numOnClickListener(position));
            subtitle.setOnClickListener(new numOnClickListener(position));
            tv_submit.setOnClickListener(new numOnClickListener(position));
            
            return convertView;  
        }  
        
        public void addNum(int position) throws Exception{
        	int _id = (Integer) listItems.get(position).get("cpid");
        	boolean selected=selectstate.get(_id,false);
            String cpnumstring= String.valueOf(listItems.get(position).get("cpnum"));
            int num= Integer.parseInt(cpnumstring);
            num++;
            listItems.get(position).put("cpnum",num);
            String cpname=(String) listItems.get(position).get("cpname");
            for(int j=0;j< mainActivity.cporder.size();j++)
            {
            	if(mainActivity.cporder.get(j).getDname().equals(cpname))
            	{
            		Integer i=mainActivity.cporder.get(j).getQuantity()+1;
            		mainActivity.cporder.get(j).setQuantity(i);
            		Integer dprice=mainActivity.cporder.get(j).getDprice();
            		Integer cpprice=dprice*i;
            		listItems.get(position).put("cpprice",cpprice);
            		if(selected==true)
                	{
            		totalPrice += dprice;
            		tv_allprice.setText("￥" + totalPrice + "");
                	}
            	}
        		
            }
            
            this.notifyDataSetChanged();
            
          }
          //减少edittext中的数据
          public void cutNum(int position){
        	  int _id = (Integer) listItems.get(position).get("cpid");
        	  System.out.println(_id);
           	boolean selected=selectstate.get(_id,false);
           	System.out.println(selected);
        	  String cpnumstring= String.valueOf(listItems.get(position).get("cpnum"));
              int num= Integer.parseInt(cpnumstring);
            	  num--;
                //将数据源中改变后的数据重新放进数据源中，再加载到item中
                listItems.get(position).put("cpnum",num);
                String cpname=(String) listItems.get(position).get("cpname");
                for(int j=0;j< mainActivity.cporder.size();j++)
                {
                	if(mainActivity.cporder.get(j).getDname().equals(cpname))
                	{
                		Integer i=mainActivity.cporder.get(j).getQuantity()-1;
                		mainActivity.cporder.get(j).setQuantity(i);
                		Integer dprice=mainActivity.cporder.get(j).getDprice();
                		Integer cpprice=dprice*i;
                		listItems.get(position).put("cpprice",cpprice);
                		if(selected==true)
                    	{
                		totalPrice -= dprice;
                		tv_allprice.setText("￥" + totalPrice + "");
                    	}
                	}
                }
              if(num==0)
              {
            	 listItems.remove(position);
            	 if(selected==true)
              	{
          		len--;
          		tv_selectnum.setText("已选" + len + "类菜品");
              	}
                  
              }
            
            //重新刷新页面
            this.notifyDataSetChanged();
          }
        
    class numOnClickListener implements  View.OnClickListener{
        private int position;
        numOnClickListener(int pos){
          position = pos;
        }
        @Override
        //复写onClick方法用来监听按钮
        public void onClick(View v){
          int vid = v.getId();
    	if(vid ==listItemView.tv_add1.getId()){
            try {
    			addNum(position);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
          }
        if(vid == listItemView.tv_reduce1.getId()){
            cutNum(position);
          }
        if(vid==listItemView.cb1.getId()){
        	  int _id = (Integer) listItems.get(position).get("cpid");
        	  System.out.println(_id);
        	  boolean selected = !selectstate.get(_id, false);
        	  System.out.println(selected);

        	  if (selected)
    			{
        		    selectstate.put(_id,true);
    				Integer cpprice=(Integer) listItems.get(position).get("cpprice");
    				totalPrice += cpprice;
    				len++;
    			} else
    			{
    				selectstate.delete(_id);
    				Integer cpprice=(Integer) listItems.get(position).get("cpprice");
    				totalPrice -= cpprice;
    				len--;
    			}
        	  tv_selectnum.setText("已选" + len + "类菜品");
        	  tv_allprice.setText("￥" + totalPrice + "");
        	  
        	  if (len== listItems.size())
  			{
  				check_box.setChecked(true);
  			} else
  			{
  				check_box.setChecked(false);
  			}
        	  
          }
       if(vid==check_box.getId()){
			if (check_box.isChecked())
			{
				totalPrice=0;
				int size = listItems.size();
				for (int i = 0; i < size; i++)
				{
					int _id =(Integer) listItems.get(i).get("cpid");
					selectstate.put(_id, true);
					Integer cpprice=(Integer) listItems.get(i).get("cpprice");
    				totalPrice += cpprice;
    				len=listItems.size();
				}
				 tv_selectnum.setText("已选" + listItems.size() + "类菜品");
	        	  tv_allprice.setText("￥" + totalPrice + "");
			}
			else
			{
				int size = listItems.size();
				for (int i = 0; i < size; i++)
				{
					int _id =(Integer) listItems.get(i).get("cpid");
				    selectstate.put(_id, false);
				}
					totalPrice = 0;
					len=0;
					tv_selectnum.setText("已选" + len + "类菜品");
		        	 tv_allprice.setText("￥" + totalPrice + "");
			}
			refreshListView();
          }
       
       
       if(vid==subtitle.getId()){
    	   isBatchModel=!isBatchModel;
    	   if (isBatchModel)
			{
    		   tv_submit.setText("删除");
    		   subtitle.setText("完成");
			}
    	   else{
    		   tv_submit.setText("提交订单");
    		   subtitle.setText("编辑");
    	   }
       }
       
       if(vid==tv_submit.getId()){
    	   if (isBatchModel)
			{
				List<Integer> ids = getSelectedIds();
				doDelete(ids);
			} else
			{
				if((tv_desk.getText().toString()).equals("{text=请选择桌台号!}"))
				{
					Toast.makeText(mainActivity.this, "请先在首页选择桌台号！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					boolean f=false;
					for (int i = 0; i < listItems.size(); i++)
		    		{
						int _id = (Integer) listItems.get(i).get("cpid");
			        	boolean selected=selectstate.get(_id,false);
					    if(check_box.isChecked()==false&&selected==false)
					    {
					    	f=false;
					    }
					    else
					    {
					    	f=true;
					    	break;
					    }
		    		}
				if(f==false)
				{
					Toast.makeText(mainActivity.this, "请先选择所需菜品！", Toast.LENGTH_SHORT).show();
				}
				else
				{
				Date dt=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				final String cptime = sdf.format(dt);
				String cpdesk1=tv_desk.getText().toString().substring(6,9);
				final String cpdesk=cpdesk1.substring(0,1);
				String cpid1=cptime.substring(5,7).trim();
    			String cpid2=cptime.substring(8,10).trim();
    			String cpid3=cptime.substring(11,13).trim();
    			String cpid4=cptime.substring(14,16).trim();
    			String cpid5=cpdesk1.substring(0,1).trim();
    			final String cpid=cpid5+cpid1+cpid2+cpid3+cpid4;
				String cpamount1=tv_allprice.getText().toString();
				final String cpamount=cpamount1.substring(1);
				final String cpstate="未打印";
				final Handler orderHandler=new Handler(){
        			public void handleMessage(Message msg){
        				String response=(String)msg.obj;
        				System.out.println(response);
        			}
        		};
        		Thread thread1=new Thread(new Runnable(){
        			public void run(){
        				try {
							String result=allorderParse.doPost(cpid,cptime,cpamount,cpstate,cpdesk);
							Message msg=new Message();
	        				msg.obj=result;
	        				orderHandler.sendMessage(msg);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        		});
        		thread1.start();
        		try {
					thread1.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	
                List<HashMap<String, Object>> cplist=new ArrayList<HashMap<String,Object>>();
        		for (int i = 0; i < listItems.size(); i++)
	    		{
        			HashMap<String, Object> cpmap=new HashMap<String, Object>();
					int _id = (Integer) listItems.get(i).get("cpid");
		        	boolean selected=selectstate.get(_id,false);
		        	System.out.println(selected);
					for(int k=0;k< mainActivity.cporder.size();k++)
		            {
		            	if(mainActivity.cporder.get(k).getDid().equals(_id))
		            	{
		            		mainActivity.cporder.get(k).setQuantity(0);
		            	}
		            }
					if(selected==true)
		        	{
						final String cpname=(String)listItems.get(i).get("cpname");
		    			final Integer cpprice=(Integer) listItems.get(i).get("cpprice");
		    			final Integer cpnum=(Integer) listItems.get(i).get("cpnum");
						cpmap.put("cpname",cpname);
						cpmap.put("cpnum", cpnum+"份");
						cpmap.put("cpprice","￥"+cpprice);
						System.out.println(cpname);
						System.out.println(cpprice);
						
						
					final Handler lastHandler=new Handler(){
	    	        			public void handleMessage(Message msg){
	    	        				String response=(String)msg.obj;
	    	        			}
	    	        		};
	    	        Thread thread2=new Thread(new Runnable(){
	    	        			public void run(){
	    	        				String cpprice1=String.valueOf(cpprice);
	    	        				String cpnum1=String.valueOf(cpnum);
	    	        				try {
										String result=orderParse.doPost(cpid,cpname,cpnum1,cpprice1);
										Message msg=new Message();
				        				msg.obj=result;
				        				lastHandler.sendMessage(msg);
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
	    	        			}
	    	        		});
	    	            thread2.start();
	    		      }
					cplist.add(cpmap);
	    		}
                System.out.println(cplist);
        		Intent cpintent=new Intent(mainActivity.this,orderActivity.class);  
                cpintent.putExtra("cplist", (Serializable)cplist);  
                cpintent.putExtra("cpdesk", cpdesk);
                cpintent.putExtra("cpid", cpid);
                cpintent.putExtra("cptime", cptime);
                cpintent.putExtra("cpamount", cpamount);
                
                startActivity(cpintent);      
	    	    listItems.clear();
				totalPrice= 0;
				len=0;
				check_box.setChecked(false);
        		tv_allprice.setText("￥" + totalPrice + "");
  		        tv_selectnum.setText("已选" + len + "类菜品");
	    		refreshListView();
	    		finish();
			}
				}
			}
       }
        }
    }
    }
}










