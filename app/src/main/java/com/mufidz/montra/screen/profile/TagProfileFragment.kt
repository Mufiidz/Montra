package com.mufidz.montra.screen.profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.montra.R
import com.mufidz.montra.base.BaseFragment
import com.mufidz.montra.databinding.FragmentTagProfileBinding
import com.mufidz.montra.intention.PreferencesAction
import com.mufidz.montra.utils.initToolbar
import com.mufidz.montra.utils.viewBinding
import com.mufidz.montra.viewmodel.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TagProfileFragment :
    BaseFragment<FragmentTagProfileBinding, PreferencesViewModel>(R.layout.fragment_tag_profile) {

    private val tagAdapter by lazy { TagAdapter() }

    private var listTags = mutableListOf<String>()

    override val viewModel: PreferencesViewModel by viewModels()

    override val binding: FragmentTagProfileBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.apply {
                initToolbar(this@TagProfileFragment)
            }
            rvTag.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = tagAdapter
                itemTouchHelper.attachToRecyclerView(this)
            }
            btnAddTag.setOnClickListener {
                val tag = edtTag.text.toString().trim()
                if (tag.isNotEmpty()) {
                    viewModel.addTag(tag)
                    rvTag.smoothScrollToPosition(tagAdapter.itemCount)
                    edtTag.setText("")
                }
            }

            btnSave.setOnClickListener {
                val tags = tagAdapter.list
                Timber.d(tags.toString())
                viewModel.apply {
                    execute(PreferencesAction.SetTag(tags))
                }
            }
        }

        with(viewModel) {
            execute(PreferencesAction.GetTag)
            viewState.observe(viewLifecycleOwner) {
                Timber.d(it.listTag.toString())
                addAllTag(it.listTag)
            }
            getListTag.observe(viewLifecycleOwner) {
                tagAdapter.list = listTags.ifEmpty { it }
                Timber.tag("tag").d("$listTags")
            }
        }
    }

    private val itemTouchHelper =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                tagAdapter.onMoveItem(from, to)
                viewModel.onMove(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position >= 0) {
                    var isConfirmDelete = true
                    val adapter = binding.rvTag.adapter as TagAdapter
                    val tag = adapter.list[position]
                    adapter.removeAt(position)
                    snackbar("Tag \"$tag\" Berhasil dihapus", true, "Batal") {
                        adapter.updateAt(position, tag)
                        isConfirmDelete = false
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (isConfirmDelete) {
                            viewModel.removeTag(tag)
                        }
                    }, 3000)
                }
            }
        })
}