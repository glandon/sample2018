package njupt.simbaba.com.app1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import njupt.simbaba.com.applib.A1;

public class ShareReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (A1.ACTION_SHARE_PICTURE.equals(action)) {
            Intent i = new Intent();
            i.setClassName("njupt.simbaba.com.app1", "njupt.simbaba.com.app1.SharePicture");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
