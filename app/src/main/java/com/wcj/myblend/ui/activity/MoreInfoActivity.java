package com.wcj.myblend.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.wcj.myblend.R;
import com.wcj.myblend.bean.Account;
import com.wcj.myblend.bean.ProvinceBean;
import com.wcj.myblend.common.BaseActivity;
import com.wcj.myblend.common.Common;
import com.wcj.myblend.common.manager.PreferencesManager;
import com.wcj.myblend.ui.widget.CircleImageView;
import com.wcj.myblend.utils.CityDataUtil;
import com.wcj.myblend.utils.GlideLoader;
import com.wcj.myblend.utils.ToastUtils;
import com.wcj.myblend.utils.picture.ImageLoader;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by jayli on 2017/5/13 0013.
 * 提交头像、性别，年龄，地址
 */

public class MoreInfoActivity extends BaseActivity {
    @BindView(R.id.layout_photo)
    RelativeLayout layoutPhoto;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.layout_sex)
    LinearLayout layoutSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.layout_age)
    LinearLayout layoutAge;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.layout_address)
    LinearLayout layoutAddress;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.cimg_photo)
    CircleImageView cimgPhoto;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<String> ageItems = new ArrayList<>();
    private ArrayList<String> sexItems = new ArrayList<>();

    private String photoUrl, sex, age, address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfo);
        ButterKnife.bind(this);

        setRightVisibility(View.GONE);
        setTitle(getString(R.string.more_info));
    }


    @Override
    protected void onResume() {
        super.onResume();
        options1Items = CityDataUtil.getProvinceData();
        options2Items = CityDataUtil.getCityData();
        options3Items = CityDataUtil.getAreData();
    }

    @OnClick({R.id.layout_photo, R.id.layout_sex, R.id.layout_age, R.id.layout_address, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_photo:
                selectTOUpload();
                break;
            case R.id.layout_sex:
                showSexPickView();
                break;
            case R.id.layout_age:
                showAgePickView();
                break;
            case R.id.layout_address:
                showCityPickView();
                break;
            case R.id.btn_submit:
                updateUserInfo();
                break;
        }
    }

    /**
     * 更新前需要先登录
     */
    private void updateUserInfo() {
        sex = tvSex.getText().toString();
        age = tvAge.getText().toString();
        address = tvAddress.getText().toString();
        if (!TextUtils.isEmpty(photoUrl)
                && !TextUtils.isEmpty(sex)
                && !TextUtils.isEmpty(age)
                && !TextUtils.isEmpty(address)) {
            Account newUser = new Account();
            newUser.setPhoto(photoUrl);
            newUser.setSex("男".equals(sex) ? true : false);
            newUser.setAge(Integer.valueOf(age));
            newUser.setAddress(address);
            Account bmobUser = BmobUser.getCurrentUser(MoreInfoActivity.this, Account.class);
            newUser.update(MoreInfoActivity.this, bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    ToastUtils.shortToast(MoreInfoActivity.this, getString(R.string.update_userinfo_success));
                    PreferencesManager.getInstance(MoreInfoActivity.this).put(Common.USER_PHOTO, photoUrl);
                    MoreInfoActivity.this.finish();
                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub
                    ToastUtils.shortToast(MoreInfoActivity.this, getString(R.string.update_userinfo_failed) + msg);
                }
            });
        } else {
            ToastUtils.shortToast(MoreInfoActivity.this, getString(R.string.checkinfo));
        }
    }

    private void selectTOUpload() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                .crop(1, 1, 300, 300)
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/six/Pictures")
                .build();
        ImageSelector.open(MoreInfoActivity.this, imageConfig);   // 开启图片选择器
    }

    private void showSexPickView() {
        sexItems.add("男");
        sexItems.add("女");
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvSex.setText(sexItems.get(options1));
            }
        })
                .setTitleText(getString(R.string.select_sex))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(sexItems);//一级选择器
        pvOptions.show();
    }

    private void showAgePickView() {
        for (int i = 1; i < 100; i++) {
            ageItems.add(String.valueOf(i));
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvAge.setText(ageItems.get(options1));
            }
        })
                .setTitleText(getString(R.string.select_age))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(ageItems);//一级选择器
        pvOptions.show();
    }

    private void showCityPickView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                tvAddress.setText(tx);
            }
        })
                .setTitleText(getString(R.string.select_city))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            if (pathList.size() > 0) {
                //由于单选只需要回去第一个数据就好,获取图片URL并上传
                uploadPhotoForURL(pathList.get(0));
            } else {
                ToastUtils.shortToast(MoreInfoActivity.this, getString(R.string.select_pic_failed));
            }
        }
    }

    private void uploadPhotoForURL(String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(MoreInfoActivity.this, new UploadFileListener() {

            @Override
            public void onSuccess() {
                //bmobFile.getFileUrl(context)--返回的上传文件的完整地址
                photoUrl = bmobFile.getFileUrl(MoreInfoActivity.this);
                ImageLoader.getInstance().displayImageTarget(cimgPhoto, photoUrl);
                ToastUtils.shortToast(MoreInfoActivity.this, getString(R.string.upload_photo_success) + photoUrl);
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.shortToast(MoreInfoActivity.this, getString(R.string.upload_failed) + msg);
            }
        });
    }
}
