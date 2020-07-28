package com.example.hw512;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;


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
    private ImageView backgroundImg;
    private ImageView defaultBackgroundImg;
    private EditText editTextFileName;
    private View filenameLayout;
    private static String fileName = null;

    private static final int CLEAR_BACKROUND = 0;
    private static final int DEFAULT_BACKROUND = 1;
    private static final int LOADED_BACKROUND = 2;
    private static int backgroundMode = DEFAULT_BACKROUND;

    public static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 10;
    public static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 11;


    private final Calculator calculator = new Calculator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //получаем статус разрешения на чтение из файлового хранилища
        int permissionStatus = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_READ_STORAGE);
        }


        Toolbar settingsToolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(settingsToolbar);
        getSupportActionBar().setTitle("");

        InitViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_clear_background) {
            backgroundMode = CLEAR_BACKROUND;
            setBackgroundMode();
            return true;
        }

        if (id == R.id.action_default_background) {
            backgroundMode = DEFAULT_BACKROUND;
            setBackgroundMode();
            return true;
        }

        if (id == R.id.action_load_background) {
            filenameLayout.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        defaultBackgroundImg = findViewById(R.id.defaultBackroundImg);
        backgroundImg = findViewById(R.id.backroundImg);
        defaultBackgroundImg.setVisibility(View.INVISIBLE);
        backgroundImg.setVisibility(View.INVISIBLE);
        editTextFileName = findViewById(R.id.editTextFileName);
        Button loadButton = findViewById(R.id.loadButton);
        filenameLayout = findViewById(R.id.filenameLayout);


        loadButton.setOnClickListener(onClickListener);


        loadImage(fileName);

        setBackgroundMode();

        calcTypeSwitch.setOnCheckedChangeListener(switchOnChecked);
        calcScreen.setText("0");
    }

    public void setBackgroundMode() {
        switch (backgroundMode) {
            case LOADED_BACKROUND:
                backgroundImg.setVisibility(View.VISIBLE);
                defaultBackgroundImg.setVisibility(View.INVISIBLE);
                break;
            case CLEAR_BACKROUND:
                backgroundImg.setVisibility(View.INVISIBLE);
                defaultBackgroundImg.setVisibility(View.INVISIBLE);
                break;
            default:
                backgroundImg.setVisibility(View.INVISIBLE);
                defaultBackgroundImg.setVisibility(View.VISIBLE);
                break;
        }
    }


    final Switch.OnCheckedChangeListener switchOnChecked = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                calcTypeTxt.setText(getString(R.string.sci_type));
                standardKeyboard.setVisibility(View.INVISIBLE);
                sciKeyboard.setVisibility(View.VISIBLE);
            } else {
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
                case R.id.loadButton:
                    fileName = editTextFileName.getText().toString();
                    filenameLayout.setVisibility(View.INVISIBLE);
                    if (loadImage(fileName)) {
                        Toast.makeText(MainActivity.this, getString(R.string.file_loaded), Toast.LENGTH_LONG).show();
                        backgroundMode = LOADED_BACKROUND;
                        setBackgroundMode();
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.load_error), Toast.LENGTH_LONG).show();
                    }
                    break;


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

    public boolean loadImage(String fileName) {

        if (fileName == null) return false;
        if (fileName.length() == 0) return false;

        if (isExternalStorageWritable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    fileName);
            Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (b == null) return false;
//            backgroundImg.;
            backgroundImg.setImageBitmap(b);
        } else {
            Toast.makeText(this, "File Error", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }


}