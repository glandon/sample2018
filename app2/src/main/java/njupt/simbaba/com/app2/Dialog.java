package njupt.simbaba.com.app2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Dialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, "Show Dialog failed!", Toast.LENGTH_SHORT).show();
            return;
        }

        String state = intent.getStringExtra("state");

        TextView stateView = findViewById(R.id.music_state);
        stateView.setText(state);
    }
}
