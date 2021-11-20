package com.example.tip_calculator;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner tips;
    EditText money;
    TextView total, num, totaltip;
    Button minus, plus, toasty;
    Boolean ran = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        minus = findViewById(R.id.minus);
        minus.setEnabled(false);
        plus = findViewById(R.id.plus);
        plus.setEnabled(true);
        money = findViewById(R.id.editText);
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(money.getText().toString().length() != 0)
                {
                    calcTotal();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tips = findViewById(R.id.spinner);
        tips.setSelection(0);
        String[] amount = {"5%", "10%", "15%"};
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, amount);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tips.setAdapter(dataAdapter);
        if(ran) //setOnItemSelectedListener will run once at initialization, this is to prevent it
        {
            tips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    calcTotal();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else ran = true;

        toasty = findViewById(R.id.button3);
        toasty.setOnClickListener(this);

        //Buildng AlertDialog
        //1. Instantiate an AlertDialog.Builder with it's constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Hello World, I have completed the Turing Test, Now Choose")
                .setMessage("Would you like to be dominated?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toasty.warning(MainActivity.this, "The spanking shall begin now", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toasty.info(MainActivity.this, "What a rebellious one", Toast.LENGTH_LONG).show();
                    }
                })
                .setItems(R.array.choice,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // int which contains the position of the selected item

                            }
                        });


        //3. Get tha Alertdialog from create()
        AlertDialog dialog = builder.create();

        //4. Show the AlertDialog
        dialog.show();
    }


    public void reduce(View view)
    {
        num = findViewById(R.id.textView2);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        int sum = Integer.parseInt(num.getText().toString());
        if(sum <= 1)
        {
            minus.setEnabled(false);
            plus.setEnabled(true);
            sum = 1;
            num.setText(String.valueOf(sum));
        }
        else
        {
            plus.setEnabled(true);
            --sum;
            num.setText(String.valueOf(sum));
        }
        calcTotal();
    }

    public void increase(View view)
    {
        num = findViewById(R.id.textView2);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        int sum = Integer.parseInt(num.getText().toString());
        if(sum >= 9)
        {
            plus.setEnabled(false);
            minus.setEnabled(true);
            sum = 10;
            num.setText(String.valueOf(sum));
        }
        else
        {
            minus.setEnabled(true);
            sum++;
            num.setText(String.valueOf(sum));
        }
        calcTotal();
    }

    public void calcTotal()
    {
        money = findViewById(R.id.editText);
        totaltip = findViewById(R.id.textView3);
        total = findViewById(R.id.textView4);
        if(money.getText().toString().length() != 0)
        {
            DecimalFormat df = new DecimalFormat("#,###,##0.00");
            double bill = Double.parseDouble(df.format(Double.valueOf(money.getText().toString())));
            double tip = 0.05;
            tips = findViewById(R.id.spinner);
            num = findViewById(R.id.textView2);
            int sum = Integer.parseInt(num.getText().toString());
            switch (tips.getSelectedItem().toString()) {
                case "5%":
                    tip = 0.05;
                    break;
                case "10%":
                    tip = 0.10;
                    break;
                case "15%":
                    tip = 0.15;
                    break;
            }
            total.setText("Bill to pay(Per Person): £" + String.format("%.2f", bill / sum));
            totaltip.setText("Tip to pay(Per Person): £" + String.format("%.2f", bill / sum * tip));
        }
        else
        {
            total.setText("Bill to Pay(Per Person): Bill has not been entered");
            totaltip.setText("Bill to Pay(Per Person): Bill has not been entered");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button3 : Drawable icon = getResources().getDrawable(R.drawable.heart);
                                Toasty.normal(this, "You have activated me trap card", Toast.LENGTH_LONG, icon, true).show();
                            break;
        }
    }
}
// For toasty check gradle files, app level dependency and global level maven repository