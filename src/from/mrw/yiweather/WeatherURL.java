package from.mrw.yiweather;

import java.net.URLEncoder;

public class WeatherURL {

//	Ҫ��ѯ�ĳ���
	private String city;
//	Ҫ��ѯ������
	private String language = "zh-chs";
//	�¶ȵ�λ
	private String unit = "c";
//	aqi
	private String aqi = "city";
//	key
	private String key = "IXOP31EQDO";
	
	public WeatherURL(String city)
	{
		this.city = city;
	}
//	���URL��
	public String getURL()
	{
		return "https://api.thinkpage.cn/v2/weather/all.json?city=" + city+"&language=" + language +"&unit="+unit +"&aqi="+ aqi + "&key="+ key;
	}
	
	
}
