package from.mrw.yiweather;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import from.mrw.yiweather.GetDataFromNet.GetDataCallBack;

public class AddCityActivity extends Activity implements OnClickListener {
	
//	用于临时存放省份
	public String now_province = null;
//	用于临时存放市名
	public String now_cities = null;
	
//	位置服务
	private LocationManager locationManager;
	
	
//	存列表项值的List
	public ArrayList<String> cities_list = new ArrayList<String>();
//	存列表中县名ID的list
	public ArrayList<String> cities_id = new ArrayList<String>();
	
	
//	适配器
	public SimpleCursorAdapter myadapter = null;
	
	
//	获得城市列表的列表控件
	public ListView cities_listview ;
//	返回控件
	public ImageView backup_addcity_titlebar_bn;
//	gps定位控件
	public ImageView gps_addcity_titlebar_bn;
//	搜索控件
	public ImageView search_addcity_titlebar_bn;
	
//	进度条对话框
	public ProgressDialog progressDialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcity_activity);
		
		init();
		
	}
	
	
	/*
	 * 初始化函数
	 */
	public void init()
	{
//		 初始化控件函数
		findviewbyid();
		
//		给控件绑定监听器函数
		setonclicklistener();
		
//		给listview装载适配器
    	cities_listview.setAdapter(myadapter);
    	
//    	加载列表
    	inflatelist(null);
		
//    	给列表项添加点击事件
    	cities_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				未到县列表
				if(Params.LIST_NOW <2)
				{
//				列表状态加一
				Params.LIST_NOW += 1 ;
//				获得点击列表项对应的值
				String selection = cities_list.get(arg2);
//				加载listview
				inflatelist(selection);
				}
				else
				{
//					已经到达县区列表
//					获得对应的县名ID
					String id = cities_id.get(arg2);
					String name = cities_list.get(arg2);
//					查询是否已存在
					Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from saved_cities where name=?",new String[]{name});
					int i = 0;
					while(cursor.moveToNext())
					{
						i++;
					}
					if(i==0)
					{
//					将县名存入已保存的数据库
					MainActivity.dataBaseHelper.getReadableDatabase().execSQL("insert into saved_cities values(null,?,?,?,?)", new String[]{name,id,now_cities,now_province});
					}
//					获得url
					String url = new WeatherURL(id).getURL();
					
//					启动多线程访问网络
					new GetDataFromNet(url, Params.JSON_DATA).getDataFromNet(new GetDataCallBack() {
						
						@Override
						public void CallBack(int arg0) {
							switch(arg0)
							{
//							加载中，显示对话框
							case Params.LOADING:
								progressDialog=ProgressDialog.show(AddCityActivity.this, null, "正在加载数据",true,false);
								break;
//							加载完成，取消对话框
							case Params.DONE_LOADING:
								progressDialog.dismiss();
								AddCityActivity.this.finish();
								break;
							}
						}
					});
					
				}

			}
		});
    	
	}
	
	/*
	 * 初始化控件函数
	 */
	public void findviewbyid()
	{
//		返回按钮
		backup_addcity_titlebar_bn = (ImageView) findViewById(R.id.backup_addcity_titlebar_bn);
//		gps定位按钮
		gps_addcity_titlebar_bn = (ImageView) findViewById(R.id.gps_addcity_titlebar_bn);
//		搜索按钮
		search_addcity_titlebar_bn = (ImageView) findViewById(R.id.search_addcity_titlebar_bn);
//		城市列表
		cities_listview = (ListView) findViewById(R.id.cities_listview);
		
	}
	
	/*
	 * 给控件绑定监听器函数
	 */
	public void setonclicklistener()
	{
//		给返回按钮绑定监听器
		backup_addcity_titlebar_bn.setOnClickListener(this);
//		给gps定位按钮绑定监听器
		gps_addcity_titlebar_bn.setOnClickListener(this);
//		给搜索按钮绑定监听器
		search_addcity_titlebar_bn.setOnClickListener(this);
	}
	

	/*
	 * 点击事件监听函数
	 */
	@Override
	public void onClick(View arg0) {
//		获得点击控件的id
		int ViewId = arg0.getId();
		switch(ViewId)
		{
//		返回的控件
		case R.id.backup_addcity_titlebar_bn:
//			列表状态减一
			Params.LIST_NOW -= 1 ;
//			未到省列表
			if(Params.LIST_NOW >= 0 )
			{
//				判断当前列表项状态
				switch(Params.LIST_NOW)
				{
//				如果是省份列表，直接刷新listview
				case 0:
					inflatelist(null);
					break;
//					如果是市名列表
				case 1:
//					市名
					String cities = null;
//					省名
					String province = null;
//					根据第一个县名查询其所在市名
					final Cursor backup_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","cities"}, "name = ?", new String[]{cities_list.get(0)}, "cities", null, null, null);
//					获得市名
					while(backup_cursor.moveToNext())
					{
//					获得市名
					cities = backup_cursor.getString(backup_cursor.getColumnIndex("cities"));
					}
//					根据市名查询其所在省名
					final Cursor backup_cities_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","province"}, "cities =  ?", new String[]{cities}, "province", null, null, null);
//					获得省名
					while(backup_cities_cursor.moveToNext())
					{
//						获得省名
					province = backup_cities_cursor.getString(backup_cities_cursor.getColumnIndex("province"));
					}
//					刷新listview
					inflatelist(province);
					break;
				}
				
			}
//			若在省份列表点击返回按钮，直接结束activity
			else
			{
				Params.LIST_NOW = 0;
				this.finish();
			}
			break;
//			定位的控件
		case R.id.gps_addcity_titlebar_bn:
//			创建对象
			locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//			获得location对象
			Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//			获得维度
			String latitude =Double.toString( location.getLatitude());
//			获得经度
			String longrirude = Double.toString(location.getLongitude());
//			获得url
			String url = new WeatherURL(latitude + ":" + longrirude).getURL();
			
//			启动多线程访问网络
			new GetDataFromNet(url, Params.JSON_DATA).getDataFromNet(new GetDataCallBack() {
				
				@Override
				public void CallBack(int arg0) {
					switch(arg0)
					{
//					加载中，显示对话框
					case Params.LOADING:
						progressDialog=ProgressDialog.show(AddCityActivity.this, null, "正在加载数据",true,false);
						break;
//					加载完成，取消对话框
					case Params.DONE_LOADING:
						progressDialog.dismiss();
						AddCityActivity.this.finish();
						break;
					}
				}
			});
			break;
//			搜索的控件
		case R.id.search_addcity_titlebar_bn:
			Intent search_intent = new Intent(AddCityActivity.this, SearchActivity.class);
			startActivity(search_intent);
			AddCityActivity.this.finish();
			break;
		}
		
	}
	
	
	/*
	 * 更新列表控件函数
	 * @param selection 查询条件
	 */
	public void inflatelist(String selection)
	{
//		判断当前列表控件处于哪个状态
		switch(Params.LIST_NOW)
		{
		case 0:
//			省份查询
			final Cursor province_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","id","province"}, null, null, "province", null, "id", null);
//			将查询装载入适配器中
			myadapter = new SimpleCursorAdapter(AddCityActivity.this, R.layout.cities_list_item, province_cursor, new String[]{"province"}, new int[]{R.id.cities_tv});
//			清空list
			cities_list.clear();
//			将查询结果添加到list中
			while(province_cursor.moveToNext())
			{
//				获得省份
				String province = province_cursor.getString(province_cursor.getColumnIndex("province"));
//				添加入list中
				cities_list.add(province);
			}
//			通知适配器刷新数据
			myadapter.notifyDataSetChanged();
//			设置适配器
			cities_listview.setAdapter(myadapter);
			break;
		case 1:
//			存放临时省份
			now_province = selection;
//			查询对应省份的市名
			final Cursor cities_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","id","cities"}, "province=?", new String[]{selection}, "cities", null, "id", null);
//			将查询装载入适配器中
			myadapter = new SimpleCursorAdapter(AddCityActivity.this, R.layout.cities_list_item, cities_cursor, new String[]{"cities"}, new int[]{R.id.cities_tv});
//			清空list
			cities_list.clear();
//			将查询结果添加到list中
			while(cities_cursor.moveToNext())
			{
//				获得市名
				String cities = cities_cursor.getString(cities_cursor.getColumnIndex("cities"));
//				添加入list中
				cities_list.add(cities);
			}
//			通知适配器刷新数据
			myadapter.notifyDataSetChanged();
//			设置适配器
			cities_listview.setAdapter(myadapter);
			break;
		case 2:
//			存放市名
			now_cities = selection;
//			查询对应市的县名
			final Cursor name_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","id","name"}, "cities = ?", new String[]{selection}, "name", null, "id", null);
//			将查询装载入适配器中
			myadapter = new SimpleCursorAdapter(AddCityActivity.this,R.layout.cities_list_item,name_cursor,new String[]{"name"},new int[]{R.id.cities_tv});
//			清空list
			cities_list.clear();
//			清空保存id的list
			cities_id.clear();
//			将查询结果添加到list中
			while(name_cursor.moveToNext())
			{
//				获得县名
				String name = name_cursor.getString(name_cursor.getColumnIndex("name"));
//				添加入list中
				cities_list.add(name);
//				获得id
				String id = name_cursor.getString(name_cursor.getColumnIndex("id"));
//				添加入list中
				cities_id.add(id);
			}
//			通知适配器刷新数据
			myadapter.notifyDataSetChanged();
//			设置适配器
			cities_listview.setAdapter(myadapter);
			break;
		}
	}


	@Override
	protected void onPause() {
//		activity暂停即将列表项状态置为0
		Params.LIST_NOW = 0;
		super.onPause();
	}


	@Override
	protected void onResume() {
//		activity恢复时加载省份列表
		inflatelist(null);
		super.onResume();
	}
	
	
	
	
	
	
	
	
}
