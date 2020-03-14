package com.finance.geex.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.finance.geex.statisticslibrary.http.DefinitionHttpRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 2019/7/3 15:03.
 *
 * @author Geex302
 */
public class SecondActivity extends FragmentActivity implements View.OnClickListener {

    @BindView(R.id.btn_hello1)
    Button btnClick1;
    @BindView(R.id.btn_hello2)
    Button btnClick2;
    @BindView(R.id.btn_hello3)
    Button btnClick3;

    @BindView(R.id.image)
    ImageView imageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ButterKnife.bind(this);

        btnClick2.setOnClickListener(this);

        btnClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnClick1.setText("哈哈哈1");

            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_hello2:

                btnClick2.setText("哈哈哈2");

                break;

        }

    }

    @OnClick({R.id.btn_hello3, R.id.image})
    public void onButterClick(View view) {

        switch (view.getId()) {

            case R.id.btn_hello3:


                List<String> aaaa = new ArrayList<>();
                String s = aaaa.get(10);


                btnClick3 = null;
                btnClick3.setText("哈哈哈3");

                int i = 0;
                int j = 1;


                int a = j / i;
                Log.d("wang", "onButterClick: " + a);

                break;

            case R.id.image:


                break;

        }

    }

}
