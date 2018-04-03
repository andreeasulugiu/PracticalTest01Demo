package practicaltest01.eim.systems.cs.pub.ro.practicaltest01demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    Button firstButton;
    Button secondButton;
    Button goToActivity;
    TextView firstTextView;
    TextView secondTextView;


    ButtonOnClickListener listener = new ButtonOnClickListener();

    private class ButtonOnClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.first_button:
                    int leftNumberOfClicks = Integer.parseInt(firstTextView.getText().toString());
                    leftNumberOfClicks++;
                    firstTextView.setText(String.valueOf(leftNumberOfClicks));
                    break;
                case R.id.second_button:
                    int rightNumberOfClicks = Integer.parseInt(secondTextView.getText().toString());
                    rightNumberOfClicks++;
                    secondTextView.setText(String.valueOf(rightNumberOfClicks));
                    break;
                case R.id.go_to_activity_button : {
                    int leftNumberOfClickss = Integer.parseInt(firstTextView.getText().toString());
                    int rightNumberOfClickss = Integer.parseInt(secondTextView.getText().toString());
                    int sum = leftNumberOfClickss + rightNumberOfClickss;
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    intent.putExtra("number",sum);
                    startActivityForResult(intent, 1 );
                    break;
                }
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_practical_test01_main);
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");

        firstButton = (Button) findViewById(R.id.first_button);

        firstButton.setOnClickListener(listener);

        secondButton = (Button) findViewById(R.id.second_button);
        secondButton.setOnClickListener(listener);

        goToActivity = (Button) findViewById(R.id.go_to_activity_button);
        goToActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int leftNumberOfClicks = Integer.parseInt(firstTextView.getText().toString());
                int rightNumberOfClicks = Integer.parseInt(secondTextView.getText().toString());
                if (leftNumberOfClicks + rightNumberOfClicks > 1) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                    intent.putExtra("firstNumber", leftNumberOfClicks);
                    intent.putExtra("secondNumber", rightNumberOfClicks);
                    getApplicationContext().startService(intent);
                }
            }
        });

        firstTextView = (TextView) findViewById(R.id.first_text);
        secondTextView = (TextView) findViewById(R.id.second_text);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("first_value")) {
                firstTextView.setText(savedInstanceState.get("first_value").toString());
            }

            if (savedInstanceState.containsKey("second_value")) {
                secondTextView.setText(savedInstanceState.get("second_value").toString());
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("first_value",firstTextView.getText().toString());
        outState.putString("second_value",secondTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("first_value")) {
            firstTextView.setText(savedInstanceState.get("first_value").toString());
        }

        if (savedInstanceState.containsKey("second_value")) {
            secondTextView.setText(savedInstanceState.get("second_value").toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
