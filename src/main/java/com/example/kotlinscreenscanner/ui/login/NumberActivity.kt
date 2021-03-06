package com.example.kotlinscreenscanner.ui.login

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.kotlinscreenscanner.R
import com.example.kotlinscreenscanner.service.model.CounterResultModel
import com.example.kotlinscreenscanner.ui.login.fragment.NumberBottomSheetFragment
import com.timelysoft.tsjdomcom.service.AppPreferences
import com.timelysoft.tsjdomcom.service.AppPreferences.toFullPhone
import com.timelysoft.tsjdomcom.service.NetworkRepository
import com.timelysoft.tsjdomcom.service.Status
import com.timelysoft.tsjdomcom.utils.MyUtils
import kotlinx.android.synthetic.main.activity_number.*
import kotlinx.android.synthetic.main.actyviti_questionnaire.*
import java.util.*
import kotlin.collections.HashMap

class NumberActivity : AppCompatActivity() {
    private var viewModel = NetworkRepository()
    private var numberCharacters: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number)
        number_focus_text.requestFocus()
        getListCountry()
        initClick()
        initViews()
        initToolBar()
    }

    private fun initBottomSheet(id: Int) {
        val bottomSheetDialogFragment = NumberBottomSheetFragment(id)
        bottomSheetDialogFragment.isCancelable = false;
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Регистрация"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initClick() {
        number_next.setOnClickListener {
            if (validate()) {
                MainActivity.alert.show()
                val map = HashMap<String, String>()
                map.put("phone", MyUtils.toFormatMask(number_phone.text.toString()))
                viewModel.numberPhone(map).observe(this, Observer { result ->
                    val msg = result.msg
                    val data = result.data
                    when (result.status) {
                        Status.SUCCESS -> {
                            if (data!!.result == null) {
                                Toast.makeText(this, data.error.message, Toast.LENGTH_LONG).show()
                            } else {
                                AppPreferences.number = number_phone.text.toString()
                                initBottomSheet(data.result.id!!)
                            }
                        }
                        Status.ERROR, Status.NETWORK -> {
                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                        }
                    }
                    MainActivity.alert.hide()
                })
            }
        }
    }

    private fun getListCountry() {
        var list: ArrayList<CounterResultModel> = arrayListOf()
        val map = HashMap<String, Int>()
        map.put("id", 0)
        MainActivity.alert.show()
        viewModel.listAvailableCountry(map).observe(this, androidx.lifecycle.Observer { result ->
            val msg = result.msg
            val data = result.data
            when (result.status) {
                Status.SUCCESS -> {
                    val adapterListCountry = ArrayAdapter(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        data!!.result
                    )
                    number_list_country.setAdapter(adapterListCountry)
                    list = data.result
                }
                Status.ERROR, Status.NETWORK -> {
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                }
            }
            MainActivity.alert.hide()
        })
        number_list_country.keyListener = null
        number_list_country.setOnItemClickListener { adapterView, view, position, l ->
            number_list_country.showDropDown()
            AppPreferences.numberCharacters = list[position].phoneLength!!.toInt()
            AppPreferences.isFormatMask = list[position].phoneMask
            numberCharacters = list[position].phoneLength!!.toInt()
            number_phone.setText("")
            number_phone.mask = ""
            if (position == 0) {
                number_phone.mask = list[position].phoneMask
            } else if (position == 1) {
                number_phone.mask = list[position].phoneMask
            } else if (position == 2) {
                number_phone.mask = list[position].phoneMask
            }
            if (number_list_country.text.toString() != "") {
                number_phone_visibility.visibility = View.VISIBLE
            }

            number_list_country.clearFocus()
        }
        number_list_country.setOnClickListener {
            number_list_country.showDropDown()
        }
        number_list_country.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            try {
                if (hasFocus) {
                    number_list_country.showDropDown()
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun validate(): Boolean {
        var valid = true
        if (number_list_country.text.isEmpty()) {
            number_list_country.error = "Выберите страну"
            valid = false
        }else{
            number_phone.error = null
        }

        if (numberCharacters != 11) {
            if (number_phone.text!!.toString().length != 19) {
                number_phone.error = "Ввидите валидный номер"
                valid = false
            } else {
                number_phone.error = null
            }
        } else {
            if (number_phone.text!!.toString().toFullPhone().length != 20) {
                number_phone.error = "Ввидите валидный номер"
                valid = false
            } else {
                number_phone.error = null
            }
        }
        return valid
    }

    private fun initViews() {
        number_list_country.addTextChangedListener {
            number_list_country.error = null
            number_phone.error = null
        }
    }
}