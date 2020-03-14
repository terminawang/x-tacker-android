package com.finance.geex.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.finance.geex.statistics.adapter.TestListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 2019/7/4 14:36.
 *
 * @author Geex302
 */
public class ListActivity extends FragmentActivity{

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    //data
    private LinearLayoutManager linearLayoutManager;
    private TestListAdapter testListAdapter;
    private List<String> data = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);

        initParam();

        initListener();

        initData();


    }

    private void initListener() {

        testListAdapter.setOnItemClickListener(new TestListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(ListActivity.this,"position "+position,Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initData() {

        for (int i = 0; i < 20; i++) {

            data.add("hahahaha"+i);

        }

        testListAdapter.notifyDataSetChanged();

    }

    private void initParam() {

        linearLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(linearLayoutManager);
        //adapter
        testListAdapter = new TestListAdapter(this, data);
        rvList.setAdapter(testListAdapter);

    }


}
