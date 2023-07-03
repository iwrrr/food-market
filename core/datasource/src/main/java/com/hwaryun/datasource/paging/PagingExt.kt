package com.hwaryun.datasource.paging

import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig

private const val DEFAULT_PAGE_SIZE = 10
private const val PREFETCH_DISTANCE = 2

fun <T : Any> createPager(
    pageSize: Int = DEFAULT_PAGE_SIZE,
    enablePlaceholders: Boolean = false,
    prefetchDistance: Int = PREFETCH_DISTANCE,
    block: suspend (Int) -> List<T>?
): Pager<Int, T> = Pager(
    config = PagingConfig(
        pageSize = pageSize
    ),
    pagingSourceFactory = { BasePagingSource(block = block) }
)

fun LoadState.subscribe(
    doOnError: ((resource: LoadState) -> Unit)? = null,
    doOnLoading: ((resource: LoadState) -> Unit)? = null,
    doOnNotLoading: ((resource: LoadState) -> Unit)? = null,
) {
    when (this) {
        is LoadState.Error -> doOnError?.invoke(this)
        is LoadState.Loading -> doOnLoading?.invoke(this)
        is LoadState.NotLoading -> doOnNotLoading?.invoke(this)
    }
}