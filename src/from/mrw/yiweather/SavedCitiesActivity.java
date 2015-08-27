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
	
//	���ذ�ť
	public ImageView backup_savedcities_titlebar_bn;
//	��ռ�¼
	public ImageView delete_savedcities_titlebar_bn;
//	�б�
	public ListView saved_cities_listview;
//	���id��arraylist
	private ArrayList<String> cities_id = new ArrayList<String>();
//	�������Ի���
	private ProgressDialog progressDialog;
	
//	������
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
	
//��ʼ��listview
	public void listviewinit()
	{
//		��ѯ���ݿ�
		Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from saved_cities order by _id desc", null);
//		�����ݴ���arraylist��
		while(cursor.moveToNext())
		{
				cities_id.add(cursor.getString(cursor.getColumnIndex("id")));
		}
		
		
//		������
		myadapter=new SimpleCursorAdapter(SavedCitiesActivity.this, R.layout.cities_item, cursor, new String[]{"name","cities","province"}, new int[]{R.id.item_name,R.id.item_cities,R.id.item_province});
//		����������
		saved_cities_listview.setAdapter(myadapter);
		
//		�������
		saved_cities_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				���id
				String id = cities_id.get(arg2);
				
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
							progressDialog=ProgressDialog.show(SavedCitiesActivity.this, null, "���ڼ�������",true,false);
							break;
//						������ɣ�ȡ���Ի���
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
//	����
	case R.id.backup_savedcities_titlebar_bn:
		SavedCitiesActivity.this.finish();
		break;
//���
	case R.id.delete_savedcities_titlebar_bn:
		new AlertDialog.Builder(SavedCitiesActivity.this).setIcon(null).setTitle(null).setMessage("ȷ�������ʷ���ݣ�").setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				MainActivity.dataBaseHelper.getReadableDatabase().execSQL("delete from saved_cities");
//				��ѯ���ݿ�
				Cursor cursor = MainActivity.dataBaseHelper.getReadableDatabase().rawQuery("select * from saved_cities", null);
//				������
				myadapter=new SimpleCursorAdapter(SavedCitiesActivity.this, R.layout.cities_item, cursor, new String[]{"name","cities","province"}, new int[]{R.id.item_name,R.id.item_cities,R.id.item_province});
//				����������
				saved_cities_listview.setAdapter(myadapter);
			}
		}
		).setNegativeButton("ȡ��", null).show();
		break;
	}
		
	}
	
	
	

}
