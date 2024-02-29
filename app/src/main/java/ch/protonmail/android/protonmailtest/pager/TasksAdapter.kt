package ch.protonmail.android.protonmailtest.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.protonmail.android.protonmailtest.databinding.ItemTaskBinding
import ch.protonmail.android.protonmailtest.databinding.ItemTaskDatesBinding
import ch.protonmail.android.protonmailtest.util.loadWithPlaceholder
import ch.protonmail.uimodels.TaskUI
import coil.ImageLoader

typealias TaskClicked = (TaskUI) -> Unit

object DiffCallbackImpl : DiffUtil.ItemCallback<TaskUI>() {
    override fun areItemsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
        return oldItem.id == newItem.id && oldItem.shouldDownloadImage == newItem.shouldDownloadImage
    }

    override fun areContentsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
        return oldItem == newItem
    }
}

class TasksAdapter(
    private val imageLoader: ImageLoader,
    private val taskClicked: TaskClicked
) :
    ListAdapter<TaskUI, TasksAdapter.TaskViewHolder>(DiffCallbackImpl) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
            .let {
                TaskViewHolder(
                    binding = it,
                    bindingDates = ItemTaskDatesBinding.bind(it.root)
                )
            }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int): Unit =
        with(holder.binding) {
            currentList[position].let {
                root.setOnClickListener { _ -> taskClicked(it) }
                title.text = it.title
                description.text = it.description
                holder.bindingDates.run {
                    creationDate.text = it.creationDate
                    dueDate.text = it.dueDate
                }
                if (it.shouldDownloadImage) image.loadWithPlaceholder(it.image, imageLoader)
            }
        }

    class TaskViewHolder(
        val binding: ItemTaskBinding,
        val bindingDates: ItemTaskDatesBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
