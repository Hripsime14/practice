package com.cypress.myapplication

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var buttonNext: Button
    private var position = 0

    private var adapter2 = ViewPager2Adapter()
    private val items = mutableListOf<IntroItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeActivityFullScreen()
        setContentView(R.layout.activity_main)
        hideActionBar()

        initItems()
        initViews()
        setListeners()

        adapter2.items = items


        /*val fragment = BlankFragment.newInstance()

        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout_id, fragment)
            .commit()*/

    }

    private fun initItems() {
        items.add(IntroItem("Hot Dogs", "It will harm your health", R.drawable.img1 ))
        items.add(IntroItem("Fast Delivery", "But we will appreciate that you come and get", R.drawable.img2))
        items.add(IntroItem("Easy Payment", "Pay!\nQuickly!!\nNow!!!", R.drawable.img3))
    }

    private fun initViews() { //TODO: Change to binding
        viewPager = findViewById(R.id.view_pager2)
        tabLayout = findViewById(R.id.tab_layout)
        buttonNext = findViewById(R.id.button_next)

        viewPager.adapter = adapter2

        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->

        }.attach()
    }

    private fun setListeners() {
        buttonNext.setOnClickListener {
            viewPager.setCurrentItem(++position, true)
        }
    }


    private fun makeActivityFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

//    viewPager.addItemDecoration(object : RecyclerView.ItemDecoration(){
//        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
//
//        }
//
//        override fun getItemOffsets(
//            outRect: Rect,
//            view: View,
//            parent: RecyclerView,
//            state: RecyclerView.State
//        ) {
//
//        }
//    })
}