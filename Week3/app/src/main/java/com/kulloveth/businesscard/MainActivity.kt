package com.kulloveth.businesscard

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kulloveth.businesscard.databinding.ActivityMainBinding
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var currentTips: String? = null
    var imageRes: Int = 0
    lateinit var toolbar: Toolbar
    var tipsList = listOf<String>()
    var toolbarTitle: Int = 0
    var alert: AlertDialog? = null


    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val TIPS_KEY = "TIPS_KEY"
        private val IMAGE_KEY = "IMAGE_KEY"
        private val LIST_KEY = "LIST_KEY"
        private val Title_KEY = "TITLE_KEY"
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

        toolbar = binding.toolbar
        val tipsBtn = binding.tipsBtn
        setSupportActionBar(toolbar)

        tipsBtn.setOnClickListener {
            currentTips = tipsList.random()
            binding.languageTipsTv.text = currentTips
        }

        if (savedInstanceState != null) {
            currentTips = savedInstanceState.getString(TIPS_KEY)
            imageRes = savedInstanceState.getInt(IMAGE_KEY)
            toolbarTitle = savedInstanceState.getInt(Title_KEY)
            supportActionBar?.title = getString(toolbarTitle)
            tipsList = savedInstanceState.getStringArrayList(LIST_KEY)?.toList() as List<String>
        } else {
            imageRes = R.drawable.kotlin_logo
            currentTips = getString(R.string.why_kotlin)
            tipsList = resources.getStringArray(R.array.kotlin_array).toList()
            toolbarTitle = R.string.kotlin_language
            supportActionBar?.title = getString(toolbarTitle)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> optionDialog()
            R.id.about -> aboutDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun aboutDialog() {

    }

    /**
     * saves the data state using [bundle]
     * to ensure its not recreated on orientation change
     * */
    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putString(TIPS_KEY, currentTips)
        bundle.putInt(IMAGE_KEY, imageRes)
        bundle.putInt(Title_KEY, toolbarTitle)
        bundle.putStringArrayList(LIST_KEY, tipsList.toList() as ArrayList<String>)
    }

    private fun optionDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Select Language")
        val items = arrayOf(
            resources.getString(R.string.kotlin_language),
            resources.getString(R.string.python_language),
            resources.getString(R.string.java_language)
        )

        alertDialog.setSingleChoiceItems(items, -1)
        { dialog, which ->
            when (which) {
                0 -> {
                    imageRes = R.drawable.kotlin_logo
                    currentTips = getString(R.string.why_kotlin)
                    tipsList = resources.getStringArray(R.array.kotlin_array).toList()
                    toolbarTitle = R.string.kotlin_language
                    recreate()
                }

                1 -> {
                    imageRes = R.drawable.pyhton_logo
                    currentTips = getString(R.string.why_python)
                    tipsList = resources.getStringArray(R.array.python_array).toList()
                    toolbarTitle = R.string.python_language
                    recreate()
                }

                2 -> {
                    imageRes = R.drawable.java_logo
                    currentTips = getString(R.string.why_java)
                    tipsList = resources.getStringArray(R.array.java_array).toList()
                    toolbarTitle = R.string.java_language
                    recreate()
                }
            }
        }
        alert = alertDialog.create()
        alert?.setCanceledOnTouchOutside(true)
        alert?.show()
    }

    //dismis dialog when activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        alert?.dismiss()
    }

}
