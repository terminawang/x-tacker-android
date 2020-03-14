package com.finance.geex.statistics.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.finance.geex.statistics.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 2019/7/4 14:40.
 *
 * @author Geex302
 */
public class TestListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<String> data;
    private LayoutInflater layoutInflater;


    //设置点击事件
    public  interface OnItemClickListener{
//        void OnItemClick(View view, int position);
        void OnItemClick(int position);
    }
    //设置item监听回调
    private OnItemClickListener onItemClickListener;
    //供外部的Activity设置回调
    public  void  setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;
    }


    public TestListAdapter(Context context,List<String> data){

        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BorrowMoneyHolder(layoutInflater.inflate(R.layout.adapter_holder_list,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        String s = data.get(position);
        ((BorrowMoneyHolder) holder).tvMoney.setText(s);


        if(onItemClickListener != null){

            ((BorrowMoneyHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(position);
                }
            });

        }



    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    static class BorrowMoneyHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;



        public BorrowMoneyHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);

            itemView.setTag(this);
        }
    }

}
