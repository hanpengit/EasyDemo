package com.hunder.easylib.entity;

/**
 * 饼状图实体类
 * Created by hp on 2018/11/29.
 */

public class PieData {
    // 用户关心数据
    public String name;        // 名字
    public float value;        // 数值
    public float percentage;   // 百分比

    // 非用户关心数据
    public int color = 0;      // 颜色
    public float angle = 0;    // 角度

    public PieData(float value) {
        this.value = value;
    }
}
