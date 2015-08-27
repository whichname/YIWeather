package from.mrw.yiweather;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import from.mrw.yiweather.GetDataFromNet.GetDataCallBack;

public class SettingActivity extends Activity implements OnClickListener{

//	返回按钮
	private ImageView backup_setting_titlebar_bn;
//	更新按钮
	private Button update_app_bn;
//	进度条对话框
	private ProgressDialog progressDialog;
//	about
	private TextView setting_about_tv;
//	当前版本名
	private String versionName = null;
//	当前版本号
	private int versionCode = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity_layout);
		findviewbyid();
		
//		获取版本名
		versionName = getVersionName();
		
//		获取版本号
		versionCode = getVersionCode();
		
		
		Params.LOCAL_VERSION = versionCode;
		
//		设置关于文本框的显示
		setting_about_tv.setText("当前版本:"+versionName+"\n关于作者:\n邮箱:wzm625278436@qq.com");
		

	}
	
//	初始化控件函数
	private void findviewbyid()
	{
		backup_setting_titlebar_bn=(ImageView)findViewById(R.id.backup_setting_titlebar_bn);
		update_app_bn=(Button)findViewById(R.id.update_app_bn);
		setting_about_tv=(TextView) findViewById(R.id.setting_about_tv);
		
		backup_setting_titlebar_bn.setOnClickListener(this);
		update_app_bn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId())
		{
		case R.id.backup_setting_titlebar_bn:
			SettingActivity.this.finish();
			break;
//			更新
		case R.id.update_app_bn:
//			查看是否有更新
			new GetDataFromNet(Params.CHECK_UPDATE_URL, Params.CHECK_UPDATE).getDataFromNet(new GetDataCallBack() {
				
				@Override
				public void CallBack(int arg0) {
					switch(arg0)
					{
//					正在访问网络
					case Params.LOADING:
						progressDialog=ProgressDialog.show(SettingActivity.this, null, "正在加载数据",true,false);
						break;
//						访问网络结束
					case Params.DONE_LOADING:
						progressDialog.dismiss();
						break;
//						无更新
					case Params.NONE_UPDATE:
						Toast.makeText(SettingActivity.this, "当前已是最新版本", 3000).show();
						break;
//						有更新
					case Params.HAS_UPDATE:
//						创建对话框
						Builder dialog = new AlertDialog.Builder(SettingActivity.this);
//						设置图标、标题、内容
						dialog.setIcon(null).setTitle("发现新版本，是否立即更新？").setMessage(Params.UPDATE_MSG);
//						设置确定按钮
						dialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
//								下载更新程序
								new appUpdate(SettingActivity.this).update();
								
								
							}
						});
//						设置取消按钮
						dialog.setNegativeButton("下次再说", null);
//						显示
						dialog.create().show();
						
						break;
					
					}
					
				}
			});
			
			
			break;
		}
	}
	
	
//	获取当前版本名
	private String getVersionName()
	{
		try
		{
//		获得PackageManager
		PackageManager packageManager = this.getPackageManager();
//		获取PackageInfo
		PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
//		获得版本号
		String version = packageInfo.versionName;
		return version;
		}
		catch(NameNotFoundException e)
		{
			e.printStackTrace();
			return "获取版本号失败";
		}
		
	}
	
//	获得版本号
	private int getVersionCode()
	{
		try
		{
//		获得PackageManager
		PackageManager packageManager = this.getPackageManager();
//		获取PackageInfo
		PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
//		获得版本号
		int versionCode = packageInfo.versionCode;
		return versionCode;
		}
		catch(NameNotFoundException e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
}
