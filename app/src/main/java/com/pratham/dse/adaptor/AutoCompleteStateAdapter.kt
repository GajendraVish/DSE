package com.pratham.dse.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.pratham.dse.R
import com.pratham.dse.models.StateModel
import java.util.*

class AutoCompleteStateAdapter(
    context: Context,
    private val viewResourceId: Int,
    private val items: ArrayList<StateModel>
) : ArrayAdapter<StateModel>(context, viewResourceId, items) {

    private lateinit var itemsAll: ArrayList<StateModel>
    private lateinit var suggestions: ArrayList<StateModel>

    internal var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): String? {
            return (resultValue as StateModel).stateName
        }

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            if (constraint != null) {
                suggestions.clear()

                for (customer in itemsAll) {
                    if (customer.stateName!!.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(customer)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                return filterResults
            } else {
                return Filter.FilterResults()
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.count > 0) {
                val filteredList = results?.values as ArrayList<StateModel>
                clear()
                for (c in filteredList) {
                    add(c)
                }
                notifyDataSetChanged()
            }


        }
    }

    init {
        this.itemsAll = items.clone() as ArrayList<StateModel>
        this.suggestions = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView
        if (v == null) {
            val vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(viewResourceId, null)
        }
        val stateModel = items[position]
        if (stateModel != null) {
            val customerNameLabel = v!!.findViewById<View>(R.id.item) as TextView
            if (customerNameLabel != null) {
                customerNameLabel.text = stateModel.stateName
            }
        }
        return v!!
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

}
