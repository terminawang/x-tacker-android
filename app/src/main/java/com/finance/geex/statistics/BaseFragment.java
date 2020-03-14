package com.finance.geex.statistics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finance.geex.statisticslibrary.mananger.GeexPackage;

/**
 * Created on 2019/8/20 13:46.
 *
 * @author Geex302
 */
public abstract class BaseFragment extends Fragment{

    public Context mActivity;
    private View view;


    private String className;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null) {
            view=initView(inflater);
            initData();
        }else {
            if((ViewGroup)view.getParent()!=null)
                ((ViewGroup)view.getParent()).removeView(view);
        }
        return view;
    }
    public abstract View initView(LayoutInflater inflater);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        className = getClass().getCanonicalName();
    }

    public abstract  void initData();//初始化数据


    @Override
    public void onResume() {
        super.onResume();
        //页面进入
        if (!isHidden() && getUserVisibleHint()) {
            GeexPackage.onEvent(className,"页面进入");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!isHidden() && getUserVisibleHint()) {
            GeexPackage.onEvent(className,"页面消失");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            if(isVisibleToUser){
                GeexPackage.onEvent(className,"页面进入");
            }else {
                GeexPackage.onEvent(className,"页面消失");
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            GeexPackage.onEvent(className,"页面消失");
        }else {
            GeexPackage.onEvent(className,"页面进入");
        }
    }


}
