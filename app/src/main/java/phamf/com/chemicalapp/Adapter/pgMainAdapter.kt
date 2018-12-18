package phamf.com.chemicalapp.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.PostsFragment
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.SchoolFragment
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.ToolFragment

class pgMainAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ToolFragment.newInstance()
            1 -> return PostsFragment.newInstance()
            else -> return SchoolFragment.newInstance()
        }
    }

}