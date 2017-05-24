package com.wcj.myblend.ui.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.wcj.myblend.R;
import com.wcj.myblend.common.BaseActivity;
import com.wcj.myblend.common.Common;
import com.wcj.myblend.common.manager.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.layout_update)
    LinearLayout layoutUpdate;
    @BindView(R.id.btn_loginout)
    Button btnLoginout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setRightVisibility(View.GONE);
        setTitle(getString(R.string.setting));
    }

    @OnClick({R.id.layout_update, R.id.btn_loginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_update:
                break;
            case R.id.btn_loginout:
                loginout();
                break;
        }
    }

    private void loginout() {
        BmobUser.logOut(SettingsActivity.this);   //清除缓存用户对象
        PreferencesManager.getInstance(SettingsActivity.this).put(Common.IS_LOGIN, false);
        SettingsActivity.this.finish();
    }
}
