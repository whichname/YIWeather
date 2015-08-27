package from.mrw.yiweather;

import java.io.File;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

public class appUpdate {

//	������
	private Context context;
//	�ļ������ַ
	private String filepath;
//	DownloadManager
	private DownloadManager downloadManager;
//	DownloadManager.Request
	private DownloadManager.Request request;
	
	
	
//	���캯��
	public appUpdate(Context context)
	{
		this.context = context;
		this.filepath = getPath();
	}
	
//	���³�����
	public void update()
	{
//		���DownloadManager
		downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
//		���DownloadManager.Request
		request = new DownloadManager.Request(Uri.parse(Params.UPDATE_URL));
//		��������·��
		request.setDestinationInExternalPublicDir("YIWeather", "YIWeather.apk");
//		�����ļ��ɱ�ɨ��
		request.allowScanningByMediaScanner();
//		����֪ͨ������
		request.setTitle("һ��������");
//		����֪ͨ������
		request.setDescription("����·����"+filepath);
//		����֪ͨ���ɼ�
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//		��ʼ����,��ô˴����ص�id
		Long downId =  downloadManager.enqueue(request);
	}
	
	
	
	
//	�����ļ��к���
	private String getPath()
	{
		File sdDir = Environment.getExternalStoragePublicDirectory("YIWeather");
		String path = sdDir.getAbsolutePath() + "/YIWeather";
//		�����ļ���
		File file = new File(path);
		if(!file.exists())
		{
			file.mkdirs();
		}
		return path;
	}
	
}
