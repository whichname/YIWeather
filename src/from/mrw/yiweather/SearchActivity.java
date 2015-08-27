package from.mrw.yiweather;

import java.util.ArrayList;

import from.mrw.yiweather.GetDataFromNet.GetDataCallBack;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity implements OnClickListener{

//	输入框
	private EditText search_input;
//	列表框
	private ListView cities_search_listview;
//	返回按钮
	private ImageView backup_search_titlebar_bn;
//	适配器
	private SimpleCursorAdapter adapter = null;
//	存放id的arraylist
	private ArrayList<String> cities_id = new ArrayList<String>();
//	存放name的arraylist
	private ArrayList<String> cities_name = new ArrayList<String>();
//	存放province的arraylist
	private ArrayList<String> province_list = new ArrayList<String>();
//	存放cities的arraylist
	private ArrayList<String> cities_list = new ArrayList<String>();
//	进度条对话框
	private ProgressDialog progressDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchactivity_layout);
		
		findviewbyid();
	}
	
	private void findviewbyid()
	{
		search_input = (EditText)findViewById(R.id.search_input);
		
		search_input.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//				获得输入的城市名
				String name = search_input.getText().toString();
//				查询数据库
				Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from ch_cities where name like ? or province like ? or cities like ?", new String[]{"%"+name+"%","%"+name+"%","%"+name+"%"});
//				清空数组
				cities_id.clear();
				cities_name.clear();
				province_list.clear();
				cities_list.clear();
//				将查询结果放入数组中
				while(cursor.moveToNext())
				{
					cities_id.add(cursor.getString(cursor.getColumnIndex("id")));
					cities_name.add(cursor.getString(cursor.getColumnIndex("name")));
					province_list.add(cursor.getString(cursor.getColumnIndex("province")));
					cities_list.add(cursor.getString(cursor.getColumnIndex("cities")));
				}
//				装载入适配器
				adapter = new SimpleCursorAdapter(SearchActivity.this, R.layout.cities_item, cursor, new String[]{"name","cities","province"}, new int[]{R.id.item_name,R.id.item_cities,R.id.item_province});
//				设置适配器
				cities_search_listview.setAdapter(adapter);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
			}
		});
		
		
		cities_search_listview=(ListView)findViewById(R.id.cities_search_listview);
		backup_search_titlebar_bn=(ImageView)findViewById(R.id.backup_search_titlebar_bn);
		
		backup_search_titlebar_bn.setOnClickListener(this);
		
		cities_search_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				获得name
				String name = cities_name.get(arg2);
//				获得province
				String province = province_list.get(arg2);
//				获得cities
				String cities = cities_list.get(arg2);
				
//				获得id
				String id = cities_id.get(arg2);
				
//				查询是否已存在
				Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from saved_cities where name=?",new String[]{name});
				int i = 0;
				while(cursor.moveToNext())
				{
					i++;
				}
				if(i==0)
				{
//				将县名存入已保存的数据库
				MainActivity.dataBaseHelper.getReadableDatabase().execSQL("insert into saved_cities values(null,?,?,?,?)", new String[]{name,id,cities,province});
				}
				
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
							progressDialog=ProgressDialog.show(SearchActivity.this, null, "正在加载数据",true,false);
							break;
//						加载完成，取消对话框
						case Params.DONE_LOADING:
							progressDialog.dismiss();
							SearchActivity.this.finish();
							break;
						}
			}
				});
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId())
		{
		case R.id.backup_search_titlebar_bn:
			SearchActivity.this.finish();
			break;
		}
		
	}

	
	
	
	
	
}
