package cn.cqut.edu.view.autochangeviewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AutochangeVierpagerAdapter extends PagerAdapter {

	Drawable[] drawables;
	ImageView[] imageViews;
	Context context;
	private boolean isSetCycle = false;

	// ViewPager mViewPager;

	public void setIsCycleable(boolean is) {
		if (this.isSetCycle != is) {
			this.isSetCycle = is;
			initview();
		}
	}
	
	public int getDrawableCount(){
		return drawables.length;
	}
	
	public AutochangeVierpagerAdapter(Drawable[] drawables, Context context) {
		super();
		this.drawables = drawables;
		this.context = context;
		// this.mViewPager = mViewPager;
		initview();
	}


	private void initview() {
		imageViews = new ImageView[drawables.length];
		for(int i = 0 ;i < drawables.length ;i++){
			ImageView temp = new ImageView(context);
			temp.setImageDrawable(drawables[i]);
			imageViews[i] = temp;
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		container.addView(imageViews[position%imageViews.length],0);

		return imageViews[position%imageViews.length];
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(imageViews[position%imageViews.length]);
	}

	@Override
	public int getCount() {
		return isSetCycle?Integer.MAX_VALUE:drawables.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
