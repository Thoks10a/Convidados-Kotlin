package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    //Importing GuestFormViewModel
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //To relate GuestFormViewModel to GuestFormActivity (If activity die then viewModel will die)
        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.GuestFormButtonSaveID.setOnClickListener(this)
        binding.GuestFormRadioButtonPresentID.isChecked = true

        observe()
        loadData()

    }

    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id == R.id.GuestFormButtonSaveID){
                val nome = binding.GuestFormNameInputID.text.toString()
                val presence = binding.GuestFormRadioButtonPresentID.isChecked

                val model = GuestModel(guestId, nome, presence)

                viewModel.save(model)

                finish()
            }
        }
    }

    private fun observe(){
        viewModel.guest.observe(this) {
            binding.GuestFormNameInputID.setText(it.name)
            if(it.presence){
                binding.GuestFormRadioButtonPresentID.isChecked = true
            } else {
                binding.GuestFormRadioButtonAbsentID.isChecked = true
            }
        }

        viewModel.saveGuest.observe(this, Observer {
            if(it){
                if(guestId == 0){
                    Toast.makeText(this, "Inserido com sucesso!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_LONG).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Falha!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadData(){
        val bundle = intent.extras
        if(bundle != null){
            guestId = bundle.getInt(DataBaseConstants.Guest.ID)
            viewModel.get(guestId)
        }
    }

}
