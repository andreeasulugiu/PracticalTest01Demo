package practicaltest01.eim.systems.cs.pub.ro.practicaltest01demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Andreea on 4/3/2018.
 */

public class PracticalTest01SecondaryActivity extends AppCompatActivity {
    Button saveButton;
    Button cancelButton;
    TextView text;

    ButtonOnClickListener listener = new ButtonOnClickListener();

    private class ButtonOnClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.save_button){
                setResult(RESULT_OK, null);
            }
            if (view.getId() == R.id.cancel_button){
                setResult(RESULT_CANCELED, null);
            }
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);
        saveButton = (Button) findViewById(R.id.save_button);

        saveButton.setOnClickListener(listener);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(listener);
        text = (TextView) findViewById(R.id.text);
        Intent intent = getIntent();
        if (intent != null){
            if (intent.getExtras().containsKey("number")){
                text.setText(intent.getExtras().get("number").toString());
            }
        }
    }
}
