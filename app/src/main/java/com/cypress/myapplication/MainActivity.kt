package com.cypress.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: IntroFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeActivityFullScreen()
        setContentView(R.layout.activity_main)
        hideActionBar()
        createFragment()
    }


    private fun createFragment() {
        fragment = IntroFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
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

//TODO: Do we actually need to override this method?
//    viewPager.addItemDecoration(object : RecyclerView.ItemDecoration(){
//        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
//        }
//        override fun getItemOffsets(
//            outRect: Rect,
//            view: View,
//            parent: RecyclerView,
//            state: RecyclerView.State
//        ) {
//        }
//    })
}