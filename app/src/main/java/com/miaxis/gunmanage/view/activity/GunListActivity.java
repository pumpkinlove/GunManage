package com.miaxis.gunmanage.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.miaxis.gunmanage.R;
import com.miaxis.gunmanage.adapter.GunAdapter;
import com.miaxis.gunmanage.bean.Gun;
import com.miaxis.gunmanage.event.CountGunEvent;
import com.miaxis.gunmanage.event.DownGunEvent;
import com.miaxis.gunmanage.greendao.gen.GunDao;
import com.miaxis.gunmanage.service.CountGunService;
import com.miaxis.gunmanage.service.DownGunService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miaxis.gunmanage.app.Gun_App.gunDao;

public class GunListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_middle)
    TextView tvMiddle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.srl_escort)
    SwipeRefreshLayout srlEscort;
    @BindView(R.id.rv_gun)
    RecyclerView rvGun;

    private List<Gun> gunList;
    private GunAdapter gunAdapter;

    private int curPageNum = 1;
    private int totalPageNum;
    private int gunCount;
    private static final int PAGE_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gun_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
        initView();
        initCount();
    }

    private void initView() {
        tvLeft.setVisibility(View.INVISIBLE);
        tvMiddle.setText("枪支列表");
        srlEscort.setOnRefreshListener(this);
    }

    private void initData() {
        gunList = gunDao.loadAll();
        gunAdapter = new GunAdapter(gunList, this);
        rvGun.setAdapter(gunAdapter);
        rvGun.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initCount() {
        CountGunService.startActionCountGun(this);
    }

    @Override
    public void onRefresh() {
//        curPageNum = 1;
//        DownGunService.startActionDownGun(this, curPageNum+"", PAGE_SIZE+"");
        long localCount = gunDao.count();
        initCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountGunEvent(CountGunEvent e) {
        gunCount = e.getGunCount();
        totalPageNum = gunCount % PAGE_SIZE == 0 ? gunCount / PAGE_SIZE : (gunCount / PAGE_SIZE + 1);

        for (int i=1; i<=totalPageNum; i++) {
            DownGunService.startActionDownGun(this, i+"", PAGE_SIZE+"");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownGunEvent(DownGunEvent e) {
        List<Gun> newGunList = gunDao.queryBuilder().offset((curPageNum - 1) * PAGE_SIZE).limit(PAGE_SIZE).orderDesc(GunDao.Properties.Id).list();
        gunList.clear();
        gunList.addAll(newGunList);
        gunAdapter.notifyDataSetChanged();
        srlEscort.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
