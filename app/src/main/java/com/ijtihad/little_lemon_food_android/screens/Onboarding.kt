package com.bush.littlelemoncappro.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bush.littlelemoncappro.R
import com.bush.littlelemoncappro.data.PreferencesKeys
import com.bush.littlelemoncappro.ui.theme.LittleLemonColors.cloud
import com.bush.littlelemoncappro.ui.theme.LittleLemonColors.charcoal
import com.bush.littlelemoncappro.ui.theme.LittleLemonColors.green
import com.bush.littlelemoncappro.ui.theme.LittleLemonColors.yellow

@Composable
fun OnboardingScreen(navHostController: NavHostController) {
    val firstName = remember {
        mutableStateOf("")
    }
    val lastName = remember {
        mutableStateOf("")
    }
    val emailAddress = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val sharedPreferences =
        context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.little_lemon_logo_trans_bg),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp)
                .height(50.dp)
        )
        Text(
            text = "Let's get to know you",
            color = cloud,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(green)
                .padding(vertical = 30.dp),
            textAlign = TextAlign.Center,
        )
        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
            Text(
                text = "Personal Information",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 40.dp)
            )
            OutlinedTextField(
                value = firstName.value,
                onValueChange = { firstName.value = it },
                label = { Text(text = "First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            )
            OutlinedTextField(
                value = lastName.value,
                onValueChange = { lastName.value = it },
                label = { Text(text = "Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            )
            OutlinedTextField(
                value = emailAddress.value,
                onValueChange = { emailAddress.value = it },
                label = { Text(text = "Email Address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            )
        }
        Button(
            onClick = {
                if (firstName.value.isBlank() || lastName.value.isBlank() || emailAddress.value.isBlank()) {
                    val message = "Registration unsuccessful. Please enter all data."
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                } else {
                    sharedPreferences.edit()
                        .putString(PreferencesKeys.FIRST_NAME, firstName.value)
                        .putString(PreferencesKeys.LAST_NAME, lastName.value)
                        .putString(PreferencesKeys.EMAIL_ADDRESS, emailAddress.value)
                        .putBoolean(PreferencesKeys.IS_LOGGED_IN, true)
                        .apply()
                    val message = "Registration successful!"
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    navHostController.navigate("Home")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = yellow, contentColor = charcoal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp, horizontal = 15.dp)
        ) {
            Text(text = "Register")
        }
    }
}