package com.farmarket.farmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class ThamkYouActivity extends AppCompatActivity {
    TextView estimated;
    Button mContinue;
    String expected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thamk_you);
        Date m = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(m);
        cal.add(Calendar.DATE, 7); // 10 is the days you want to add or subtract
        m = cal.getTime();

        Intent intent = getIntent();
        try{
            expected = (String)intent.getExtras().get("expected");
            System.out.println(expected);
        }
        catch (Exception e)
        {
            Intent intent1 = new Intent(ThamkYouActivity.this,MainActivity.class);
            Toast.makeText(getApplicationContext(),"Thank you for your order",Toast.LENGTH_LONG).show();
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent1);
            finish();
            return;
        }

        estimated = (TextView)findViewById(R.id.estmitatedTV);
        mContinue= (Button)findViewById(R.id.submitReview);
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThamkYouActivity.this,MyOrdersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        estimated.setText("Estimated delivery ( 7 days from now) "+expected);
    }
}
