package id.smkcoding.smkcodingproject2.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.smkcoding.smkcodingproject2.HomeFragment
import id.smkcoding.smkcodingproject2.IndonesiaFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity){

    private val JUMLAH_MENU = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> { return HomeFragment()
            }
            1 -> { return IndonesiaFragment()
            }
            else -> {
                return HomeFragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return JUMLAH_MENU
    }


}