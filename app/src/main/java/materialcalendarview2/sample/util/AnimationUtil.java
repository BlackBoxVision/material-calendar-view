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
package materialcalendarview2.sample.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import static materialcalendarview2.util.ScreenUtils.getScreenHeight;

/**
 * @author jonatan.salas
 */
public final class AnimationUtil {

    private AnimationUtil() { }

    public static void animate(@NonNull View v, @NonNull Context ctx) {
        ViewCompat.setTranslationY(v, getScreenHeight(ctx));
        ViewCompat.setAlpha(v, 0f);
        ViewCompat.animate(v)
                .translationY(0f)
                .setDuration(1500)
                .alpha(1f)
                .setInterpolator(new DecelerateInterpolator(3.0f))
                .start();
    }
}
