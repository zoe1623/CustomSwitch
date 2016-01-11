package com.zhe.myswitch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MySwitch extends View {

	private Bitmap mBitmapBg;
	private Bitmap mSlideButton;
	private int MAX_VALUES;
	private int mSlideX;
	private boolean isOpen;
	private OnCheckChangeListener mListener;
	private String mNamespace="http://schemas.android.com/apk/res-auto";

	public MySwitch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MySwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		isOpen=attrs.getAttributeBooleanValue(mNamespace, "isOpen", false);
		if(isOpen){
			mSlideX=MAX_VALUES;
		}else{
			mSlideX=0;
		}
		//getAttributeResourceValue
//		int value = attrs.getAttributeResourceValue(mNamespace, "slideDrawable", -1);
//		if(value > 0){
//			
//			mSlideButton=BitmapFactory.decodeResource(getResources(), value);
//		}
		invalidate();
	}

	public MySwitch(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		mSlideButton = BitmapFactory.decodeResource(getResources(),
				R.drawable.slide_button);
		mBitmapBg = BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_background);
		MAX_VALUES = mBitmapBg.getWidth() - mSlideButton.getWidth();
		if (isOpen) {
			mSlideX = MAX_VALUES;
		} else {
			mSlideX = 0;
		}
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("=====onClick======");
				click();
			}

		});
	}
	private void click() {
		if (isClick) {
			if (isOpen) {
				isOpen = false;
				mSlideX = 0;
				
			} else {
				isOpen = true;
				mSlideX = MAX_VALUES;
			}
			invalidate();
			if (mListener != null) {
				mListener.onCheckChanged(MySwitch.this, isOpen);
			}
		}
	}
	
	private int startX;
	private int moveX;
	private boolean isClick;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) event.getX();
			break;

		case MotionEvent.ACTION_MOVE:
			int newX = (int) event.getX();
			int dx = newX - startX;
			moveX = Math.abs(dx);
			mSlideX += dx;
			//避免滑出边界
			if (mSlideX < 0) {
				mSlideX = 0;
			}
			if (mSlideX > MAX_VALUES) {
				mSlideX = MAX_VALUES;
			}
			invalidate();
//			startX = (int) event.getX();
			break;

		case MotionEvent.ACTION_UP:
			if (moveX > 5) {//防止手抖
				isClick = false;
				if (mSlideX < MAX_VALUES / 2) {
					mSlideX = 0;
					isOpen = false;
				} else {
					isOpen = true;
					mSlideX = MAX_VALUES;
				}
				invalidate();
				if (mListener != null) {
					mListener.onCheckChanged(MySwitch.this, isOpen);
				}
			} else {
				isClick = true;
			}
			System.out.println("isClick:"+isClick);
			moveX = 0;
			
			break;
		}

		return super.onTouchEvent(event);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(mBitmapBg.getWidth(),mBitmapBg.getHeight());
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mBitmapBg, 0, 0, null);
		canvas.drawBitmap(mSlideButton, mSlideX, 0, null);
	}
	
	public void setOnCheckChangeListener(OnCheckChangeListener listener){
		mListener=listener;
	}
	
	public interface OnCheckChangeListener{
		void onCheckChanged(View view, boolean isOpen);
	}
}
