package com.fj.footballmatchscedulefinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_favorite_initial.*
import org.jetbrains.anko.startActivity
import com.fj.footballmatchscedulefinal.R.string.*

class FavoriteInitialActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(p0: View?) {
        when(p0){
            btn_team -> startActivity<FavoriteTeamActivity>()
            btn_match -> startActivity<FavoriteMatchActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_initial)

        supportActionBar?.title = getString(favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_team.setOnClickListener(this)
        btn_match.setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
