package com.example.capar.eventreminder

import android.content.Context
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.StateListDrawable
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable



class CustomMatchers {
    companion object {
        fun withItemCount(count: Int): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("RecyclerView with item count: $count")
                }

                override fun matchesSafely(item: RecyclerView?): Boolean {
                    return item?.adapter?.itemCount == count
                }
            }
        }

        fun withBackground(expectedResourceId: Int): Matcher<View> {

            return object : BoundedMatcher<View, View>(View::class.java) {

                public override fun matchesSafely(view: View): Boolean {
                    return sameBitmap(view.context, view.background, expectedResourceId)
                }

                override fun describeTo(description: Description) {
                    description.appendText("has background resource $expectedResourceId")
                }
            }
        }

        private fun sameBitmap(context: Context, drawable: Drawable?, expectedId: Int): Boolean {
            var drawable = drawable
            var expectedDrawable = ContextCompat.getDrawable(context, expectedId)
            if (drawable == null || expectedDrawable == null) {
                return false
            }
            if (drawable is StateListDrawable && expectedDrawable is StateListDrawable) {
                drawable = drawable.current
                expectedDrawable = expectedDrawable.current
            }
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                val otherBitmap = (expectedDrawable as BitmapDrawable).bitmap
                return bitmap.sameAs(otherBitmap)
            }
            return false
        }
    }
}