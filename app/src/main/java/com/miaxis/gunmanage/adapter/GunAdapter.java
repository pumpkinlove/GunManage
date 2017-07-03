package com.miaxis.gunmanage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miaxis.gunmanage.R;
import com.miaxis.gunmanage.bean.Gun;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu.nan on 2017/6/30.
 */

public class GunAdapter extends RecyclerView.Adapter<GunAdapter.ViewHolder> {

    private List<Gun> gunList;
    private Context context;

    public GunAdapter(List<Gun> gunList, Context context) {
        this.gunList = gunList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_gun, parent,
                false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 复用缓存（ListView的优化）
        Gun gun = gunList.get(position);
        if (gun != null) {
            holder.tvGunCode.setText(gun.getCode());
            holder.tvStatus.setText(gun.getStatus());
            holder.tvOpUserName.setText(gun.getOpUserName());
            holder.tvOpDate.setText(gun.getOpDate());
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_gunCode)
        TextView tvGunCode;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_opUserName)
        TextView tvOpUserName;
        @BindView(R.id.tv_opDate)
        TextView tvOpDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
