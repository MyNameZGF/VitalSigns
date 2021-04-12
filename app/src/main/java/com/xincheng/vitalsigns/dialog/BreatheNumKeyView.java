package com.xincheng.vitalsigns.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xincheng.vitalsigns.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BreatheNumKeyView extends View {
    /**
     * 列
     */
    private static final int TOTAL_COL = 3;
    /**
     * 行
     */
    private static final int TOTAL_ROW = 4;

    private Paint HuiseBgPaint, linePaint, mCutTextPaint;
    private Paint mTextPaint;
    private int mViewWidth; // 键盘宽度
    private int mViewHight; // 键盘高度
    private float mCellWidth, mCellHight; // 单元格宽度、高度
    private Row rows[] = new Row[TOTAL_ROW];
    private Bitmap bitmap; // 删除按钮图片

    public interface CallBack {
        void clickNum(String num);// 回调点击的数字

        void deleteNum();// 回调删除
    }

    private CallBack mCallBack;// 回调

    public void setOnCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    public BreatheNumKeyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    public BreatheNumKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public BreatheNumKeyView(Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        for (int i = 0; i < TOTAL_ROW; i++) {
            if (rows[i] != null)
                rows[i].drawCells(canvas);
        }
    }

    /**
     * 画6条直线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        canvas.drawLine(0, 0, mViewWidth, 0, linePaint);
        canvas.drawLine(0, mCellHight, mViewWidth, mCellHight, linePaint);
        canvas.drawLine(0, mCellHight * 2, mViewWidth, mCellHight * 2, linePaint);
        canvas.drawLine(0, mCellHight * 3, mViewWidth, mCellHight * 3, linePaint);
        canvas.drawLine(mCellWidth, 0, mCellWidth, mViewHight, linePaint);
        canvas.drawLine(mCellWidth * 2, 0, mCellWidth * 2, mViewHight, linePaint);


    }

    /**
     * 初始化画笔
     *
     * @param
     */
    private void init(Context context) {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCutTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setTextSize(2.0f);
        linePaint.setColor(0x90000000);

        HuiseBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        HuiseBgPaint.setStyle(Paint.Style.FILL);
        HuiseBgPaint.setColor(Color.parseColor("#e9e9e9"));

        initDate();
    }

    private void initDate() {
        fillDate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHight = h;
        mCellWidth = mViewWidth / TOTAL_COL;
        mCellHight = mViewHight / TOTAL_ROW;
        mTextPaint.setTextSize(mCellHight / 3);

    }

    private Cell mClickCell = null;
    private float mDownX;
    private float mDownY;

    /*
     *
     * 触摸事件为了确定点击位置的数字
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                int col = (int) (mDownX / mCellWidth);
                int row = (int) (mDownY / mCellHight);
                measureClickCell(col, row);
                break;
            case MotionEvent.ACTION_UP:
                if (mClickCell != null) {
                    // 在抬起后把状态置为默认
                    rows[mClickCell.i].cells[mClickCell.j].state = State.DEFAULT_NUM;
                    mClickCell = null;
                    invalidate();
                }
                break;
        }
        return true;
    }

    /**
     * 测量点击单元格
     *
     * @param col 列
     * @param row 行
     */
    private void measureClickCell(int col, int row) {
//        if(mCallBack==null){
//            mCallBack = new CallBack() {
//                @Override
//                public void clickNum(String num) {
//                    Log.d("CustomNumKeyView", "clickNum: "+num);
//                }
//
//                @Override
//                public void deleteNum() {
//
//                }
//            };
//        }
        if (col >= TOTAL_COL || row >= TOTAL_ROW)
            return;
        if (rows[row] != null) {
            mClickCell = new Cell(rows[row].cells[col].num, rows[row].cells[col].state, rows[row].cells[col].i,
                    rows[row].cells[col].j);
            rows[row].cells[col].state = State.CLICK_NUM;
            if ("-5".equals(rows[row].cells[col].num)) {
                mCallBack.deleteNum();
            } else {
                mCallBack.clickNum(rows[row].cells[col].num);
            }
            invalidate();
        }
    }

    /**
     * 组 以一行为一组
     */
    private class Row {
        public int j;

        Row(int j) {
            this.j = j;
        }

        // 一行3个单元格
        public Cell[] cells = new Cell[TOTAL_COL];

        public void drawCells(Canvas canvas) {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null)
                    cells[i].drawSelf(canvas);
            }

        }
    }

    // 单元格
    private class Cell {
        public String num;
        public State state;
        /**
         * i = 行 j = 列
         */
        public int i;
        public int j;

        public Cell(String num, State state, int i, int j) {
            super();
            this.num = num;
            this.state = state;
            this.i = i;
            this.j = j;
        }

        // 绘制一个单元格 如果颜色需要自定义可以修改
        public void drawSelf(Canvas canvas) {
            switch (state) {
                case CLICK_NUM:
                    // 绘制点击效果灰色背景
                    canvas.drawRect((float) (mCellWidth * j), (float) (mCellHight * i),
                            (float) (mCellWidth * (j + 1)), (float) (mCellHight * (i + 1)), HuiseBgPaint);
                    break;
            }
            if ("-5".equals(num)) {
                // 绘制删除图片
                canvas.drawBitmap(bitmap, (float) (mCellWidth * 2.5 - bitmap.getWidth() / 2), (float) (mCellHight * 3.5 - bitmap.getHeight() / 2), HuiseBgPaint);
            } else {
                if (num == "※") {
                    // 绘制数字
                    mCutTextPaint.setTextSize(mCellHight / 2);
                    canvas.drawText(num, (float) ((j + 0.5) * mCellWidth - mCutTextPaint.measureText(num) / 2),
                            (float) ((i + 0.5) * mCellHight + mCutTextPaint.measureText(num, 0, 1) / 2),
                            mCutTextPaint);
                } else {
                    // 绘制数字
                    canvas.drawText(num, (float) ((j + 0.5) * mCellWidth - mTextPaint.measureText(num) / 2),
                            (float) ((i + 0.5) * mCellHight + mTextPaint.measureText(num, 0, 1) / 2),
                            mTextPaint);
                }
            }


        }
    }

    /**
     * cell的state
     */
    private enum State {
        DEFAULT_NUM, CLICK_NUM;
    }

    private List<String> numKeys = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");

    /**
     * 填充数字
     */
    private void fillDate() {
        int postion = 0;
        for (int i = 0; i < TOTAL_ROW; i++) {
            rows[i] = new Row(i);
            for (int j = 0; j < TOTAL_COL; j++) {
                if (i == 3 && j == 0) {
                    rows[i].cells[j] = new Cell("※", State.DEFAULT_NUM, i, j);
                    continue;
                } else if (i == 3 && j == 2) {
                    rows[i].cells[j] = new Cell("-5", State.DEFAULT_NUM, i, j);
                    continue;
                } else {
                    rows[i].cells[j] = new Cell(numKeys.get(postion), State.DEFAULT_NUM, i, j);
                    postion++;
                }
            }
        }
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.remove);
    }

    /**
     * 随机键盘
     *
     * @param isRandom
     */
    public void setRandomKeyBoard(boolean isRandom) {
        if (isRandom) {
            Collections.shuffle(numKeys);
            initDate();
            invalidate();
        }
    }

}
