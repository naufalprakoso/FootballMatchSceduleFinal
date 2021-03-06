package com.fj.footballmatchscedulefinal.presenter

import com.fj.footballmatchscedulefinal.utils.TestContextProvider
import com.fj.footballmatchscedulefinal.api.APIRepository
import com.fj.footballmatchscedulefinal.api.TheSportDBApi
import com.fj.footballmatchscedulefinal.model.Match
import com.fj.footballmatchscedulefinal.ui.match.search.MatchSearchResponse
import com.fj.footballmatchscedulefinal.ui.match.MatchView
import com.fj.footballmatchscedulefinal.ui.match.search.MatchSearchPresenter
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestMatchSearchPresenter{
    @Test
    fun getMatchList() {
        val teams: MutableList<Match> = mutableListOf()
        val response = MatchSearchResponse(teams)
        val league = "Arsenal"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchSearch(league)),
                MatchSearchResponse::class.java
        )).thenReturn(response)

        presenter.getMatchList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(teams)
        Mockito.verify(view).hideLoading()
    }

    @Mock
    private lateinit var view: MatchView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: APIRepository
    @Mock
    private lateinit var presenter: MatchSearchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MatchSearchPresenter(view, apiRepository, gson, TestContextProvider())
    }
}