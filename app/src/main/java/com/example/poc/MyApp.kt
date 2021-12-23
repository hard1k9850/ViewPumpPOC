package com.example.poc

import android.app.Application
import android.widget.TextView


import io.github.inflationx.viewpump.Interceptor
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.ViewPump

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(TextUpdatingInterceptor())
                .build()
        )
    }

    class TextUpdatingInterceptor : Interceptor {
        val prefix = "@t/"

        override fun intercept(chain: Interceptor.Chain): InflateResult {
            val result: InflateResult = chain.proceed(chain.request())
            if (result.view is TextView) {
                // Do something to result.view()
                // You have access to result.context() and result.attrs()
                val textView = result.view as TextView

                formatText(textView.text)?.let {
                    textView.text = it
                }
            }
            return result
        }

        private fun formatText(text: CharSequence) : CharSequence? {
            return if (text.startsWith(prefix)){
                var key = text.removePrefix(prefix)
                //TODO associate key with value from server response
                /**
                 * example
                 * map.getOrDefault(key,null)
                 */
                "Server sent value"
            }else{
                text.toString()
            }
        }
    }

}