package com.kulloveth.moviesapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.FragmentSigninBinding
import com.kulloveth.moviesapp.ui.MoviesDataManager
import com.kulloveth.moviesapp.ui.main.MainActivity
import com.squareup.picasso.Picasso
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignInFragment : Fragment() {

    var binding: FragmentSigninBinding? = null

    var userEditText: TextInputEditText? = null
    var userPassword: TextInputEditText? = null
    private val PICK_IMAGE = 5
    var imageUrl: String? = null

    private val TAG = SignInFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //retrieveUser()
        userEditText = binding?.userNameEditText
        userPassword = binding?.passwordEditText


        binding?.signInBtn?.setOnClickListener {
            validateUser()
        }
        binding?.select?.setOnClickListener {
            getImagefromGallery()
        }

        val moviesDataManager =
            ViewModelProvider(requireActivity()).get(MoviesDataManager::class.java)
        moviesDataManager.getMovieComposites().observe(requireActivity(), Observer {
            Log.d(TAG, "$it")
        })
    }


    //check that input fields meets criteria
    private fun validateUser() {
        val userName = userEditText?.text?.trim()
        val password = userPassword?.text?.trim()
        if (userName.isNullOrEmpty()) {
            userEditText?.error = getString(R.string.user_cannot_be_empty)
        } else if (password.isNullOrEmpty()) {
            userPassword?.error = getString(R.string.password_cannot_be_empty)
        } else if (userName.length < 5) {
            userEditText?.error = getString(R.string.usser_cant_be_less_than_5)
        } else if (!isValidPassword(password.toString())) {
            userPassword?.error = getString(R.string.password_check)
        } else {
            saveUser(userName.toString(), imageUrl)

            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

        }


    }


    //check if password matches criteria
    fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        return matcher.matches()
    }


    /**
     * save [userName]  and [userPassword]
     * using [sharedPrefs]
     * */
    fun saveUser(userName: String, userImage: String?) {
        SignInRepository.saveUser(userName, userImage)
    }


    companion object {
        const val USER_NAME_KEY = "USER_NAME_KEY"

        //const val USER_PASS_KEY = "USER_PASS_KEY"
        const val USER_IMAGE_KEY = "USER_IMAGE_KEY"

        fun picassoTool(url:String,view:ImageView){
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_account_box_black_24dp)
                .error(R.drawable.ic_movie_filter_black_24dp)
                .into(view)
        }


    }

    private fun getImagefromGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        intent.type = "image/*"
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                val uri: Uri? = data?.data
                imageUrl = uri.toString()
                binding?.userImage?.let {imgView->
                    imageUrl?.let {url->
                        picassoTool(url,imgView)
                    }

                }
            }
        }
    }

}
