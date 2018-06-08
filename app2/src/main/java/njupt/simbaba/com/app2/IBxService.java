package njupt.simbaba.com.app2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


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

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(getApplicationContext(), "Service onUnbind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Service onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
