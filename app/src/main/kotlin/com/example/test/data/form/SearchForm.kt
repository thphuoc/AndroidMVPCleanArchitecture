package com.example.test.data.form

data class SearchForm(
    var searchText: String = "",
    var page: Int = 0
) : IPageForm