package njupt.simbaba.com.app2;


import android.content.Context;
import android.content.Intent;


public class IBxImpl extends IBx.Stub {
    private Context mContext;

    IBxImpl(Context context) {
        mContext = context;
    }

    @Override
    public void play(String music) {
        Intent intent = new Intent();
        intent.setClass(mContext, Dialog.class);
        intent.putExtra("state", String.format("play music %s", music));
        mContext.startActivity(intent);
    }

    @Override
    public void stop() {
        Intent intent = new Intent();
        intent.setClass(mContext, Dialog.class);
        intent.putExtra("state", "okay, stopped!");
        mContext.startActivity(intent);
    }
}
