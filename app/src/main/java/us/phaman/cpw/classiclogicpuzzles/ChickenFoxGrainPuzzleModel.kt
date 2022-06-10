package us.phaman.cpw.classiclogicpuzzles

import android.widget.Button
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModel

class ChickenFoxGrainPuzzleModel : ViewModel() {
    enum class CFGItem {
        CHICKEN, FOX, GRAIN, NOTHING
    }

    private var farmerCrossed: Boolean = false

    private var chickenCrossed: Boolean = false

    private var foxCrossed: Boolean = false

    private var grainCrossed: Boolean = false

    fun canCrossWith(item: CFGItem): Boolean {
        //Farmer must be on the same side as the item
        if (farmerCrossed != when (item) {
            CFGItem.CHICKEN -> chickenCrossed
            CFGItem.FOX -> foxCrossed
            CFGItem.GRAIN -> grainCrossed
            CFGItem.NOTHING -> farmerCrossed
        }) return false

        return when (item) {
            //The Chicken is always safe to cross with
            CFGItem.CHICKEN -> true

            //Not safe if the Chicken and Grain are on the same side
            CFGItem.FOX -> {
                chickenCrossed != grainCrossed
            }

            //Not safe if the Chicken and Fox are on the same side
            CFGItem.GRAIN -> {
                chickenCrossed != foxCrossed
            }

            //Not safe unless Chicken is alone
            CFGItem.NOTHING -> {
                chickenCrossed != grainCrossed && chickenCrossed != foxCrossed
            }
        }
    }

    @StringRes
    fun getCannotCrossMessage(item: CFGItem): Int {
        return when (item) {
            //Chicken left with Grain
            CFGItem.FOX -> {
                R.string.cfg_puzzle_message_cannot_cross_chicken_eat_grain
            }

            //Chicken left with Fox
            CFGItem.GRAIN -> {
                R.string.cfg_puzzle_message_cannot_cross_fox_eat_chicken
            }

            //Chicken left with Fox or Grain
            CFGItem.NOTHING -> {
                if (chickenCrossed == grainCrossed && chickenCrossed == foxCrossed)
                    R.string.cfg_puzzle_message_cannot_cross_all_unattended
                else if (chickenCrossed == grainCrossed)
                    R.string.cfg_puzzle_message_cannot_cross_chicken_eat_grain
                else
                    R.string.cfg_puzzle_message_cannot_cross_fox_eat_chicken
            }

            //Invalid state...
            else -> {
                R.string.cfg_puzzle_message_cannot_cross_error
            }
        }
    }

    fun crossWith(item: CFGItem) {
        if (canCrossWith(item)) {
            farmerCrossed = !farmerCrossed
            when (item) {
                CFGItem.CHICKEN -> chickenCrossed = !chickenCrossed
                CFGItem.FOX -> foxCrossed = !foxCrossed
                CFGItem.GRAIN -> grainCrossed = !grainCrossed
                else -> {
                    //Nothing to do; just getting rid of a warning/prohibited non-exhaustive when
                }
            }
        }
    }

    @StringRes
    fun getDirectHintObject(): Int {
        //If the farmer has crossed, and can go back with nothing, do so.
        if (farmerCrossed && canCrossWith(CFGItem.NOTHING)) {
            return R.string.hints_cfg_direct_hint_nothing
        }

        //If the farmer hasn't crossed, and can take the grain, do so.
        if (!farmerCrossed && canCrossWith(CFGItem.GRAIN)) {
            return R.string.hints_cfg_direct_hint_grain
        }

        //If the farmer hasn't crossed, and can take the fox, do so.
        if (!farmerCrossed && canCrossWith(CFGItem.FOX)) {
            return R.string.hints_cfg_direct_hint_fox
        }

        //If the farmer can cross with the chicken, do so.
        if (canCrossWith(CFGItem.CHICKEN)) {
            return R.string.hints_cfg_direct_hint_chicken
        }

        //Default to nothing... I don't think this is possible to trigger.
        return R.string.hints_cfg_direct_hint_nothing
    }

    fun enableButtonsBasedOnFarmerPosition(chicken: Button, fox: Button, grain: Button) {
        chicken.isEnabled = farmerCrossed == chickenCrossed
        fox.isEnabled = farmerCrossed == foxCrossed
        grain.isEnabled = farmerCrossed == grainCrossed
    }

    fun adjustLayoutConstraintBasedOnPositions(farmer: ImageView, chicken: ImageView, fox: ImageView, grain: ImageView) {
        setConstraintLayoutHorizontalBias(farmer, farmerCrossed)
        setConstraintLayoutHorizontalBias(chicken, chickenCrossed)
        setConstraintLayoutHorizontalBias(fox, foxCrossed)
        setConstraintLayoutHorizontalBias(grain, grainCrossed)
        farmer.requestLayout()
    }

    private fun setConstraintLayoutHorizontalBias(image: ImageView, crossed: Boolean) {
        /*
        if (image.layoutParams is ConstraintLayout.LayoutParams) {
            val layout: ConstraintLayout.LayoutParams = image.layoutParams as ConstraintLayout.LayoutParams
            layout.horizontalBias = if (crossed) 1.0F else 0.0F
            image.requestLayout()
        }
        */
        image.updateLayoutParams<ConstraintLayout.LayoutParams> {
            horizontalBias = if (crossed) 1.0F else 0.0F
        }
    }

    fun isPuzzleSolved(): Boolean {
        return farmerCrossed && chickenCrossed && foxCrossed && grainCrossed
    }
}
