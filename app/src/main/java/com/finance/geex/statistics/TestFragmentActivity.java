package com.finance.geex.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 2019/8/20 13:30.
 *
 * @author Geex302
 */
public class TestFragmentActivity extends FragmentActivity{


    private Fragment[] fragments;
    private int mIndex = 0;


    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;


    @BindView(R.id.rb_one)
    RadioButton rbOne;
    @BindView(R.id.rb_two)
    RadioButton rbTwo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        ButterKnife.bind(this);

//        initView();

        initFragment();


//        initListener();


    }

//    private void initListener() {
//
//        rbOne.setOnClickListener(this);
//        rbTwo.setOnClickListener(this);
//
//
//    }

    private void initFragment() {

        Test1Fragment test1Fragment = Test1Fragment.newInstance();
        Test2Fragment test2Fragment = Test2Fragment.newInstance();


        //添加到数组
        fragments = new Fragment[]{test1Fragment, test2Fragment};

        //添加首页

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.frame_layout, test1Fragment).commit();

    }

//    private void initView() {
//
//        frameLayout = findViewById(R.id.frame_layout);
//
//        rbOne = findViewById(R.id.rb_one);
//        rbTwo = findViewById(R.id.rb_two);
//
//    }


    private void setIndexSelected(int index) {

        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();


        Fragment fragment = fragments[index];
        //判断是否添加
        if (!fragment.isAdded()) {
            //首次创建fragment需要传值
            ft.add(R.id.frame_layout, fragment).show(fragment);

        } else {
            ft.show(fragment);
        }


        //隐藏
        ft.hide(fragments[mIndex]);
        ft.commitAllowingStateLoss();
        //再次赋值
        mIndex = index;

    }

    @OnClick({R.id.rb_one,R.id.rb_two})
    public void onClick(View v){

        switch (v.getId()){

            case R.id.rb_one:

                rbOne.setChecked(true);
                rbTwo.setChecked(false);

                rbOne.setTextColor(ContextCompat.getColor(this,R.color.color_0B7CDE));
                rbTwo.setTextColor(ContextCompat.getColor(this,R.color.color_000000));

                setIndexSelected(0);

                break;
            case R.id.rb_two:

                rbOne.setChecked(false);
                rbTwo.setChecked(true);

                rbOne.setTextColor(ContextCompat.getColor(this,R.color.color_000000));
                rbTwo.setTextColor(ContextCompat.getColor(this,R.color.color_0B7CDE));

                setIndexSelected(1);

                break;

        }


    }

}
