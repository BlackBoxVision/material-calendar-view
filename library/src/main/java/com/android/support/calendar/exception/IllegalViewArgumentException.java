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
package com.android.support.calendar.exception;

/**
 * Exception to be thrown when a related View method receives a bad argument.
 *
 * @author jonatan.salas
 */
public class IllegalViewArgumentException extends IllegalArgumentException {
    public static final String TYPEFACE_NOT_NULL_MESSAGE = "typeface argument can't be null!";
    public static final String SIZE_NOT_NULL_MESSAGE = "size argument can't be null!";

    public static final String DRAWABLE_NOT_NULL_MESSAGE = "drawable argument can't be null!";
    public static final String DRAWABLE_ID_NOT_ZERO_VALUE = "drawableId argument can't be zero value";

    public static final String COLOR_NOT_NULL_MESSAGE = "color argument can't be null!";
    public static final String COLOR_ID_NOT_ZERO_VALUE = "colorId argument can't be zero value";

    public static final String BUTTON_CLICK_LISTENER_NOT_NULL_MESSAGE = "onButtonClicked can't be null";
    public static final String ITEM_SELECTED_LISTENER_NOT_NULL_MESSAGE = "onListItemClickListener can't be null";
    public static final String ITEM_LONG_SELECTED_LISTENER_NOT_NULL_MESSAGE = "onListItemLongClickListener can't be null";


    /**
     * Default constructor with detail message param, the message is a String representation of the
     * error to propagate.
     *
     * @param detailMessage the error message
     */
    public IllegalViewArgumentException(String detailMessage) {
        super(detailMessage);
    }
}
