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

//从网络上更新数据的函数


public class GetDataFromNet {
	
//	线程池，用于分配线程从网络下载数据
	ExecutorService threads = Executors.newFixedThreadPool(1);
//	下载数据的链接
	private final String url; 
//	数据类型，用于判断采用哪种解析方式
	private final int datatype;

	
	/*构造函数
	 * @param url 下载数据的链接
	 * @param datatype 数据类型，用于判断采用哪种解析方式
	 */
	public GetDataFromNet(String url,int datatype)
	{
		this.url = url;
		this.datatype = datatype;
	}
	
	
	/*
	 * 下载函数
	 */
	
	public void getDataFromNet(final GetDataCallBack getDataCallBack)
	{
		
//		创建handler
		final Handler handler = new Handler()
		{

			@Override
			public void handleMessage(Message msg) {
//				调用回调函数的方法，向activity发送消息
				getDataCallBack.CallBack(msg.what);
			}
			
		};
		
		
		
//		从线程池中分配线程执行下载
		threads.submit(new Runnable() {
			
			@Override
			public void run() {
				try {
//					开始下载，发送消息
					handler.sendEmptyMessage(Params.LOADING);
					
					
//					新建URL对象
					URL Url = new URL(url);
//					新建HttpURLConnect对象
					HttpURLConnection httpURLConnection = (HttpURLConnection) Url.openConnection();
//					获得输入流
					InputStream inputStream = httpURLConnection.getInputStream();
//					每一行数据
					String line = null;
//					最后得到的数据
					StringBuilder data = new StringBuilder();
//					新建BufferedReader对象将输入流中数据提取出来
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//					提取输入流中数据
					while((line = bufferedReader.readLine()) != null)
					{
						data.append(line + "\n");
					}
//					关闭输入流
					inputStream.close();

//					判断解析方式
					switch(datatype)
					{
//					采用XML解析
					case Params.XML_DATA:
//						新建XML解析器对象
						XMLPULLParser xmlpullParser = new XMLPULLParser(data.toString());
//						开始解析
						xmlpullParser.startparser(new XMLParserCallBack() {
							
							@Override
							public void CallBack(int arg0) {
//								给主线程发送加载完成的消息
								handler.sendEmptyMessage(arg0);
							}
						});
						break;
//						采用JSON解析,直接存入数据库中						
					case Params.JSON_DATA:
//						删除现有数据
						MainActivity.dataBaseHelper.getReadableDatabase().execSQL("delete from weathers");
//						存入数据库中
						MainActivity.dataBaseHelper.getReadableDatabase().execSQL("insert into weathers values(null,?)", new String[]{data.toString()});
//						发送完成消息
						handler.sendEmptyMessage(Params.DONE_LOADING);
						break;
//						检查更新
					case Params.CHECK_UPDATE:
						try {
//							获得jsonobject
							JSONObject jsonObject = new JSONObject(data.toString());
//							获得下载地址
							String address = jsonObject.getString("address");
//							获得更新信息
							String msg = jsonObject.getString("msg");
//							获得版本号
							int server_version = jsonObject.getInt("version");
//							发送完成消息
							handler.sendEmptyMessage(Params.DONE_LOADING);
//							与本地版本号对比
							if(Params.LOCAL_VERSION<server_version)
							{
								Params.UPDATE_URL = address;
								Params.UPDATE_MSG=msg;
//								发送有更新的消息
								handler.sendEmptyMessage(Params.HAS_UPDATE);
								
							}
							else
							{
//								发送无更新的消息
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
//			run函数结束
		}
//		runnable对象结束
		);
		
	}
	
//	回调接口，向Activity中发送状态
	public interface GetDataCallBack
	{
		public void CallBack(int arg0);
	}
	
}
