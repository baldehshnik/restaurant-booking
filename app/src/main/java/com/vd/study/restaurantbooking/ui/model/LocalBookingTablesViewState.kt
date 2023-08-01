package com.vd.study.restaurantbooking.ui.model

sealed class LocalBookingTablesViewState {
    object LocalBookingTablesErrorState : LocalBookingTablesViewState()
    object LocalBookingTablesLoadingState : LocalBookingTablesViewState()
    object LocalBookingTablesEmptyState : LocalBookingTablesViewState()
    object LocalBookingTablesLoadedState : LocalBookingTablesViewState()
}