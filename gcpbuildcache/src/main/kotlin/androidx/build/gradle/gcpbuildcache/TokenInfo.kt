/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package androidx.build.gradle.gcpbuildcache

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

internal fun interface TokenInfoService {

    fun tokenInfo(accessToken: String): Call

    companion object {
        fun tokenService(): TokenInfoService {
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(NetworkErrorInterceptor())
                .build()

            return TokenInfoService { accessToken ->
                val request = Request.Builder()
                    .url("https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=$accessToken")
                    .build()

                httpClient.newCall(request)
            }
        }
    }
}
