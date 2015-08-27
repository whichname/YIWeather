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

//	���ذ�ť
	private ImageView backup_setting_titlebar_bn;
//	���°�ť
	private Button update_app_bn;
//	�������Ի���
	private ProgressDialog progressDialog;
//	about
	private TextView setting_about_tv;
//	��ǰ�汾��
	private String versionName = null;
//	��ǰ�汾��
	private int versionCode = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity_layout);
		findviewbyid();
		
//		��ȡ�汾��
		versionName = getVersionName();
		
//		��ȡ�汾��
		versionCode = getVersionCode();
		
		
		Params.LOCAL_VERSION = versionCode;
		
//		���ù����ı������ʾ
		setting_about_tv.setText("��ǰ�汾:"+versionName+"\n��������:\n����:wzm625278436@qq.com");
		

	}
	
//	��ʼ���ؼ�����
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
//			����
		case R.id.update_app_bn:
//			�鿴�Ƿ��и���
			new GetDataFromNet(Params.CHECK_UPDATE_URL, Params.CHECK_UPDATE).getDataFromNet(new GetDataCallBack() {
				
				@Override
				public void CallBack(int arg0) {
					switch(arg0)
					{
//					���ڷ�������
					case Params.LOADING:
						progressDialog=ProgressDialog.show(SettingActivity.this, null, "���ڼ�������",true,false);
						break;
//						�����������
					case Params.DONE_LOADING:
						progressDialog.dismiss();
						break;
//						�޸���
					case Params.NONE_UPDATE:
						Toast.makeText(SettingActivity.this, "��ǰ�������°汾", 3000).show();
						break;
//						�и���
					case Params.HAS_UPDATE:
//						�����Ի���
						Builder dialog = new AlertDialog.Builder(SettingActivity.this);
//						����ͼ�ꡢ���⡢����
						dialog.setIcon(null).setTitle("�����°汾���Ƿ��������£�").setMessage(Params.UPDATE_MSG);
//						����ȷ����ť
						dialog.setPositiveButton("��������", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
//								���ظ��³���
								new appUpdate(SettingActivity.this).update();
								
								
							}
						});
//						����ȡ����ť
						dialog.setNegativeButton("�´���˵", null);
//						��ʾ
						dialog.create().show();
						
						break;
					
					}
					
				}
			});
			
			
			break;
		}
	}
	
	
//	��ȡ��ǰ�汾��
	private String getVersionName()
	{
		try
		{
//		���PackageManager
		PackageManager packageManager = this.getPackageManager();
//		��ȡPackageInfo
		PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
//		��ð汾��
		String version = packageInfo.versionName;
		return version;
		}
		catch(NameNotFoundException e)
		{
			e.printStackTrace();
			return "��ȡ�汾��ʧ��";
		}
		
	}
	
//	��ð汾��
	private int getVersionCode()
	{
		try
		{
//		���PackageManager
		PackageManager packageManager = this.getPackageManager();
//		��ȡPackageInfo
		PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
//		��ð汾��
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
