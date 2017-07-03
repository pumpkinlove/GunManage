package com.miaxis.gunmanage.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miaxis.gunmanage.R;
import com.miaxis.gunmanage.app.Gun_App;
import com.miaxis.gunmanage.bean.Config;
import com.miaxis.gunmanage.event.ConfigFinishEvent;
import com.miaxis.gunmanage.greendao.gen.ConfigDao;
import com.miaxis.gunmanage.service.DownEscortService;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConfigFragment extends Fragment {

    @BindView(R.id.etv_ip)
    EditText etvIp;
    @BindView(R.id.etv_port)
    EditText etvPort;
    @BindView(R.id.etv_deptno)
    EditText etvDeptno;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    Unbinder unbinder;
    private ConfigDao configDao;
    private Config config;

    public ConfigFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        unbinder = ButterKnife.bind(this, view);
        configDao = Gun_App.getConfigDao();
        initConfigView();
        return view;
    }

    void initConfigView() {
        config = configDao.loadByRowId(1);
        if (config != null) {
            etvIp.setText(config.getIp());
            etvPort.setText(config.getPort()+"");
            etvDeptno.setText(config.getOrgCode());
        }
    }

    @OnClick(R.id.btn_confirm)
    void onSave() {
        try {
            if (config == null) {
                config = new Config();
                config.setId(1);
                config.setIp(etvIp.getText().toString());
                config.setPort(Integer.valueOf(etvPort.getText().toString()));
                config.setOrgCode(etvDeptno.getText().toString());
                configDao.insert(config);
            } else {
                config.setIp(etvIp.getText().toString());
                config.setPort(Integer.valueOf(etvPort.getText().toString()));
                config.setOrgCode(etvDeptno.getText().toString());
                configDao.update(config);
            }
            DownEscortService.startActionDownEscort(getActivity(), config.getOrgCode());
            EventBus.getDefault().post(new ConfigFinishEvent(config));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_cancel)
    void onCancel() {
        EventBus.getDefault().post(new ConfigFinishEvent(config));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
