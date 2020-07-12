package com.kulloveth.covid19virustracker.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.data.Injection

import com.kulloveth.covid19virustracker.R
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    private var viewModel: NewsViewModel? = null
    private var newsRv: RecyclerView? = null
    private val adapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app_bar.title = "Covid News"
        viewModel = ViewModelProvider(requireActivity(),
            Injection.provideViewModelFactory()).get(NewsViewModel::class.java)
        newsRv = news_list
        newsRv?.adapter = adapter
        fetchNews()
    }

    fun fetchNews() {
        viewModel?.getNews()?.observe(requireActivity(), Observer {
            adapter.submitList(it)
        })
    }

}
