package me.blog.korn123.easydiary.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder_simple_checkbox.view.*
import me.blog.korn123.commons.utils.FontUtils
import me.blog.korn123.easydiary.R
import me.blog.korn123.easydiary.extensions.initTextSize
import me.blog.korn123.easydiary.extensions.updateAppViews
import me.blog.korn123.easydiary.extensions.updateTextColors

class SimpleCheckboxAdapter (
        private val activity: Activity,
        private val realmFiles: List<SimpleCheckbox>
) : RecyclerView.Adapter<SimpleCheckboxAdapter.SimpleCheckboxViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleCheckboxViewHolder {
        val viewGroup = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.viewholder_simple_checkbox, parent, false) as ViewGroup
        return SimpleCheckboxViewHolder(activity, viewGroup, this)
    }

    override fun getItemCount(): Int = realmFiles.size

    override fun onBindViewHolder(holder: SimpleCheckboxViewHolder, position: Int) {
        holder.bindTo(realmFiles[position])
    }

    fun onItemHolderClick(itemHolder: SimpleCheckboxViewHolder) {
        itemHolder.itemView.checkbox.isChecked = itemHolder.itemView.checkbox.isChecked.not()
    }

    fun onItemCheckedChange(position: Int, isChecked: Boolean) {
        realmFiles[position].isChecked = isChecked
    }

    class SimpleCheckboxViewHolder(
            activity: Activity, itemView: View, val adapter: SimpleCheckboxAdapter
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        init {
            if (itemView is ViewGroup) {
                itemView.run {
                    activity.updateTextColors(this)
                    activity.initTextSize(this)
                    activity.updateAppViews(this)
                    FontUtils.setFontsTypeface(activity, activity.assets, null, this)
                    setOnClickListener(this@SimpleCheckboxViewHolder)
                    checkbox.setOnCheckedChangeListener(this@SimpleCheckboxViewHolder)
                }
            }
        }

        fun bindTo(simpleCheckbox: SimpleCheckbox) {
            itemView.run {
                title.text = simpleCheckbox.title
                description.text = simpleCheckbox.description
                checkbox.isChecked = simpleCheckbox.isChecked
            }
        }

        override fun onClick(p0: View?) {
            adapter.onItemHolderClick(this)
        }

        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            adapter.onItemCheckedChange(this.adapterPosition, p1)
        }
    }

    data class SimpleCheckbox(var title: String, var description: String, var isChecked: Boolean = false)
}


