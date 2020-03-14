package com.finance.geex.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 2019/8/20 13:50.
 *
 * @author Geex302
 */
public class Test2Fragment extends BaseFragment {

    private Unbinder bind;

    @BindView(R.id.tv_fragment2)
    TextView tvFragment2;


    @Override
    public View initView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.fragment_test2, null);

        bind = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {

    }

    public static Test2Fragment newInstance() {
        Bundle args = new Bundle();
        Test2Fragment fragment = new Test2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.tv_fragment2})
    public void onClick(View view){

        switch (view.getId()){

            case R.id.tv_fragment2:


                tvFragment2.setText("tvFragment2被点击了");

                break;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }



}
