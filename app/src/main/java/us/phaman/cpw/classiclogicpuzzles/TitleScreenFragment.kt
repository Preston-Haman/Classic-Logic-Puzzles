package us.phaman.cpw.classiclogicpuzzles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class TitleScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_title_screen, container, false)

        view.findViewById<Button>(R.id.title_chicken_fox_grain_button).setOnClickListener {
            findNavController().navigate(R.id.action_titleScreenFragment_to_chickenFoxGrainExplanationFragment)
        }

        view.findViewById<Button>(R.id.title_tutorial_button).setOnClickListener {
            findNavController().navigate(R.id.action_titleScreenFragment_to_tutorialFragment)
        }

        view.findViewById<Button>(R.id.title_about_button).setOnClickListener {
            findNavController().navigate(R.id.action_titleScreenFragment_to_aboutFragment)
        }

        return view
    }
}
