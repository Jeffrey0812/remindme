package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.R
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Todo
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoListAdapter(todoEvents: TodoEvents) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>(),
    Filterable {

    private var todoList: List<Todo> = arrayListOf()
    private var filteredTodoList: List<Todo> = arrayListOf()
    private val listener: TodoEvents = todoEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredTodoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredTodoList[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: Todo, listener: TodoEvents) {
            itemView.item_title.text = todo.title
            itemView.tv_item_desc.text = todo.description
            itemView.tv_item_date.text = todo.date
            itemView.tv_item_time.text = todo.time

            itemView.iv_item_delete.setOnClickListener {
                listener.onDeleteClicked(todo)
            }

            itemView.setOnClickListener {
                listener.onViewClicked(todo)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                filteredTodoList = if (charString.isEmpty()) {
                    todoList
                } else {
                    val filteredList = arrayListOf<Todo>()
                    for (row in todoList) {
                        if (row.title.toLowerCase().contains(charString.toLowerCase())
                            || row.description?.contains(charString.toLowerCase())!!
                                ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredTodoList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredTodoList = p1?.values as List<Todo>
                notifyDataSetChanged()
            }

        }
    }

    fun setAllTodoItems(todoItems: List<Todo>) {
        this.todoList = todoItems
        this.filteredTodoList = todoItems
        notifyDataSetChanged()
    }

    interface TodoEvents {
        fun onDeleteClicked(todo: Todo)
        fun onViewClicked(todo: Todo)
    }
}