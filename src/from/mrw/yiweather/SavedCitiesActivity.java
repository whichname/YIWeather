package from.mrw.yiweather;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import from.mrw.yiweather.GetDataFromNet.GetDataCallBack;

public class SavedCitiesActivity extends Activity implements OnClickListener{
	
//	返回按钮
	public ImageView backup_savedcities_titlebar_bn;
//	清空记录
	public ImageView delete_savedcities_titlebar_bn;
//	列表
	public ListView saved_cities_listview;
//	存放id的arraylist
	private ArrayList<String> cities_id = new ArrayList<String>();
//	进度条对话框
	private ProgressDialog progressDialog;
	
//	适配器
	public SimpleCursorAdapter myadapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_cities);
		
		findviewbyid();
		
		listviewinit();
		
	}

	public void findviewbyid()
	{
		backup_savedcities_titlebar_bn=(ImageView)findViewById(R.id.backup_savedcities_titlebar_bn);
		delete_savedcities_titlebar_bn=(ImageView)findViewById(R.id.delete_savedcities_titlebar_bn);
		saved_cities_listview=(ListView)findViewById(R.id.saved_cities_listview);
		
		backup_savedcities_titlebar_bn.setOnClickListener(this);
		delete_savedcities_titlebar_bn.setOnClickListener(this);
		
		
	}
	
//初始化listview
	public void listviewinit()
	{
//		查询数据库
		Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from saved_cities order by _id desc", null);
//		将数据存入arraylist中
		while(cursor.moveToNext())
		{
				cities_id.add(cursor.getString(cursor.getColumnIndex("id")));
		}
		
		
//		适配器
		myadapter=new SimpleCursorAdapter(SavedCitiesActivity.this, R.layout.cities_item, cursor, new String[]{"name","cities","province"}, new int[]{R.id.item_name,R.id.item_cities,R.id.item_province});
//		加载设配器
		saved_cities_listview.setAdapter(myadapter);
		
//		点击监听
		saved_cities_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				获得id
				String id = cities_id.get(arg2);
				
//				获得url
				String url = new WeatherURL(id).getURL();
				
//				启动多线程访问网络
				new GetDataFromNet(url, Params.JSON_DATA).getDataFromNet(new GetDataCallBack() {
					
					@Override
					public void CallBack(int arg0) {
						switch(arg0)
						{
//						加载中，显示对话框
						case Params.LOADING:
							progressDialog=ProgressDialog.show(SavedCitiesActivity.this, null, "正在加载数据",true,false);
							break;
//						加载完成，取消对话框
						case Params.DONE_LOADING:
							progressDialog.dismiss();
							SavedCitiesActivity.this.finish();
							break;
						}
					}
				});
				
			}
		});
		
		
	}



	@Override
	public void onClick(View arg0) {
	switch (arg0.getId()) {
//	返回
	case R.id.backup_savedcities_titlebar_bn:
		SavedCitiesActivity.this.finish();
		break;
//清空
	case R.id.delete_savedcities_titlebar_bn:
		new AlertDialog.Builder(SavedCitiesActivity.this).setIcon(null).setTitle(null).setMessage("确定清空历史数据？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				MainActivity.dataBaseHelper.getReadableDatabase().execSQL("delete from saved_cities");
//				查询数据库
				Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from saved_cities", null);
//				适配器
				myadapter=new SimpleCursorAdapter(SavedCitiesActivity.this, R.layout.cities_item, cursor, new String[]{"name","cities","province"}, new int[]{R.id.item_name,R.id.item_cities,R.id.item_province});
//				加载设配器
				saved_cities_listview.setAdapter(myadapter);
			}
		}
		).setNegativeButton("取消", null).show();
		break;
	}
		
	}
	
	
	

}
