package com.guozy.helloworldapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View .OnClickListener{

    public EditText et1;
    public EditText et2;

    public Button btn;

    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.edit_1);
        et2 = findViewById(R.id.edit_2);

        btn = findViewById(R.id.btn);

        tv = findViewById(R.id.tv);

        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                login();
        }
    }

        public  void login() {
            String s1 = et1.getText().toString();
            String s2 = et2.getText().toString();

            final String serverUrl = "https://152.136.113.21/login.json?username=" + s1 + "&password=" + s2;

            new Thread() {
                public void run() {
                    try {
                        getConnection(new URL(serverUrl));
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            tv.setText("11111111111111111111111");



                        }
                    });

                };
            }.start();




        }





    @SuppressWarnings("static-access")
    private void getConnection(URL url) throws IOException, KeyManagementException, NoSuchAlgorithmException,
            UnrecoverableKeyException, CertificateException, KeyStoreException {
        // TODO Auto-generated method stub
        // https://github.com/shenglintang?tab=repositories
        // http://blog.csdn.net/lin_t_s
        //URL url = new URL("https://github.com/shenglintang?tab=repositories");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5 * 1000);
        connection.setReadTimeout(5 * 1000);
        connection.setRequestMethod("GET");

        // 得到sslContext对象，有两种情况：1.需要安全证书，2.不需要安全证书
        Log.e("geek", "url==" + url);
        Log.e("geek", "是否为https请求==" + (connection instanceof HttpsURLConnection));


        if (connection instanceof HttpsURLConnection) {// 判断是否为https请求
//            SSLContext sslContext = HttpsUtil.getSSLContextWithCer();
			 SSLContext sslContext = HttpsUtil.getSSLContextWithoutCer();
            if (sslContext != null) {
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                ((HttpsURLConnection) connection).setDefaultSSLSocketFactory(sslSocketFactory);
//				((HttpsURLConnection) connection).setHostnameVerifier(HttpsUtil.hostnameVerifier);
            }
        }

        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            Log.e("geek", "responseCode==" + responseCode);
            InputStream is = connection.getInputStream();
            Log.e("geek", "is==" + is);
            is.close();
        }
        connection.disconnect();
    }



}
