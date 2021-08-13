package com.example.test.data.usecases

import com.example.test.data.apimgr.APIService
import com.example.test.data.dao.PersonDAO
import com.example.test.data.dao.base.PagingDataDAO
import com.example.test.data.form.SearchForm
import com.example.test.data.services.IGetContactService
import io.reactivex.Single

class GetContactUseCase : BaseUseCase<SearchForm, Single<PagingDataDAO<PersonDAO>>> {

    override fun execute(input: SearchForm): Single<PagingDataDAO<PersonDAO>> {
        return APIService.build(IGetContactService::class.java).getContacts(input.page)
            .map { response ->
                val contacts = response.data
                val filtered =
                    contacts?.filter { it.email?.contains(input.searchText, true) == true }
                response.copy(data = filtered)
            }
    }
}