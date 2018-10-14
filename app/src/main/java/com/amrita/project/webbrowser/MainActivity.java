package com.amrita.project.webbrowser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;




public class MainActivity extends AppCompatActivity implements android.support.v7.widget.PopupMenu.OnMenuItemClickListener{

    String home = "https://www.google.com";                     //home url
    WebView wv;
    EditText edit;
    ProgressBar pb;
    ImageButton forward, back, reload, option;
    Button clear;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        edit  = (EditText)findViewById(R.id.bar);

        wv = findViewById(R.id.webv);

        wv.setWebViewClient(new viewClient()); //setting client for WebView as current browser



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



        home1();  //load home page at startup


        //go = (Button)findViewById(R.id.go);
        forward = (ImageButton)findViewById(R.id.forward);
        back = (ImageButton)findViewById(R.id.back);
        reload = (ImageButton)findViewById(R.id.reload);
        clear = (Button)findViewById(R.id.clear);
        option = (ImageButton)findViewById(R.id.option);
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
                {wv.goForward();edit.setText(wv.getOriginalUrl());}

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wv.canGoBack())
                {wv.goBack();edit.setText(wv.getOriginalUrl());}
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

    public void showPopup(View v){
        android.support.v7.widget.PopupMenu popup = new android.support.v7.widget.PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.toolbar);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.loadHome:
                home1();
                return true;

            case R.id.setHome:
                home =  wv.getOriginalUrl();
                return true;

            default:
                 return false;
        }

    }

    public void home1(){
        wv.loadUrl(home);
        edit.setText(home);
    }

    private class viewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            edit.setText(request.getUrl().toString());
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

}
