package com.example.myandroidapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class second_screen : Fragment(R.layout.fragment_second_screen) {
    private lateinit var name: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = arguments?.getString("name").toString()
        val fullname = arguments?.getString("fullname")

        val nameTextView = view.findViewById<TextView>(R.id.name_text)
        nameTextView.text = name

        val fullnameTextView = view.findViewById<TextView>(R.id.fullname)
        fullnameTextView.text = fullname

        val backButton = view.findViewById<Button>(R.id.back)
//        backButton.setOnClickListener {
////            requireActivity().supportFragmentManager.popBackStack()
//            requireActivity().finish()
//            requireActivity().findViewById<View>(R.id.main_content).visibility = View.VISIBLE
//        }
        backButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        val choose_user_button = view.findViewById<Button>(R.id.choose_user)
        choose_user_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", name)
            val fragment = third_screen().apply {
                arguments = bundle
            }
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }
}
