package com.kulloveth.moviesapp.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.kulloveth.moviesapp.databinding.FragmentProfileBinding

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : DialogFragment() {

    var binding: FragmentProfileBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sharedPref = SignInRepository.sharedPrefs()
        val userName =
            sharedPref.getString(SignInFragment.USER_NAME_KEY, "")
        binding?.userName?.text = userName?.toUpperCase()
        val userImage = SignInRepository.sharedPrefs().getString(SignInFragment.USER_IMAGE_KEY, "")

        binding?.image?.let { imgView ->
            userImage?.let { url ->
                SignInFragment.picassoTool(url, imgView)
            }

        }

    }


}
