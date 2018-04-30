package com.example.diancanclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class itemActivity extends Activity {
	private TextView txtname;
	private TextView txtprice;
	private TextView txtintro;
	private ImageView imgview;
	Bitmap bitmap;
	String durl;
	String dintro;
	String dname;
	String dprice;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);
		imgview=(ImageView)findViewById(R.id.imgview);
		txtname=(TextView)findViewById(R.id.txtname);
		txtprice=(TextView)findViewById(R.id.txtprice);
		txtintro=(TextView)findViewById(R.id.txtintro);
		Intent itemintent=getIntent(); 
		durl=itemintent.getExtras().getString("bitmap");
        dname=itemintent.getExtras().getString("dname");
        dprice=itemintent.getExtras().getString("dprice");
        dintro=itemintent.getExtras().getString("dintro");
        for(int j=0;j< mainActivity.cporder.size();j++)
        {
        	if(mainActivity.cporder.get(j).getDpicture().equals(durl))
        	{
        		bitmap=mainActivity.cporder.get(j).getDbitmap();
        	}
    		
        }
				imgview.setImageBitmap(bitmap); 
	            txtname.setText("Ãû³Æ£º"+dname);
	            txtprice.setText("¼Û¸ñ£º"+dprice);
				txtintro.setText("¼ò½é£º"+dintro);	
	}
}