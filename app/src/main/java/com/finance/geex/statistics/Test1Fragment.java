package com.finance.geex.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 2019/8/20 13:45.
 *
 * @author Geex302
 */
public class Test1Fragment extends BaseFragment{

    private Unbinder bind;

    @BindView(R.id.tv_fragment3)
    TextView tvFragment3;


    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_test1, null);
        bind = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {

    }

    public static Test1Fragment newInstance() {
        Bundle args = new Bundle();
        Test1Fragment fragment = new Test1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.tv_fragment3})
    public void onClick(View view){

        switch (view.getId()){

            case R.id.tv_fragment3:


                tvFragment3.setText("tvFragment3被点击了");

                break;

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
