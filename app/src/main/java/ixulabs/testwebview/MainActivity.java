package ixulabs.testwebview;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProgressBar chromeProgressBar;
    WebView myWebView;
    private ImageView imageView;
    private View main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imagevieww);
        main = findViewById(R.id.webview);
        Button btn = (Button) findViewById(R.id.boton_screenshot);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = Screenshot.takescreenshotOfRootView(imageView);
                Log.d("ENTE","entro");
                imageView.setImageBitmap(b);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);

            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
            }
        }



        myWebView = (WebView) findViewById(R.id.webview);
        //chromeProgressBar = (ProgressBar) findViewById(R.id.progressBarChrome);
        //Settings
        WebSettings webSettings = myWebView.getSettings();

        myWebView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
        }

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);

        //webSettings.setLoadsImagesAutomatically(true);
        //inizialize client

        //load website by URL

        myWebView.loadUrl("https://web-test.ixuah.com/webcam_boton.html");

        //myWebView.loadUrl("https://webcamtests.com/");  LINK CAMERA
        //myWebView.loadUrl("https://simpl.info/videoalpha/");  LINK VIDEO ALPHA
        //register token for notification

        // this.onStart();

        myWebView.setWebChromeClient(new WebChromeClient() {
            // Grant permissions for cam

            @Override
            public void onPermissionRequest(final PermissionRequest request) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }
        });

        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
    }


    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed()
    {
        if(myWebView.canGoBack())
            myWebView.goBack();
        else
            super.onBackPressed();
    }
}