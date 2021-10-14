package com.phuoc.domain.form

data class SearchForm(
    var searchText: String = "",
    var page: Int = 0
) : IPageForm