package com.fj.footballmatchscedulefinal.ui.favorite.match

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.fj.footballmatchscedulefinal.ui.match.detail.MatchDetailActivity
import com.fj.footballmatchscedulefinal.R
import com.fj.footballmatchscedulefinal.model.FavoriteMatch
import com.fj.footballmatchscedulefinal.data.Const
import com.fj.footballmatchscedulefinal.db.database
import kotlinx.android.synthetic.main.activity_favorite_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.startActivity
import com.fj.footballmatchscedulefinal.R.string.*

class FavoriteMatchActivity : AppCompatActivity() {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_match)

        supportActionBar?.title = getString(fav_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoriteMatchAdapter(this, favorites) {
            startActivity<MatchDetailActivity>(
                    Const.HOME_ID_KEY to it.teamHomeId,
                    Const.AWAY_ID_KEY to it.teamAwayId,
                    Const.EVENT_ID_KEY to it.eventId
            )
        }

        rv_match.layoutManager = LinearLayoutManager(this)
        rv_match.adapter = adapter
        showFavorite()
        swipe.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        database.use {
            swipe.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
