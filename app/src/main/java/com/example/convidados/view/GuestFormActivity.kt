package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    //Importing GuestFormViewModel
    private lateinit var viewModel: GuestFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //To relate GuestFormViewModel to GuestFormActivity (If activity die then viewModel will die)
        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.GuestFormButtonSaveID.setOnClickListener(this)
        binding.GuestFormRadioButtonPresentID.isChecked = true
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id == R.id.GuestFormButtonSaveID){
                val nome = binding.GuestFormNameInputID.text.toString()
                val presence = binding.GuestFormRadioButtonPresentID.isChecked

                val model = GuestModel(0, nome, presence)
                //viewModel.insert(GuestModel(0, nome, presence))
                viewModel.insert(model)
            }
        }
    }
}
