package phamf.com.chemicalapp.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

import phamf.com.chemicalapp.Fragment.LessonPartFragment

import phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.Companion.BIG_TITLE
import phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.Companion.COMPONENT_DEVIDER

class ViewPager_Lesson_Adapter(fm: android.support.v4.app.FragmentManager) : FragmentPagerAdapter(fm) {
    private var list: ArrayList<String>? = null

    val titles: ArrayList<String>
        get() {
            val titles = ArrayList<String>()

            for (part in list!!) {
                val title_start_pos = part.indexOf(BIG_TITLE) + 22
                val title_end_pos = part.indexOf(COMPONENT_DEVIDER)
                val title = part.substring(title_start_pos, title_end_pos)
                titles.add(title)
            }

            return titles
        }

    init {
        list = ArrayList()
    }

    fun setData(list: Collection<String>) {
        this.list = list as ArrayList<String>
        notifyDataSetChanged()
    }

    fun addData(vararg newContent: String) {
        for (part in newContent) {
            list!!.add(part)
        }
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return LessonPartFragment.newInstance(list!![position])
    }

    override fun getCount(): Int {
        return list!!.size
    }
}
