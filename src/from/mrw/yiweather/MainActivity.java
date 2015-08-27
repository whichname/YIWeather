package from.mrw.yiweather;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import from.mrw.yiweather.GetDataFromNet.GetDataCallBack;

public class MainActivity extends Activity implements OnClickListener {
//	�������ݿ�ʵ��
	static DataBaseHelper dataBaseHelper;
//	��ӳ��еİ�ť
	public ImageView main_titlebar_addcities_bn;
//	�鿴�ѱ���ĳ��еİ�ť
	public ImageView main_titlebar_savedcities_bn;
//	ˢ�°�ť
	public ImageView main_titlebar_refresh_bn;
//	���ð�ť
	public ImageView main_titlebar_setting_bn;
//�������������ؼ�
	public ImageView weather_now_icon_iv;
	public TextView weather_now_tempeture_tv;
	public TextView weather_now_text_tv;
	public TextView feels_like_tv;
	public TextView humidity_tv;
	public TextView wind_direction_tv;
	public TextView visibility_tv;
	public TextView wind_scale_tv;
	public TextView pressure_tv;
	public TextView sunrise_tv;
	public TextView sunset_tv;
	public ImageView weather_future_one_icona;
	public ImageView weather_future_one_iconb;
	public TextView weather_future_one_high;
	public TextView weather_future_one_low;
	public TextView weather_future_one_cop;
	public TextView weather_future_one_wind;
	public ImageView weather_future_two_icona;
	public ImageView weather_future_two_iconb;
	public TextView weather_future_two_high;
	public TextView weather_future_two_low;
	public TextView weather_future_two_cop;
	public TextView weather_future_two_wind;
	public TextView city_name_tv;
	public TextView last_update_tv;
	
//	��������
	public TextView weather_future_today;
	public TextView weather_future_tomorrow;
	public LinearLayout weather_future_today_ll;
	public LinearLayout weather_future_tomorrow_ll;
	
//	�����������Ի���
	public ProgressDialog progressDialog;	
	
//	��ǰ����id
	public String now_id =null;
	
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//     ��ʼ������
        init();
//        ���³����б���
        RefreshCitiesData();
        
        
        
    }
    
    
    /*
     * ��ʼ������
     */
    public void init()
    {
//    	��ʼ���ؼ�����
    	findviewbyid();
//    	���ؼ��󶨼�����
    	setonclicklistener();
    	
//    	�����ݿ�
    	dataBaseHelper = new DataBaseHelper(MainActivity.this, "yiweather.db3", null, 1);

    	
    }
    
    
    
    
    
    /*
     * ��ʼ���ؼ�����
     */
    public void findviewbyid()
    {
//    	��ӳ��еİ�ť
    	main_titlebar_addcities_bn = (ImageView) findViewById(R.id.main_titlebar_addcities_bn);
//    �鿴�ѱ���ĳ��еİ�ť
    	main_titlebar_savedcities_bn = (ImageView) findViewById(R.id.main_titlebar_savedcities_bn);
//    	���ð�ť
    	main_titlebar_setting_bn = (ImageView) findViewById(R.id.main_titlebar_setting_bn);
//    	ˢ�°�ť
    	main_titlebar_refresh_bn = (ImageView) findViewById(R.id.main_titlebar_refresh_bn);
    	
    	weather_now_icon_iv=(ImageView)findViewById(R.id.weather_now_icon_iv);
    	weather_future_one_icona=(ImageView)findViewById(R.id.weather_future_one_icona);
    	weather_future_one_iconb=(ImageView)findViewById(R.id.weather_future_one_iconb);
    	weather_future_two_icona=(ImageView)findViewById(R.id.weather_future_two_icona);
    	weather_future_two_iconb=(ImageView)findViewById(R.id.weather_future_two_iconb);
    	weather_now_tempeture_tv=(TextView)findViewById(R.id.weather_now_tempeture_tv);
    	weather_now_text_tv=(TextView)findViewById(R.id.weather_now_text_tv);
    	feels_like_tv=(TextView)findViewById(R.id.feels_like_tv);
    	humidity_tv=(TextView)findViewById(R.id.humidity_tv);
    	wind_direction_tv=(TextView)findViewById(R.id.wind_direction_tv);
    	visibility_tv=(TextView)findViewById(R.id.visibility_tv);
    	wind_scale_tv=(TextView)findViewById(R.id.wind_scale_tv);
    	pressure_tv=(TextView)findViewById(R.id.pressure_tv);
    	sunrise_tv=(TextView)findViewById(R.id.sunrise_tv);
    	sunset_tv=(TextView)findViewById(R.id.sunset_tv);
    	weather_future_one_high=(TextView)findViewById(R.id.weather_future_one_high);
    	weather_future_one_low=(TextView)findViewById(R.id.weather_future_one_low);
    	weather_future_one_cop=(TextView)findViewById(R.id.weather_future_one_cop);
    	weather_future_one_wind=(TextView)findViewById(R.id.weather_future_one_wind);
    	weather_future_two_high=(TextView)findViewById(R.id.weather_future_two_high);
    	weather_future_two_low=(TextView)findViewById(R.id.weather_future_two_low);
    	weather_future_two_cop=(TextView)findViewById(R.id.weather_future_two_cop);
    	weather_future_two_wind=(TextView)findViewById(R.id.weather_future_two_wind);
    	city_name_tv = (TextView)findViewById(R.id.city_name_tv);
    	last_update_tv = (TextView)findViewById(R.id.last_update_tv);
    	
    	
    	weather_future_today = (TextView)findViewById(R.id.weather_future_today);
    	weather_future_tomorrow = (TextView)findViewById(R.id.weather_future_tomorrow);
    	weather_future_today_ll=(LinearLayout)findViewById(R.id.weather_future_today_ll);
    	weather_future_tomorrow_ll=(LinearLayout)findViewById(R.id.weather_future_tomorrow_ll);
    	
    }
    
    /*
     * �󶨼������ĺ���
     */
    public void setonclicklistener()
    {
//    	����ӳ��а�ť�ļ�����
    	main_titlebar_addcities_bn.setOnClickListener(this);
//    	���ѱ�����а�ť�ļ�����
    	main_titlebar_savedcities_bn.setOnClickListener(this);
//    	�����ð�ť�ļ�����
    	main_titlebar_setting_bn.setOnClickListener(this);
//    	ˢ�µļ�����
    	main_titlebar_refresh_bn.setOnClickListener(this);
    	
//    	��������
    	weather_future_today.setOnClickListener(this);
    	weather_future_tomorrow.setOnClickListener(this);
    }
    
    

    /*
     *����¼��������� 
     */
	@Override
	public void onClick(View arg0) {
//		��õ���ؼ���id
		int ViewId = arg0.getId();
		switch(ViewId)
		{
//		��ӳ��еĿؼ�
		case R.id.main_titlebar_addcities_bn:
//			������ӳ��е�activity
			Intent intent = new Intent(MainActivity.this,AddCityActivity.class);
			startActivity(intent);
			break;
//			�ѱ���ĳ��еĿؼ�
		case R.id.main_titlebar_savedcities_bn:
			Intent saved_intent = new Intent(MainActivity.this, SavedCitiesActivity.class);
			startActivity(saved_intent);
			break;
//			���õĿؼ�
		case R.id.main_titlebar_setting_bn:
			Intent setting_intent = new Intent(MainActivity.this,SettingActivity.class);
			startActivity(setting_intent);
			break;
//			ˢ��
		case R.id.main_titlebar_refresh_bn:
//			���url
			String url = new WeatherURL(now_id).getURL();
			
//			�������̷߳�������
			new GetDataFromNet(url, Params.JSON_DATA).getDataFromNet(new GetDataCallBack() {
				
				@Override
				public void CallBack(int arg0) {
					switch(arg0)
					{
//					�����У���ʾ�Ի���
					case Params.LOADING:
						progressDialog=ProgressDialog.show(MainActivity.this, null, "���ڼ�������",true,false);
						break;
//					������ɣ�ȡ���Ի���
					case Params.DONE_LOADING:
						progressDialog.dismiss();
						RefreshWeather();
						break;
					}
				}
			});
			
			break;
		case R.id.weather_future_today:
			weather_future_today.setBackgroundColor(0x77555555);
			weather_future_tomorrow.setBackgroundColor(0x11555555);
			weather_future_today_ll.setVisibility(View.VISIBLE);
			weather_future_tomorrow_ll.setVisibility(View.GONE);
			break;
		case R.id.weather_future_tomorrow:
			weather_future_today.setBackgroundColor(0x11555555);
			weather_future_tomorrow.setBackgroundColor(0x77555555);
			weather_future_today_ll.setVisibility(View.GONE);
			weather_future_tomorrow_ll.setVisibility(View.VISIBLE);
			break;
		}
	}
    
	
	/*
	 * ���³����б���
	 */
	public void RefreshCitiesData()
	{
//		��ѯ��ǰ�������ݱ�������
		final Cursor cursor = dataBaseHelper.getReadableDatabase().rawQuery("select * from ch_cities", null);
//		�鿴���ݱ����ж�������
		int count = 0;
//		������ݱ���������
		while(cursor.moveToNext())
		{
			count++;
		}
//		�ж����ݱ��������Ƿ���ȫ
		if(count < Params.CITIES_COUNT)
		{
//			������ȫ�����³����б�
			new GetDataFromNet("http://git.oschina.net/mrwww/CallBackW/raw/master/ch-cities.xml", Params.XML_DATA).getDataFromNet(new GetDataCallBack() {
				
				@Override
				public void CallBack(int arg0) {
					switch(arg0)
					{
//					������
					case Params.LOADING:
						progressDialog = ProgressDialog.show(MainActivity.this, null, "���ڻ�ȡ�����б�(50KB)\n����������Ҫ�ϳ�ʱ��", true, false);
						break;
//					�������
					case Params.DONE_LOADING:
						progressDialog.dismiss();
						break;
					}
					
				}
			});
		}
		
	}
	
	
	
	/*
	 * ����ҳ�溯��
	 */
	
	public void RefreshWeather()
	{
//		������Ϣ
		String weather = null;
//		������ݿ�������
		Cursor cursor = dataBaseHelper.getReadableDatabase().rawQuery("select * from weathers", null);
		int i=0;
		while(cursor.moveToNext())
		{
			i++;
			if(i!=0)
			{
			weather=cursor.getString(cursor.getColumnIndex("weather"));
			}
		}
		if(i!=0)
		{
//		����
		WeatherInfo weatherinfo = new JSONParser(weather).jsonParser();
//		���city_id
		now_id = weatherinfo.get_city_id();
		
//		ˢ�½���
		weather_now_tempeture_tv.setText(weatherinfo.get_now_temperature()+"��C");
		weather_now_text_tv.setText(weatherinfo.get_now_text());
		feels_like_tv.setText(weatherinfo.get_now_feels_like() + "��C");
		humidity_tv.setText(weatherinfo.get_now_humidity() + "%");
		wind_direction_tv.setText(weatherinfo.get_now_wind_direction());
		visibility_tv.setText(weatherinfo.get_now_visibility()+ "%");
		wind_scale_tv.setText(weatherinfo.get_now_wind_scale());
		pressure_tv.setText(weatherinfo.get_now_pressure() + "kPa");
		sunrise_tv.setText(weatherinfo.get_sunrise());
		sunset_tv.setText(weatherinfo.get_sunset());
		weather_future_one_high.setText(weatherinfo.get_today_high()+"��C");
		weather_future_one_low.setText(weatherinfo.get_today_low()+"��C");
		weather_future_one_cop.setText(weatherinfo.get_today_cop());
		weather_future_one_wind.setText(weatherinfo.get_today_wind());
		weather_future_two_high.setText(weatherinfo.get_tomorrow_high()+"��C");
		weather_future_two_low.setText(weatherinfo.get_tomorrow_low()+"��C");
		weather_future_two_cop.setText(weatherinfo.get_tomorrow_cop());
		weather_future_two_wind.setText(weatherinfo.get_tomorrow_wind());
		city_name_tv.setText(weatherinfo.get_city_name());
		last_update_tv.setText(weatherinfo.get_last_update().substring(5, 10) + "  " +weatherinfo.get_last_update().substring(11, 16));
		
//		�������ͼ��id
		weather_now_icon_iv.setImageResource(parserIcon(weatherinfo.get_now_code()));
		weather_future_one_icona.setImageResource(parserIcon(weatherinfo.get_today_icon1()));
		weather_future_one_iconb.setImageResource(parserIcon(weatherinfo.get_today_icon2()));
		weather_future_two_icona.setImageResource(parserIcon(weatherinfo.get_tomorrow_icon1()));
		weather_future_two_iconb.setImageResource(parserIcon(weatherinfo.get_tomorrow_icon1()));
		
		
		}
//		û����������
		else if(i==0)
		{
//			�����Ի���
			Builder dialog = new AlertDialog.Builder(MainActivity.this);
//			����ͼ�ꡢ���������
			dialog.setIcon(null).setTitle(null).setMessage("�Ƿ�����ѡ����У�");
//			����ȷ����ť
			dialog.setPositiveButton("����ǰ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
//					������ӳ��е�activity
					Intent add_intent = new Intent(MainActivity.this,AddCityActivity.class);
					startActivity(add_intent);
					
				}
			});
//			����ȡ����ť
			dialog.setNegativeButton("�ݲ�ǰ��", null);
//			��ʾ
			dialog.create().show();
		}
	}
	
//	�������ͼ��id
	private int parserIcon(String icon)
	{
		Context context = getBaseContext();
    	int resId = getResources().getIdentifier("icon"+icon, "drawable", context.getPackageName());
    	return resId;
	}


	@Override
	protected void onResume() {
		RefreshWeather();
		super.onResume();
	}
	
	
	
	
}
