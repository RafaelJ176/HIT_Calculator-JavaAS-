package com.example.calci_hit;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

    final int RESET = -9999;
    private int finalANS=RESET;
    private boolean flagItx=false, flagIty=false;
    private TextView textViewResult;
    private int firstNumber = RESET;
    private int secondNumber = RESET;
    private String operator = "";
    TextView result;
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

        num0.setOnClickListener(v -> handleNumberClick(0));
        num1.setOnClickListener(v -> handleNumberClick(1));
        num2.setOnClickListener(v -> handleNumberClick(2));
        num3.setOnClickListener(v -> handleNumberClick(3));
        num4.setOnClickListener(v -> handleNumberClick(4));
        num5.setOnClickListener(v -> handleNumberClick(5));
        num6.setOnClickListener(v -> handleNumberClick(6));
        num7.setOnClickListener(v -> handleNumberClick(7));
        num8.setOnClickListener(v -> handleNumberClick(8));
        num9.setOnClickListener(v -> handleNumberClick(9));
        plus.setOnClickListener(v -> handleOperatorClick("+"));
        minus.setOnClickListener(v -> handleOperatorClick("-"));
        div.setOnClickListener(v -> handleOperatorClick("/"));
        dup.setOnClickListener(v -> handleOperatorClick("x"));
        modulu.setOnClickListener(v -> handleOperatorClick("%"));
        abs.setOnClickListener(v -> handleOperatorClick("$"));
        ac.setOnClickListener(v-> resetAC());
        eq.setOnClickListener(v -> handleEqualClick());
        del.setOnClickListener(v -> deleteNumberClick());
        ans.setOnClickListener(v-> prevAns(finalANS));


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
