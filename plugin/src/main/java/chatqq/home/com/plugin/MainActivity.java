package chatqq.home.com.plugin;

import android.os.Bundle;

import com.ryg.dynamicload.DLBasePluginActivity;

/**
 * 插件模块  必须要用host中的DL框架来加载plugin,而不是plugin自带的DL框架
 */
public class MainActivity extends DLBasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
