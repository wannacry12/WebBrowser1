package com.amrita.project.webbrowser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    WebView wv;
    EditText edit;
    ProgressBar pb;
    ImageButton forward, back, reload;
    Button clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv = (WebView)findViewById(R.id.webv);
        edit  = (EditText)findViewById(R.id.bar);

        wv.setWebViewClient(new viewClient());                      //setting client for WebView as current browser

        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if(newProgress == 100)
                    pb.setVisibility(View.GONE);
                else
                    pb.setVisibility(View.VISIBLE);
            }
        });

        WebSettings wsetting = wv.getSettings();
        wsetting.setJavaScriptEnabled(true);                        //enable JavaScript

        String home = "https://www.google.com";                     //home url
        wv.loadUrl(home);

        //go = (Button)findViewById(R.id.go);
        forward = (ImageButton)findViewById(R.id.forward);
        back = (ImageButton)findViewById(R.id.back);
        reload = (ImageButton)findViewById(R.id.reload);
        clear = (Button)findViewById(R.id.clear);
        pb = (ProgressBar)findViewById(R.id.progress);

        /*go.setOnClickListener(new View.OnClickListener() {          //onClick listener for "go"
            @Override
            public void onClick(View view) {
                String temp = edit.getText().toString();
                String url = temp;
                if(!temp.startsWith("https://"))                        //adding https if not typed
                    url = "https://" + temp;
                boolean val = Patterns.WEB_URL.matcher(url).matches();
                if(val == false)
                    wv.loadUrl("https://google.com/search?q=" + temp);
                else wv.loadUrl(url);

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);    //instance of input method
                imm.hideSoftInputFromWindow(edit.getWindowToken(),0);   //setting keyboard to hide
            }
        });*/

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wv.canGoForward())
                    wv.goForward();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wv.canGoBack())
                    wv.goBack();
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.reload();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.clearHistory();
            }
        });

        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {   //listener on the textEdit field
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    String temp = edit.getText().toString();
                    String url = temp;
                    if(!temp.startsWith("https://"))                        //adding https if not typed
                        url = "https://" + temp;
                    boolean val = Patterns.WEB_URL.matcher(url).matches();
                    if(!val)
                        wv.loadUrl("https://google.com/search?q=" + temp);
                    else wv.loadUrl(url);

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);    //instance of input method
                    imm.hideSoftInputFromWindow(edit.getWindowToken(),0);   //setting keyboard to hide
                }
                return false;
            }
        });
    }


}
