package from.mrw.yiweather;

//������Ϣ��javabean

public class WeatherInfo {

//	������
	private String city_name;
//	����id
	private String city_id;
//	������ʱ��
	private String last_update;
	
//	��ǰ����
	private String now_text;
//	��ǰ��������
	private String now_code;
//	��ǰ�¶�
	private String now_temperature;
//	��ǰ����¶�
	private String now_feels_like;
//	��ǰ����
	private String now_wind_direction;
//	��ǰ����
	private String now_wind_scale;
//	��ǰʪ��
	private String now_humidity;
//	��ǰ�ɼ���
	private String now_visibility;
//	��ǰ��ѹ
	private String now_pressure;
//	�����ճ�ʱ��
	private String sunrise;
//	��������ʱ��
	private String sunset;
	
//	����ʱ��
	private String today_date;
//	�����ܼ�
	private String today_day;
//	��������
	private String today_text;
//	������������1
	private String today_icon1;
//	������������2
	private String today_icon2;
//	�����������
	private String today_high;
//	�����������
	private String today_low;
//	���ս�ˮ����
	private String today_cop;
//	���շ�
	private String today_wind;
	
//	����ʱ��
	private String tomorrow_date;
//	�����ܼ�
	private String tomorrow_day;
//	��������
	private String tomorrow_text;
//	������������1
	private String tomorrow_icon1;
//	������������2
	private String tomorrow_icon2;
//	�����������
	private String tomorrow_high;
//	�����������
	private String tomorrow_low;
//	���ս�ˮ����
	private String tomorrow_cop;
//	���շ�
	private String tomorrow_wind;
	
	public String get_city_name()
	{
		return this.city_name;
	}
	
	public void set_city_name(String city_name)
	{
		this.city_name = city_name;
	}
	
	public String get_city_id()
	{
		return this.city_id;
	}
	
	public void set_city_id(String city_id)
	{
		this.city_id = city_id;
	}
	
	public String get_last_update()
	{
		return this.last_update;
	}
	
	public void set_last_update(String last_update)
	{
		this.last_update = last_update;
	}
	
	public String get_now_text()
	{
		return this.now_text;
	}
	
	public void set_now_text(String now_text)
	{
		this.now_text = now_text;
	}
	
	public String get_now_code()
	{
		return this.now_code;
	}
	
	public void set_now_code(String now_code)
	{
		this.now_code = now_code;
	}
	
	public String get_now_temperature()
	{
		return this.now_temperature;
	}
	
	public void set_now_temperature(String now_temperature)
	{
		this.now_temperature = now_temperature;
	}
	
	public String get_now_feels_like()
	{
		return this.now_feels_like;
	}
	
	public void set_now_feels_like(String now_feels_like)
	{
		this.now_feels_like = now_feels_like;
	}
	
	public String get_now_wind_direction()
	{
		return this.now_wind_direction;
	}
	
	public void set_now_wind_direction(String now_wind_direction)
	{
		this.now_wind_direction = now_wind_direction;
	}
	
	public String get_now_wind_scale()
	{
		return this.now_wind_scale;
	}
	
	public void set_now_wind_scale(String now_wind_scale)
	{
		this.now_wind_scale = now_wind_scale;
	}
	
	public String get_now_humidity()
	{
		return this.now_humidity;
	}
	
	public void set_now_humidity(String now_humidity)
	{
		this.now_humidity = now_humidity;
	}
	
	public String get_now_visibility()
	{
		return this.now_visibility;
	}
	
	public void set_now_visibility(String now_visibility)
	{
		this.now_visibility = now_visibility;
	}
	
	public String get_now_pressure()
	{
		return this.now_pressure;
	}
	
	public void set_now_pressure(String now_pressure)
	{
		this.now_pressure = now_pressure;
	}
	
	public String get_sunrise()
	{
		return this.sunrise;
	}
	
	public void set_sunrise(String sunrise)
	{
		this.sunrise = sunrise;
	}
	
	public String get_sunset()
	{
		return this.sunset;
	}
	
	public void set_sunset(String sunset)
	{
		this.sunset = sunset;
	}
	
	public String get_today_date()
	{
		return this.today_date;
	}
	
	public void set_today_date(String today_date)
	{
		this.today_date = today_date;
	}
	
	public String get_today_day()
	{
		return this.today_day;
	}
	
	public void set_today_day(String today_day)
	{
		this.today_day = today_day;
	}
	
	public String get_today_text()
	{
		return this.today_text;
	}
	
	public void set_today_text(String today_text)
	{
		this.today_text = today_text;
	}
	
	public String get_today_icon1()
	{
		return this.today_icon1;
	}
	
	public void set_today_icon1(String today_icon1)
	{
		this.today_icon1 = today_icon1;
	}
	
	public String get_today_icon2()
	{
		return this.today_icon2;
	}
	
	public void set_today_icon2(String today_icon2)
	{
		this.today_icon2 = today_icon2;
	}
	
	public String get_today_high()
	{
		return this.today_high;
	}
	
	public void set_today_high(String today_high)
	{
		this.today_high = today_high;
	}
	
	public String get_today_low()
	{
		return this.today_low;
	}
	
	public void set_today_low(String today_low)
	{
		this.today_low = today_low;
	}
	
	public String get_today_cop()
	{
		return this.today_cop;
	}
	
	public void set_today_cop(String today_cop)
	{
		this.today_cop = today_cop;
	}
	
	public String get_today_wind()
	{
		return this.today_wind;
	}
	
	public void set_today_wind(String today_wind)
	{
		this.today_wind = today_wind;
	}
	
	public String get_tomorrow_date()
	{
		return this.tomorrow_date;
	}
	
	public void set_tomorrow_date(String tomorrow_date)
	{
		this.tomorrow_date = tomorrow_date;
	}
	
	
	
	public String get_tomorrow_day()
	{
		return this.tomorrow_day;
	}
	
	public void set_tomorrow_day(String tomorrow_day)
	{
		this.tomorrow_day = tomorrow_day;
	}
	
	public String get_tomorrow_text()
	{
		return this.tomorrow_text;
	}
	
	public void set_tomorrow_text(String tomorrow_text)
	{
		this.tomorrow_text = tomorrow_text;
	}
	public String get_tomorrow_icon1()
	{
		return this.tomorrow_icon1;
	}
	
	public void set_tomorrow_icon1(String tomorrow_icon1)
	{
		this.tomorrow_icon1 = tomorrow_icon1;
	}
	public String get_tomorrow_icon2()
	{
		return this.tomorrow_icon2;
	}
	
	public void set_tomorrow_icon2(String tomorrow_icon2)
	{
		this.tomorrow_icon2 = tomorrow_icon2;
	}
	public String get_tomorrow_high()
	{
		return this.tomorrow_high;
	}
	
	public void set_tomorrow_high(String tomorrow_high)
	{
		this.tomorrow_high = tomorrow_high;
	}
	public String get_tomorrow_low()
	{
		return this.tomorrow_low;
	}
	
	public void set_tomorrow_low(String tomorrow_low)
	{
		this.tomorrow_low = tomorrow_low;
	}
	public String get_tomorrow_cop()
	{
		return this.tomorrow_cop;
	}
	
	public void set_tomorrow_cop(String tomorrow_cop)
	{
		this.tomorrow_cop = tomorrow_cop;
	}
	public String get_tomorrow_wind()
	{
		return this.tomorrow_wind;
	}
	
	public void set_tomorrow_wind(String tomorrow_wind)
	{
		this.tomorrow_wind = tomorrow_wind;
	}
	

}
