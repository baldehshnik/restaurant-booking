package com.vd.study.restaurantbooking.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vd.study.restaurantbooking.R
import com.vd.study.restaurantbooking.ui.screens.wigets.TextWithHead

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        TextWithHead(
            headId = R.string.about_application,
            resourceId = R.string.about_application_information
        )

        Divider(modifier = Modifier.padding(top = 24.dp))

        TextWithHead(
            headId = R.string.working_hours,
            resourceId = R.string.working_hours_information
        )

        Divider(modifier = Modifier.padding(top = 24.dp))

        TextWithHead(headId = R.string.booking, resourceId = R.string.periods_information)

        Divider(modifier = Modifier.padding(top = 24.dp))

        TextWithHead(headId = R.string.important, resourceId = R.string.important_information)

        Spacer(modifier = Modifier.height(12.dp))
    }
}