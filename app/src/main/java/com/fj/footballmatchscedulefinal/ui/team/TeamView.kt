package com.fj.footballmatchscedulefinal.ui.team

import com.fj.footballmatchscedulefinal.model.Team

/**
 * Created by naufalprakoso on 15/05/18.
 */
interface TeamView{
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}