package com.tolstoy.zurichat.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tolstoy.zurichat.R
import com.tolstoy.zurichat.databinding.ListItemSelectMemberBinding
import com.tolstoy.zurichat.models.MembersData

import com.tolstoy.zurichat.models.User
import com.tolstoy.zurichat.ui.newchannel.NewChannelActivity

class SelectMemberAdapter(private val user: (User) -> Unit):

    RecyclerView.Adapter<SelectMemberAdapter.SelectMemberViewModel>(), Filterable {
    private var members = listOf<MembersData>()

    @SuppressLint("NotifyDataSetChanged")
    fun loadMembers(contacts: List<MembersData>) {
        this.members = contacts
        notifyDataSetChanged()
    }

    lateinit var list: List<User>
    private val _list: List<User> by lazy { list }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectMemberViewModel {
        val binding = ListItemSelectMemberBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SelectMemberViewModel(binding)
    }

    override fun onBindViewHolder(holder: SelectMemberViewModel, position: Int) {
        holder.apply{
            bind(list[position])
            itemView.setOnClickListener {
                user(list[position])

            }
        }
    }


    override fun getItemCount(): Int = list.size


    inner class SelectMemberViewModel(val binding: ListItemSelectMemberBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(user: User) {
            binding.channelItemPersonNameTxt.text = if(user.first_name.isEmpty() && user.last_name.isEmpty())
                "No name"
            else "${user.first_name} ${user.last_name}"
            binding.channelItemPersonIcon.setImageResource(R.drawable.ic_kolade_icon)
            binding.channelItemMessageTxt.text = user.email
        }
    }

    override fun getFilter(): Filter {
        return _filter
    }

    val _filter = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterList = mutableListOf<User>()

            for(i in _list) {
                if("${i.first_name}${i.last_name}".contains(constraint?:"",true)) {
                    filterList.add(i)
                }
            }

            if(filterList.isEmpty()) {
                for(i in _list) {
                    if(i.email.contains(constraint?:"",true)) {
                        filterList.add(i)
                    }
                }
            }
            return FilterResults().apply {
                values = filterList
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val resultList = results?.values as MutableList<User>

            if (resultList.isEmpty()) {
                list = _list
                notifyDataSetChanged()
            } else {
                list = results.values as MutableList<User>
                notifyDataSetChanged()
            }

        }

    }
}