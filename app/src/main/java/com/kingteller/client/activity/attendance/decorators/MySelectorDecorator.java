package com.kingteller.client.activity.attendance.decorators;

import com.kingteller.R;
import com.kingteller.client.view.calendar.CalendarDay;
import com.kingteller.client.view.calendar.DayViewDecorator;
import com.kingteller.client.view.calendar.DayViewFacade;

import android.app.Activity;
import android.graphics.drawable.Drawable;



/**
 * Use a custom selector
 */
public class MySelectorDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public MySelectorDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.ic_error_nomal);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
