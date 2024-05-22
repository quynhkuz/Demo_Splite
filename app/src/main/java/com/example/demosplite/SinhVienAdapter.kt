package com.example.demosplite

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class SinhVienAdapter(
    var list: List<DataModel>,
    var onDelete: (DataModel) -> Unit,
    var onUpdate: (DataModel) -> Unit
) : RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder>() {


    class SinhVienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var sbd: TextView
        lateinit var ten: TextView
        var diem: TextView
        lateinit var vSum: ConstraintLayout

        init {

            sbd = itemView.findViewById(R.id.tv_sbd)
            ten = itemView.findViewById(R.id.tv_ten)
            diem = itemView.findViewById(R.id.tv_diem)

            vSum = itemView.findViewById(R.id.v_sum)
        }
    }


    fun updateListData(l : List<DataModel>)
    {
        list = l
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinhVienViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sinh_vien_adapter, parent, false)
        return SinhVienViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SinhVienViewHolder, position: Int) {

        holder.sbd.text = list[position].sbd
        holder.ten.text = list[position].hoten
        holder.diem.text = "${list[position].toan + list[position].ly + list[position].hoa}"


        holder.vSum.setOnClickListener {
            showMenu(holder.itemView.context, it, onDelete = {
                onDelete(list[position])
            }, onUpdate = {
                onUpdate(list[position])
            })
        }
    }


    fun showMenu(contex: Context, v: View, onDelete: () -> Unit, onUpdate: () -> Unit) {
        var mPopupMenu = PopupMenu(contex, v)
        mPopupMenu.menuInflater.inflate(R.menu.menu_popup, mPopupMenu.menu)

        mPopupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(p0: MenuItem): Boolean {
                when (p0.itemId) {
                    R.id.mn_sua -> {
                        onUpdate()
                    }

                    R.id.mn_xoa -> {
                        Log.d("AAA","delete")
                        onDelete()
                    }
                }
                return false
            }
        })

        mPopupMenu.show()

    }


}