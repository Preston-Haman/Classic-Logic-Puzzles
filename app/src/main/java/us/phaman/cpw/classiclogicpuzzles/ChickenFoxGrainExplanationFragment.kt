package us.phaman.cpw.classiclogicpuzzles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class ChickenFoxGrainExplanationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chicken_fox_grain_explanation, container, false)

        view.findViewById<Button>(R.id.cfg_explanation_back_button).setOnClickListener {
            findNavController().navigateUp()
        }

        view.findViewById<Button>(R.id.cfg_explanation_start_button).setOnClickListener {
            findNavController().navigate(R.id.action_chickenFoxGrainExplanationFragment_to_chickenFoxGrainPuzzleFragment)
        }

        return view
    }
}
