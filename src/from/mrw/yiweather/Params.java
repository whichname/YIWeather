package from.mrw.yiweather;

import android.net.Uri;

public class Params {
//	��������Ϊxml
	public final static int XML_DATA = 0x1010;
//	��������Ϊjson
	public final static int JSON_DATA = 0x1011;
//	������
	public final static int CHECK_UPDATE = 0x1001;
//	���ذ汾��
	public static int LOCAL_VERSION= 0;
//	�и���
	public final static int HAS_UPDATE =0x0100;
//	�޸���
	public final static int NONE_UPDATE = 0x0101;
//	���º���ļ����ص�ַ
	public static String UPDATE_URL = null;
//	������Ϣ
	public static String UPDATE_MSG = null;


	
	
//	�����µ���ַ
	public final static String CHECK_UPDATE_URL = "http://git.oschina.net/mrwww/CallBackW/raw/master/update.txt";

	
	
//	�������ݱ���������
	public final static int CITIES_COUNT = 2567;
	
//	����
	public final static int NET_ERROR = 0x1100;
	public final static int XMLPARSER_ERROR = 0x1101;
	
//	������
	public final static int LOADING= 0x0010;
//	�������
	public final static int DONE_LOADING=0x0011;
	
//	SQL���,�����������ݱ�
	public final static String CREATE_CITIES = "create table ch_cities(_id integer primary key autoincrement,id,name,province,cities)";
//	SQL���,�ѱ���ĳ���
	public final static String SAVED_CITIES = "create table saved_cities(_id integer primary key autoincrement,name,id,cities,province)";
//	SQL���,��������
	public final static String WEATHERS= "create table weathers(_id integer primary key autoincrement,weather)";
	
//	addactivity���б�ؼ�״̬��
	public static int LIST_NOW = 0;
	
}
