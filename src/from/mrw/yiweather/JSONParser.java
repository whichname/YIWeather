package from.mrw.yiweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

//	将要解析的json数据
	String json_data = null;
	
	/*
	 * 构造函数
	 * @param json_data 将要解析的json数据
	 */
	public JSONParser(String json_data)
	{
		this.json_data = json_data;
	}
	
	/*
	 * 解析函数
	 */
	public WeatherInfo jsonParser()
	{
//		新建WeatherInfo对象
		WeatherInfo weatherInfo = new WeatherInfo();
		try {
//			获得json对象
			JSONObject json = new JSONObject(json_data);
//			获得jsonarray（weather）
			JSONArray weather = json.getJSONArray("weather");
//			循环获得json数组中对象
			for(int i = 0 ;i < weather.length();i++)
			{
//				获得json对象
				JSONObject weather_0 = weather.getJSONObject(i);
//				获得city_name
				String city_name = weather_0.getString("city_name");
				weatherInfo.set_city_name(city_name);
//				获得city_id
				String city_id = weather_0.getString("city_id");
				weatherInfo.set_city_id(city_id);
//				获得last_update
				String last_update = weather_0.getString("last_update");
				weatherInfo.set_last_update(last_update);
//				获得JSON对象（now）
				JSONObject now = weather_0.getJSONObject("now");
//				获得now_text
				String now_text = now.getString("text");
				weatherInfo.set_now_text(now_text);
//				获得now_code
				String now_code = now.getString("code");
				weatherInfo.set_now_code(now_code);
//				获得now_temperature
				String now_temperature = now.getString("temperature");
				weatherInfo.set_now_temperature(now_temperature);
//				获得feels_like
				String now_feels_like = now.getString("feels_like");
				weatherInfo.set_now_feels_like(now_feels_like);
//				获得wind_direction
				String now_wind_direction = now.getString("wind_direction");
				weatherInfo.set_now_wind_direction(now_wind_direction);
//				获得wind_scale
				String now_wind_scale = now.getString("wind_scale");
				weatherInfo.set_now_wind_scale(now_wind_scale);
//				获得humidity
				String now_humidity = now.getString("humidity");
				weatherInfo.set_now_humidity(now_humidity);
//				获得visibility
				String now_visibility = now.getString("visibility");
				weatherInfo.set_now_visibility(now_visibility);
//				获得pressure
				String now_pressure = now.getString("pressure");
				weatherInfo.set_now_pressure(now_pressure);
//				获得JSON对象（sun）
				JSONObject sun = weather_0.getJSONObject("today");
//				获得日出时间
				String sunrise = sun.getString("sunrise");
				weatherInfo.set_sunrise(sunrise);
//				获得日落时间
				String sunset = sun.getString("sunset");
				weatherInfo.set_sunset(sunset);
//				获得future数组
				JSONArray future = weather_0.getJSONArray("future");
				for(int j = 0; j <future.length();j++)
				{
//					获得JSONObject
					JSONObject future_object = future.getJSONObject(j);
//					获得date
					String future_date = future_object.getString("date");
//					获得day
					String future_day = future_object.getString("day");
//					获得text
					String future_text = future_object.getString("text");
//					获得code1
					String future_code1 = future_object.getString("code1");
//					获得code2
					String future_code2 = future_object.getString("code2");
//					获得high
					String future_high = future_object.getString("high");
//					获得low
					String future_low = future_object.getString("low");
//					获得cop
					String future_cop = future_object.getString("cop");
//					获得wind
					String future_wind = future_object.getString("wind");
					if(j==0)
					{
						weatherInfo.set_today_date(future_date);
						weatherInfo.set_today_day(future_day);
						weatherInfo.set_today_text(future_text);
						weatherInfo.set_today_icon1(future_code1);
						weatherInfo.set_today_icon2(future_code2);
						weatherInfo.set_today_high(future_high);
						weatherInfo.set_today_low(future_low);
						weatherInfo.set_today_cop(future_cop);
						weatherInfo.set_today_wind(future_wind);
					}
					if(j==1)
					{
						weatherInfo.set_tomorrow_date(future_date);
						weatherInfo.set_tomorrow_day(future_day);
						weatherInfo.set_tomorrow_text(future_text);
						weatherInfo.set_tomorrow_icon1(future_code1);
						weatherInfo.set_tomorrow_icon2(future_code2);
						weatherInfo.set_tomorrow_high(future_high);
						weatherInfo.set_tomorrow_low(future_low);
						weatherInfo.set_tomorrow_cop(future_cop);
						weatherInfo.set_tomorrow_wind(future_wind);
					}	
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return weatherInfo;
		
		
	}
	
	
}
