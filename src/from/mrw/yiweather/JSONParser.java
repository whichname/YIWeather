package from.mrw.yiweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

//	��Ҫ������json����
	String json_data = null;
	
	/*
	 * ���캯��
	 * @param json_data ��Ҫ������json����
	 */
	public JSONParser(String json_data)
	{
		this.json_data = json_data;
	}
	
	/*
	 * ��������
	 */
	public WeatherInfo jsonParser()
	{
//		�½�WeatherInfo����
		WeatherInfo weatherInfo = new WeatherInfo();
		try {
//			���json����
			JSONObject json = new JSONObject(json_data);
//			���jsonarray��weather��
			JSONArray weather = json.getJSONArray("weather");
//			ѭ�����json�����ж���
			for(int i = 0 ;i < weather.length();i++)
			{
//				���json����
				JSONObject weather_0 = weather.getJSONObject(i);
//				���city_name
				String city_name = weather_0.getString("city_name");
				weatherInfo.set_city_name(city_name);
//				���city_id
				String city_id = weather_0.getString("city_id");
				weatherInfo.set_city_id(city_id);
//				���last_update
				String last_update = weather_0.getString("last_update");
				weatherInfo.set_last_update(last_update);
//				���JSON����now��
				JSONObject now = weather_0.getJSONObject("now");
//				���now_text
				String now_text = now.getString("text");
				weatherInfo.set_now_text(now_text);
//				���now_code
				String now_code = now.getString("code");
				weatherInfo.set_now_code(now_code);
//				���now_temperature
				String now_temperature = now.getString("temperature");
				weatherInfo.set_now_temperature(now_temperature);
//				���feels_like
				String now_feels_like = now.getString("feels_like");
				weatherInfo.set_now_feels_like(now_feels_like);
//				���wind_direction
				String now_wind_direction = now.getString("wind_direction");
				weatherInfo.set_now_wind_direction(now_wind_direction);
//				���wind_scale
				String now_wind_scale = now.getString("wind_scale");
				weatherInfo.set_now_wind_scale(now_wind_scale);
//				���humidity
				String now_humidity = now.getString("humidity");
				weatherInfo.set_now_humidity(now_humidity);
//				���visibility
				String now_visibility = now.getString("visibility");
				weatherInfo.set_now_visibility(now_visibility);
//				���pressure
				String now_pressure = now.getString("pressure");
				weatherInfo.set_now_pressure(now_pressure);
//				���JSON����sun��
				JSONObject sun = weather_0.getJSONObject("today");
//				����ճ�ʱ��
				String sunrise = sun.getString("sunrise");
				weatherInfo.set_sunrise(sunrise);
//				�������ʱ��
				String sunset = sun.getString("sunset");
				weatherInfo.set_sunset(sunset);
//				���future����
				JSONArray future = weather_0.getJSONArray("future");
				for(int j = 0; j <future.length();j++)
				{
//					���JSONObject
					JSONObject future_object = future.getJSONObject(j);
//					���date
					String future_date = future_object.getString("date");
//					���day
					String future_day = future_object.getString("day");
//					���text
					String future_text = future_object.getString("text");
//					���code1
					String future_code1 = future_object.getString("code1");
//					���code2
					String future_code2 = future_object.getString("code2");
//					���high
					String future_high = future_object.getString("high");
//					���low
					String future_low = future_object.getString("low");
//					���cop
					String future_cop = future_object.getString("cop");
//					���wind
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
