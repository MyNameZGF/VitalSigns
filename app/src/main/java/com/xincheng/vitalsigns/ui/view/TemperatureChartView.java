package com.xincheng.vitalsigns.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.temp.TemperatureBean;
import com.xincheng.vitalsigns.bean.temp.TimeBean;

import java.util.List;

/**
 * 自定义体温单控件
 * 自定义View的整体流程
 * 构造函数
 * 初始化
 * 测量                       onMeasure()
 * 控件大小改变时             onSizeChanged()
 * 确定子控件布局             onLayout()
 * 绘制                      onDraw()
 * 提供接口                  invalidate()
 * <p>
 * 自定义ViewGroup流程
 * <p>
 * <p>
 * <p>
 * 自定View流程
 * TODO 先优化封装样式2
 * TODO 在绘制的时候在根据是样式1还是样式2，在调用各自的方法，这样思路稍微清晰一些
 * TODO 数据传递格式需要梳理
 * TODO 上面三步做了之后后续有时间在优化合并封装。
 * Observer<? super Object>
 */
public class TemperatureChartView extends View {
    //====================================以下是控件属性默认值======================================
    private static final int DEFULAT_BG_COLOR = Color.WHITE;
    private static final int DEFULAT_TEXT_SIZE = 24;
    private static final int DEFULAT_TEXT_SIZE_2_LEVEL = 20;
    private static final int DEFULAT_TEXT_COLOR = Color.BLACK;
    private static final int DEFULAT_COLUMN_WIDTH = 150;
    private static final int DEFULAT_RIGHT_COLUMN_WIDTH = 75;
    private static final int DEFULAT_LEFT_COLUMN_WIDTH = 150;
    private static final int DEFULAT_TITLE_ROW_HEIGHT = 100;
    private static final int DEFULAT_TITLE_ROW_COUNT = 3;
    private static final int DEFULAT_TITLE_COLUMN_COUNT = 7;
    private static final int DEFULAT_CENTER_CELL_ROW_HEIGHT = 150;
    private static final int DEFULAT_CENTER_CELL_COLUMN_COUNT = 10;
    private static final int DEFULAT_CENTER_CELL_TITLE_1_LEVEL_COLUMN_COUNT = 2;
    private static final int DEFULAT_CENTER_CELL_TITLE_2_LEVEL_COLUMN_COUNT = 6;
    private static final int DEFULAT_CENTER_CELL_ROW_CHILD = 5;
    private static final int DEFULAT_BOTTOM_ROW_HEIGHT = 60;
    private static final int DEFULAT_BOTTOM_COLUMN_COUNT = 8;
    private static final float DEFUALT_CELL_LINE_SIZE = 2.0f;
    private static final float DEFUALT_CELL_LINE_SIZE_2_LEVEL = 1.0f;
    private static final float DEFUALT_PAINT_STROKE_WIDTH = 1.0f;
    //===================================以上是控件属性默认值======================================
    //===================================以下控件属性==============================================
    /**
     * 标题背景
     */
    private int titleBg;
    /**
     * 标题大小
     */
    private int titleTextSize;
    /**
     * 标题颜色
     */
    private int titleTextColor;
    /**
     * 标题高度
     */
    private int titleRowHeight;
    /**
     * 每一列标题的宽度
     */
    private int titleColumnWidth;
    /**
     * 有几列标题
     */
    private int titleColumnCount;
    /**
     * 有几行标题
     */
    private int titleRowCount;


    /**
     * 左侧背景
     */
    private int leftBg;
    /**
     * 左侧文字大小
     */
    private int leftTextSize;
    /**
     * 左侧文字颜色
     */
    private int leftTextColor;
    /**
     * 左侧一列的宽度
     */
    private int leftColumnWidth;


    /**
     * 右侧背景
     */
    private int rightBg;
    /**
     * 右侧文字的大小
     */
    private int rightTextSize;
    /**
     * 右侧文字的颜色
     */
    private int rightTextColor;
    /**
     * 右侧一列的宽度
     */
    private int rightColumnWidth;


    /**
     * 中间格子一级级标题(上午、下午）的大小
     */
    private int centerCellTitleSize;
    /**
     * 中间格子一级级标题(上午、下午）的颜色
     */
    private int centerCellTitleColor;
    /**
     * 中间格子二级标题(时间段）的大小
     */
    private int centerCellTitle2LevelSize;
    /**
     * 中间格子二级标题(时间段）的颜色
     */
    private int centerCellTitle2LevelColor;
    /**
     * 中间格子一行的高度
     */
    private int centerCellRowHeight;
    /**
     * 中间格子一个有多少行
     */
    private int centerCellRowCount;
    /**
     * 中间格子一级标题有多少列
     */
    private int centerCellColumn1LevelCount;
    /**
     * 中间格子二级标题有多少列
     */
    private int centerCellColumn2LevelCount;
    /**
     * 中间格子一行有多少个小格子
     */
    private int centerCellRowChild;


    /**
     * 底部背景
     */
    private int bottomBg;
    /**
     * 底部每一行的高度
     */
    private int bottomRowHeight;
    /**
     * 底部一共有多少行
     */
    private int bottomRowCount;
    /**
     * 底部文本大小
     */
    private int bottomTextSize;
    /**
     * 底部文本颜色
     */
    private int bottomTextColor;


    /**
     * 一级表格线的大小
     */
    private float cellLineSize;
    /**
     * 一级表格线的颜色
     */
    private int cellLineColor;
    /**
     * 二级表格线的大小
     */
    private float cellLine2LevelSize;
    /**
     * 二级表格线的颜色
     */
    private int cellLine2LevelColor;
    //===================================以上是控件属性==============================================

    /**
     * 网格线画笔
     */
    private Paint cellLinePaint;


    /**
     * 当前选中的那一列网格画笔
     */
    private Paint selectCellLinePaint;
    private int selectCellColor;
    private float selectCellLineSize;


    /**
     * 二级网格线画笔
     */
    private Paint cellLine2LevelPaint;


    /**
     * 背景画笔
     */
    private Paint bgPaint;

    /**
     * 绘制折线图的画笔
     */
    private Paint tempAndpluseLinePaint;

    /**
     * 左侧文字画笔
     */
    private TextPaint leftTextPaint;
    private float leftTextMeasureHeight;
    private float leftTextBottomMeasureHeight;


    /**
     * 右侧文字画笔
     */
    private TextPaint rightTextPaint;
    private float rightTextMeasureHeight;
    private float rightTextBottomMeasureHeight;

    /**
     * 标题文字画笔
     */
    private TextPaint titleTextPaint;
    private float titleTextMeasureHeight;
    private float titleTextBottomMeasureHeight;

    /**
     * 表格中间一级标题画笔
     */
    private TextPaint centerCellTitle1LevelTextPaint;
    private float centerCellTitle1LevelTextMeasureHeight;
    private float centerCellTitle1LevelTextButtomMeasureHeight;

    /**
     * 表格中间二级标题画笔
     */
    private TextPaint centerCellTitle1Leve2TextPaint;
    private float centerCellTitle2LevelTextMeasureHeight;
    private float centerCellTitle2LevelTextBottomMeasureHeight;

    /**
     * 底部标题画笔
     */
    private TextPaint bottomTitlePain;
    private float bottomTitleTextMeasureHeight;
    private float bottomTitleTextBottomMeasureHeight;

    /**
     * 文字的宽度
     */
    private float textMeasureWidth;


    private Context context;

    /**
     * 控件的宽度
     */
    private int mWidth;

    /**
     * 控件的高度
     */
    private int mHeight;

    /**
     * 每列的宽度
     */
    private float columnWidth;

    /**
     * 标题行的总高度
     */
    private int titleRowHeightCount;

    /**
     * 大格子行的总高度
     */
    private int cellRowHightCount;


    /**
     * 底部总高度
     */
    private int bottomRowHegihtCount;


    /**
     * 总共有多少列
     */
    private static float totalColumn;


    /**
     * 左边有多少列
     */
    private static int leftColumn = 1;
    /**
     * 右边有多少列
     */
    private static int rightColumn = 0;

    /**
     * 总共有多少行
     */
    private static int totalRow;

    /**
     * 表头的标题
     */
    private String[][] arrayTitles;

    /**
     * 时间那一行的数据
     */
    private TimeBean timeBean;

    /**
     * 每个小格子的宽度
     */
    private float cellChildWidth;

    /**
     * 每个小格子的高度
     */
    private int cellChildHeight;

    /**
     * 底部左侧的标题
     */
    private List<String> bottomDatas;

    /**
     * 控件的数据
     */
    private TemperatureBean bean;

    private static int pMax = 180;//脉搏最多值
    private static int tMax = 42;//温度最大值

    /**
     * 当前选中
     */
    private int selectIndex;

    public TemperatureChartView(Context context) {
        this(context, null);
    }

    public TemperatureChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemperatureChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    /**
     * 初始化 属性
     *
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle) {
        loadAttr(attrs, defStyle);
        init();
    }

    private void loadAttr(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TemperatureChartView, defStyle, 0);
        int n = a.getIndexCount();//这种方式只要在xml中设置了属性才会调用，没有设置相应的属性就不会去调用
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.TemperatureChartView_tep_title_bg://标题背景
                    titleBg = a.getColor(attr, DEFULAT_BG_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_title_text_size://标题文字大小
                    titleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFULAT_TEXT_SIZE, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TemperatureChartView_tep_title_text_color://标题文字颜色
                    titleTextColor = a.getColor(attr, DEFULAT_TEXT_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_title_row_height://标题一行的高度
                    titleRowHeight = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFULAT_TITLE_ROW_HEIGHT, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TemperatureChartView_tep_title_column_width://标题一列的宽度
                    titleColumnWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFULAT_COLUMN_WIDTH, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TemperatureChartView_tep_title_column_count://标题一共有多少列
                    titleColumnCount = a.getInt(attr, DEFULAT_TITLE_COLUMN_COUNT);
                    break;
                case R.styleable.TemperatureChartView_tep_title_row_count://标题一共有多少行
                    titleRowCount = a.getInt(attr, DEFULAT_TITLE_ROW_COUNT);
                    break;


                case R.styleable.TemperatureChartView_tep_left_bg://左侧背景
                    leftBg = a.getColor(attr, DEFULAT_BG_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_left_text_size://左侧文字大小
                    leftTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFULAT_TEXT_SIZE, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TemperatureChartView_tep_left_text_color://左侧文字颜色
                    leftTextColor = a.getColor(attr, DEFULAT_TEXT_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_left_column_width://左侧一列的宽度
                    leftColumnWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFULAT_LEFT_COLUMN_WIDTH, getResources().getDisplayMetrics()));
                    break;


                case R.styleable.TemperatureChartView_tep_right_bg://右侧背景
                    rightBg = a.getColor(attr, DEFULAT_BG_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_right_text_size://右侧文字大小
                    rightTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFULAT_TEXT_SIZE, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TemperatureChartView_tep_right_text_color://右侧文字颜色
                    rightTextColor = a.getColor(attr, DEFULAT_TEXT_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_right_column_width://右侧一列的宽度
                    rightColumnWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFULAT_RIGHT_COLUMN_WIDTH, getResources().getDisplayMetrics()));
                    break;


                case R.styleable.TemperatureChartView_tep_center_cell_title_size://中间表格一级标题大小
                    centerCellTitleSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFULAT_TEXT_SIZE, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TemperatureChartView_tep_center_cell_title_color://中间表格一级标题颜色
                    centerCellTitleColor = a.getColor(attr, DEFULAT_TEXT_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_center_cell_title_2_level_size://中间表格二级标题大小
                    centerCellTitle2LevelSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFULAT_TEXT_SIZE_2_LEVEL, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TemperatureChartView_tep_center_cell_title_2_level_color://中间表格二级标题颜色
                    centerCellTitle2LevelColor = a.getColor(attr, DEFULAT_TEXT_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_center_cell_row_height://中间部分一行的高度
                    centerCellRowHeight = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFULAT_CENTER_CELL_ROW_HEIGHT, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TemperatureChartView_tep_center_cell_row_count://中间部分一共有多少行
                    centerCellRowCount = a.getInt(attr, DEFULAT_CENTER_CELL_COLUMN_COUNT);
                    break;
                case R.styleable.TemperatureChartView_tep_center_cell_child_1_level_column_count://中间部分一级标题有多少行
                    centerCellColumn1LevelCount = a.getInt(attr, DEFULAT_CENTER_CELL_TITLE_1_LEVEL_COLUMN_COUNT);
                    break;
                case R.styleable.TemperatureChartView_tep_center_cell_child_2_level_column_count://中间部分一级标题有多少行
                    centerCellColumn2LevelCount = a.getInt(attr, DEFULAT_CENTER_CELL_TITLE_2_LEVEL_COLUMN_COUNT);
                    break;
                case R.styleable.TemperatureChartView_tep_center_cell_row_child://中间部分一级标题有多少行
                    centerCellRowChild = a.getInt(attr, DEFULAT_CENTER_CELL_ROW_CHILD);
                    break;


                case R.styleable.TemperatureChartView_tep_cell_line_size://一级网格线条大小
                    cellLineSize = a.getFloat(attr, DEFUALT_CELL_LINE_SIZE);
                    break;
                case R.styleable.TemperatureChartView_tep_cell_line_color://一级网格线条颜色
                    cellLineColor = a.getColor(attr, DEFULAT_TEXT_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_cell_line_2_level_size://二级网格线条大小
                    cellLine2LevelSize = a.getFloat(attr, DEFUALT_CELL_LINE_SIZE_2_LEVEL);
                    break;
                case R.styleable.TemperatureChartView_tep_cell_line_2_level_color://二级网格线条颜色
                    cellLine2LevelColor = a.getColor(attr, DEFULAT_TEXT_COLOR);
                    break;


                case R.styleable.TemperatureChartView_tep_bottom_row_height://底部一行的高度
                    bottomRowHeight = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFULAT_BOTTOM_ROW_HEIGHT, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TemperatureChartView_tep_bottom_row_count://底部一共有多少行
                    bottomRowCount = a.getInt(attr, DEFULAT_BOTTOM_COLUMN_COUNT);
                    break;
                case R.styleable.TemperatureChartView_tep_bottom_bg:
                    bottomBg = a.getColor(attr, DEFULAT_BG_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_bottom_text_color:
                    bottomTextColor = a.getColor(attr, DEFULAT_TEXT_COLOR);
                    break;
                case R.styleable.TemperatureChartView_tep_bottom_text_size:
                    bottomTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFULAT_TEXT_SIZE, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
    }

    /**
     * 初始化网格线画笔
     */
    private void initCellLinePaint() {
        cellLinePaint = new Paint();
        cellLinePaint.setStyle(Paint.Style.FILL);
        cellLinePaint.setAntiAlias(true);
        cellLinePaint.setColor(cellLineColor);
        cellLinePaint.setStrokeWidth(cellLineSize);
    }


    /**
     * 初始化当前选择的网格线画笔
     */
    private void initSelectCellLinePaint() {
        selectCellLinePaint = new Paint();
        selectCellLinePaint.setStyle(Paint.Style.FILL);
        selectCellLinePaint.setAntiAlias(true);
        selectCellLinePaint.setColor(selectCellColor);
        selectCellLinePaint.setStrokeWidth(selectCellLineSize);
    }

    /**
     * 初始化网格线画笔
     */
    private void initCellLine2LevelPaint() {
        cellLine2LevelPaint = new Paint();
        cellLine2LevelPaint.setStyle(Paint.Style.FILL);
        cellLine2LevelPaint.setAntiAlias(true);
        cellLine2LevelPaint.setColor(cellLine2LevelColor);
        cellLine2LevelPaint.setStrokeWidth(cellLine2LevelSize);
    }


    /**
     * 初始化背景画笔
     */
    private void initBgPaint() {
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(DEFULAT_BG_COLOR);
        bgPaint.setStrokeWidth(DEFUALT_PAINT_STROKE_WIDTH);
    }

    private void initTempAndpluseLinePaintPaint(){
        tempAndpluseLinePaint = new Paint();
        tempAndpluseLinePaint.setStyle(Paint.Style.FILL);
        tempAndpluseLinePaint.setStrokeWidth(4.0f);
        tempAndpluseLinePaint.setAntiAlias(true);
    }

    /**
     * 初始化左侧文字画笔
     */
    private void initLeftTextPaint() {
        leftTextPaint = new TextPaint();
        leftTextPaint.setStyle(Paint.Style.FILL);
        leftTextPaint.setAntiAlias(true);
        leftTextPaint.setTextSize(leftTextSize);
        leftTextPaint.setColor(leftTextColor);
        leftTextPaint.setStrokeWidth(DEFUALT_PAINT_STROKE_WIDTH);
        Paint.FontMetrics fontMetrics = leftTextPaint.getFontMetrics();
        leftTextMeasureHeight = fontMetrics.descent - fontMetrics.ascent;
        leftTextBottomMeasureHeight = fontMetrics.bottom;
    }


    /**
     * 初始化右侧文字画笔
     */
    private void initRightTextPaint() {
        rightTextPaint = new TextPaint();
        rightTextPaint.setStyle(Paint.Style.FILL);
        rightTextPaint.setAntiAlias(true);
        rightTextPaint.setTextSize(rightTextSize);
        rightTextPaint.setColor(rightTextColor);
        rightTextPaint.setStrokeWidth(DEFUALT_PAINT_STROKE_WIDTH);
        Paint.FontMetrics fontMetrics = rightTextPaint.getFontMetrics();
        rightTextMeasureHeight = fontMetrics.descent - fontMetrics.ascent;
        rightTextBottomMeasureHeight = fontMetrics.bottom;
    }

    /**
     * 初始化右侧文字画笔
     */
    private void initBottomTextPaint() {
        bottomTitlePain = new TextPaint();
        bottomTitlePain.setStyle(Paint.Style.FILL);
        bottomTitlePain.setAntiAlias(true);
        bottomTitlePain.setTextSize(bottomTextSize);
        bottomTitlePain.setColor(bottomTextColor);
        bottomTitlePain.setStrokeWidth(DEFUALT_PAINT_STROKE_WIDTH);
        Paint.FontMetrics fontMetrics = bottomTitlePain.getFontMetrics();
        bottomTitleTextMeasureHeight = fontMetrics.descent - fontMetrics.ascent;
        bottomTitleTextBottomMeasureHeight = fontMetrics.bottom;
    }


    /**
     * 初始化标题文字画笔
     */
    private void initTitleTextPaint() {
        titleTextPaint = new TextPaint();
        titleTextPaint.setStyle(Paint.Style.FILL);
        titleTextPaint.setAntiAlias(true);
        titleTextPaint.setTextSize(titleTextSize);
        titleTextPaint.setColor(titleTextColor);
        titleTextPaint.setStrokeWidth(DEFUALT_PAINT_STROKE_WIDTH);
        Paint.FontMetrics fontMetrics = titleTextPaint.getFontMetrics();
        titleTextMeasureHeight = fontMetrics.descent - fontMetrics.ascent;
        titleTextBottomMeasureHeight = fontMetrics.bottom;
    }


    /**
     * 初始化表格中间一级标题画笔
     */
    private void initCenterCellTitle1LevelTextPaint() {
        centerCellTitle1LevelTextPaint = new TextPaint();
        centerCellTitle1LevelTextPaint.setStyle(Paint.Style.FILL);
        centerCellTitle1LevelTextPaint.setAntiAlias(true);
        centerCellTitle1LevelTextPaint.setTextSize(centerCellTitleSize);
        centerCellTitle1LevelTextPaint.setColor(centerCellTitleColor);
        centerCellTitle1LevelTextPaint.setStrokeWidth(DEFUALT_PAINT_STROKE_WIDTH);
        Paint.FontMetrics fontMetrics = centerCellTitle1LevelTextPaint.getFontMetrics();
        centerCellTitle1LevelTextMeasureHeight = fontMetrics.descent - fontMetrics.ascent;
        centerCellTitle1LevelTextButtomMeasureHeight = fontMetrics.bottom;
    }


    /**
     * 初始化表格中间二级标题画笔
     */
    private void initCenterCellTitle2LevelTextPaint() {
        centerCellTitle1Leve2TextPaint = new TextPaint();
        centerCellTitle1Leve2TextPaint.setStyle(Paint.Style.FILL);
        centerCellTitle1Leve2TextPaint.setAntiAlias(true);
        centerCellTitle1Leve2TextPaint.setTextSize(centerCellTitle2LevelSize);
        centerCellTitle1Leve2TextPaint.setColor(centerCellTitle2LevelColor);
        centerCellTitle1Leve2TextPaint.setStrokeWidth(DEFUALT_PAINT_STROKE_WIDTH);
        Paint.FontMetrics fontMetrics = centerCellTitle1Leve2TextPaint.getFontMetrics();
        centerCellTitle2LevelTextMeasureHeight = fontMetrics.descent - fontMetrics.ascent;
        centerCellTitle2LevelTextBottomMeasureHeight = fontMetrics.bottom;
    }


    /**
     * 初始化
     */
    private void init() {
        selectCellColor = context.getResources().getColor(R.color.colorPrimary);
        selectCellLineSize = DEFUALT_CELL_LINE_SIZE;
        initCellLinePaint();
        initSelectCellLinePaint();
        initCellLine2LevelPaint();
        initBgPaint();
        initLeftTextPaint();
        initRightTextPaint();
        initBottomTextPaint();
        initTitleTextPaint();
        initCenterCellTitle1LevelTextPaint();
        initCenterCellTitle2LevelTextPaint();
        initTempAndpluseLinePaintPaint();

        totalColumn = leftColumn + titleColumnCount + rightColumn;
        totalRow = titleRowCount + centerCellRowCount + bottomRowCount;

        titleRowHeightCount = titleRowHeight * titleRowCount;

        cellRowHightCount = centerCellRowHeight * centerCellRowCount;

        bottomRowHegihtCount = bottomRowHeight * bottomRowCount;

        mHeight = titleRowHeightCount + cellRowHightCount + bottomRowHegihtCount;

        arrayTitles = new String[titleRowCount][titleColumnCount];

        selectIndex = -1;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //TODO 这里后续有时间在继续优化
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);      //取出宽度的确切数值
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
        setMeasuredDimension(widthSize, mHeight > heightSize ? mHeight : heightSize);
        //下面这些一般是做一些默认设置的，比如说超出啥条件就设置成默认的宽度高度
       /* int widthSize = MeasureSpec.getSize(widthMeasureSpec);      //取出宽度的确切数值
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);      //取出宽度的测量模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);    //取出高度的测量模式

        int width = 0;
        int height = 0;

        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED://默认值，父控件没有给子view任何限制，子View可以设置为任意大小
            case MeasureSpec.AT_MOST://表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
                width = leftColumnWidth + titleColumnWidth * titleColumnCount + rightColumnWidth;
                break;

            case MeasureSpec.EXACTLY://表示父控件已经确切的指定了子View的大小
                width = widthSize;
                break;
        }

        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED://默认值，父控件没有给子view任何限制，子View可以设置为任意大小
            case MeasureSpec.AT_MOST://表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
                height = titleRowHeight * titleRowCount + centerCellRowHeight * cellRowHightCount + bottomRowHeight * bottomRowCount;
                break;

            case MeasureSpec.EXACTLY://表示父控件已经确切的指定了子View的大小
                height = heightSize;
                break;
        }
        //测量完成之后，设置实际的宽高
        setMeasuredDimension(width, height);*/
        //super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    /**
     * @param w    现在的宽度
     * @param h    现在的高度
     * @param oldw 上一次的宽度
     * @param oldh 上一次的高度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h > mHeight ? h : mHeight;
    }

    /**
     * @param changed 是否改变
     * @param left    View左侧距父View左侧的距离,getLeft()
     * @param top     View顶部距父View顶部的距离,getTop()
     * @param right   View右侧距父View左侧的距离,getRight()
     * @param bottom  View底部距父View顶部的距离,getBottom()
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //在自定义ViewGroup中，onLayout一般是循环取出子View，然后经过计算得出各个子View位置的坐标值，然后用以下函数设置子View位置。
        //child.onLayout(left,top,right,bottom)
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //TODO 这里后续需要考虑，表格里面的文本内容大于表格宽度的时候，让他换行吗？还是整体缩放
        columnWidth = mWidth / totalColumn;
        cellChildWidth = columnWidth / centerCellColumn2LevelCount;
        cellChildHeight = centerCellRowHeight / centerCellRowChild;
        Log.e("see", "控件宽度:" + mWidth + ";控件高度:" + mHeight + ";每个表格的宽度:" + columnWidth);
        drawBg(canvas);
        drawCell(canvas);
        draw2LevelCell(canvas);
        drawSelectCell(canvas);
        drawTitle(canvas);
        drawTimeRow(canvas);
        drawLeftTitle(canvas);
        //drawTemperature(canvas);
        drawBottomTitle(canvas);
        drawPulseLine(canvas);
        //drawTemperatureLine(canvas);
    }


    /**
     * 绘制温度计
     * TODO 这里还需要调整，不是想要的效果
     * 计算公式: C=(F-32) * 5/9
     * F = c / 5 *9 +32;
     * F = 5 / 9 * C
     *
     * @param canvas
     */
    private void drawTemperature(Canvas canvas) {
        String str1 = "体温";
        String str2 = "华氏";
        String str3 = "摄氏";
        String str4 = "( °F )";
        String str5 = "( °C )";

        //体温位置
        float start = centerCellRowHeight - (centerCellRowHeight - leftTextMeasureHeight * 3) / 2;
        textMeasureWidth = leftTextPaint.measureText(str1);
        canvas.drawText(str1, columnWidth / 2 - textMeasureWidth / 2, titleRowHeightCount + centerCellRowHeight + start + leftTextMeasureHeight, leftTextPaint);

        textMeasureWidth = leftTextPaint.measureText(str2);
        canvas.drawText(str2, 10, titleRowHeightCount + centerCellRowHeight + start + 2 * leftTextMeasureHeight, leftTextPaint);

        textMeasureWidth = leftTextPaint.measureText(str3);
        canvas.drawText(str3, columnWidth - textMeasureWidth - 10, titleRowHeightCount + centerCellRowHeight + start + 2 * leftTextMeasureHeight, leftTextPaint);

        textMeasureWidth = leftTextPaint.measureText(str4);
        canvas.drawText(str4, 10, titleRowHeightCount + centerCellRowHeight + start + 3 * leftTextMeasureHeight, leftTextPaint);

        textMeasureWidth = leftTextPaint.measureText(str5);
        canvas.drawText(str5, columnWidth - textMeasureWidth - 10, titleRowHeightCount + centerCellRowHeight + start + 3 * leftTextMeasureHeight, leftTextPaint);
        //这里下面绘制刻度顶部的横线时需要
        float topCTextMeasureWidth = 0.0f;
        String strC = "";
        for (int r = 0; r < 8; r++) {//摄氏度
            strC = String.valueOf(42 - r);
            textMeasureWidth = leftTextPaint.measureText(strC);
            if(r == 0){
                topCTextMeasureWidth = textMeasureWidth;
            }
            canvas.drawText(strC, columnWidth - textMeasureWidth - 10, titleRowHeightCount + (r + 2) * centerCellRowHeight - leftTextMeasureHeight / 2 - leftTextBottomMeasureHeight, leftTextPaint);
        }

        //1华氏度的宽度
        float f_width = 5 * centerCellRowHeight / 9;
        //华氏刻度的最小宽度
        float f_child_width = f_width / 5;
        //华氏刻度的X起点
        float startLineX = 10 + textMeasureWidth + 10;
        //华氏刻度的X钟点
        float endLineX = startLineX + cellChildWidth * 1.5f;
        float lineY = 0.0f;
        float lineChildY = 0.0f;
        //画纵线，竖直方式起点y坐标
        float starLineY_v = 0.0f;
        float starLineX_v = 0.0f;
        for (int r = 0; r <= 13; r++) {//摄氏度
            //文本
            strC = String.valueOf(94 + r);
            //文字的宽度
            textMeasureWidth = leftTextPaint.measureText(strC);
            //绘制华氏度
            canvas.drawText(strC, 10, titleRowHeightCount + centerCellRowCount * centerCellRowHeight + f_width + leftTextMeasureHeight / 2 - leftTextBottomMeasureHeight, leftTextPaint);
            lineY = titleRowHeightCount + centerCellRowCount * centerCellRowHeight -  + f_width -  r * f_width;
            //绘制整数华氏度刻度线，如：94F,95F,96F等
            canvas.drawLine( startLineX , lineY, endLineX  ,lineY,  cellLinePaint);
            if( r == 0){
                starLineX_v = endLineX;
                starLineY_v = lineY;
            }
            if(r == 13){
                canvas.drawLine(endLineX,titleRowHeightCount + centerCellRowHeight * 2,columnWidth - topCTextMeasureWidth - 20,titleRowHeightCount + centerCellRowHeight * 2,cellLinePaint);
            }else{//绘制华氏度小数部分刻度线，如：94.2F,94.4F,94.6F,94.8F等
                for(int j = 0;j< centerCellRowChild -1;j++){
                    lineChildY = lineY - (j + 1) * f_child_width;
                    canvas.drawLine( endLineX - cellChildWidth , lineChildY, endLineX, lineChildY,  cellLinePaint);
                }
            }
        }

        //绘制刻度纵线
        canvas.drawLine( starLineX_v , starLineY_v, columnWidth - topCTextMeasureWidth - 20, titleRowHeightCount + centerCellRowHeight * 2,  cellLinePaint);
        //F = 94
    }


    /**
     * 绘制温度柱状图和折线图
     * TODO 数据是float 类型需要特殊处理。
     * 思路：把数据类型固定成小数点后一位，这样在乘10，那这个数就是整数了
     * 或者在存入数据的时候就乘10变成整数存入。
     *
     * @param canvas
     */
    private void drawTemperatureLine(Canvas canvas) {
        if (null == timeBean && timeBean.getTemperatureValueDatas().size() <= 0) {
            return;
        }
        //TODO 这里还需优化
        if (null == timeBean && timeBean.getPluseValueDatas().size() <= 0) {
            return;
        }
        //每一小格代表多少的温度值
        float average = 20 / centerCellRowChild;
        Log.e("position", "average:" + average);
        //float value = 0;
        float cellCount = 0f;//每个温度值换算成格子是多少个格子
        float maxCellCount = tMax * 1.0f / average;//最大的温度值有多少个格子
        Log.e("position", "maxCellCount:" + maxCellCount);
        float tempCellCount = 0f;//最大值和当前值的差距
        RectF ovalRectf = null;
       /* Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(cellLineSize);
        paint.setAntiAlias(true);*/
        float x = 0, y = 0, preX = -1, preY = -1;
        tempAndpluseLinePaint.setColor(Color.GREEN);
        for (int p = 0; p < timeBean.getPluseValueDatas().size(); p++) {
            List<Integer> dayDats = timeBean.getPluseValueDatas().get(p);
            if (null == dayDats && dayDats.size() <= 0) {
                continue;
            }
            int i = 0;
            //TODO 数据是float 类型需要特殊处理。
            for (float f : dayDats) {//这里传入的数据必须为6个，没有实际数据的用-1表示
                if (dayDats.get(i) <= 0) {//没有数据就下一数据
                    i++;
                    continue;
                }

                //TODO 绘制 X
                //canvas.drawLine(x, y - 5, x, y + 5, paint);
                //canvas.drawLine(x - 5, y, x + 5, y, paint);
                //canvas.rotate(45);
                if (preX > 0 && preY > 0) {
                    canvas.drawLine(preX, preY, x, y, tempAndpluseLinePaint);
                }
                preX = x;
                preY = y;
                i++;
            }
        }
    }

    /**
     * 绘制脉搏柱状图和折线图
     *
     * @param canvas
     */
    private void drawPulseLine(Canvas canvas) {
        //TODO 这里还需优化
        if (null == timeBean && timeBean.getPluseValueDatas().size() <= 0) {
            return;
        }
        //每一小格代表多少的脉搏值
        float average = 20 / centerCellRowChild;
        Log.e("position", "average:" + average);
        int value = 0;
        float cellCount = 0f;//每个脉搏值换算成格子是多少个格子
        float maxCellCount = pMax * 1.0f / average;//最大的脉搏值有多少个格子
        Log.e("position", "maxCellCount:" + maxCellCount);
        float tempCellCount = 0f;//最大值和当前值的差距
        RectF ovalRectf = null;
       /* Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4.0f);
        paint.setAntiAlias(true);*/
        float x = 0, y = 0, preX = -1, preY = -1;

        for (int p = 0; p < timeBean.getPluseValueDatas().size(); p++) {
            List<Integer> dayDats = timeBean.getPluseValueDatas().get(p);
            if (null == dayDats && dayDats.size() <= 0) {
                continue;
            }
            for (int i = 0; i < dayDats.size(); i++) {//这里的数据必须是6个，没有数据就用-1表示
                if (dayDats.get(i) <= 0) {//如果是空数据就下一个
                    continue;
                }
                tempAndpluseLinePaint.setColor(Color.RED);
                value = dayDats.get(i);
                Log.e("position", "value:" + value);
                cellCount = value * 1.0f / average;
                Log.e("position", "cellCount:" + cellCount);
                tempCellCount = maxCellCount - cellCount;
                Log.e("position", "tempCellCount:" + tempCellCount);
                //画一个半径为3的圆作为节点。
                x = columnWidth + p * columnWidth + i * cellChildWidth + cellChildWidth / 2;
                y = titleRowHeightCount + 2 * centerCellRowHeight + tempCellCount * cellChildHeight;
                ovalRectf = new RectF(x - 8, y - 8, x + 8, y + 8);
                canvas.drawOval(ovalRectf, tempAndpluseLinePaint);
                if (preX > 0 && preY > 0) {
                    tempAndpluseLinePaint.setColor(Color.RED);
                    canvas.drawLine(preX, preY, x, y, tempAndpluseLinePaint);
                }
                Log.e("position", "preX:" + preX + ";preY:" + preY + ";x:" + x + ";y:" + y);
                preX = x;
                preY = y;
            }
        }
    }

    private void drawBg(Canvas canvas) {
        RectF leftRectF = new RectF(0, 0, columnWidth, titleRowHeightCount + cellRowHightCount);//leftBg
        bgPaint.setColor(leftBg);
        canvas.drawRect(leftRectF, bgPaint);

        leftRectF = new RectF(columnWidth, 0, mWidth - rightColumn * columnWidth / 2, titleRowHeightCount + centerCellRowHeight);//titleBg
        bgPaint.setColor(titleBg);
        canvas.drawRect(leftRectF, bgPaint);

        leftRectF = new RectF(0, titleRowHeightCount + centerCellRowHeight + 1, columnWidth, mHeight);//leftBg
        bgPaint.setColor(bottomBg);
        canvas.drawRect(leftRectF, bgPaint);
    }

    /**
     * 绘制二级网格线
     *
     * @param canvas
     */
    private void draw2LevelCell(Canvas canvas) {
        //绘制纵线
        for (int c = 0; c <= totalColumn; c++) {
            if (c > 0 && c < totalColumn) {
                for (int i = 0; i < centerCellColumn2LevelCount - 1; i++) {//这里是绘制时间那一行的纵线
                    //TODO 这里直接写死了 中间item = 2,后续需要优化
                    if (i == 2) {//这里是画体温单里面的时间那一行，上午和下午中间的那一条纵线
                        canvas.drawLine(c * columnWidth * 1.0f + (i + 1) * cellChildWidth, titleRowHeightCount, c * columnWidth * 1.0f + (i + 1) * cellChildWidth, titleRowHeightCount + cellRowHightCount + bottomRowHeight * 2, cellLine2LevelPaint);
                    } else {
                        canvas.drawLine(c * columnWidth * 1.0f + (i + 1) * cellChildWidth, titleRowHeightCount + centerCellRowHeight / 2, c * columnWidth * 1.0f + (i + 1) * cellChildWidth, titleRowHeightCount + cellRowHightCount + bottomRowHeight, cellLine2LevelPaint);
                    }
                }
            }
        }
        //绘制横线
        for (int r = 0; r <= totalRow; r++) {
            if (r > titleRowCount && r < titleRowCount + centerCellRowCount) {
                for (int t = 0; t < centerCellRowChild - 1; t++) {
                    canvas.drawLine(columnWidth, titleRowHeightCount + (r - titleRowCount) * centerCellRowHeight + (t + 1) * cellChildHeight, mWidth, titleRowHeightCount + (r - titleRowCount) * centerCellRowHeight + (t + 1) * cellChildHeight, cellLine2LevelPaint);
                }
            }
        }
    }

    private void drawSelectCell(Canvas canvas) {
        if (selectIndex >= 0 && selectIndex < titleColumnCount) {
            canvas.drawLine((selectIndex + 1) * columnWidth * 1.0f, 1, (selectIndex + 2) * columnWidth * 1.0f, 1, selectCellLinePaint);
            canvas.drawLine((selectIndex + 1) * columnWidth * 1.0f, mHeight - 1, (selectIndex + 2) * columnWidth * 1.0f, mHeight - 1, selectCellLinePaint);
            canvas.drawLine((selectIndex + 1) * columnWidth * 1.0f, selectCellLineSize, (selectIndex + 1) * columnWidth * 1.0f, mHeight - selectCellLineSize, selectCellLinePaint);
            if (selectIndex == titleColumnCount - 1) {
                canvas.drawLine((selectIndex + 2) * columnWidth * 1.0f, selectCellLineSize, (selectIndex + 2) * columnWidth * 1.0f - 1, mHeight * 1.0f - selectCellLineSize, selectCellLinePaint);
            } else {
                canvas.drawLine((selectIndex + 2) * columnWidth * 1.0f, selectCellLineSize, (selectIndex + 2) * columnWidth * 1.0f, mHeight * 1.0f - selectCellLineSize, selectCellLinePaint);
            }
        }
    }

    /**
     * 绘制表格
     */
    private void drawCell(Canvas canvas) {
        //绘制纵线
        for (int c = 0; c <= totalColumn; c++) {
            if (c == 0) {
                canvas.drawLine(c * columnWidth * 1.0f + 1, 0, c * columnWidth * 1.0f + 1, mHeight * 1.0f, cellLinePaint);
            } else if (c == totalColumn) {
                canvas.drawLine(c * columnWidth * 1.0f - 1, 0, mWidth - 1, mHeight * 1.0f, cellLinePaint);
            } else {
                canvas.drawLine(c * columnWidth * 1.0f, 0, c * columnWidth * 1.0f, mHeight * 1.0f, cellLinePaint);
            }
        }
        //绘制横线
        for (int r = 0; r <= totalRow; r++) {
            if (r == 0) {
                canvas.drawLine(2, 1, mWidth - 2, 1, cellLinePaint);
            } else if (r > 0 && r <= titleRowCount) {//标题栏部分
                canvas.drawLine(2, r * titleRowHeight, mWidth - 2, r * titleRowHeight, cellLinePaint);
            } else if (r > titleRowCount && r <= titleRowCount + centerCellRowCount) {//表格部分
                canvas.drawLine(2, titleRowHeightCount + (r - titleRowCount) * centerCellRowHeight, mWidth - 2, titleRowHeightCount + (r - titleRowCount) * centerCellRowHeight, cellLinePaint);
                if (r == titleRowCount + centerCellRowCount) {
                    continue;
                }
            } else if (r > titleRowCount + centerCellRowCount && r <= totalRow - 1) {//底部部分
                canvas.drawLine(2, titleRowHeightCount + cellRowHightCount + (r - titleRowCount - centerCellRowCount) * bottomRowHeight, mWidth - 2, titleRowHeightCount + cellRowHightCount + (r - titleRowCount - centerCellRowCount) * bottomRowHeight, cellLinePaint);
            } else if (r == totalRow) {
                canvas.drawLine(2, mHeight - 1, mWidth - 2, mHeight - 1, cellLinePaint);
            }
            if (r == titleRowCount) {
                canvas.drawLine(columnWidth, titleRowHeightCount + centerCellRowHeight / 2, mWidth, titleRowHeightCount + centerCellRowHeight / 2, cellLinePaint);
            }
        }
    }


    /**
     * 绘制标题
     */
    private void drawTitle(Canvas canvas) {
        if (null == arrayTitles || arrayTitles.length <= 0) {
            return;
        }
        String content;
        for (int r = 0; r < titleRowCount; r++) {//行
            for (int c = 0; c < titleColumnCount + leftColumn; c++) {//列
                //绘制文本
                content = arrayTitles[r][c];
                if (r == 0 && c == 0 || r == 1 && c == 0 || r == 2 && c == 0) {//TODO 这里数据的原因，暂时放在titleListDatas这里，后续需要把数据放在leftListDatas里
                    textMeasureWidth = leftTextPaint.measureText(content);
                    canvas.drawText(content, c * columnWidth + columnWidth / 2 - textMeasureWidth / 2, r * titleRowHeight + (titleRowHeight + leftTextMeasureHeight) / 2 - leftTextBottomMeasureHeight, leftTextPaint);
                } else {
                    textMeasureWidth = titleTextPaint.measureText(content);
                    canvas.drawText(content, c * columnWidth + columnWidth / 2 - textMeasureWidth / 2, r * titleRowHeight + (titleRowHeight + titleTextMeasureHeight) / 2 - titleTextBottomMeasureHeight, titleTextPaint);
                }
            }
        }
    }

    /**
     * 绘制时间那一行文本
     *
     * @param canvas
     */
    private void drawTimeRow(Canvas canvas) {
        if (null == timeBean) {
            return;
        }
        for (int c = 0; c < titleColumnCount + leftColumn; c++) {//列
            if (c == 0) {
                textMeasureWidth = leftTextPaint.measureText(timeBean.getTitle());
                canvas.drawText(timeBean.getTitle(), c * columnWidth + columnWidth / 2 - textMeasureWidth / 2, titleRowHeightCount + (centerCellRowHeight + leftTextMeasureHeight) / 2 - leftTextBottomMeasureHeight, leftTextPaint);
            } else {
                //TODO 这里还需要优化
                textMeasureWidth = centerCellTitle1LevelTextPaint.measureText(timeBean.getAm());
                canvas.drawText(timeBean.getAm(), c * columnWidth + columnWidth / 4 - textMeasureWidth / 2, titleRowHeightCount + (centerCellRowHeight / 2 + centerCellTitle1LevelTextMeasureHeight) / 2 - centerCellTitle1LevelTextButtomMeasureHeight, centerCellTitle1LevelTextPaint);//上午
                textMeasureWidth = centerCellTitle1LevelTextPaint.measureText(timeBean.getAn());
                canvas.drawText(timeBean.getAn(), c * columnWidth + columnWidth / 4 * 3 - textMeasureWidth / 2, titleRowHeightCount + (centerCellRowHeight / 2 + centerCellTitle1LevelTextMeasureHeight) / 2 - centerCellTitle1LevelTextButtomMeasureHeight, centerCellTitle1LevelTextPaint);//下午
                for (int i = 0; i < timeBean.getAmTitleDatas().size(); i++) {
                    if (i == 0) {
                        centerCellTitle1Leve2TextPaint.setColor(Color.RED);
                    } else {
                        centerCellTitle1Leve2TextPaint.setColor(Color.BLACK);
                    }
                    textMeasureWidth = centerCellTitle1Leve2TextPaint.measureText(timeBean.getAmTitleDatas().get(i));
                    canvas.drawText(timeBean.getAmTitleDatas().get(i), c * columnWidth + i * cellChildWidth + cellChildWidth / 2 - textMeasureWidth / 2, titleRowHeightCount + centerCellRowHeight / 4 * 3 + centerCellTitle2LevelTextMeasureHeight / 2 - centerCellTitle2LevelTextBottomMeasureHeight, centerCellTitle1Leve2TextPaint);//上午
                }
                for (int i = 0; i < timeBean.getAnTitleDatas().size(); i++) {
                    if (i == 0) {
                        centerCellTitle1Leve2TextPaint.setColor(Color.BLACK);
                    } else {
                        centerCellTitle1Leve2TextPaint.setColor(Color.RED);
                    }
                    textMeasureWidth = centerCellTitle1Leve2TextPaint.measureText(timeBean.getAnTitleDatas().get(i));
                    canvas.drawText(timeBean.getAnTitleDatas().get(i), c * columnWidth + columnWidth / 2 + i * cellChildWidth + cellChildWidth / 2 - textMeasureWidth / 2, titleRowHeightCount + centerCellRowHeight / 4 * 3 + centerCellTitle2LevelTextMeasureHeight / 2 - centerCellTitle2LevelTextBottomMeasureHeight, centerCellTitle1Leve2TextPaint);//下午
                }
            }
        }
    }


    /**
     * 绘制左侧部分
     */
    private void drawLeftTitle(Canvas canvas) {
        int p = pMax;
        int t = tMax;
        //绘制中间的纵线
        canvas.drawLine(columnWidth * 1.0f / 2, titleRowHeightCount + centerCellRowHeight + 1, columnWidth * 1.0f / 2, titleRowHeightCount + cellRowHightCount, cellLine2LevelPaint);
        for (int i = 1; i < centerCellRowCount; i++) {
            if (i == 1) {
                textMeasureWidth = leftTextPaint.measureText(context.getString(R.string.pulse));
                canvas.drawText(context.getString(R.string.pulse), columnWidth / 4 - textMeasureWidth / 2, titleRowHeightCount + i * centerCellRowHeight + leftTextMeasureHeight - leftTextBottomMeasureHeight, leftTextPaint);
                tempAndpluseLinePaint.setColor(Color.RED);
                canvas.drawLine(columnWidth / 4 -  textMeasureWidth / 2,titleRowHeightCount + i * centerCellRowHeight + leftTextMeasureHeight + leftTextBottomMeasureHeight,columnWidth / 4 + textMeasureWidth / 2,titleRowHeightCount + i * centerCellRowHeight + leftTextMeasureHeight + leftTextBottomMeasureHeight,tempAndpluseLinePaint);
                textMeasureWidth = leftTextPaint.measureText(context.getString(R.string.temp));
                canvas.drawText(context.getString(R.string.temp), columnWidth / 4 * 3 - textMeasureWidth / 2, titleRowHeightCount + i * centerCellRowHeight + leftTextMeasureHeight - leftTextBottomMeasureHeight, leftTextPaint);
                tempAndpluseLinePaint.setColor(Color.GREEN);
                canvas.drawLine(columnWidth / 4 * 3 - textMeasureWidth / 2,titleRowHeightCount + i * centerCellRowHeight + leftTextMeasureHeight + leftTextBottomMeasureHeight,columnWidth / 4 * 3 + textMeasureWidth / 2,titleRowHeightCount + i * centerCellRowHeight + leftTextMeasureHeight + leftTextBottomMeasureHeight,tempAndpluseLinePaint);
                textMeasureWidth = leftTextPaint.measureText(context.getString(R.string.unit_minute));
                canvas.drawText(context.getString(R.string.unit_minute), columnWidth / 4 - textMeasureWidth / 2, titleRowHeightCount + i * centerCellRowHeight + centerCellRowHeight - leftTextBottomMeasureHeight, leftTextPaint);
                textMeasureWidth = leftTextPaint.measureText(context.getString(R.string.unit_temp));
                canvas.drawText(context.getString(R.string.unit_temp), columnWidth / 4 * 3 - textMeasureWidth / 2, titleRowHeightCount + i * centerCellRowHeight + centerCellRowHeight - leftTextBottomMeasureHeight, leftTextPaint);
            } else {
                textMeasureWidth = leftTextPaint.measureText(String.valueOf(p));
                canvas.drawText(String.valueOf(p), columnWidth / 4 - textMeasureWidth / 2, titleRowHeightCount + i * centerCellRowHeight + leftTextMeasureHeight - leftTextBottomMeasureHeight, leftTextPaint);
                textMeasureWidth = leftTextPaint.measureText(String.valueOf(t));
                canvas.drawText(String.valueOf(t), columnWidth / 4 * 3 - textMeasureWidth / 2, titleRowHeightCount + i * centerCellRowHeight + leftTextMeasureHeight - leftTextBottomMeasureHeight, leftTextPaint);
                p -= 20;
                t -= 1;
            }
        }
    }

    /**
     * 绘制底部
     *
     * @param canvas
     */
    private void drawBottomTitle(Canvas canvas) {
        if (null == bottomDatas && bottomDatas.size() < 0) {
            return;
        }
        for (int i = 0; i < bottomDatas.size(); i++) {
            textMeasureWidth = bottomTitlePain.measureText(bottomDatas.get(i));
            canvas.drawText(bottomDatas.get(i), columnWidth / 2 - textMeasureWidth / 2, titleRowHeightCount + cellRowHightCount + i * bottomRowHeight + (bottomRowHeight + bottomTitleTextMeasureHeight) / 2 - bottomTitleTextBottomMeasureHeight, bottomTitlePain);
        }
    }

   /* public void setTitleDatas(String[][] datas) {
        this.arrayTitles = datas;
        invalidate();
    }

    public void setTimeBean(TimeBean timeBean) {
        this.timeBean = timeBean;
        invalidate();
    }

    public void setBottomTitles(List<String> bottomDatas) {
        this.bottomDatas = bottomDatas;
        invalidate();
    }


    public void select(int selectIndex) {
        this.selectIndex = selectIndex;
        invalidate();
    }*/

    public void setData(TemperatureBean bean){
        this.bean = bean;
        if(null == bean){
            return;
        }
        this.arrayTitles = bean.getTitleDatas();
        this.timeBean = bean.getTimeBean();
        this.bottomDatas = bean.getBottomTitles();
        this.selectIndex = bean.getPosition();
        invalidate();
    }
}
