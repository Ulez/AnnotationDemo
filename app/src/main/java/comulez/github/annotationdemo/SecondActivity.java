package comulez.github.annotationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.Bind;
import com.example.OnClick;

import comulez.github.annotationdemo.ButterKnife.ButterKnife;

public class SecondActivity extends AppCompatActivity {

    @Bind(id = R.id.button)
    Button button;
    private String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                button.setText("已经点击");
                Log.i(TAG, "button");
                break;
        }
    }
}
