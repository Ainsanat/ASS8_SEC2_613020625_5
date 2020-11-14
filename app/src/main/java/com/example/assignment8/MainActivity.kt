package com.example.assignment8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_dialog_layout.*
import kotlinx.android.synthetic.main.add_dialog_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var customerList = arrayListOf<Customer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.adapter = CustomersAdapter(this.customerList, applicationContext)
        recyclerview.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onResume() {
        super.onResume()
        callCustomerData()
    }

    fun callCustomerData() {
        customerList.clear()
        val serv: customerAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(customerAPI::class.java)

        serv.retrieveCustomer()
                .enqueue(object : Callback<List<Customer>> {
                    override fun onResponse(
                            call: Call<List<Customer>>,
                            response: Response<List<Customer>>
                    ) {
                        response.body()?.forEach {
                            customerList.add(Customer(it.name, it.gender, it.email, it.salary))
                        }
                        recyclerview.adapter = CustomersAdapter(customerList, applicationContext)
                    }

                    override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                        return t.printStackTrace()
                    }

                })
    }

    fun addData(view: View) {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.add_dialog_layout, null)
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setView(mDialogView)
        val mAlertDialog = mBuilder.show()

        mDialogView.add.setOnClickListener {
            val api: customerAPI = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(customerAPI::class.java)

            var radioGroup: RadioGroup = mDialogView.sex
            var selectedId: Int = radioGroup.checkedRadioButtonId
            var radioButton: RadioButton = mDialogView.findViewById(selectedId)

            api.insertCustomer(
                    mDialogView.edt_name.text.toString(),
                    radioButton.text.toString(),
                    mDialogView.edt_email.text.toString(),
                    mDialogView.edt_salary.text.toString()).enqueue(object : Callback<Customer> {

                override fun onResponse(
                        call: Call<Customer>, response: retrofit2.Response<Customer>) {
                    if (response.isSuccessful()) {
                        Toast.makeText(
                                applicationContext,
                                "Successfully Inserted",
                                Toast.LENGTH_SHORT
                        ).show()
                        mAlertDialog.dismiss()
                        onResume()


                    } else {
                        Toast.makeText(applicationContext, "Error ", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    Toast.makeText(
                            applicationContext, "Error onFailure " + t.message,
                            Toast.LENGTH_LONG
                    ).show()
                }
            })
        }

        mDialogView.cancel.setOnClickListener() {
            mAlertDialog.dismiss()
        }

        }
        fun resetData(view: View) {
            edt_name.text.clear()
            edt_email.text.clear()
            edt_salary.text.clear()
        }
    }
