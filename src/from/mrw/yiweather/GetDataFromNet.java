package from.mrw.yiweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import from.mrw.yiweather.XMLPULLParser.XMLParserCallBack;

//�������ϸ������ݵĺ���


public class GetDataFromNet {
	
//	�̳߳أ����ڷ����̴߳�������������
	ExecutorService threads = Executors.newFixedThreadPool(1);
//	�������ݵ�����
	private final String url; 
//	�������ͣ������жϲ������ֽ�����ʽ
	private final int datatype;

	
	/*���캯��
	 * @param url �������ݵ�����
	 * @param datatype �������ͣ������жϲ������ֽ�����ʽ
	 */
	public GetDataFromNet(String url,int datatype)
	{
		this.url = url;
		this.datatype = datatype;
	}
	
	
	/*
	 * ���غ���
	 */
	
	public void getDataFromNet(final GetDataCallBack getDataCallBack)
	{
		
//		����handler
		final Handler handler = new Handler()
		{

			@Override
			public void handleMessage(Message msg) {
//				���ûص������ķ�������activity������Ϣ
				getDataCallBack.CallBack(msg.what);
			}
			
		};
		
		
		
//		���̳߳��з����߳�ִ������
		threads.submit(new Runnable() {
			
			@Override
			public void run() {
				try {
//					��ʼ���أ�������Ϣ
					handler.sendEmptyMessage(Params.LOADING);
					
					
//					�½�URL����
					URL Url = new URL(url);
//					�½�HttpURLConnect����
					HttpURLConnection httpURLConnection = (HttpURLConnection) Url.openConnection();
//					���������
					InputStream inputStream = httpURLConnection.getInputStream();
//					ÿһ������
					String line = null;
//					���õ�������
					StringBuilder data = new StringBuilder();
//					�½�BufferedReader������������������ȡ����
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//					��ȡ������������
					while((line = bufferedReader.readLine()) != null)
					{
						data.append(line + "\n");
					}
//					�ر�������
					inputStream.close();

//					�жϽ�����ʽ
					switch(datatype)
					{
//					����XML����
					case Params.XML_DATA:
//						�½�XML����������
						XMLPULLParser xmlpullParser = new XMLPULLParser(data.toString());
//						��ʼ����
						xmlpullParser.startparser(new XMLParserCallBack() {
							
							@Override
							public void CallBack(int arg0) {
//								�����̷߳��ͼ�����ɵ���Ϣ
								handler.sendEmptyMessage(arg0);
							}
						});
						break;
//						����JSON����,ֱ�Ӵ������ݿ���						
					case Params.JSON_DATA:
//						ɾ����������
						MainActivity.dataBaseHelper.getReadableDatabase().execSQL("delete from weathers");
//						�������ݿ���
						MainActivity.dataBaseHelper.getReadableDatabase().execSQL("insert into weathers values(null,?)", new String[]{data.toString()});
//						���������Ϣ
						handler.sendEmptyMessage(Params.DONE_LOADING);
						break;
//						������
					case Params.CHECK_UPDATE:
						try {
//							���jsonobject
							JSONObject jsonObject = new JSONObject(data.toString());
//							������ص�ַ
							String address = jsonObject.getString("address");
//							��ø�����Ϣ
							String msg = jsonObject.getString("msg");
//							��ð汾��
							int server_version = jsonObject.getInt("version");
//							���������Ϣ
							handler.sendEmptyMessage(Params.DONE_LOADING);
//							�뱾�ذ汾�ŶԱ�
							if(Params.LOCAL_VERSION<server_version)
							{
								Params.UPDATE_URL = address;
								Params.UPDATE_MSG=msg;
//								�����и��µ���Ϣ
								handler.sendEmptyMessage(Params.HAS_UPDATE);
								
							}
							else
							{
//								�����޸��µ���Ϣ
								handler.sendEmptyMessage(Params.NONE_UPDATE);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					
						
						
					}
					
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
				}
			}
//			run��������
		}
//		runnable�������
		);
		
	}
	
//	�ص��ӿڣ���Activity�з���״̬
	public interface GetDataCallBack
	{
		public void CallBack(int arg0);
	}
	
}
