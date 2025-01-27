package com.example.jetpackpracticeapplicatiion.ui.theme.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun StudentFormWithValidations(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var selectedCourse by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var genderError by remember { mutableStateOf("") }
    var courseError by remember { mutableStateOf("") }

    val courses = listOf("Computer Science", "Mathematics", "Physics", "English Literature")

    val isValidEmail: (String) -> Boolean = { it.contains("@") && it.contains(".") }
    val isValidPhone: (String) -> Boolean = { it.length == 10 && it.all { char -> char.isDigit() } }
    val isNonEmpty: (String) -> Boolean = { it.isNotBlank() }

    fun validateForm() {
        nameError = if (!isNonEmpty(name)) "Name cannot be empty" else ""
        emailError = if (!isValidEmail(email)) "Invalid email address" else ""
        phoneError = if (!isValidPhone(phone)) "Phone number must be 10 digits" else ""
        genderError = if (!isNonEmpty(gender)) "Please select a gender" else ""
        courseError = if (!isNonEmpty(selectedCourse)) "Please select a course" else ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Student Registration Form", fontSize = 20.sp, textAlign = TextAlign.Center)

        // Name Input
        Column {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )
            if (nameError.isNotBlank()) {
                Text(nameError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }
        }

        // Email Input
        Column {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            if (emailError.isNotBlank()) {
                Text(emailError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }
        }

        // Phone Input
        Column {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (phoneError.isNotBlank()) {
                Text(phoneError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }
        }

        // Gender Selection
        Column {
            Text("Gender")
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                RadioButton(
                    selected = gender == "Male",
                    onClick = { gender = "Male" }
                )
                Text("Male")
                RadioButton(
                    selected = gender == "Female",
                    onClick = { gender = "Female" }
                )
                Text("Female")
            }
            if (genderError.isNotBlank()) {
                Text(genderError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }
        }

        // Course Dropdown
        var isDropdownExpanded by remember { mutableStateOf(false) }

        Box {
            Column {
                OutlinedTextField(
                    value = selectedCourse,
                    onValueChange = {},
                    label = { Text("Select Course") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { isDropdownExpanded = !isDropdownExpanded }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Arrow")
                        }
                    }
                )
                if (courseError.isNotBlank()) {
                    Text(courseError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }
            }
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                courses.forEach { course ->
                    DropdownMenuItem(
                        text = { Text(text = course) }, // Correct API usage
                        onClick = {
                            selectedCourse = course
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // Submit Button
        Button(
            onClick = {
                validateForm()
                if (nameError.isBlank() && emailError.isBlank() && phoneError.isBlank() &&
                    genderError.isBlank() && courseError.isBlank()
                ) {
                    // All fields are valid; proceed with submission
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
