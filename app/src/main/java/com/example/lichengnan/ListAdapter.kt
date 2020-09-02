package com.example.lichengnan

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author：gaohangbo on 2020/8/31 16:34
 */
class ListAdapter:BaseQuickAdapter<ItemListDto, BaseViewHolder>(R.layout.item_list) {
    override fun convert(holder: BaseViewHolder, item: ItemListDto) {
        //val cardView: CardView =holder.getView(R.id.cardView)
        val text:TextView=holder.getView(R.id.text)
        text.text=item.bookName
    }
}