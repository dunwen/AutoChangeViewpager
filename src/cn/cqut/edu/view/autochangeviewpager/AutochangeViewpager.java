package cn.cqut.edu.view.autochangeviewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class AutochangeViewpager extends ViewPager{

	private AutochangeVierpagerAdapter avadapter = null;
	
	/**
	 * 切换时间间隔，默认3s
	 * */
	private int changeTime = 3000;
	
	/**
	 * 是否启用循环
	 * */
	private boolean isSetCycle = false;
	
	/**
	 * 是否启用自动切换，仅当循环启用后才有效
	 * */
	private boolean isSetAutoChange =false;
	
	private final ViewPager vp= this;
	private final int MESSAGE_CHANGE = 0x1111;
	private static AutoChangeThread at;
	
	
	
	
	public AutochangeViewpager(Context context) {
		super(context);
		init();
	}

	public AutochangeViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public void setChangeTime(int time){
		this.changeTime = time;
	}
	
	public void setAutochangeViewpager(AutochangeVierpagerAdapter av){
		this.avadapter  =  av;
		init();
	}
	
	public void setIsSetAutoChang(boolean is){
		this.isSetAutoChange = is;
		init();
	}
	
	public void setIsCycle(boolean is){
		if(isSetCycle != is){
			this.isSetCycle = is;
			init();
		}
	}
	
	
	

	private void init() {
//		this.setPageTransformer(true, new ZoomOutPageTransformer());
		
		
		if(avadapter!=null){
			avadapter.setIsCycleable(isSetCycle);
			this.setAdapter(this.avadapter);
			if(isSetCycle){
				this.setCurrentItem(100 * ((AutochangeVierpagerAdapter) getAdapter()).getDrawableCount());
			}
		}
		
		if(isSetCycle){
			
			if(isSetAutoChange){
				if(at==null){
					
					at = new AutoChangeThread();
					at.start();
				}
			}
		}else{
			Log.e("TAG","please set isSetcycle true");
		}
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case MESSAGE_CHANGE :
				System.out.println(vp.getCurrentItem());
				vp.setCurrentItem(vp.getCurrentItem()+1);
				break;
			}
		}
	};
	
	
	class AutoChangeThread extends Thread{
		
		@Override
		public void run() {
			while(true){
				handler.sendEmptyMessage(MESSAGE_CHANGE);
				try {
					Thread.sleep(changeTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
//	class ZoomOutPageTransformer implements ViewPager.PageTransformer  
//	{  
//	    private static final float MIN_SCALE = 0.85f;  
//	    private static final float MIN_ALPHA = 0.5f;  
//	   
//		@Override	  
//	    public void transformPage(View view, float position)  
//	    {  
//	        int pageWidth = view.getWidth();  
//	        int pageHeight = view.getHeight();  
//	  
//	        Log.e("TAG", view + " , " + position + "");  
//	  
//	        if (position < -1)  
//	        { // [-Infinity,-1)  
//	            // This page is way off-screen to the left.  
//	            view.setAlpha(0);  
//	  
//	        } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0  
//	        { // [-1,1]  
//	            // Modify the default slide transition to shrink the page as well  
//	            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));  
//	            float vertMargin = pageHeight * (1 - scaleFactor) / 2;  
//	            float horzMargin = pageWidth * (1 - scaleFactor) / 2;  
//	            if (position < 0)  
//	            {  
//	                view.setTranslationX(horzMargin - vertMargin / 2);  
//	            } else  
//	            {  
//	                view.setTranslationX(-horzMargin + vertMargin / 2);  
//	            }  
//	  
//	            // Scale the page down (between MIN_SCALE and 1)  
//	            view.setScaleX(scaleFactor);  
//	            view.setScaleY(scaleFactor);  
//	  
//	            // Fade the page relative to its size.  
//	            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)  
//	                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));  
//	  
//	        } else  
//	        { // (1,+Infinity]  
//	            // This page is way off-screen to the right.  
//	            view.setAlpha(0);  
//	        }  
//	    }
//	}
		
}
