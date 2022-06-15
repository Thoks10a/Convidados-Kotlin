package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.FragmentPresentBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.viewmodel.AllGuestsViewModel

class PresentFragment : Fragment() {

    private var _binding: FragmentPresentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: AllGuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)
        _binding = FragmentPresentBinding.inflate(inflater, container, false)


        //Layout
        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)

        //Adapter
        binding.recyclerGuests.adapter = adapter


        val listener = object : OnGuestListener {
            override fun onClick(id: Int) {
                //Toast.makeText(context,"O id do convidado é: ${id}", Toast.LENGTH_LONG).show()
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.Guest.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getPresent()
                Toast.makeText(context,"Convidado Deletado...", Toast.LENGTH_LONG).show()
            }

        }

        adapter.attachListener(listener)

        observe()

        return binding.root
    }

    override fun onResume(){
        super.onResume()
        viewModel.getPresent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updatedGuests(it)
        }
    }
}
