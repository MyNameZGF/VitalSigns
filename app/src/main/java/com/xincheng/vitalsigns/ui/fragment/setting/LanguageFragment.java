package com.xincheng.vitalsigns.ui.fragment.setting;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.hjq.language.MultiLanguages;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.LanguageAdapter;
import com.xincheng.vitalsigns.bean.LanguageBean;
import com.xincheng.vitalsigns.ui.activity.SettingActivity;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.SP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageFragment extends BaseFragment {
    private RecyclerView rv_language;
    private LanguageAdapter adapter;
    private List<LanguageBean> datas;
    private static int prePosition;
    private static int fragmentPosition;


    public static BaseFragment newInstance(int fragmentPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.SETTING_DEFUALT_FRAGMENT_POSITION,fragmentPosition);
        LanguageFragment fragment = new LanguageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            fragmentPosition = bundle.getInt(Constant.SETTING_DEFUALT_FRAGMENT_POSITION);
            SP.putData(Constant.SETTING_DEFUALT_FRAGMENT_POSITION,0);//添加这句是为了，设置语言过后恢复默认显示的Fragment为0
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_language;
    }

    @Override
    protected void initView() {
        rv_language = $(R.id.rv_language);
    }

    @Override
    protected void initData() {
        if(adapter == null){
            getDatas();
            adapter = new LanguageAdapter(R.layout.item_language,datas);
            rv_language.setAdapter(adapter);
            adapter.setOnItemClickListener((adapter, view, position)-> {
                if(position != prePosition){
                    datas.get(prePosition).setSelected(false);
                    adapter.notifyItemChanged(prePosition);
                    datas.get(position).setSelected(true);
                    adapter.notifyItemChanged(position);
                    SP.putData(Constant.LANGUAGE_KEY,position);
                    prePosition = position;
                    setLanguage(position);
                }
            });
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置语言
     * @param position
     */
    private void setLanguage(int position) {
        boolean restart;
        switch (position){
            case 0:
                // 简体中文
                restart = MultiLanguages.setAppLanguage(getActivity(), Locale.CHINA);
                break;

            case 1:
                // 繁体中文
                restart = MultiLanguages.setAppLanguage(getActivity(), Locale.TAIWAN);
                break;

            default:
                // 英语
                restart = MultiLanguages.setAppLanguage(getActivity(), Locale.ENGLISH);
                break;
        }
        if(restart){
            // 运用 Activity 跳转动画，在跳转的时候设置一个渐变的效果，相比前面的两种带来的体验是最佳的
            SP.putData(Constant.SETTING_DEFUALT_FRAGMENT_POSITION,fragmentPosition);
            getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
            getActivity().overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_alpha_out);
            getActivity().finish();
        }
    }

    /**
     * 获取数据
     */
    private void getDatas() {
        if(datas == null){
            datas = new ArrayList<>();
        }
        ArrayList<String> menus = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_language)));
        prePosition = (int) SP.getData(Constant.LANGUAGE_KEY,0);//获取上次保存的设置
        for(int i = 0;i<menus.size();i++){
            LanguageBean bean = new LanguageBean(menus.get(i),false,false);
            if(prePosition == i){
                bean.setSelected(true);
            }
            if(i == menus.size()-1){
                bean.setLastItem(true);
            }
            datas.add(bean);
        }
    }
}
