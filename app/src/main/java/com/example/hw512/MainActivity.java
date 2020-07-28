package com.example.hw512;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    final int[] buttonsId = new int[]{R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonC, R.id.buttonMul, R.id.buttonDiv, R.id.buttonPlus, R.id.buttonMinus,
            R.id.buttonPercent, R.id.buttonSign, R.id.buttonEqual, R.id.buttonDot};
    final Button[] buttons = new Button[buttonsId.length];

    private TextView calcScreen;
    private TextView calcTypeTxt;
    private View standardKeyboard;
    private View sciKeyboard;

    private final Calculator calculator = new Calculator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitViews();
    }

    private void InitViews() {

        for (int i = 0; i < buttonsId.length; i++) {
            buttons[i] = findViewById(buttonsId[i]);
            buttons[i].setOnClickListener(onClickListener);
        }
        calcScreen = findViewById(R.id.calcScreen);
        Switch calcTypeSwitch = findViewById(R.id.calc_type_switch);
        standardKeyboard = findViewById(R.id.std_keyboard);
        sciKeyboard = findViewById(R.id.sci_keyboard);
        calcTypeTxt = findViewById(R.id.calc_type_txt);

        calcTypeSwitch.setOnCheckedChangeListener(switchOnChecked);
        calcScreen.setText("0");
    }

    final Switch.OnCheckedChangeListener switchOnChecked = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                calcTypeTxt.setText(getString(R.string.sci_type));
                standardKeyboard.setVisibility(View.INVISIBLE);
                sciKeyboard.setVisibility(View.VISIBLE);
            }
            else {
                calcTypeTxt.setText(getString(R.string.std_type));
                standardKeyboard.setVisibility(View.VISIBLE);
                sciKeyboard.setVisibility(View.INVISIBLE);
            }
        }
    };


    final Button.OnClickListener onClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            String result = "0";
            switch (v.getId()) {
                case R.id.buttonDot:
                    result = calculator.getDigit('.');
                    break;
                case R.id.button0:
                    result = calculator.getDigit('0');
                    break;
                case R.id.button1:
                    result = calculator.getDigit('1');
                    break;
                case R.id.button2:
                    result = calculator.getDigit('2');
                    break;
                case R.id.button3:
                    result = calculator.getDigit('3');
                    break;
                case R.id.button4:
                    result = calculator.getDigit('4');
                    break;
                case R.id.button5:
                    result = calculator.getDigit('5');
                    break;
                case R.id.button6:
                    result = calculator.getDigit('6');
                    break;
                case R.id.button7:
                    result = calculator.getDigit('7');
                    break;
                case R.id.button8:
                    result = calculator.getDigit('8');
                    break;
                case R.id.button9:
                    result = calculator.getDigit('9');
                    break;
                case R.id.buttonC:
                    result = calculator.getOperation(Operation.C);
                    break;
                case R.id.buttonEqual:
                    result = calculator.getOperation(Operation.EQUAL);
                    break;
                case R.id.buttonSign:
                    result = calculator.getOperation(Operation.SIGN);
                    break;
                case R.id.buttonPlus:
                    result = calculator.getOperation(Operation.PLUS);
                    break;
                case R.id.buttonMinus:
                    result = calculator.getOperation(Operation.MINUS);
                    break;
                case R.id.buttonPercent:
                    result = calculator.getOperation(Operation.PERCENT);
                    break;
                case R.id.buttonMul:
                    result = calculator.getOperation(Operation.MUL);
                    break;
                case R.id.buttonDiv:
                    result = calculator.getOperation(Operation.DIV);
                    break;
                default:
                    break;
            }
            calcScreen.setText(result);

        }
    };


}