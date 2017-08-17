package comulez.github.annotationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import comulez.github.annotationdemo.ButterKnife.Bind;
import comulez.github.annotationdemo.ButterKnife.MyButterKnife;
import comulez.github.annotationdemo.ButterKnife.OnClick;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Bind(id = R.id.button1)
    Button button;
    @Bind(id = R.id.button2)
    Button button2;
    @Bind(id = R.id.button3)
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                button.setText("已经点击");
                Log.i(TAG, "button1");
                break;
            case R.id.button2:
                button2.setText("已经点击");
                Log.i(TAG, "button2");
                break;
            case R.id.button3:
                button3.setText("已经点击");
                Log.i(TAG, "button3");
                break;
        }
    }
}
