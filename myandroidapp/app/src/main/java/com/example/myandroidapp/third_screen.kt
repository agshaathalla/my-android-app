package com.example.myandroidapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class third_screen : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var userAdapter: usersAdapter
    private lateinit var backButton: Button
    private lateinit var nama: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_third_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nama = arguments?.getString("name").toString()

        recyclerView = view.findViewById(R.id.recycler_view)
        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh)
        backButton = view.findViewById<Button>(R.id.back)

        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        loadData()
    }

    private fun loadData() {
        val apiService = ApiClient.create()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getUsers(1, 10)
                if (response.isSuccessful) {
                    val userDataList = response.body()?.data ?: emptyList()
                    withContext(Dispatchers.Main) {
                        userAdapter = usersAdapter(userDataList, object : usersAdapter.OnItemClickListener {
                            override fun onItemClick(userData: UserData) {
                                navigateToSecondScreen("${userData.firstName} ${userData.lastName}", nama)
                            }
                        })
                        recyclerView.adapter = userAdapter
                    }
                } else {
                    Log.e("third_screen", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("third_screen", "Exception: ${e.message}")
            } finally {
                // Hentikan indikator refresh setelah selesai memuat ulang data
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun navigateToSecondScreen(name: String?, nama: String?) {
        val bundle = Bundle()
        bundle.putString("fullname", name)
        bundle.putString("name", nama)
        val fragment = second_screen().apply {
            arguments = bundle
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
