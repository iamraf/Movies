/*
 * Copyright (C) 2019 Raf.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.h01d.movies.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse
{
    @SerializedName("status_message")
    @NonNull
    private String message;
    @SerializedName("status_code")
    private int code;

    public ErrorResponse(@NonNull String message, int code)
    {
        this.message = message;
        this.code = code;
    }

    @NonNull
    public String getMessage()
    {
        return message;
    }

    public int getCode()
    {
        return code;
    }
}
