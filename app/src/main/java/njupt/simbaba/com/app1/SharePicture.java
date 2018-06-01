package njupt.simbaba.com.app1;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



public class SharePicture extends AppCompatActivity {
    public static String DIR_NAME = "images";
    public static String IMG_NAME = "share.png";

    private File mHomeDir = Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_picture);


        //mHomeDir = getCacheDir();
        checkRequiredPermission(); // if external sdcard

        // choose a image to share
        findViewById(R.id.btn_image).setOnClickListener(v-> {
            Animator anim = AnimatorInflater.loadAnimator(this, R.animator.anim);
            anim.setTarget(v);
            anim.addListener(new AnimDefault(this::getImage));
            anim.start();
        });

        // share image
        findViewById(R.id.btn_share).setOnClickListener(v->{
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
            anim.setDuration(2000);
            anim.setAnimationListener(new AnimDefault(()->share()));
            v.startAnimation(anim);
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, R.string.choose_picture_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = data.getData();
        if (uri == null) return;

        ContentResolver cr = getContentResolver();

        try(InputStream inputStream = cr.openInputStream(data.getData())){
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            signature(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signature(Bitmap bitmap) throws IOException {

        EditText edit = findViewById(R.id.signature);
        String signature = edit.getText().toString();

        if (signature.isEmpty()) {
            signature = "simbaba";
        }

        Bitmap bmp2Draw = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(bmp2Draw);
        canvas.drawBitmap(bitmap, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAlpha(80);
        paint.setTextSize(32);

        canvas.save();
        canvas.rotate(-30);

        Rect rect = new Rect();
        paint.getTextBounds(signature, 0, signature.length(), rect);

        int w = rect.width();
        int step = bitmap.getHeight()/10;
        int bmpWidth = bitmap.getWidth();

        // draw text
        for (int y = step; y < bitmap.getHeight(); y += step) {
            for (int x = -bmpWidth; x < bmpWidth; x+= w *2) {
                canvas.drawText(signature, x, y, paint);
            }
        }

        canvas.restore();

        // save as a temp file

        String cache = mHomeDir.getCanonicalPath();
        File dir = new File(cache,DIR_NAME);
        if (!dir.exists() && !dir.mkdir()){
            Log.e("simbaba", "Can't create image dir!");
            return;
        }

        File share = new File(dir, IMG_NAME);
        FileOutputStream fos = new FileOutputStream(share);
        bmp2Draw.compress(Bitmap.CompressFormat.PNG, 80, fos);
        fos.close();

        // update preview
        ImageView imageView = findViewById(R.id.preview);
        imageView.setImageBitmap(bmp2Draw);
    }

    private boolean checkRequiredPermission() {
        int w = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int r = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (w == PackageManager.PERMISSION_GRANTED && r == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                0);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults[0] != 0 || grantResults[1] != 0){
            Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Log.d("simbaba", "got rw sd permissions!");
        }
    }

    private void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0);
    }

    private void share() {
        // Here, thisActivity is the current activity
        if (!checkRequiredPermission()) {
            return;
        }

        File newFile= null;

        try {
            String cache = mHomeDir.getCanonicalPath();
            File imagePath = new File(cache, DIR_NAME);
            newFile = new File(imagePath, IMG_NAME);
            Log.d("simbaba", cache);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (newFile == null || !newFile.exists()) {
            Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = FileProvider.getUriForFile(this,
                "njupt.simbaba.com.app1.file_provider", newFile);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        //startActivity(Intent.createChooser(intent, "share by simbaba"));
        startActivity(intent);
    }
}
