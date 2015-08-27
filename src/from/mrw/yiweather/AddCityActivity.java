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
	
//	������ʱ���ʡ��
	public String now_province = null;
//	������ʱ�������
	public String now_cities = null;
	
//	λ�÷���
	private LocationManager locationManager;
	
	
//	���б���ֵ��List
	public ArrayList<String> cities_list = new ArrayList<String>();
//	���б�������ID��list
	public ArrayList<String> cities_id = new ArrayList<String>();
	
	
//	������
	public SimpleCursorAdapter myadapter = null;
	
	
//	��ó����б���б�ؼ�
	public ListView cities_listview ;
//	���ؿؼ�
	public ImageView backup_addcity_titlebar_bn;
//	gps��λ�ؼ�
	public ImageView gps_addcity_titlebar_bn;
//	�����ؼ�
	public ImageView search_addcity_titlebar_bn;
	
//	�������Ի���
	public ProgressDialog progressDialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcity_activity);
		
		init();
		
	}
	
	
	/*
	 * ��ʼ������
	 */
	public void init()
	{
//		 ��ʼ���ؼ�����
		findviewbyid();
		
//		���ؼ��󶨼���������
		setonclicklistener();
		
//		��listviewװ��������
    	cities_listview.setAdapter(myadapter);
    	
//    	�����б�
    	inflatelist(null);
		
//    	���б�����ӵ���¼�
    	cities_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				δ�����б�
				if(Params.LIST_NOW <2)
				{
//				�б�״̬��һ
				Params.LIST_NOW += 1 ;
//				��õ���б����Ӧ��ֵ
				String selection = cities_list.get(arg2);
//				����listview
				inflatelist(selection);
				}
				else
				{
//					�Ѿ����������б�
//					��ö�Ӧ������ID
					String id = cities_id.get(arg2);
					String name = cities_list.get(arg2);
//					��ѯ�Ƿ��Ѵ���
					Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from saved_cities where name=?",new String[]{name});
					int i = 0;
					while(cursor.moveToNext())
					{
						i++;
					}
					if(i==0)
					{
//					�����������ѱ�������ݿ�
					MainActivity.dataBaseHelper.getReadableDatabase().execSQL("insert into saved_cities values(null,?,?,?,?)", new String[]{name,id,now_cities,now_province});
					}
//					���url
					String url = new WeatherURL(id).getURL();
					
//					�������̷߳�������
					new GetDataFromNet(url, Params.JSON_DATA).getDataFromNet(new GetDataCallBack() {
						
						@Override
						public void CallBack(int arg0) {
							switch(arg0)
							{
//							�����У���ʾ�Ի���
							case Params.LOADING:
								progressDialog=ProgressDialog.show(AddCityActivity.this, null, "���ڼ�������",true,false);
								break;
//							������ɣ�ȡ���Ի���
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
	 * ��ʼ���ؼ�����
	 */
	public void findviewbyid()
	{
//		���ذ�ť
		backup_addcity_titlebar_bn = (ImageView) findViewById(R.id.backup_addcity_titlebar_bn);
//		gps��λ��ť
		gps_addcity_titlebar_bn = (ImageView) findViewById(R.id.gps_addcity_titlebar_bn);
//		������ť
		search_addcity_titlebar_bn = (ImageView) findViewById(R.id.search_addcity_titlebar_bn);
//		�����б�
		cities_listview = (ListView) findViewById(R.id.cities_listview);
		
	}
	
	/*
	 * ���ؼ��󶨼���������
	 */
	public void setonclicklistener()
	{
//		�����ذ�ť�󶨼�����
		backup_addcity_titlebar_bn.setOnClickListener(this);
//		��gps��λ��ť�󶨼�����
		gps_addcity_titlebar_bn.setOnClickListener(this);
//		��������ť�󶨼�����
		search_addcity_titlebar_bn.setOnClickListener(this);
	}
	

	/*
	 * ����¼���������
	 */
	@Override
	public void onClick(View arg0) {
//		��õ���ؼ���id
		int ViewId = arg0.getId();
		switch(ViewId)
		{
//		���صĿؼ�
		case R.id.backup_addcity_titlebar_bn:
//			�б�״̬��һ
			Params.LIST_NOW -= 1 ;
//			δ��ʡ�б�
			if(Params.LIST_NOW >= 0 )
			{
//				�жϵ�ǰ�б���״̬
				switch(Params.LIST_NOW)
				{
//				�����ʡ���б�ֱ��ˢ��listview
				case 0:
					inflatelist(null);
					break;
//					����������б�
				case 1:
//					����
					String cities = null;
//					ʡ��
					String province = null;
//					���ݵ�һ��������ѯ����������
					final Cursor backup_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","cities"}, "name = ?", new String[]{cities_list.get(0)}, "cities", null, null, null);
//					�������
					while(backup_cursor.moveToNext())
					{
//					�������
					cities = backup_cursor.getString(backup_cursor.getColumnIndex("cities"));
					}
//					����������ѯ������ʡ��
					final Cursor backup_cities_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","province"}, "cities =  ?", new String[]{cities}, "province", null, null, null);
//					���ʡ��
					while(backup_cities_cursor.moveToNext())
					{
//						���ʡ��
					province = backup_cities_cursor.getString(backup_cities_cursor.getColumnIndex("province"));
					}
//					ˢ��listview
					inflatelist(province);
					break;
				}
				
			}
//			����ʡ���б������ذ�ť��ֱ�ӽ���activity
			else
			{
				Params.LIST_NOW = 0;
				this.finish();
			}
			break;
//			��λ�Ŀؼ�
		case R.id.gps_addcity_titlebar_bn:
//			��������
			locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//			���location����
			Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//			���ά��
			String latitude =Double.toString( location.getLatitude());
//			��þ���
			String longrirude = Double.toString(location.getLongitude());
//			���url
			String url = new WeatherURL(latitude + ":" + longrirude).getURL();
			
//			�������̷߳�������
			new GetDataFromNet(url, Params.JSON_DATA).getDataFromNet(new GetDataCallBack() {
				
				@Override
				public void CallBack(int arg0) {
					switch(arg0)
					{
//					�����У���ʾ�Ի���
					case Params.LOADING:
						progressDialog=ProgressDialog.show(AddCityActivity.this, null, "���ڼ�������",true,false);
						break;
//					������ɣ�ȡ���Ի���
					case Params.DONE_LOADING:
						progressDialog.dismiss();
						AddCityActivity.this.finish();
						break;
					}
				}
			});
			break;
//			�����Ŀؼ�
		case R.id.search_addcity_titlebar_bn:
			Intent search_intent = new Intent(AddCityActivity.this, SearchActivity.class);
			startActivity(search_intent);
			AddCityActivity.this.finish();
			break;
		}
		
	}
	
	
	/*
	 * �����б�ؼ�����
	 * @param selection ��ѯ����
	 */
	public void inflatelist(String selection)
	{
//		�жϵ�ǰ�б�ؼ������ĸ�״̬
		switch(Params.LIST_NOW)
		{
		case 0:
//			ʡ�ݲ�ѯ
			final Cursor province_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","id","province"}, null, null, "province", null, "id", null);
//			����ѯװ������������
			myadapter = new SimpleCursorAdapter(AddCityActivity.this, R.layout.cities_list_item, province_cursor, new String[]{"province"}, new int[]{R.id.cities_tv});
//			���list
			cities_list.clear();
//			����ѯ�����ӵ�list��
			while(province_cursor.moveToNext())
			{
//				���ʡ��
				String province = province_cursor.getString(province_cursor.getColumnIndex("province"));
//				�����list��
				cities_list.add(province);
			}
//			֪ͨ������ˢ������
			myadapter.notifyDataSetChanged();
//			����������
			cities_listview.setAdapter(myadapter);
			break;
		case 1:
//			�����ʱʡ��
			now_province = selection;
//			��ѯ��Ӧʡ�ݵ�����
			final Cursor cities_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","id","cities"}, "province=?", new String[]{selection}, "cities", null, "id", null);
//			����ѯװ������������
			myadapter = new SimpleCursorAdapter(AddCityActivity.this, R.layout.cities_list_item, cities_cursor, new String[]{"cities"}, new int[]{R.id.cities_tv});
//			���list
			cities_list.clear();
//			����ѯ�����ӵ�list��
			while(cities_cursor.moveToNext())
			{
//				�������
				String cities = cities_cursor.getString(cities_cursor.getColumnIndex("cities"));
//				�����list��
				cities_list.add(cities);
			}
//			֪ͨ������ˢ������
			myadapter.notifyDataSetChanged();
//			����������
			cities_listview.setAdapter(myadapter);
			break;
		case 2:
//			�������
			now_cities = selection;
//			��ѯ��Ӧ�е�����
			final Cursor name_cursor = MainActivity.dataBaseHelper.getReadableDatabase().query(true, "ch_cities", new String[]{"_id","id","name"}, "cities = ?", new String[]{selection}, "name", null, "id", null);
//			����ѯװ������������
			myadapter = new SimpleCursorAdapter(AddCityActivity.this,R.layout.cities_list_item,name_cursor,new String[]{"name"},new int[]{R.id.cities_tv});
//			���list
			cities_list.clear();
//			��ձ���id��list
			cities_id.clear();
//			����ѯ�����ӵ�list��
			while(name_cursor.moveToNext())
			{
//				�������
				String name = name_cursor.getString(name_cursor.getColumnIndex("name"));
//				�����list��
				cities_list.add(name);
//				���id
				String id = name_cursor.getString(name_cursor.getColumnIndex("id"));
//				�����list��
				cities_id.add(id);
			}
//			֪ͨ������ˢ������
			myadapter.notifyDataSetChanged();
//			����������
			cities_listview.setAdapter(myadapter);
			break;
		}
	}


	@Override
	protected void onPause() {
//		activity��ͣ�����б���״̬��Ϊ0
		Params.LIST_NOW = 0;
		super.onPause();
	}


	@Override
	protected void onResume() {
//		activity�ָ�ʱ����ʡ���б�
		inflatelist(null);
		super.onResume();
	}
	
	
	
	
	
	
	
	
}
