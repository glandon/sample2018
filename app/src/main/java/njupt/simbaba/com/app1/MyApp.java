package njupt.simbaba.com.app1;

import android.content.IntentFilter;

import njupt.simbaba.com.applib.A1;
import njupt.simbaba.com.applib.App;

public class MyApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter(A1.ACTION_SHARE_PICTURE);
        registerReceiver(new ShareReceiver(), intentFilter);
    }
}
