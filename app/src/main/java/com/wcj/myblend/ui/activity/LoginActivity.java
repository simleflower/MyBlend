package com.wcj.myblend.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.wcj.myblend.R;
import com.wcj.myblend.bean.Account;
import com.wcj.myblend.common.BaseActivity;
import com.wcj.myblend.common.Common;
import com.wcj.myblend.common.manager.PreferencesManager;
import com.wcj.myblend.utils.LogUtils;
import com.wcj.myblend.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.internal.Platform;


/**
 * Created by jayli on 2017/5/11 0011.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.login_sina)
    ImageView loginSina;
    @BindView(R.id.login_wechat)
    ImageView loginWechat;
    @BindView(R.id.login_qq)
    ImageView loginQq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setRightVisibility(View.GONE);
        setTitle(getString(R.string.login));
    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.login_sina, R.id.login_wechat, R.id.login_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.login_sina:
                //新浪微博
                loginBySina();
                break;
            case R.id.login_wechat:
                loginByWechat();
                break;
            case R.id.login_qq:
                /*
                * 先使用ShareSDK第三方登录授权，获取用户授权信息
                * 然后根据授权信息，来完成在Bmob平台的一键注册和登录
                * 更新本地缓存账户信息
                * */
                loginByQQ();
                break;
        }
    }

    private void loginByQQ() {
        cn.sharesdk.framework.Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(cn.sharesdk.framework.Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(cn.sharesdk.framework.Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                PlatformDb data = arg0.getDb();
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth("qq", data.getToken(), String.valueOf(data.getExpiresIn()), data.getUserId());
                loginWithAuth(authInfo, data);
            }

            @Override
            public void onCancel(cn.sharesdk.framework.Platform arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        //authorize与showUser单独调用一个即可
        //weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        qq.showUser(null);//授权并获取用户信息
    }

    private void loginByWechat() {
        cn.sharesdk.framework.Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        wechat.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(cn.sharesdk.framework.Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(cn.sharesdk.framework.Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                PlatformDb data = arg0.getDb();
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth("weixin", data.getToken(), String.valueOf(data.getExpiresIn()), data.getUserId());
                loginWithAuth(authInfo, data);
            }

            @Override
            public void onCancel(cn.sharesdk.framework.Platform arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        //authorize与showUser单独调用一个即可
        //weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        wechat.showUser(null);//授权并获取用户信息
    }

    private void loginBySina() {
        cn.sharesdk.framework.Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(cn.sharesdk.framework.Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(cn.sharesdk.framework.Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                PlatformDb data = arg0.getDb();
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth("weibo", data.getToken(), String.valueOf(data.getExpiresIn()), data.getUserId());
                loginWithAuth(authInfo, data);
            }

            @Override
            public void onCancel(cn.sharesdk.framework.Platform arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        //authorize与showUser单独调用一个即可
        //weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        weibo.showUser(null);//授权并获取用户信息
    }


    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo, final PlatformDb data) {
        BmobUser.loginWithAuthData(LoginActivity.this, authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                // TODO Auto-generated method stub
                LogUtils.i(authInfo.getSnsType() + "登陆成功返回:" + userAuth);
                Account user = BmobUser.getCurrentUser(LoginActivity.this, Account.class);
                //更新登录的账户信息
                updateUserInfo(user, data, authInfo);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                ToastUtils.shortToast(LoginActivity.this, "第三方登录失败:" + msg);
            }
        });
    }

    private void updateUserInfo(Account user, PlatformDb data, final BmobUser.BmobThirdUserAuth authInfo) {
        Account newUser = new Account();
        newUser.setPhoto(data.getUserIcon());
        newUser.setSex("男".equals(data.getUserGender()) ? true : false);
        newUser.setUsername(data.getUserName());
        Account bmobUser = BmobUser.getCurrentUser(LoginActivity.this, Account.class);
        newUser.update(LoginActivity.this, bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ToastUtils.shortToast(LoginActivity.this, getString(R.string.update_userinfo_success));
                //保存登录信息到本地
                saveUserInfo(Common.LOGIN_TYPE_THIRD, authInfo);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                ToastUtils.shortToast(LoginActivity.this, getString(R.string.update_userinfo_failed) + msg);
            }
        });
    }


    private void register() {
        String uname = etName.getText().toString();
        String upwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(uname) || TextUtils.isEmpty(upwd)) {
            ToastUtils.shortToast(this, "账户或密码不能为空！");
            return;
        }
        //使用BmobSDK提供的注册功能
        Account user = new Account();
        user.setUsername(uname);
        user.setPassword(upwd);
        user.signUp(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.shortToast(LoginActivity.this, "注册成功，请登录！");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.shortToast(LoginActivity.this, s);
            }
        });
    }

    private void login() {
        final String uname = etName.getText().toString();
        final String upwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(uname) || TextUtils.isEmpty(upwd)) {
            ToastUtils.shortToast(LoginActivity.this, "账户或密码不能为空！");
            return;
        }
        //使用BmobSDK提供的登录功能
        Account user = new Account();
        user.setUsername(uname);
        user.setPassword(upwd);
        user.login(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.shortToast(LoginActivity.this, "登录成功！");
                saveUserInfo(Common.LOGIN_TYPE_NORMAL, null);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.shortToast(LoginActivity.this, s);
                clearInput();
            }
        });
    }

    private void saveUserInfo(int loginType, BmobUser.BmobThirdUserAuth authInfo) {
        /*
         * TODO 把用户的登录信息保存到本地：sp\sqlite：（登录状态，登录类别，登录账户信息）
         * 注意:为了保证数据安全，一般对数据进行加密
         * 通过BmobUser user = BmobUser.getCurrentUser(context)获取登录成功后的本地用户信息
         * 如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息
         * */
        Account user = BmobUser.getCurrentUser(LoginActivity.this, Account.class);
        PreferencesManager preferences = PreferencesManager.getInstance(LoginActivity.this);
        preferences.put(Common.IS_LOGIN, true);
        preferences.put(Common.LOGINTYPE, loginType);
        preferences.put(Common.USER_NAME, user.getUsername());
        preferences.put(Common.USER_PHOTO, user.getPhoto());
        preferences.put(Common.USER_PWD, etPwd.getText().toString());
        if(authInfo != null){
            preferences.put(authInfo);
        }
        LoginActivity.this.finish();
    }

    private void clearInput() {
        etName.setText("");
        etPwd.setText("");
    }

}
