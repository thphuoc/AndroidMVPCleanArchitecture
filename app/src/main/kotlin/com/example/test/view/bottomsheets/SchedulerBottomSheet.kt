package com.example.test.view.bottomsheets

import android.view.LayoutInflater
import com.example.test.R
import com.example.test.exts.observe
import com.example.test.view.base.StateFragment
import com.example.test.view.exts.buildStateEmptyLayout
import com.example.test.view.exts.buildStateInitLayout
import com.example.test.view.exts.buildStateLoadingLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jakewharton.rxbinding2.widget.RxTextView
import com.phuoc.domain.entities.PairEntity
import com.phuoc.domain.usecases.IGetSchedulersUseCase
import kotlinx.android.synthetic.main.view_search_connection_bottom_sheet.view.*
import org.koin.android.ext.android.inject

class SchedulerBottomSheet(
    private val context: StateFragment<*>
) {

    fun show(onSelected: (vehicle: PairEntity) -> Unit) {
        context.apply {
            BottomSheetDialog(requireContext(), R.style.BottomSheetDialog).apply {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.view_search_connection_bottom_sheet, null)
                setContentView(view)
                view.stateLayout.buildStateLoadingLayout()
                view.stateLayout.buildStateEmptyLayout()
                view.stateLayout.buildStateInitLayout()
                show()

                val getVehicleUseCase: IGetSchedulersUseCase by inject()
                view.stateLayout.showOffline()

                fun loadData(it: CharSequence) {
                    getVehicleUseCase.execute(it.toString())
                        .observe(viewModel, showLoadingDialog = false, onSuccess = { entry ->
                            view.stateLayout.showContent()
                            if (entry.isEmpty()) {
                                view.stateLayout.showEmpty()
                            }
                            view.placeHolderView.removeAllViews()
                            entry.forEach {
                                view.placeHolderView.addView(
                                    SimpleListItemViewBinder(
                                        it
                                    ) { selectedItem ->
                                        onSelected(selectedItem)
                                        dismiss()
                                    }
                                )
                            }
                        }, onError = {exception ->
                            view.stateLayout.showError(message = exception.message, onClickStateButton = {
                                view.stateLayout.showProgress()
                                loadData(it)
                            })
                        })
                }

                RxTextView.textChanges(view.edtSearch).skipInitialValue().filter {
                    if (it.isNotBlank()) {
                        view.stateLayout.showProgress()
                    }
                    it.isNotBlank()
                }.observe(viewModel, showLoadingDialog = false, onSuccess = {
                    loadData(it)
                })
            }
        }
    }
}