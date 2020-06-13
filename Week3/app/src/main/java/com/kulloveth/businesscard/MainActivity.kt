package com.kulloveth.businesscard

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.kulloveth.businesscard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var currentTips: String? = null
    var imageRes: Int = 0
    var tipsList = listOf<String>()


    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val TIPS_KEY = "TIPS_KEY"
        private val IMAGE_KEY = "IMAGE_KEY"
    }

    /**
     * starting point of the activity
     * @param savedInstanceState is used to retrieve
     * saved data
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        val tipsBtn = binding.tipsBtn
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.kotlin_language)
        tipsList = resources.getStringArray(R.array.kotlin_array).toList().toList()
        tipsBtn.setOnClickListener {
            currentTips = tipsList.random()
            binding.languageTipsTv.text = currentTips
        }
       if (savedInstanceState != null) {
            currentTips = savedInstanceState.getString(TIPS_KEY)
            imageRes = savedInstanceState.getInt(IMAGE_KEY)
        }else{
           imageRes = R.drawable.kotlin_logo
           currentTips = getString(R.string.why_kotlin)
       }
        binding.languageTipsTv.text = currentTips
        binding.languageIv.setImageResource(imageRes)
    }


    /**
     * create the toolbar [menu] items
     * by referencing the resource file
     * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //this adds item to the toolbar if present
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * saves the data state using [bundle]
     * to ensure its not recreated on orientation change
     * */
    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putString(TIPS_KEY, currentTips)
        bundle.putInt(IMAGE_KEY, imageRes)
    }
}
