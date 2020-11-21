package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.R
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Category
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.todo_item.view.iv_item_delete

class CategoryListAdapter (catEvents: CategoryEvents) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>(),
    Filterable {

    private var catList: List<Category> = arrayListOf()
    private var filteredCategoryList: List<Category> = arrayListOf()
    private val listener: CategoryEvents = catEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredCategoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredCategoryList[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category, listener: CategoryEvents) {
            itemView.category_title.text = category.name

            itemView.iv_item_delete.setOnClickListener {
                listener.onDeleteClicked(category)
            }

            itemView.setOnClickListener {
                listener.onViewClicked(category)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                filteredCategoryList = if (charString.isEmpty()) {
                    catList
                } else {
                    val filteredList = arrayListOf<Category>()
                    for (row in catList) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase())!!
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredCategoryList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredCategoryList = p1?.values as List<Category>
                notifyDataSetChanged()
            }
        }
    }

    fun setAllCategoryItems(catItems: List<Category>) {
        this.catList = catItems
        this.filteredCategoryList = catItems
        notifyDataSetChanged()
    }

    interface CategoryEvents {
        fun onDeleteClicked(category: Category)
        fun onViewClicked(category: Category)
    }

}