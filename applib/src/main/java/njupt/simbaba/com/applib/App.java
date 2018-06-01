package njupt.simbaba.com.applib;

import android.app.Application;

public class App extends Application {
    private static int ID_OF_ACTIVITY = 1000;

    public int genIdOfActivity() {
        return ID_OF_ACTIVITY++;
    }
}
