package com.example.calci_hit;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {
    //Test for myself
    final int RESET = -9999;
    private int finalANS=RESET;
    private boolean flagItx=false, flagIty=false;
    private int firstNumber = RESET;
    private int secondNumber = RESET;
    private String operator = "";
    TextView result;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        boolean flag=false;
        result = findViewById(R.id.textResult);
        result.setText("");
        Button num0 = findViewById(R.id.zero);
        Button num1 = findViewById(R.id.one);
        Button num2 = findViewById(R.id.two);
        Button num3 = findViewById(R.id.three);
        Button num4 = findViewById(R.id.four);
        Button num5 = findViewById(R.id.five);
        Button num6 = findViewById(R.id.six);
        Button num7 = findViewById(R.id.seven);
        Button num8 = findViewById(R.id.eight);
        Button num9 = findViewById(R.id.nine);
        Button plus= findViewById(R.id.plus);
        Button minus= findViewById(R.id.minus);
        Button div= findViewById(R.id.div);
        Button eq= findViewById(R.id.equal);
        Button dup = findViewById(R.id.duplicate);
        Button modulu=findViewById(R.id.mod);
        Button abs = findViewById(R.id.abs);
        Button ac = findViewById(R.id.AC);
        Button ans = findViewById(R.id.point);
        Button del = findViewById(R.id.BULL);


        configureButton(num0, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(0));
        configureButton(num1, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(1));
        configureButton(num2, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(2));
        configureButton(num3, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(3));
        configureButton(num4, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(4));
        configureButton(num5, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(5));
        configureButton(num6, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(6));
        configureButton(num7, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(7));
        configureButton(num8, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(8));
        configureButton(num9, R.drawable.buttongrey, R.drawable.buttonblack, () -> handleNumberClick(9));
        configureButton(plus, R.drawable.btnlightorg, R.drawable.designbutton, () -> handleOperatorClick("+"));
        configureButton(minus, R.drawable.btnlightorg, R.drawable.designbutton, () -> handleOperatorClick("-"));
        configureButton(div, R.drawable.btnlightorg, R.drawable.designbutton, () -> handleOperatorClick("/"));
        configureButton(dup, R.drawable.btnlightorg, R.drawable.designbutton, () -> handleOperatorClick("x"));
        configureButton(eq, R.drawable.btnlightorg, R.drawable.designbutton, this::handleEqualClick);
        configureButton(modulu, R.drawable.btnlightlightgrey, R.drawable.buttongrey, () -> handleOperatorClick("%"));
        configureButton(abs, R.drawable.btnlightlightgrey, R.drawable.buttongrey, () -> handleOperatorClick("$"));
        configureButton(ac, R.drawable.btnlightlightgrey, R.drawable.buttongrey, this::resetAC);
        configureButton(del, R.drawable.buttongrey, R.drawable.buttonblack, this::deleteNumberClick);
        configureButton(ans, R.drawable.buttongrey, R.drawable.buttonblack, () -> prevAns(finalANS));


    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureButton(View button, int pressedDrawable, int defaultDrawable, Runnable onPressAction) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundResource(pressedDrawable);
                    onPressAction.run(); // Execute the provided action
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setBackgroundResource(defaultDrawable);
                    break;
            }
            return true;
        });
    }


    private void prevAns(int finalANS){

        if (finalANS!=RESET && operator.isEmpty()){
            if (firstNumber==RESET){
                firstNumber = finalANS;
                result.setText(String.valueOf(firstNumber));
            }
        } else if (finalANS!=RESET && !operator.isEmpty()) {
            if(secondNumber==RESET){
                secondNumber = finalANS;
                result.setText(String.valueOf(firstNumber)+String.valueOf(operator)+String.valueOf(secondNumber));
            }
        }


    }
    private void deleteNumberClick(){

        if (!operator.isEmpty()){
            if (secondNumber==RESET){
                operator = "";
                result.setText(String.valueOf(firstNumber));
            }
            else {
                secondNumber /=10;
                if (secondNumber == 0){
                    secondNumber = RESET;
                    flagIty = false;
                }
                if(secondNumber == RESET)
                    result.setText(String.valueOf(firstNumber)+String.valueOf(operator));
                else {
                result.setText(String.valueOf(firstNumber)+String.valueOf(operator)+String.valueOf(secondNumber));}
            }
        }
        else {
            if (firstNumber!=RESET){
                firstNumber /=10;
                if (firstNumber==0){
                    firstNumber=RESET;
                    flagItx = false;
                }
                if(firstNumber == RESET)
                    result.setText("0");
                else{result.setText(String.valueOf(firstNumber));}
            }

        }


    }
    private void resetAC(){
        firstNumber = RESET;
        secondNumber = RESET;
        operator = "";
        flagItx = flagIty = false;
        result.setText("0");
    }
    private void handleNumberClick(int num){
        if (operator.isEmpty()){
            if (!flagItx){
            firstNumber = num;
            flagItx=true;
            }
            else if(flagItx){
                firstNumber *=10;
                firstNumber +=num;
            }
            result.setText(String.valueOf(firstNumber));
        }
        else {
            if (!flagIty){
            secondNumber= num;
            flagIty=true;
            } else if (flagIty) {
                secondNumber *=10;
                secondNumber +=num;
            }
            result.setText(String.valueOf(firstNumber)+operator+String.valueOf(secondNumber));
        }
    }
    private void handleOperatorClick(String op) {
        double ifPoint=0;
        if (firstNumber==RESET) {
            result.setText("Please enter a numnber first");
        } else if (op.equals("$")) {
            firstNumber = -firstNumber;
            result.setText(String.valueOf(firstNumber));
        }else{
            if (secondNumber==RESET){
                operator = op;
                result.setText(String.valueOf(firstNumber)+String.valueOf(operator));}
        }

    }
    private void handleEqualClick() {
        double res=0;
        int resultn = 0;
        switch (operator) {
            case "+":
                resultn = firstNumber + secondNumber;
                break;
            case "-":
                resultn = firstNumber - secondNumber;
                break;
            case "x":
                resultn = firstNumber * secondNumber;
                break;
            case "/":
                res = (double) firstNumber / (double) secondNumber;
                finalANS = (int)res;
                break;
            case "%":
                resultn = firstNumber % secondNumber;
                break;
        }
        if (operator.equals("/")){
            result.setText(String.valueOf(res));
        }
        else {
            if (!operator.isEmpty()){
            // Display the result
                result.setText(String.valueOf(resultn));}
            else{resetAC();}
        }

        finalANS = (int)resultn;
        // Reset for a new calculation
        firstNumber = RESET;
        secondNumber = RESET;
        operator = "";
        flagItx=flagIty=false;
    }

    }
