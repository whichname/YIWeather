package from.mrw.yiweather;

import android.net.Uri;

public class Params {
//	数据类型为xml
	public final static int XML_DATA = 0x1010;
//	数据类型为json
	public final static int JSON_DATA = 0x1011;
//	检查更新
	public final static int CHECK_UPDATE = 0x1001;
//	本地版本号
	public static int LOCAL_VERSION= 0;
//	有更新
	public final static int HAS_UPDATE =0x0100;
//	无更新
	public final static int NONE_UPDATE = 0x0101;
//	更新后的文件下载地址
	public static String UPDATE_URL = null;
//	更新信息
	public static String UPDATE_MSG = null;


	
	
//	检查更新的网址
	public final static String CHECK_UPDATE_URL = "http://git.oschina.net/mrwww/CallBackW/raw/master/update.txt";

	
	
//	城市数据表数据总数
	public final static int CITIES_COUNT = 2567;
	
//	出错
	public final static int NET_ERROR = 0x1100;
	public final static int XMLPARSER_ERROR = 0x1101;
	
//	加载中
	public final static int LOADING= 0x0010;
//	加载完成
	public final static int DONE_LOADING=0x0011;
	
//	SQL语句,建立城市数据表
	public final static String CREATE_CITIES = "create table ch_cities(_id integer primary key autoincrement,id,name,province,cities)";
//	SQL语句,已保存的城市
	public final static String SAVED_CITIES = "create table saved_cities(_id integer primary key autoincrement,name,id,cities,province)";
//	SQL语句,保存天气
	public final static String WEATHERS= "create table weathers(_id integer primary key autoincrement,weather)";
	
//	addactivity的列表控件状态；
	public static int LIST_NOW = 0;
	
}
