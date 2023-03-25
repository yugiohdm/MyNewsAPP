package com.example.mynews.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.example.mynews.util.ViewUtil;

public class ColorPointHintView extends ShapeHintView {
    private int focusColor;
    private int normalColor;

    public ColorPointHintView(Context context,int focusColor,int normalColor) {
        super(context);
        this.focusColor = focusColor;
        this.normalColor = normalColor;
    }

    @Override
    public Drawable makeFocusDrawable() {
        GradientDrawable dot_focus = new GradientDrawable();
        dot_focus.setColor(focusColor);
        dot_focus.setCornerRadius(ViewUtil.dip2px(getContext(), 4));
        dot_focus.setSize(ViewUtil.dip2px(getContext(), 8), ViewUtil.dip2px(getContext(), 8));
        return dot_focus;
    }

    @Override
    public Drawable makeNormalDrawable() {
        GradientDrawable dot_normal = new GradientDrawable();
        dot_normal.setColor(normalColor);
        dot_normal.setCornerRadius(ViewUtil.dip2px(getContext(), 4));
        dot_normal.setSize(ViewUtil.dip2px(getContext(), 8), ViewUtil.dip2px(getContext(), 8));
        return dot_normal;
    }
}
