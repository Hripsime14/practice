package com.cypress.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.cypress.myapplication.databinding.FragmentIntroBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_intro.*

class IntroFragment : Fragment(R.layout.fragment_intro) {
    private lateinit var binding: FragmentIntroBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var buttonNext: Button
    private lateinit var buttonEnable: Button
    private var position = 0

    private var adapter2 = ViewPager2Adapter()
    private val items = mutableListOf<IntroItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

//TODO: Do we actually need to override this method?
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_intro, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initItems()
        adapter2.items = items
        initViews(view)
        addViewPagerAdapter()
        addTabLayoutMediator()
        setListeners()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            IntroFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun initItems() {
        items.add(IntroItem("1Hot Dogs",
            "It will harm your health",
            R.drawable.img1))
        items.add(
            IntroItem(
                "2Fast Delivery",
                "But we will appreciate that you come and get",
                R.drawable.img2
            )
        )
        items.add(IntroItem("3Easy Payment",
            "Pay!\nQuickly!!\nNow!!!",
            R.drawable.img3))
    }

    private fun initViews(view: View) {
        binding = FragmentIntroBinding.bind(view)
        viewPager = binding.viewPager2
        tabLayout = binding.tabLayout
        buttonNext = binding.buttonNext
        buttonEnable = binding.buttonEnable
    }

    private fun addViewPagerAdapter() {
        viewPager.adapter = adapter2
    }

    private fun addTabLayoutMediator() {
        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
        }.attach()
    }

    private fun setListeners() {

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab?.position == items.size - 1) {
                    changeBtnVisibility(true)
                } else {
                    changeBtnVisibility(false)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        buttonNext.setOnClickListener {
            viewPager.currentItem = (++position)
        }
    }

    private fun changeBtnVisibility(boolean: Boolean) {
        if (boolean) {
            buttonNext.visibility = View.GONE
            button_enable.visibility = View.VISIBLE
        } else {
            button_enable.visibility = View.GONE
        }
    }
}