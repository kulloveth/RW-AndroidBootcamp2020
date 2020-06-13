package com.kulloveth.businesscard


import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.kulloveth.businesscard.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var currentTips: String? = null
    var imageRes: Int = 0
    lateinit var toolbar: Toolbar
    var tipsList = listOf<String>()
    var toolbarTitle: Int = 0
    var alert: AlertDialog? = null
    var styeTHeme: Int = 0
    var language: String? = null


    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val TIPS_KEY = "TIPS_KEY"
        private val IMAGE_KEY = "IMAGE_KEY"
        private val LIST_KEY = "LIST_KEY"
        private val Title_KEY = "TITLE_KEY"
        private val TOOLBAR_KEY = "TTHEME_KEY"
        private val LANGUAGE_KEY = "LANG_KEY"


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
        language = getString(R.string.kotlin_language)

        tipsBtn.setOnClickListener {
            currentTips = tipsList.random()
            binding.languageTipsTv.text = currentTips
        }

        if (savedInstanceState != null) {
            currentTips = savedInstanceState.getString(TIPS_KEY)
            imageRes = savedInstanceState.getInt(IMAGE_KEY)
            styeTHeme = savedInstanceState.getInt(TOOLBAR_KEY, styeTHeme)
            toolbarTitle = savedInstanceState.getInt(Title_KEY)
            language = savedInstanceState.getString(LANGUAGE_KEY)
            binding.languageTv.text = language
            supportActionBar?.title = getString(toolbarTitle)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(styeTHeme)))
            binding.tipsBtn.backgroundTintList = ContextCompat.getColorStateList(this, styeTHeme)
            binding.locationIv.imageTintList = ContextCompat.getColorStateList(this, styeTHeme)
            binding.emailIv.imageTintList = ContextCompat.getColorStateList(this, styeTHeme)
            tipsList = savedInstanceState.getStringArrayList(LIST_KEY)?.toList() as List<String>
        } else {
            imageRes = R.drawable.kotlin_logo
            currentTips = getString(R.string.why_kotlin)
            tipsList = resources.getStringArray(R.array.kotlin_array).toList()
            toolbarTitle = R.string.kotlin_language
            supportActionBar?.title = getString(toolbarTitle)
            styeTHeme = R.color.colorPrimary
            binding.languageTv.text = language
            binding.locationIv.imageTintList = ContextCompat.getColorStateList(this, styeTHeme)
            binding.emailIv.imageTintList = ContextCompat.getColorStateList(this, styeTHeme)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(styeTHeme)))
            binding.tipsBtn.backgroundTintList = ContextCompat.getColorStateList(this, styeTHeme)
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
     * create actions for the menu item
     * by referencing its it through [item]
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> optionDialog()
            R.id.about -> aboutDialog()
            R.id.share -> shareTips()
        }
        return super.onOptionsItemSelected(item)
    }

    //creates an intent share feature
    private fun shareTips() {
        startActivity(
            Intent.createChooser(
                ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(currentTips)
                    .intent, getString(toolbarTitle) + " tips"
            )
        )
    }

    //create dialog to show app information
    private fun aboutDialog() {
        val alertdialog = AlertDialog.Builder(this)
        alertdialog.apply {
            setTitle(getString(R.string.app_title))
            setMessage(getString(R.string.developer))
            setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            show()
        }


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
        bundle.putInt(TOOLBAR_KEY, styeTHeme)
        bundle.putString(LANGUAGE_KEY, language)
        bundle.putStringArrayList(LIST_KEY, tipsList.toList() as ArrayList<String>)
    }

    /**
     * creates an Alertdialog with different
     * language otion to select from
     * */
    private fun optionDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(R.string.select_language))
        val items = arrayOf(
            resources.getString(R.string.kotlin_language),
            resources.getString(R.string.python_language),
            resources.getString(R.string.java_language)
        )


        alertDialog.setSingleChoiceItems(items, -1)
        { _, which ->
            when (which) {
                0 -> {
                    imageRes = R.drawable.kotlin_logo
                    currentTips = getString(R.string.why_kotlin)
                    tipsList = resources.getStringArray(R.array.kotlin_array).toList()
                    toolbarTitle = R.string.kotlin_language
                    styeTHeme = R.color.colorPrimary
                    language = getString(R.string.kotlin_language)
                    recreate()
                }

                1 -> {
                    imageRes = R.drawable.pyhton_logo
                    currentTips = getString(R.string.why_python)
                    tipsList = resources.getStringArray(R.array.python_array).toList()
                    toolbarTitle = R.string.python_language
                    styeTHeme = R.color.pythonColorPrimary
                    language = getString(R.string.python_language)
                    recreate()
                }

                2 -> {
                    imageRes = R.drawable.java_logo
                    currentTips = getString(R.string.why_java)
                    tipsList = resources.getStringArray(R.array.java_array).toList()
                    toolbarTitle = R.string.java_language
                    styeTHeme = R.color.javaPrimaryDarkColor
                    language = getString(R.string.java_language)
                    recreate()
                }
            }
        }
        alert = alertDialog.create()
        alert?.apply {
            setCanceledOnTouchOutside(true)
            show()

        }

    }


    //dismis dialog when activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        alert?.dismiss()
    }

}
