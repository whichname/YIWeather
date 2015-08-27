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

//	Ҫ������xml����
	public String data = null;

//	���ڼ�¼tagλ��
	int i = 0;
//	ʡ��
	String province = null;
//	��
	String cities = null;
	/*
	 * ���캯��
	 * @param data ��Ҫ����������
	 */
	public XMLPULLParser(String data)
	{
		this.data = data;
	}
	
	/*
	 * �������ݺ���,������������ݴ������ݿ���
	 */
	public void startparser(final XMLParserCallBack xmlParserCallBack)
	{
		try {
//			����android�Դ���xml���ٻ�ȡpull������
			XmlPullParser xmlpullparser = Xml.newPullParser();
//			������ת��Ϊ������
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getBytes());
//			������Ҫ������xml����
			xmlpullparser.setInput(byteArrayInputStream,"UTF-8");
//			ȡ���¼�
			int event = xmlpullparser.getEventType();
//			��������ĩβ���ĵ�����
			while(event != XmlPullParser.END_DOCUMENT)
			{
//				��ǩ��
				String nodeName = xmlpullparser.getName();
				switch(event)
				{
//				�ĵ���ʼ
				case XmlPullParser.START_DOCUMENT:
//					���±�ǰ�Ȱ���������ɾ��
					deleteData(MainActivity.dataBaseHelper.getReadableDatabase());
					break;
//				��ǩ��ʼ
				case XmlPullParser.START_TAG:
//					����ǩΪareaʱ���ж�i��ֵ��iΪ2ʱΪʡ�ݣ�iΪ3ʱΪ��
					if(nodeName.equalsIgnoreCase("area"))
					{
//						i����
						i++;
						if(i == 2)
						{
//							ȡ��ʡ��
							province = xmlpullparser.getAttributeValue(null,"name");
						}
						else if(i == 3)
						{
//							ȡ����
							cities = xmlpullparser.getAttributeValue(null,"name");
						}
					}
//					����ǩΪcityʱ��ȡ������
					else if(nodeName.equalsIgnoreCase("city"))
					{
//						i����
						i++;
//						ȡ�õ�ǰ�ص�id
						String id = xmlpullparser.getAttributeValue(null, "id");
//						ȡ�õ�ǰ�ص�����
						String name = xmlpullparser.getAttributeValue(null, "name");
//						���ص���Ϣ�������
						insertData(MainActivity.dataBaseHelper.getReadableDatabase(), id, name,province,cities);
					}
					break;
//				��ǩ����
				case XmlPullParser.END_TAG:
//					��ǩ����ʱ��i�Լ�
					i--;
					break;
				}
//				��һ����ǩ
				event = xmlpullparser.next();
			}
//			�ر�������
			byteArrayInputStream.close();

//			�ص���Ϣ
			xmlParserCallBack.CallBack(Params.DONE_LOADING);
			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * д��������ݱ���
	 * @param db ���ݿ�ʵ��
	 */
	
	public void insertData(SQLiteDatabase db,String id,String name,String province,String cities)
	{
		db.execSQL("insert into ch_cities values(null,?,?,?,?)",new String[]{id,name,province,cities});
	}
	
	
	/*
	 * ɾ���������ݱ���
	 * @param db ���ݿ�ʵ��
	 */
	public void deleteData(SQLiteDatabase db)
	{
		db.execSQL("delete from ch_cities");
	}
	
	
//	XML�����Ļص��ӿ�
	public interface XMLParserCallBack
	{
		public void CallBack(int arg0);
	}
	
}
