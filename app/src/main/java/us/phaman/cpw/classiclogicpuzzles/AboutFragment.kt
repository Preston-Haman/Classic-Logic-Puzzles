package us.phaman.cpw.classiclogicpuzzles

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        view.findViewById<Button>(R.id.about_author_website_button).setOnClickListener {
            openWebpageInDeviceBrowser(TODO())
        }

        view.findViewById<Button>(R.id.about_artist_website_button).setOnClickListener {
            openWebpageInDeviceBrowser(TODO())
        }

        view.findViewById<Button>(R.id.about_back_button).setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun openWebpageInDeviceBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}
