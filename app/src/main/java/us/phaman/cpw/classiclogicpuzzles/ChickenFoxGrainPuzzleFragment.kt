package us.phaman.cpw.classiclogicpuzzles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class ChickenFoxGrainPuzzleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chicken_fox_grain_puzzle, container, false)

        //Enable drawer
        val drawer = activity?.findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END)

        //TODO: Populate the drawer's text views with hint text

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        //disable drawer
        val drawer = activity?.findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END)
    }
}
