package com.fj.footballmatchscedulefinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fj.footballmatchscedulefinal.R.string.no_data
import com.fj.footballmatchscedulefinal.adapter.MatchAdapter
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.data.KEY
import com.fj.footballmatchscedulefinal.model.Match
import com.fj.footballmatchscedulefinal.presenter.MatchSearchPresenter
import com.fj.footballmatchscedulefinal.view.MatchView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_match_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import com.fj.footballmatchscedulefinal.R.string.*

class MatchSearchActivity : AppCompatActivity(), MatchView {

    private lateinit var adapter: MatchAdapter
    private lateinit var searchPresenter: MatchSearchPresenter
    private var events: MutableList<Match> = mutableListOf()

    override fun showLoading() {
        progressbar.visible()
    }

    override fun hideLoading() {
        progressbar.invisible()
    }

    override fun showMatchList(data: List<Match>?) {
        swipe.isRefreshing = false
        events.clear()
        data?.let {
            events.addAll(data)
            adapter.notifyDataSetChanged()
        } ?: toast(getString(no_data))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_search)

        supportActionBar?.title = getString(search_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = APIRepository()
        val gson = Gson()
        searchPresenter = MatchSearchPresenter(this, request, gson)

        val i = intent
        val getName = i.getStringExtra(KEY.MATCH_NAME_KEY)

        txt_search.text = getName

        adapter = MatchAdapter(this, events) {
            startActivity<MatchDetailActivity>(
                    KEY.HOME_ID_KEY to it.homeId,
                    KEY.AWAY_ID_KEY to it.awayId,
                    KEY.EVENT_ID_KEY to it.eventId)
        }

        rv_match.layoutManager = LinearLayoutManager(this)
        rv_match.adapter = adapter

        searchPresenter.getMatchList(getName)

        swipe.onRefresh {
            searchPresenter.getMatchList(getName)
        }
    }

    private fun View.visible(){
        visibility = View.VISIBLE
    }

    private fun View.invisible(){
        visibility = View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
