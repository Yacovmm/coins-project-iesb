package com.example.iesbcoinapp.presentation.base

import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen

class GenericListSkeleton(var recyclerView: RecyclerView, private var layoutId: Int) {

    var skeletonScreen: SkeletonScreen? = null
    var count = 10

    fun showSkeletons() {
        skeletonScreen =
            Skeleton.bind(recyclerView).adapter(recyclerView.adapter).count(count).load(layoutId).show()
    }

    fun hideSkeletons() {
        skeletonScreen?.hide()
        skeletonScreen = null
    }
}
