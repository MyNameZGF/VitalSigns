package com.xincheng.vitalsigns.utlis;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xincheng.vitalsigns.R;

public class ViewUtils {
    /**
     * 设置TextView的左边drawable
     *
     * @param tv
     * @param imageId drawable id
     */
    public static void setTextLeftDrawable(Context context, TextView tv, int imageId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawable = context.getDrawable(imageId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置TextView的上边drawable
     *
     * @param tv
     * @param imageId drawable id
     */
    public static void setTextTopDrawable(Context context, TextView tv, int imageId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawable = context.getDrawable(imageId);
        drawable.setBounds(0,  0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 设置TextView的右边drawable
     *
     * @param tv
     * @param imageId drawable id
     */
    public static void setTextRightDrawable(Context context, TextView tv, int imageId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawable = context.getDrawable(imageId);
        drawable.setBounds(0,  0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置TextView的下边drawable
     *
     * @param tv
     * @param imageId drawable id
     */
    public static void setTextBottomDrawable(Context context, TextView tv, int imageId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawable = context.getDrawable(imageId);
        drawable.setBounds(0,  0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, null, null, drawable);
    }

    public static void setTextLeft_TopDrawable(Context context, TextView tv, int leftId,int topId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawableLeft = context.getDrawable(leftId);
        Drawable drawableTop= context.getDrawable(topId);
        drawableLeft.setBounds(0,  0,drawableLeft.getMinimumWidth(),drawableLeft.getMinimumHeight());
        drawableTop.setBounds(0,  0,drawableTop.getMinimumWidth(),drawableTop.getMinimumHeight());
        tv.setCompoundDrawables(drawableLeft, drawableTop,null , null);
    }


    public static void setTextRight_TopDrawable(Context context, TextView tv, int rightId,int topId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawableRight = context.getDrawable(rightId);
        Drawable drawableTop= context.getDrawable(topId);
        drawableRight.setBounds(0,  0,drawableRight.getMinimumWidth(),drawableRight.getMinimumHeight());
        drawableTop.setBounds(0,  0,drawableTop.getMinimumWidth(),drawableTop.getMinimumHeight());
        tv.setCompoundDrawables(null, drawableTop,drawableRight , null);
    }


    public static void setTextLeft_BottomDrawable(Context context, TextView tv, int leftId,int bottomId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawableLeft = context.getDrawable(leftId);
        Drawable drawableBottom= context.getDrawable(bottomId);
        drawableLeft.setBounds(0,  0,drawableLeft.getMinimumWidth(),drawableLeft.getMinimumHeight());
        drawableBottom.setBounds(0,  0,drawableBottom.getMinimumWidth(),drawableBottom.getMinimumHeight());
        tv.setCompoundDrawables(drawableLeft, null,null , drawableBottom);
    }


    public static void setTextRight_BottomDrawable(Context context, TextView tv, int rightId,int bottomId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawableRight = context.getDrawable(rightId);
        Drawable drawableBottom= context.getDrawable(bottomId);
        drawableRight.setBounds(0,  0,drawableRight.getMinimumWidth(),drawableRight.getMinimumHeight());
        drawableBottom.setBounds(0,  0,drawableBottom.getMinimumWidth(),drawableBottom.getMinimumHeight());
        tv.setCompoundDrawables(null, null,drawableRight , drawableBottom);
    }


    public static void setTextLeft_Top_BottomDrawable(Context context, TextView tv, int leftId,int topId,int bottomId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawableLeft = context.getDrawable(leftId);
        Drawable drawableTop = context.getDrawable(topId);
        Drawable drawableBottom= context.getDrawable(bottomId);
        drawableLeft.setBounds(0,  0,drawableLeft.getMinimumWidth(),drawableLeft.getMinimumHeight());
        drawableTop.setBounds(0,  0,drawableTop.getMinimumWidth(),drawableTop.getMinimumHeight());
        drawableBottom.setBounds(0,  0,drawableBottom.getMinimumWidth(),drawableBottom.getMinimumHeight());
        tv.setCompoundDrawables(drawableLeft, drawableTop,null , drawableBottom);
    }


    public static void setTextRight_Top_BottomDrawable(Context context, TextView tv, int rightId,int topId,int bottomId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawableRight = context.getDrawable(rightId);
        Drawable drawableTop = context.getDrawable(topId);
        Drawable drawableBottom= context.getDrawable(bottomId);
        drawableRight.setBounds(0,  0,drawableRight.getMinimumWidth(),drawableRight.getMinimumHeight());
        drawableTop.setBounds(0,  0,drawableTop.getMinimumWidth(),drawableTop.getMinimumHeight());
        drawableBottom.setBounds(0,  0,drawableBottom.getMinimumWidth(),drawableBottom.getMinimumHeight());
        tv.setCompoundDrawables(null, drawableTop,drawableRight , drawableBottom);
    }




    public static void setTextRight_LeftDrawable(Context context, TextView tv, int leftId, int rightId) {
        //Drawable drawable = context.getResources().getDrawable(imageId);
        Drawable drawableLeft = context.getDrawable(leftId);
        Drawable drawableRight = context.getDrawable(rightId);
        drawableLeft.setBounds(0,  0,drawableLeft.getMinimumWidth(),drawableLeft.getMinimumHeight());
        drawableRight.setBounds(0,  0,drawableRight.getMinimumWidth(),drawableRight.getMinimumHeight());
        tv.setCompoundDrawables(drawableLeft, null,drawableRight , null);
    }



    public static View getEmptyView(LayoutInflater inflater, int imageId, String emptyContent){
        View view = inflater.inflate(R.layout.empty_view,null);
        ImageView iv_empty = view.findViewById(R.id.iv_empty);
        TextView tv_empty = view.findViewById(R.id.tv_empty);
        iv_empty.setImageResource(imageId);
        tv_empty.setText(emptyContent);
        return view;
    }

    public static View getEmptyView(LayoutInflater inflater,String emptyContent){
        View view = inflater.inflate(R.layout.empty_tv,null);
        TextView tv_empty = view.findViewById(R.id.tv_empty);
        tv_empty.setText(emptyContent);
        return view;
    }

    public static View setEmptyView(LayoutInflater inflater,View addView,int imageId,String emptyContent){
        View view = inflater.inflate(R.layout.empty_view, (ViewGroup) addView.getParent(), false);
        ImageView iv_empty = view.findViewById(R.id.iv_empty);
        TextView tv_empty = view.findViewById(R.id.tv_empty);
        iv_empty.setImageResource(imageId);
        tv_empty.setText(emptyContent);
        return view;
    }



    public static View getEmptyView(LayoutInflater inflater,View addView,int imageId,String emptyContent){
        View view = inflater.inflate(R.layout.empty_view, (ViewGroup) addView.getParent(), false);
        ImageView iv_empty = view.findViewById(R.id.iv_empty);
        TextView tv_empty = view.findViewById(R.id.tv_empty);
        iv_empty.setImageResource(imageId);
        tv_empty.setText(emptyContent);
        return view;
    }

    public static View getEmptyView(LayoutInflater inflater,View addView,String emptyContent){
        View view = inflater.inflate(R.layout.empty_tv, (ViewGroup) addView.getParent(), false);
        TextView tv_empty = view.findViewById(R.id.tv_empty);
        tv_empty.setText(emptyContent);
        return view;
    }

    public static View getEmptyView(LayoutInflater inflater,View addView,int imageId,String emptyContent,int textColor){
        View view = inflater.inflate(R.layout.empty_view, (ViewGroup) addView.getParent(), false);
        ImageView iv_empty = view.findViewById(R.id.iv_empty);
        TextView tv_empty = view.findViewById(R.id.tv_empty);
        iv_empty.setImageResource(imageId);
        tv_empty.setTextColor(textColor);
        tv_empty.setText(emptyContent);
        return view;
    }

    public static View getEmptyView(LayoutInflater inflater,View addView,String emptyContent,int textColor){
        View view = inflater.inflate(R.layout.empty_tv, (ViewGroup) addView.getParent(), false);
        TextView tv_empty = view.findViewById(R.id.tv_empty);
        tv_empty.setTextColor(textColor);
        tv_empty.setText(emptyContent);
        return view;
    }
}
