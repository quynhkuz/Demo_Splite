package com.example.demosplite

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demosplite.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    lateinit var adapter: SinhVienAdapter

    var launch = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode.equals(RESULT_OK)) {
            getData()
            adapter.notifyDataSetChanged()
        }
    }

    lateinit var listData: MutableList<DataModel>
    lateinit var listSearch : MutableList<DataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listData = mutableListOf()
        listSearch = mutableListOf()


        binding.icInsert.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString(KEY_DATA, INSERT)
            val intent = Intent(this,InsertDataActivity::class.java)
            intent.putExtras(mBundle)
            launch.launch(intent)
        }




        adapter = SinhVienAdapter(listData, onDelete = {
            myDataBase.deleteSV(it.id)
            getData()
            adapter.notifyDataSetChanged()
        }, onUpdate = {
//            myDataBase.updateSV(it)
            val mBundle = Bundle()
            mBundle.putString(KEY_DATA, UPDATE)
            mBundle.putSerializable(KEY_MODEL,it)
            val intent = Intent(this,InsertDataActivity::class.java)
            intent.putExtras(mBundle)
            launch.launch(intent)

        })
        binding.rcv.layoutManager = LinearLayoutManager(this)
        binding.rcv.adapter = adapter


        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                   upDateListView(p0)
                }
                else
                {
                    adapter.updateListData(listData)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.tvSapXep.setOnClickListener {
            bubbleSort(listData)
            adapter.updateListData(listData)
        }

    }

    private fun upDateListView(p0: CharSequence) {
        listSearch.clear()

        listData.forEach {
            if (it.hoten.toLowerCase().contains(p0.toString().trim().toLowerCase()))
            {
                listSearch.add(it)
            }
        }
        adapter.updateListData(listSearch)
    }

    override fun onStart() {
        super.onStart()
        getData()
        adapter.notifyDataSetChanged()
    }

    private fun getData() {
        val cursor = myDataBase.DisplayAll()

        listData.clear()
        cursor?.let {
            for (i in 0..<cursor.count) {
                cursor.moveToPosition(i)
                val id = cursor.getInt(0)
                val sbd = cursor.getString(1)
                val ten = cursor.getString(2)
                val toan = cursor.getFloat(3)
                val ly = cursor.getFloat(4)
                val hoa = cursor.getFloat(5)
                listData.add(DataModel(id, sbd, ten, toan, ly, hoa))
            }
        }

    }



    fun bubbleSort(students: MutableList<DataModel>) {
        val n = students.size
        var swapped: Boolean

        for (i in 0 until n - 1) {
            swapped = false
            for (j in 0 until n - i - 1) {
                if (students[j].hoten > students[j + 1].hoten) {
                    // Hoán đổi students[j] và students[j + 1]
                    val temp = students[j]
                    students[j] = students[j + 1]
                    students[j + 1] = temp
                    swapped = true
                }
            }
            // Nếu không có phần tử nào được hoán đổi trong vòng lặp nội bộ, dừng lại
            if (!swapped) break
        }
    }


}