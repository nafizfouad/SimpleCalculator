package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv,solutionTv;
    MaterialButton buttonC,buttonBrackOpen,buttonBrackClose;
    MaterialButton buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonAC,buttonDot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonC = new MaterialButton(this);
        buttonBrackOpen = new MaterialButton(this);
        buttonBrackClose = new MaterialButton(this);
        buttonDivide = new MaterialButton(this);
        buttonMultiply = new MaterialButton(this);
        buttonPlus = new MaterialButton(this);
        buttonMinus = new MaterialButton(this);
        buttonEquals = new MaterialButton(this);
        button0 = new MaterialButton(this);
        button1 = new MaterialButton(this);
        button2 = new MaterialButton(this);
        button3 = new MaterialButton(this);
        button4 = new MaterialButton(this);
        button5 = new MaterialButton(this);
        button6 = new MaterialButton(this);
        button7 = new MaterialButton(this);
        button8 = new MaterialButton(this);
        button9 = new MaterialButton(this);
        buttonAC = new MaterialButton(this);
        buttonDot = new MaterialButton(this);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonC,R.id.button_c);
        assignId(buttonBrackOpen,R.id.button_open_bracket);
        assignId(buttonBrackClose,R.id.button_close_bracket);
        assignId(buttonDivide,R.id.button_divide);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(buttonPlus,R.id.button_plus);
        assignId(buttonMinus,R.id.button_minus);
        assignId(buttonEquals,R.id.button_equals);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
        assignId(buttonAC,R.id.button_ac);
        assignId(buttonDot,R.id.button_dot);





    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        MaterialButton button =(MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }
        if(buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
        }else{
            dataToCalculate = dataToCalculate+buttonText;
        }
        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("Err")){
            resultTv.setText(finalResult);
        }

    }

    String getResult(String data) {
        try {
            WebView webView = new WebView(this);
            webView.getSettings().setJavaScriptEnabled(true);

            MyJavaScriptInterface jsInterface = new MyJavaScriptInterface();
            webView.addJavascriptInterface(jsInterface, "JSInterface");

            String script = "JSInterface.setResult(eval('" + data + "'));";
            webView.loadDataWithBaseURL(null, "<script>" + script + "</script>", "text/html", "UTF-8", null);

            return jsInterface.getResult();
        } catch (Exception e) {
            return "Err";
        }
    }

    private class MyJavaScriptInterface {
        private String result;

        @JavascriptInterface
        public void setResult(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }

}