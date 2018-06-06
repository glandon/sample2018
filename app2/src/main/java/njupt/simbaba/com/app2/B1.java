package njupt.simbaba.com.app2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class B1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b1);


        /*
         * write fail, because no SD-CARD permission
         */
        File dir = Environment.getExternalStorageDirectory();
        File config = new File(dir, "config.txt");

        try(FileWriter fileWriter = new FileWriter(config)) {
            fileWriter.write("abc:123");
        }catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * write success, although no SD-CARD permission
         * /sdcard/Android/data/njupt.simbaba.com.app2/files
         */
        File appFilesExDir = getExternalFilesDir(null);
        config = new File(appFilesExDir, "config.txt");

        try(FileWriter fileWriter = new FileWriter(config)) {
            fileWriter.write("abc:123");
        }catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * write success, although no SD-CARD permission
         * /sdcard/Android/data/njupt.simbaba.com.app2/files/Music
         */
        File appMusicExDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        config = new File(appMusicExDir, "config.txt");

        try(FileWriter fileWriter = new FileWriter(config)) {
            fileWriter.write("abc:123");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
