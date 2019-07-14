package com.grosalex.sidetoilets.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grosalex.sidetoilets.R
import com.grosalex.sidetoilets.adapter.ToiletsAdapter
import com.grosalex.sidetoilets.contract.ToiletsContract
import com.grosalex.sidetoilets.model.ToiletData
import com.grosalex.sidetoilets.presenter.ToiletsPresenter
import com.grosalex.sidetoilets.provider.ToiletsProvider

class ToiletsListFragment : Fragment(), ToiletsContract.View {
    private lateinit var loader: ProgressBar
    private lateinit var rvToilets: RecyclerView
    private lateinit var toiletsAdapter: ToiletsAdapter
    private lateinit var presenter: ToiletsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_toilet_fragment, container, false)
        loader = view.findViewById(R.id.loader)
        rvToilets = view.findViewById(R.id.rv_list)
        val layoutManager = LinearLayoutManager(context)
        rvToilets.layoutManager = layoutManager
        toiletsAdapter = ToiletsAdapter(ArrayList())
        rvToilets.adapter = toiletsAdapter

        presenter = ToiletsPresenter(this, ToiletsProvider())
        presenter.getToilets()
        return view
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        loader.visibility = View.GONE
    }

    override fun onLoading() {
        loader.visibility = View.VISIBLE
    }

    override fun onBindToiletsList(list: List<ToiletData>) {
        loader.visibility = View.GONE
        toiletsAdapter.list = list
        toiletsAdapter.notifyDataSetChanged()
    }
}