package com.nexttapp.securekeytest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText num1;
    private EditText num2;
    private Button calBtn;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText) findViewById(R.id.main_num1_input);
        num2 = (EditText) findViewById(R.id.main_num2_input);
        calBtn = (Button) findViewById(R.id.main_calculate_btn);
        resultView = (TextView) findViewById(R.id.main_result);

        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calResult();
            }
        });

    }

    private void calResult() {
        resultView.setText("");
        String st = num1.getText().toString();
        if (st == null || st.isEmpty()) {
            resultView.setText("Please input number 1");
            return;
        }
        int a = Integer.valueOf(st);
        st = num2.getText().toString();
        if (st == null || st.isEmpty()) {
            resultView.setText("Please input number 2");
            return;
        }
        int b = Integer.valueOf(st);
        int c = 0;
        for (int z = a^b; z != 0; z = z & (z - 1)) {
            c++;
        }
        resultView.setText("Result: " + c);
    }
}
