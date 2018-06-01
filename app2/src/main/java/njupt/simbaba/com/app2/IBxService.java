package njupt.simbaba.com.app2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class IBxService extends Service {
    public IBxService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IBxImpl(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}
