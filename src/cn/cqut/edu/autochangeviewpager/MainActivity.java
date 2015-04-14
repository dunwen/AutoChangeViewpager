package cn.cqut.edu.autochangeviewpager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.cqut.edu.view.autochangeviewpager.AutochangeVierpagerAdapter;
import cn.cqut.edu.view.autochangeviewpager.AutochangeViewpager;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;

public class MainActivity extends Activity {
	
	Drawable[] drawables = new Drawable[4];
	int[] drawablesID = new int[]{R.drawable.a,R.drawable.d,R.drawable.n,R.drawable.v};
	
	AutochangeViewpager av ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initdata();
		
	}

	private void initdata() {
		for(int i = 0 ; i<4 ; i++){
			drawables[i] = getResources().getDrawable(drawablesID[i]);
		}
		
		av = (AutochangeViewpager) findViewById(R.id.autochangeViewpager1);
		av.setIsCycle(true);//设置循环
		av.setIsSetAutoChang(true);//设置自动切换
		av.setAutochangeViewpager(new AutochangeVierpagerAdapter(drawables, this));
		//设置adapter
	}


}
