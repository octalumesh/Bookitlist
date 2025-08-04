package com.app.bookitlist.data.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.app.bookitlist.data.models.base.BaseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// Context Extensions
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.openUrl(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    } catch (e: Exception) {
        showToast("Cannot open URL")
    }
}

fun Context.shareText(text: String, title: String = "Share") {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(intent, title))
}

fun Context.dialNumber(phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    } catch (e: Exception) {
        showToast("Cannot dial number")
    }
}

fun Context.sendEmail(email: String, subject: String = "", body: String = "") {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        startActivity(intent)
    } catch (e: Exception) {
        showToast("Cannot send email")
    }
}

// Activity Extensions
fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

// Fragment Extensions
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, duration)
}

fun Fragment.hideKeyboard() {
    requireActivity().hideKeyboard()
}

// View Extensions
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.onClick(action: (view: View) -> Unit) {
    setOnClickListener(action)
}

// EditText Extensions
fun EditText.textString(): String {
    return text.toString().trim()
}

fun EditText.isEmpty(): Boolean {
    return textString().isEmpty()
}

fun EditText.isNotEmpty(): Boolean {
    return textString().isNotEmpty()
}

fun EditText.onTextChanged(action: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            action(s.toString())
        }
    })
}

fun EditText.validate(validator: (String) -> Boolean, errorMessage: String): Boolean {
    return if (validator(textString())) {
        error = null
        true
    } else {
        error = errorMessage
        false
    }
}

// ImageView Extensions
//fun ImageView.loadImage(url: String?, placeholder: Int = 0) {
//    Glide.with(context)
//        .load(url)
//        .placeholder(placeholder)
//        .error(placeholder)
//        .into(this)
//}
//
//fun ImageView.loadCircularImage(url: String?, placeholder: Int = 0) {
//    Glide.with(context)
//        .load(url)
//        .placeholder(placeholder)
//        .error(placeholder)
//        .transform(CircleCrop())
//        .into(this)
//}

// String Extensions
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPhone(): Boolean {
    return matches(Constants.Validation.PHONE_PATTERN.toRegex())
}

fun String.isValidPassword(): Boolean {
    return length >= Constants.Validation.MIN_PASSWORD_LENGTH
}

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }
}

fun String.toDate(format: String = Constants.DateFormats.API_DATE_FORMAT): Date? {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).parse(this)
    } catch (e: Exception) {
        null
    }
}

// Date Extensions
fun Date.toString(format: String = Constants.DateFormats.DISPLAY_DATE_FORMAT): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

fun Date.isToday(): Boolean {
    val today = Calendar.getInstance()
    val date = Calendar.getInstance().apply { time = this@isToday }

    return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            today.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)
}

fun Date.isYesterday(): Boolean {
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
    val date = Calendar.getInstance().apply { time = this@isYesterday }

    return yesterday.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            yesterday.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)
}

// LiveData Extensions
fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer.onChanged(value)
        }
    })
}

// Retrofit Response Extensions
fun <T> Response<T>.isSuccessful(): Boolean {
    return isSuccessful && body() != null
}

fun <T> Response<BaseResponse<T>>.getResult(): T? {
    return if (isSuccessful) {
        body()?.data
    } else {
        null
    }
}

fun <T> Response<BaseResponse<T>>.getErrorMessage(): String {
    return if (isSuccessful) {
        body()?.message ?: "Unknown error"
    } else {
        message() ?: "Network error"
    }
}

// Gson Extensions
inline fun <reified T> Gson.fromJson(json: String): T? {
    return try {
        fromJson(json, object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        null
    }
}

fun Any.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T> String.fromJson(): T? {
    return try {
        Gson().fromJson(this, object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        null
    }
}

// Bundle Extensions
fun Bundle.putEnum(key: String, enum: Enum<*>) {
    putString(key, enum.name)
}

inline fun <reified T : Enum<T>> Bundle.getEnum(key: String): T? {
    return getString(key)?.let { enumValueOf<T>(it) }
}

// Collection Extensions
fun <T> List<T>.isNotEmpty(): Boolean = !isEmpty()

fun <T> MutableList<T>.addIfNotExists(item: T) {
    if (!contains(item)) {
        add(item)
    }
}

fun <T> MutableList<T>.removeIfExists(item: T) {
    if (contains(item)) {
        remove(item)
    }
}

// Number Extensions
fun Int.toDp(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun Int.toPx(context: Context): Int {
    return (this / context.resources.displayMetrics.density).toInt()
}

// Boolean Extensions
fun Boolean.toInt(): Int = if (this) 1 else 0

fun Int.toBoolean(): Boolean = this != 0