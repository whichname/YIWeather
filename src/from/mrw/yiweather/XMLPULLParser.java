package from.mrw.yiweather;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;

public class XMLPULLParser {

//	要解析的xml数据
	public String data = null;

//	用于记录tag位置
	int i = 0;
//	省份
	String province = null;
//	市
	String cities = null;
	/*
	 * 构造函数
	 * @param data 需要解析的数据
	 */
	public XMLPULLParser(String data)
	{
		this.data = data;
	}
	
	/*
	 * 解析数据函数,将解析后的数据存入数据库中
	 */
	public void startparser(final XMLParserCallBack xmlParserCallBack)
	{
		try {
//			利用android自带的xml快速获取pull解析器
			XmlPullParser xmlpullparser = Xml.newPullParser();
//			将数据转换为输入流
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getBytes());
//			设置需要解析的xml数据
			xmlpullparser.setInput(byteArrayInputStream,"UTF-8");
//			取得事件
			int event = xmlpullparser.getEventType();
//			若解析到末尾，文档结束
			while(event != XmlPullParser.END_DOCUMENT)
			{
//				标签名
				String nodeName = xmlpullparser.getName();
				switch(event)
				{
//				文档开始
				case XmlPullParser.START_DOCUMENT:
//					更新表前先把现有数据删除
					deleteData(MainActivity.dataBaseHelper.getReadableDatabase());
					break;
//				标签开始
				case XmlPullParser.START_TAG:
//					当标签为area时，判断i的值，i为2时为省份，i为3时为市
					if(nodeName.equalsIgnoreCase("area"))
					{
//						i自增
						i++;
						if(i == 2)
						{
//							取得省份
							province = xmlpullparser.getAttributeValue(null,"name");
						}
						else if(i == 3)
						{
//							取得市
							cities = xmlpullparser.getAttributeValue(null,"name");
						}
					}
//					当标签为city时，取得数据
					else if(nodeName.equalsIgnoreCase("city"))
					{
//						i自增
						i++;
//						取得当前地点id
						String id = xmlpullparser.getAttributeValue(null, "id");
//						取得当前地点名称
						String name = xmlpullparser.getAttributeValue(null, "name");
//						将地点信息插入表中
						insertData(MainActivity.dataBaseHelper.getReadableDatabase(), id, name,province,cities);
					}
					break;
//				标签结束
				case XmlPullParser.END_TAG:
//					标签结束时，i自减
					i--;
					break;
				}
//				下一个标签
				event = xmlpullparser.next();
			}
//			关闭输入流
			byteArrayInputStream.close();

//			回调消息
			xmlParserCallBack.CallBack(Params.DONE_LOADING);
			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 写入城市数据表函数
	 * @param db 数据库实例
	 */
	
	public void insertData(SQLiteDatabase db,String id,String name,String province,String cities)
	{
		db.execSQL("insert into ch_cities values(null,?,?,?,?)",new String[]{id,name,province,cities});
	}
	
	
	/*
	 * 删除城市数据表函数
	 * @param db 数据库实例
	 */
	public void deleteData(SQLiteDatabase db)
	{
		db.execSQL("delete from ch_cities");
	}
	
	
//	XML解析的回调接口
	public interface XMLParserCallBack
	{
		public void CallBack(int arg0);
	}
	
}
