package njupt.simbaba.com.applib;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import njupt.simbaba.com.app2.IBx;


public class A2 extends Base {
    private IBx mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);

        findViewById(R.id.btn_start).setOnClickListener(v -> playMusic());
        findViewById(R.id.btn_stop).setOnClickListener(v -> stopMusic());

        tryBindService();
    }

    private void playMusic() {
        if (mService == null) {
            showAlert("Service disconnected!");
            return;
        }

        try {
            mService.play("Yesterday once more!");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (mService == null) {
            showAlert("Service disconnected!");
            return;
        }

        try {
            mService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void tryBindService() {
        Intent intent = new Intent("njupt.simbaba.action.ibx");
        intent.setPackage("njupt.simbaba.com.app2");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IBx.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            showAlert("Service disconnected!");
        }

        @Override
        public void onBindingDied(ComponentName name) {
            mService = null;
            showAlert("Service died!");
        }
    };

    private void showAlert(String alert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name)
                .setMessage(alert);

        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unbind connection
        unbindService(mServiceConnection);

        // stop the service explicity
        Intent intent = new Intent("njupt.simbaba.action.ibx");
        intent.setPackage("njupt.simbaba.com.app2");
        stopService(intent);
    }
}
