package cn.cqut.edu.view.autochangeviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

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
			}else{
				if(at!= null){
					at.stop();
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
	
}
