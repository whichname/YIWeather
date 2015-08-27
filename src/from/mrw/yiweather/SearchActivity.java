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

//	�����
	private EditText search_input;
//	�б��
	private ListView cities_search_listview;
//	���ذ�ť
	private ImageView backup_search_titlebar_bn;
//	������
	private SimpleCursorAdapter adapter = null;
//	���id��arraylist
	private ArrayList<String> cities_id = new ArrayList<String>();
//	���name��arraylist
	private ArrayList<String> cities_name = new ArrayList<String>();
//	���province��arraylist
	private ArrayList<String> province_list = new ArrayList<String>();
//	���cities��arraylist
	private ArrayList<String> cities_list = new ArrayList<String>();
//	�������Ի���
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
//				�������ĳ�����
				String name = search_input.getText().toString();
//				��ѯ���ݿ�
				Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from ch_cities where name like ? or province like ? or cities like ?", new String[]{"%"+name+"%","%"+name+"%","%"+name+"%"});
//				�������
				cities_id.clear();
				cities_name.clear();
				province_list.clear();
				cities_list.clear();
//				����ѯ�������������
				while(cursor.moveToNext())
				{
					cities_id.add(cursor.getString(cursor.getColumnIndex("id")));
					cities_name.add(cursor.getString(cursor.getColumnIndex("name")));
					province_list.add(cursor.getString(cursor.getColumnIndex("province")));
					cities_list.add(cursor.getString(cursor.getColumnIndex("cities")));
				}
//				װ����������
				adapter = new SimpleCursorAdapter(SearchActivity.this, R.layout.cities_item, cursor, new String[]{"name","cities","province"}, new int[]{R.id.item_name,R.id.item_cities,R.id.item_province});
//				����������
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
//				���name
				String name = cities_name.get(arg2);
//				���province
				String province = province_list.get(arg2);
//				���cities
				String cities = cities_list.get(arg2);
				
//				���id
				String id = cities_id.get(arg2);
				
//				��ѯ�Ƿ��Ѵ���
				Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from saved_cities where name=?",new String[]{name});
				int i = 0;
				while(cursor.moveToNext())
				{
					i++;
				}
				if(i==0)
				{
//				�����������ѱ�������ݿ�
				MainActivity.dataBaseHelper.getReadableDatabase().execSQL("insert into saved_cities values(null,?,?,?,?)", new String[]{name,id,cities,province});
				}
				
//				���url
				String url = new WeatherURL(id).getURL();
				
//				�������̷߳�������
				new GetDataFromNet(url, Params.JSON_DATA).getDataFromNet(new GetDataCallBack() {
					
					@Override
					public void CallBack(int arg0) {
						switch(arg0)
						{
//						�����У���ʾ�Ի���
						case Params.LOADING:
							progressDialog=ProgressDialog.show(SearchActivity.this, null, "���ڼ�������",true,false);
							break;
//						������ɣ�ȡ���Ի���
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
