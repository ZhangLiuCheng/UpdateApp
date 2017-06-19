package com.ayw.updateapp;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView versionTv = (TextView) findViewById(R.id.version);
        versionTv.setText("当前版本：" + getVersion());
    }
    
    public void update(View view) {
    	// 已经安装apk的路径
    	final String oldFile = getPackageResourcePath();
    	// 合并后apk的路径
    	final String newFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/newpatchNew1.apk";
    	// 差分包路径
    	final String patchFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch1.apk";
    	if (!new File(patchFile).exists()) {
    		Toast.makeText(this, "没有新版本", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	// 合并版本
    	BsPatch.patch(oldFile, newFile, patchFile);
    	Intent intent = new Intent(Intent.ACTION_VIEW);   
    	intent.setDataAndType(Uri.fromFile(new File(newFile)), "application/vnd.android.package-archive");   
    	startActivity(intent); 
    }
    
    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
