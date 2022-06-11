package us.phaman.cpw.classiclogicpuzzles

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton

class CheckableToggleButton : AppCompatToggleButton {
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int):
        super(context!!, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?):
        super(context!!, attrs)

    constructor(context: Context?):
        super(context!!)

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isClickable) {
            mergeDrawableStates(drawableState, intArrayOf(android.R.attr.state_checkable))
        }
        return drawableState
    }
}
