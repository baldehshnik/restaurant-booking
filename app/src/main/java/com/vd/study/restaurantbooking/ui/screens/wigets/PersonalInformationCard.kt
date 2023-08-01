package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vd.study.restaurantbooking.R

@Composable
fun PersonalInformationCard(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int,
    firstnameValue: String,
    lastnameValue: String,
    patronymicValue: String,
    isErrorFirstname: Boolean,
    isErrorLastname: Boolean,
    isErrorPatronymic: Boolean,
    onFirstnameValueChange: (String) -> Unit,
    onLastnameValueChange: (String) -> Unit,
    onPatronymicValueChange: (String) -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = stringResource(id = titleId),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 16.dp),
            fontSize = 16.sp
        )

        PersonalInformationOutlinedTextField(
            value = firstnameValue,
            onValueChange = onFirstnameValueChange,
            labelValue = stringResource(id = R.string.firstname),
            modifier = null,
            isError = isErrorFirstname
        )

        PersonalInformationOutlinedTextField(
            value = lastnameValue,
            onValueChange = onLastnameValueChange,
            labelValue = stringResource(id = R.string.lastname),
            modifier = null,
            isError = isErrorLastname
        )

        PersonalInformationOutlinedTextField(
            value = patronymicValue,
            onValueChange = onPatronymicValueChange,
            labelValue = stringResource(id = R.string.patronymic),
            isError = isErrorPatronymic,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp)
        )
    }
}