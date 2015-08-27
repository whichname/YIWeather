package from.mrw.yiweather;

import java.net.URLEncoder;

public class WeatherURL {

//	要查询的城市
	private String city;
//	要查询的语言
	private String language = "zh-chs";
//	温度单位
	private String unit = "c";
//	aqi
	private String aqi = "city";
//	key
	private String key = "IXOP31EQDO";
	
	public WeatherURL(String city)
	{
		this.city = city;
	}
//	获得URL名
	public String getURL()
	{
		return "https://api.thinkpage.cn/v2/weather/all.json?city=" + city+"&language=" + language +"&unit="+unit +"&aqi="+ aqi + "&key="+ key;
	}
	
	
}
