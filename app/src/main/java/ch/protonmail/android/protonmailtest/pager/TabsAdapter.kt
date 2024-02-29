package ch.protonmail.android.protonmailtest.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabsAdapter(val fetchString: (Int) -> String, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return AllTasksFragment()
        }
        return UpcomingFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? = fetchString(position)

    override fun getCount(): Int {
        return 2
    }
}
