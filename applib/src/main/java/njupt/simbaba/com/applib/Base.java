package njupt.simbaba.com.applib;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("Registered")
public class Base extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        BottomNavigationView bar = findViewById(R.id.bottom_bar);
        bar.setOnNavigationItemSelectedListener(this::onBottomBarClicked);

        TextView name = findViewById(R.id.name_of_activity);
        StringBuilder sb = new StringBuilder(name.getText());

        App app = (App) getApplication();
        sb.append("( ");
        sb.append(app.genIdOfActivity());
        sb.append(" )");

        name.setText(sb.toString());
    }

    boolean onBottomBarClicked(MenuItem item) {
        Intent intent = new Intent();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int id = item.getItemId();
        if (id == R.id.id_a1) {
            intent.setClass(this, A1.class);

        } else if (id == R.id.id_a2) {
            intent.setClass(this, A2.class);

        } else if (id == R.id.id_a3) {
            intent.setClass(this, A3.class);

        } else if (id == R.id.id_of_b1) {
            intent.setClassName("njupt.simbaba.com.app2", "njupt.simbaba.com.app2.B1");

        } else if (id == R.id.id_of_b2) {
            intent.setClassName("njupt.simbaba.com.app2", "njupt.simbaba.com.app2.B2");

        } else if (id == R.id.id_of_b3) {
            intent.setClassName("njupt.simbaba.com.app2", "njupt.simbaba.com.app2.B3");

        } else if (id == R.id.id_2b) {
            return popupToStartB();
        } else {
            Toast.makeText(this, R.string.warning_no_match, Toast.LENGTH_SHORT).show();
            return false;
        }

        PackageManager pm = getPackageManager();
        if (pm.queryIntentActivities(intent, 0).size() == 0) {
            Toast.makeText(this, "No match activity found!", Toast.LENGTH_SHORT).show();
            return true;
        }

        startActivity(intent);
        return true;
    }


    private boolean popupToStartB() {
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.bottom_bar), Gravity.END);

        popupMenu.getMenu().add(Menu.NONE, R.id.id_of_b1, Menu.NONE, "B1")
                .setOnMenuItemClickListener(this::onBottomBarClicked);

        popupMenu.getMenu().add(Menu.NONE, R.id.id_of_b2, Menu.NONE, "B2")
                .setOnMenuItemClickListener(this::onBottomBarClicked);

        popupMenu.getMenu().add(Menu.NONE, R.id.id_of_b3, Menu.NONE, "B3")
                .setOnMenuItemClickListener(this::onBottomBarClicked);

        popupMenu.show();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("simbaba", "onDestroy!");
    }
}
