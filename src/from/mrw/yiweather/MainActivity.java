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
//	创建数据库实例
	static DataBaseHelper dataBaseHelper;
//	添加城市的按钮
	public ImageView main_titlebar_addcities_bn;
//	查看已保存的城市的按钮
	public ImageView main_titlebar_savedcities_bn;
//	刷新按钮
	public ImageView main_titlebar_refresh_bn;
//	设置按钮
	public ImageView main_titlebar_setting_bn;
//获得天气界面各控件
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
	
//	今天明天
	public TextView weather_future_today;
	public TextView weather_future_tomorrow;
	public LinearLayout weather_future_today_ll;
	public LinearLayout weather_future_tomorrow_ll;
	
//	创建进度条对话框
	public ProgressDialog progressDialog;	
	
//	当前城市id
	public String now_id =null;
	
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//     初始化函数
        init();
//        更新城市列表函数
        RefreshCitiesData();
        
        
        
    }
    
    
    /*
     * 初始化函数
     */
    public void init()
    {
//    	初始化控件函数
    	findviewbyid();
//    	给控件绑定监听器
    	setonclicklistener();
    	
//    	打开数据库
    	dataBaseHelper = new DataBaseHelper(MainActivity.this, "yiweather.db3", null, 1);

    	
    }
    
    
    
    
    
    /*
     * 初始化控件函数
     */
    public void findviewbyid()
    {
//    	添加城市的按钮
    	main_titlebar_addcities_bn = (ImageView) findViewById(R.id.main_titlebar_addcities_bn);
//    查看已保存的城市的按钮
    	main_titlebar_savedcities_bn = (ImageView) findViewById(R.id.main_titlebar_savedcities_bn);
//    	设置按钮
    	main_titlebar_setting_bn = (ImageView) findViewById(R.id.main_titlebar_setting_bn);
//    	刷新按钮
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
     * 绑定监听器的函数
     */
    public void setonclicklistener()
    {
//    	绑定添加城市按钮的监听器
    	main_titlebar_addcities_bn.setOnClickListener(this);
//    	绑定已保存城市按钮的监听器
    	main_titlebar_savedcities_bn.setOnClickListener(this);
//    	绑定设置按钮的监听器
    	main_titlebar_setting_bn.setOnClickListener(this);
//    	刷新的监听器
    	main_titlebar_refresh_bn.setOnClickListener(this);
    	
//    	今天明天
    	weather_future_today.setOnClickListener(this);
    	weather_future_tomorrow.setOnClickListener(this);
    }
    
    

    /*
     *点击事件监听函数 
     */
	@Override
	public void onClick(View arg0) {
//		获得点击控件的id
		int ViewId = arg0.getId();
		switch(ViewId)
		{
//		添加城市的控件
		case R.id.main_titlebar_addcities_bn:
//			进入添加城市的activity
			Intent intent = new Intent(MainActivity.this,AddCityActivity.class);
			startActivity(intent);
			break;
//			已保存的城市的控件
		case R.id.main_titlebar_savedcities_bn:
			Intent saved_intent = new Intent(MainActivity.this, SavedCitiesActivity.class);
			startActivity(saved_intent);
			break;
//			设置的控件
		case R.id.main_titlebar_setting_bn:
			Intent setting_intent = new Intent(MainActivity.this,SettingActivity.class);
			startActivity(setting_intent);
			break;
//			刷新
		case R.id.main_titlebar_refresh_bn:
//			获得url
			String url = new WeatherURL(now_id).getURL();
			
//			启动多线程访问网络
			new GetDataFromNet(url, Params.JSON_DATA).getDataFromNet(new GetDataCallBack() {
				
				@Override
				public void CallBack(int arg0) {
					switch(arg0)
					{
//					加载中，显示对话框
					case Params.LOADING:
						progressDialog=ProgressDialog.show(MainActivity.this, null, "正在加载数据",true,false);
						break;
//					加载完成，取消对话框
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
	 * 更新城市列表函数
	 */
	public void RefreshCitiesData()
	{
//		查询当前城市数据表中数据
		final Cursor cursor = dataBaseHelper.getReadableDatabase().rawQuery("select * from ch_cities", null);
//		查看数据表中有多少数据
		int count = 0;
//		获得数据表数据总数
		while(cursor.moveToNext())
		{
			count++;
		}
//		判断数据表中数据是否齐全
		if(count < Params.CITIES_COUNT)
		{
//			若不齐全，更新城市列表
			new GetDataFromNet("http://git.oschina.net/mrwww/CallBackW/raw/master/ch-cities.xml", Params.XML_DATA).getDataFromNet(new GetDataCallBack() {
				
				@Override
				public void CallBack(int arg0) {
					switch(arg0)
					{
//					加载中
					case Params.LOADING:
						progressDialog = ProgressDialog.show(MainActivity.this, null, "正在获取城市列表(50KB)\n解析可能需要较长时间", true, false);
						break;
//					加载完成
					case Params.DONE_LOADING:
						progressDialog.dismiss();
						break;
					}
					
				}
			});
		}
		
	}
	
	
	
	/*
	 * 更新页面函数
	 */
	
	public void RefreshWeather()
	{
//		天气信息
		String weather = null;
//		获得数据库中数据
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
//		解析
		WeatherInfo weatherinfo = new JSONParser(weather).jsonParser();
//		获得city_id
		now_id = weatherinfo.get_city_id();
		
//		刷新界面
		weather_now_tempeture_tv.setText(weatherinfo.get_now_temperature()+"°C");
		weather_now_text_tv.setText(weatherinfo.get_now_text());
		feels_like_tv.setText(weatherinfo.get_now_feels_like() + "°C");
		humidity_tv.setText(weatherinfo.get_now_humidity() + "%");
		wind_direction_tv.setText(weatherinfo.get_now_wind_direction());
		visibility_tv.setText(weatherinfo.get_now_visibility()+ "%");
		wind_scale_tv.setText(weatherinfo.get_now_wind_scale());
		pressure_tv.setText(weatherinfo.get_now_pressure() + "kPa");
		sunrise_tv.setText(weatherinfo.get_sunrise());
		sunset_tv.setText(weatherinfo.get_sunset());
		weather_future_one_high.setText(weatherinfo.get_today_high()+"°C");
		weather_future_one_low.setText(weatherinfo.get_today_low()+"°C");
		weather_future_one_cop.setText(weatherinfo.get_today_cop());
		weather_future_one_wind.setText(weatherinfo.get_today_wind());
		weather_future_two_high.setText(weatherinfo.get_tomorrow_high()+"°C");
		weather_future_two_low.setText(weatherinfo.get_tomorrow_low()+"°C");
		weather_future_two_cop.setText(weatherinfo.get_tomorrow_cop());
		weather_future_two_wind.setText(weatherinfo.get_tomorrow_wind());
		city_name_tv.setText(weatherinfo.get_city_name());
		last_update_tv.setText(weatherinfo.get_last_update().substring(5, 10) + "  " +weatherinfo.get_last_update().substring(11, 16));
		
//		获得天气图标id
		weather_now_icon_iv.setImageResource(parserIcon(weatherinfo.get_now_code()));
		weather_future_one_icona.setImageResource(parserIcon(weatherinfo.get_today_icon1()));
		weather_future_one_iconb.setImageResource(parserIcon(weatherinfo.get_today_icon2()));
		weather_future_two_icona.setImageResource(parserIcon(weatherinfo.get_tomorrow_icon1()));
		weather_future_two_iconb.setImageResource(parserIcon(weatherinfo.get_tomorrow_icon1()));
		
		
		}
//		没有天气数据
		else if(i==0)
		{
//			创建对话框
			Builder dialog = new AlertDialog.Builder(MainActivity.this);
//			设置图标、标题和内容
			dialog.setIcon(null).setTitle(null).setMessage("是否立即选择城市？");
//			设置确定按钮
			dialog.setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
//					进入添加城市的activity
					Intent add_intent = new Intent(MainActivity.this,AddCityActivity.class);
					startActivity(add_intent);
					
				}
			});
//			设置取消按钮
			dialog.setNegativeButton("暂不前往", null);
//			显示
			dialog.create().show();
		}
	}
	
//	获得天气图标id
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
