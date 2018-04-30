
package com.example.diancanclient;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class BegActivity extends Activity {
	private Button mButton;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beg);
		mButton = (Button) findViewById(R.id.begbutton);  
		mButton.setTextColor(Color.WHITE);
        mButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        	 Intent intent=new Intent(BegActivity.this,mainActivity.class);
        	 startActivity(intent);
        	 finish();
	    }

        });
        
	}
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
				finish();
				System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}
}