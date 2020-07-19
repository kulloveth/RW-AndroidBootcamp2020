package com.kulloveth.covid19virustracker.ui.news

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : BaseFragment() {

    private var viewModel: NewsViewModel? = null
    private var newsRv: RecyclerView? = null
    private var progress: ProgressBar? = null
    private val adapter = NewsAdapter()

    override fun getLayoutId() = R.layout.fragment_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app_bar.title = getString(R.string.covid_news)
        progress = progress_bar
        viewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideViewModelFactory()
        ).get(NewsViewModel::class.java)
        newsRv = news_list
        newsRv?.adapter = adapter
        fetchNews()
    }

    //observe status from livedata
    fun fetchNews() {
        viewModel?.getNews()?.observe(requireActivity(), Observer {
            adapter.submitList(it)
            newsRv?.visibility = View.VISIBLE
            progress_bar.visibility = View.INVISIBLE

        })
    }

}
