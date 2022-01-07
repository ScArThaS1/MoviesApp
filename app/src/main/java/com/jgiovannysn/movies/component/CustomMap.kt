package com.jgiovannysn.movies.component

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
class CustomMap : MapView {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attributeSet: AttributeSet?) : super(
        context!!, attributeSet
    )

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context!!, attributeSet!!, i
    )

    constructor(context: Context?, googleMapOptions: GoogleMapOptions?) : super(
        context!!, googleMapOptions
    )

    /**
     * Resolve conflicts scrolls between ViewPager and MapView
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }
}