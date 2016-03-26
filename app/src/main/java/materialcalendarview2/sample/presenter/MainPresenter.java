package materialcalendarview2.sample.presenter;

import android.support.annotation.Nullable;

import materialcalendarview2.sample.view.MainView;

/**
 * @author jonatan.salas
 */
public final class MainPresenter {

    @Nullable
    private MainView view;

    public MainPresenter(@Nullable MainView view) {
        this.view = view;
    }

    public void addNavigationDrawer() {
        if (null != view) {
            view.prepareNavigationDrawer();
        }
    }

    public void setToday() {
        if (null != view) {
            view.setTodayDate();
        }
    }

    public void addCalendarView() {
        if (null != view) {
            view.prepareCalendarView();
        }
    }

    public void animate() {
        if (null != view) {
            view.animateViews();
        }
    }

    public void dettachView() {
        this.view = null;
    }
}
