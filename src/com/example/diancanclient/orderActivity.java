package com.example.diancanclient;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class orderActivity extends Activity {

	private ListView listorder;
	private TextView tvdesk;
	private TextView tvid;
	private TextView tvtime;
	private TextView tvamount;
	private ImageView returnimg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		listorder=(ListView)findViewById(R.id.listorder);
		tvdesk=(TextView)findViewById(R.id.tvdesk);
		tvid=(TextView)findViewById(R.id.tvid);
		tvtime=(TextView)findViewById(R.id.tvtime);
		tvamount=(TextView)findViewById(R.id.tvamount);
		returnimg=(ImageView)findViewById(R.id.returnimg);
		listorder.setDivider(null);
		Intent cpintent=getIntent();  
        List<HashMap<String,Object>> cplist = (List<HashMap<String, Object>>)cpintent.getSerializableExtra

("cplist");  
        SimpleAdapter mSchedule = new SimpleAdapter(this, cplist,R.layout.order_item,new String[] 

{"cpname","cpnum","cpprice"},new int[] {R.id.tvname,R.id.tvnum,R.id.tvprice});   
        listorder.setAdapter(mSchedule);   
        String cpdesk=cpintent.getExtras().getString("cpdesk");
        String cpid=cpintent.getExtras().getString("cpid");
        String cptime=cpintent.getExtras().getString("cptime");
        String cpamount=cpintent.getExtras().getString("cpamount");
        tvdesk.setText("桌台号"+cpdesk);
        tvid.setText("订单编号"+cpid);
        tvamount.setText("总价"+cpamount);
        tvtime.setText("下单时间"+cptime);
        
        returnimg.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        	 Intent intent=new Intent(orderActivity.this,mainActivity.class);
        	 startActivity(intent);
        	 finish();
	    }

        });
   }
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			AlertDialog.Builder build = new AlertDialog.Builder(this);
			build.setTitle("退出").setMessage("是否确定退出?");
			build.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
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
	}