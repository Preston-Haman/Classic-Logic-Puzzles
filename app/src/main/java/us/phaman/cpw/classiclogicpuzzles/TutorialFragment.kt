package us.phaman.cpw.classiclogicpuzzles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class TutorialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tutorial, container, false)

        view.findViewById<Button>(R.id.tut_back_button).setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }
}
