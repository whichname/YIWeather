package from.mrw.yiweather;

import java.io.File;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

public class appUpdate {

//	上下文
	private Context context;
//	文件保存地址
	private String filepath;
//	DownloadManager
	private DownloadManager downloadManager;
//	DownloadManager.Request
	private DownloadManager.Request request;
	
	
	
//	构造函数
	public appUpdate(Context context)
	{
		this.context = context;
		this.filepath = getPath();
	}
	
//	更新程序函数
	public void update()
	{
//		获得DownloadManager
		downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
//		获得DownloadManager.Request
		request = new DownloadManager.Request(Uri.parse(Params.UPDATE_URL));
//		设置下载路径
		request.setDestinationInExternalPublicDir("YIWeather", "YIWeather.apk");
//		设置文件可被扫描
		request.allowScanningByMediaScanner();
//		设置通知栏标题
		request.setTitle("一天气更新");
//		设置通知栏内容
		request.setDescription("保存路径："+filepath);
//		设置通知栏可见
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//		开始下载,获得此次下载的id
		Long downId =  downloadManager.enqueue(request);
	}
	
	
	
	
//	创建文件夹函数
	private String getPath()
	{
		File sdDir = Environment.getExternalStoragePublicDirectory("YIWeather");
		String path = sdDir.getAbsolutePath() + "/YIWeather";
//		创建文件夹
		File file = new File(path);
		if(!file.exists())
		{
			file.mkdirs();
		}
		return path;
	}
	
}
