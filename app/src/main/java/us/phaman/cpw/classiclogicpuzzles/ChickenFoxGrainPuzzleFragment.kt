package us.phaman.cpw.classiclogicpuzzles

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.annotation.StringRes
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider

class ChickenFoxGrainPuzzleFragment : Fragment() {

    lateinit var model: ChickenFoxGrainPuzzleModel

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProvider(this).get(ChickenFoxGrainPuzzleModel::class.java)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chicken_fox_grain_puzzle, container, false)

        //The layout for this fragment is not good for landscape on phones... lock to portrait
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Enable drawer
        val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END)

        //Populate the drawer's text views with hint text
        setHints (
            R.string.hints_cfg_rules_summary,
            R.string.hints_cfg_generic_hint,
            R.string.hints_cfg_direct_hint, model.getDirectHintObject()
        )

        //ImageViews
        val farmerImage: ImageView = view.findViewById<ImageView>(R.id.cfg_puzzle_farmer_top)
        val chickenImage: ImageView = view.findViewById<ImageView>(R.id.cfg_puzzle_chicken_top)
        val foxImage: ImageView = view.findViewById<ImageView>(R.id.cfg_puzzle_fox_top)
        val grainImage: ImageView = view.findViewById<ImageView>(R.id.cfg_puzzle_grain_top)

        //ToggleButtons
        val chickenButton: ToggleButton = view.findViewById<ToggleButton>(R.id.cfg_puzzle_chicken_select)
        val foxButton: ToggleButton = view.findViewById<ToggleButton>(R.id.cfg_puzzle_fox_select)
        val grainButton: ToggleButton = view.findViewById<ToggleButton>(R.id.cfg_puzzle_grain_select)

        //Button
        val crossBridgeButton: Button = view.findViewById<Button>(R.id.cfg_puzzle_cross_bridge_button)

        //Set image positions
        model.adjustLayoutConstraintBasedOnPositions(farmerImage, chickenImage, foxImage, grainImage)

        //Enable selection buttons for items on the same side as the farmer...
        model.enableButtonsBasedOnFarmerPosition(chickenButton, foxButton, grainButton)

        crossBridgeButton.setOnClickListener {
            val message: TextView = view.findViewById<TextView>(R.id.cfg_puzzle_message)
            if (countTrueValues(chickenButton.isChecked, foxButton.isChecked, grainButton.isChecked) > 1) {
                message.setText(R.string.cfg_puzzle_message_cannot_cross_multiple_items)
                return@setOnClickListener
            }

            //One item is selected, or nothing is selected...
            val selectedItem: ChickenFoxGrainPuzzleModel.CFGItem = getSelectedItem(chickenButton.isChecked, foxButton.isChecked, grainButton.isChecked)

            if (!model.canCrossWith(selectedItem)) {
                //Disable the selected button and uncheck it!
                when (selectedItem) {
                    //It's always safe to cross with the chicken; no error message to show
                    //If the user triggers this, then the button should have been disabled from the start!
                    ChickenFoxGrainPuzzleModel.CFGItem.CHICKEN -> {
                        chickenButton.isEnabled = false
                        chickenButton.isChecked = false
                    }

                    ChickenFoxGrainPuzzleModel.CFGItem.FOX -> {
                        foxButton.isEnabled = false
                        foxButton.isChecked = false
                    }
                    ChickenFoxGrainPuzzleModel.CFGItem.GRAIN -> {
                        grainButton.isEnabled = false
                        grainButton.isChecked = false
                    }
                    else -> {
                        //No button to disable...
                    }
                }

                //Set message saying that the selection is invalid...
                message.setText(model.getCannotCrossMessage(selectedItem))

                return@setOnClickListener
            }

            //Clear any previous messages
            message.text = ""

            //Cross with item
            model.crossWith(selectedItem)

            //Set image positions
            model.adjustLayoutConstraintBasedOnPositions(farmerImage, chickenImage, foxImage, grainImage)

            //Enable selection buttons for items on the same side as the farmer...
            model.enableButtonsBasedOnFarmerPosition(chickenButton, foxButton, grainButton)

            //Deselect buttons
            when (selectedItem) {
                ChickenFoxGrainPuzzleModel.CFGItem.CHICKEN -> chickenButton.isChecked = false
                ChickenFoxGrainPuzzleModel.CFGItem.FOX -> foxButton.isChecked = false
                ChickenFoxGrainPuzzleModel.CFGItem.GRAIN -> grainButton.isChecked = false
                else -> {
                    //Nothing to do...
                }
            }

            setDirectHint(R.string.hints_cfg_direct_hint, model.getDirectHintObject())

            if (model.isPuzzleSolved()) {
                //Disable everything
                setEnabled(false, chickenButton, foxButton, grainButton, crossBridgeButton)

                //Set solved message
                message.setText(R.string.cfg_puzzle_message_puzzle_complete)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        //Unlock orientation
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        //disable drawer
        val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END)
    }

    private fun setHints(@StringRes rulesSummary: Int, @StringRes genericHint: Int, @StringRes directHint: Int, @StringRes directHintObject: Int) {
        requireActivity().findViewById<TextView>(R.id.hints_rules_summary).setText(rulesSummary)
        requireActivity().findViewById<TextView>(R.id.hints_generic_hint).setText(genericHint)

        setDirectHint(directHint, directHintObject)
    }

    private fun setDirectHint(@StringRes directHint: Int, @StringRes directHintObject: Int) {
        val button: Button = requireActivity().findViewById<Button>(R.id.hints_direct_hint_button)
        val directHintView: TextView = requireActivity().findViewById<TextView>(R.id.hints_direct_hint)

        directHintView.text = ""
        button.setOnClickListener {
            directHintView.text = String.format(getString(directHint), getString(directHintObject))
            button.setOnClickListener(null)
        }
    }

    private fun countTrueValues(vararg values: Boolean): Int {
        var count: Int = 0
        for (bool in values) {
            if (bool) count++
        }
        return count
    }

    private fun getSelectedItem(chicken: Boolean, fox: Boolean, grain: Boolean): ChickenFoxGrainPuzzleModel.CFGItem {
        return when {
            chicken -> ChickenFoxGrainPuzzleModel.CFGItem.CHICKEN
            fox -> ChickenFoxGrainPuzzleModel.CFGItem.FOX
            grain -> ChickenFoxGrainPuzzleModel.CFGItem.GRAIN
            else -> ChickenFoxGrainPuzzleModel.CFGItem.NOTHING
        }
    }

    private fun setEnabled(enabled: Boolean, vararg buttons: Button) {
        for (button in buttons) {
            button.isEnabled = enabled
        }
    }
}
