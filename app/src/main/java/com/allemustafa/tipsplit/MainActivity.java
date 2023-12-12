package com.allemustafa.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    private float total;
    private EditText billAmount;
    private EditText totalPeople;
    private TextView tipAmount;
    private TextView totalSplit;
    private  TextView totalAmount;
    private RadioGroup radioGroup;
    private int tipPercent=0;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#BC42B34D")));
        setContentView(R.layout.activity_main);
        billAmount = findViewById(R.id.editTextNumberDecimal);
        totalPeople = findViewById(R.id.totalPeople);
        tipAmount = findViewById(R.id.tipAmount);
        totalAmount = findViewById(R.id.totalAmount);
        totalSplit = findViewById(R.id.peopleSplit);
        radioGroup = findViewById(R.id.radioGroup);
        billAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                String s1 = billAmount.getText().toString();
                setTipAndTotal(s1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void GoButtonClick(View view){
        String s1 = totalPeople.getText().toString();
        if(s1.length()<1 || total==0){
            totalSplit.setText(String.valueOf(0));
            return;
        }
        float f = Float.parseFloat(s1);
        totalSplit.setText(String.format("$%.1f0",total/f));
    }
    public void clearBtnClick(View view){
        totalSplit.setText("");
        tipAmount.setText("");
        totalAmount.setText("");
        totalPeople.setText("");
        billAmount.setText("");
        radioGroup.clearCheck();
        tipPercent=0;
    }
    private void setTipAndTotal(String s1){
        if(s1.length()<1){
            tipAmount.setText(String.valueOf(0));
            totalAmount.setText(String.valueOf(0));
            tipPercent = 0;
            radioGroup.clearCheck();
            return;
        }
        float f = Float.parseFloat(s1);
        float tip = f * tipPercent/100;
        total = tip + f;
        tipAmount.setText(String.format("$%.2f", tip));
        totalAmount.setText(String.format("$%.2f",total));
    }
    public void OnTipChange(View view){
        if(view.getId()==R.id.twelveOption)
            tipPercent = 12;
        else if(view.getId()==R.id.fifteenOption)
            tipPercent=15;
        else if(view.getId()==R.id.eighteenOption)
            tipPercent=18;
        else if(view.getId()==R.id.twentyOption)
            tipPercent=20;
        else
        {
            tipPercent = 0;
            return;
        }
        String s1 = billAmount.getText().toString();
        setTipAndTotal(s1);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Tip",tipPercent);
        outState.putFloat("total",total);
        outState.putString("tipamount",tipAmount.getText().toString());
        outState.putString("totalamount",totalAmount.getText().toString());
        outState.putString("totalsplit",totalSplit.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tipPercent = savedInstanceState.getInt("Tip");
        total = savedInstanceState.getFloat("total");
        tipAmount.setText(savedInstanceState.getString("tipamount"));
        totalAmount.setText(savedInstanceState.getString("totalamount"));
        totalSplit.setText(savedInstanceState.getString("totalsplit"));
    }
}