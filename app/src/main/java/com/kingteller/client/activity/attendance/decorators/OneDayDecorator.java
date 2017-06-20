package com.kingteller.client.activity.attendance.decorators;

import android.graphics.Typeface;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import java.util.Date;

import com.kingteller.client.view.calendar.CalendarDay;
import com.kingteller.client.view.calendar.DayViewDecorator;
import com.kingteller.client.view.calendar.DayViewFacade;

/**
 * Decorate a day by making the text big and bold
 */
public class OneDayDecorator implements DayViewDecorator {

    private CalendarDay date;

    public OneDayDecorator() {
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
