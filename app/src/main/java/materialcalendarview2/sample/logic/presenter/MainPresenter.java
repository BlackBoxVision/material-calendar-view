/*
 * Copyright (C) 2015 Jonatan E. Salas { link: http://the-android-developer.blogspot.com.ar }
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package materialcalendarview2.sample.logic.presenter;

import android.support.annotation.Nullable;

import materialcalendarview2.sample.logic.presenter_view.MainView;

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
        if (null != getView()) {
            getView().prepareNavigationDrawer();
        }
    }

    public void addTextView() {
        if (null != getView()) {
            getView().prepareTextView();
        }
    }

    public void addCalendarView() {
        if (null != getView()) {
            getView().prepareCalendarView();
        }
    }

    public void animate() {
        if (null != getView()) {
            getView().animateViews();
        }
    }

    public void detachView() {
        this.view = null;
    }

    @Nullable
    public MainView getView() {
        return view;
    }
}
