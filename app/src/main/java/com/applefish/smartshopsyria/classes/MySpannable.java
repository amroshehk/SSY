package com.applefish.smartshopsyria.classes;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;


/**
 * Created by Amro on 04/09/2017.
 */

public class MySpannable extends ClickableSpan {

    private boolean isUnderline = false;

    /**
     * Constructor
     */
    public MySpannable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {

        ds.setUnderlineText(isUnderline);
        ds.setColor(Color.parseColor("#ff0000"));

    }

    @Override
    public void onClick(View widget) {

    }
}
