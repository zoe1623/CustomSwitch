package com.zhe.myswitch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zhe.myswitch.MySwitch.OnCheckChangeListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MySwitch mMs = (MySwitch) findViewById(R.id.ms);
		mMs.setOnCheckChangeListener(new OnCheckChangeListener() {
			
			@Override
			public void onCheckChanged(View view, boolean isOpen) {
				Toast.makeText(getApplicationContext(), "当前状态:"+isOpen, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
