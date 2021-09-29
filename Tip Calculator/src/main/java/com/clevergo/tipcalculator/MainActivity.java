package com.clevergo.tipcalculator;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private ImageButton decrease1;
    private ImageButton decrease2;
    private ImageButton decrease3;
    private ImageButton increase1;
    private final TextWatcher tipPercentWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable != null && !(editable.toString().isEmpty())) {
                increase1.setEnabled(Integer.parseInt(editable.toString()) < 100);
                decrease1.setEnabled(Integer.parseInt(editable.toString()) > 0);
            }
        }
    };
    private double finalBill, splitTipAmount, splitBillAmount, tipAmount;
    private ImageButton increase2;
    private final TextWatcher splitBillWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable != null && !(editable.toString().isEmpty())) {
                increase2.setEnabled(Integer.parseInt(editable.toString()) < 50);
                decrease2.setEnabled(Integer.parseInt(editable.toString()) > 0);
            }
        }
    };
    private ImageButton increase3;
    private final TextWatcher splitTipWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable != null && !(editable.toString().isEmpty())) {
                increase3.setEnabled(Integer.parseInt(editable.toString()) < 50);
                decrease3.setEnabled(Integer.parseInt(editable.toString()) > 0);
            }
        }
    };

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO - Implement Unity Ads

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_bg));
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        //region Find View By ID
        TextView tipDisplay = findViewById(R.id.textView2);
        TextView totalDisplay = findViewById(R.id.textView3);
        TextView splitBillDisplay = findViewById(R.id.textView5);
        TextView splitTipDisplay = findViewById(R.id.textView7);

        TextInputLayout billInput = findViewById(R.id.billInput);
        TextInputLayout tipPercentInput = findViewById(R.id.tipInput);
        TextInputLayout splitBillInput = findViewById(R.id.splitBillInput);
        TextInputLayout splitTipInput = findViewById(R.id.splitTipInput);

        EditText tipEdit = tipPercentInput.getEditText();
        EditText splitBillEdit = splitBillInput.getEditText();
        EditText splitTipEdit = splitTipInput.getEditText();

        assert tipEdit != null;
        assert splitBillEdit != null;
        assert splitTipEdit != null;

        tipEdit.setText("10");
        splitBillEdit.setText("0");
        splitTipEdit.setText("0");

        tipEdit.addTextChangedListener(tipPercentWatcher);
        splitBillEdit.addTextChangedListener(splitBillWatcher);
        splitTipEdit.addTextChangedListener(splitTipWatcher);

        decrease1 = findViewById(R.id.decrementBtn1);
        decrease2 = findViewById(R.id.decrementBtn2);
        decrease3 = findViewById(R.id.decrementBtn3);
        increase1 = findViewById(R.id.incrementBtn1);
        increase2 = findViewById(R.id.incrementBtn2);
        increase3 = findViewById(R.id.incrementBtn3);

        MaterialButton calcButton = findViewById(R.id.calcButton);
        MaterialButton roundButton = findViewById(R.id.roundButton);

        LinearLayout adContainer = findViewById(R.id.adContainer);
        //endregion

        decrease1.setOnClickListener(a -> tipEdit.setText(String.valueOf(Integer.parseInt(tipEdit.getText().toString()) - 1)));
        decrease2.setOnClickListener(a -> splitBillEdit.setText(String.valueOf(Integer.parseInt(splitBillEdit.getText().toString()) - 1)));
        decrease3.setOnClickListener(a -> splitTipEdit.setText(String.valueOf(Integer.parseInt(splitTipEdit.getText().toString()) - 1)));

        increase1.setOnClickListener(a -> tipEdit.setText(String.valueOf(Integer.parseInt(tipEdit.getText().toString()) + 1)));
        increase2.setOnClickListener(a -> splitBillEdit.setText(String.valueOf(Integer.parseInt(splitBillEdit.getText().toString()) + 1)));
        increase3.setOnClickListener(a -> splitTipEdit.setText(String.valueOf(Integer.parseInt(splitTipEdit.getText().toString()) + 1)));

        calcButton.setOnClickListener(calc -> {
            assert billInput.getEditText() != null;
            double totalBill = 0, tipAmount = 0;
            double splitBill = 0, splitTip = 0;
            int billSplit = 0, tipSplit = 0;
            try {
                totalBill = Double.parseDouble(billInput.getEditText().getText().toString());
                billSplit = Integer.parseInt(splitBillEdit.getText().toString());
                tipSplit = Integer.parseInt(splitTipEdit.getText().toString());
            } catch (NumberFormatException ex) {
                billInput.getEditText().setText(String.valueOf(0));
                totalBill = 0;
                billSplit = 0;
                tipSplit = 0;
            } finally {
                if (totalBill >= 0 && billSplit >= 0 && tipSplit >= 0) {
                    tipAmount = totalBill / Double.parseDouble(tipEdit.getText().toString());
                    tipDisplay.setText(getString(R.string.tip) + " " + tipAmount);
                    totalDisplay.setText(getString(R.string.total) + " " + (totalBill + tipAmount));

                    splitBill = (totalBill + tipAmount) / billSplit;
                    splitBillDisplay.setText(getString(R.string.splitAmount) + " " + (splitBill == Double.POSITIVE_INFINITY ? 0 : splitBill));

                    splitTip = tipAmount / tipSplit;
                    splitTipDisplay.setText(getString(R.string.splitTip) + " " + (splitTip == Double.POSITIVE_INFINITY ? 0 : splitTip));
                } else {
                    Toast.makeText(this, getString(R.string.noNegative), Toast.LENGTH_SHORT).show();
                }

                setData((totalBill + tipAmount), (splitBill == Double.POSITIVE_INFINITY ? 0 : splitBill), (splitTip == Double.POSITIVE_INFINITY ? 0 : splitTip), tipAmount);
            }
        });

        roundButton.setOnClickListener(round -> {
            totalDisplay.setText(getString(R.string.total) + " " + Math.round(finalBill));
            splitBillDisplay.setText(getString(R.string.splitAmount) + " " + Math.round(splitBillAmount));
            splitTipDisplay.setText(getString(R.string.splitTip) + " " + Math.round(splitTipAmount));
            tipDisplay.setText(getString(R.string.tip) + " " + Math.round(tipAmount));
        });
    }

    private void setData(double totalAmount, double splitBillAmo, double splitTipAmo, double tip)
    {
        tipAmount = tip;
        finalBill = totalAmount;
        splitTipAmount = splitTipAmo;
        splitBillAmount = splitBillAmo;
    }
}