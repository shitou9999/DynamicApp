package chatqq.home.com.host;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ryg.dynamicload.internal.DLIntent;
import com.ryg.dynamicload.internal.DLPluginManager;
import com.ryg.utils.DLUtils;

import java.io.File;

/**
 * host
 */
public class HostActivity extends AppCompatActivity {
    private Button btnTest;
    private TextView tvTip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        btnTest = (Button) findViewById(R.id.btn_test);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        init();

    }

    //初试化
    private void init() {
        //获取插件
        String pluginFolder = Environment.getExternalStorageDirectory()+"/test";
        File file=new File(pluginFolder);
        if (!file.exists()){
            Toast.makeText(this, "s生成文件夹", Toast.LENGTH_SHORT).show();///storage/emulated/0//test
            file.mkdir();
        }

        String fileName = "ss.jpg";
        File filess = new File(file, fileName);

        File[] plugins=file.listFiles();
        //判断有没有插件
        if (plugins==null || plugins.length ==0){
            this.tvTip.setVisibility(View.VISIBLE);
            Toast.makeText(this, "没有检测到插件！", Toast.LENGTH_SHORT).show();
            return;
        }
        //调用第一个插件
        File plugin = plugins[0];
        final PluginItem item=new PluginItem();
        item.pluginPath=plugin.getAbsolutePath();
        item.packageInfo= DLUtils.getPackageInfo(this,item.pluginPath);
        //获取插件启动的actitvty的名称
        if (item.packageInfo.activities!=null && item.packageInfo.activities.length>0){
            item.launcherActitvtyName=item.packageInfo.activities[0].name;
        }
        //获取插件启动Service的名称
        if (item.packageInfo.services!=null && item.packageInfo.services.length>0){
            item.launcherServiceName=item.packageInfo.services[0].name;
        }
        //显示插件
        tvTip.setText("检测到一个插件："+item.pluginPath);
        //加载插件
        DLPluginManager.getInstance(this).loadApk(item.pluginPath);
        //添加监听
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HostActivity.this, "已经开始调用插件了", Toast.LENGTH_SHORT).show();
                //调用插件
                usePlugin(item);

            }
        });

    }

    //调用插件
    private void usePlugin(PluginItem pluginItem) {
        DLPluginManager pluginManager=DLPluginManager.getInstance(this);
        DLIntent dlIntent = new DLIntent(pluginItem.packageInfo.packageName,pluginItem.launcherActitvtyName);
        pluginManager.startPluginActivity(this,dlIntent);
    }


    //插件bean
    private class PluginItem {
        public PackageInfo packageInfo;
        public String pluginPath;
        public String launcherActitvtyName;
        public String launcherServiceName;

        public PluginItem() {
        }
    }
}























