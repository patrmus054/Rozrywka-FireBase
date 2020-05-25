package com.example.napomocinzynierom.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.rozrywka_firebase.R
import com.example.rozrywka_firebase.data.remote.Authentication
import com.example.rozrywka_firebase.ui.login.LoginActivity
import kotlinx.android.synthetic.main.register_fragment.*
import com.example.rozrywka_firebase.di.ApplicationModule
import com.example.rozrywka_firebase.di.DaggerApplicationComponent
import javax.inject.Inject


class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    @Inject lateinit var authentication: Authentication

    companion object : () -> Fragment {
        fun newInstance() = RegisterFragment()
        override fun invoke(): Fragment {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        bt_new_user_register.setOnClickListener{
            if(simpleValidation())
            {
                DaggerApplicationComponent.builder().applicationModule(ApplicationModule(context!!)).build().inject(this)
//                val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
//                    (activity?.application as AndroidApplication).appComponent
//                }
                authentication.signUp(container!!.context, et_new_user_email.text.toString(),et_new_user_password.text.toString())
                Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(activity, LoginActivity::class.java))
            }else{
                Toast.makeText(activity, "Invalid data", Toast.LENGTH_SHORT).show()
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        tv_sign_in.setOnClickListener{
            startActivity(Intent(activity, LoginActivity::class.java))
        }

    }

    private fun simpleValidation(): Boolean{
        return if (

            //et_new_user_last_name.text.isEmpty() ||
            //et_new_user_first_name.text.isEmpty() ||
            et_new_user_password.text.length < 5
            //et_new_user_login.text.isEmpty()
        ) false else return true
    }


}
