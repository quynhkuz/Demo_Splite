package com.example.demosplite

import android.os.Bundle
import android.widget.Toast
import com.example.demosplite.databinding.ActivityInsertDataBinding

class InsertDataActivity : BaseActivity<ActivityInsertDataBinding>(ActivityInsertDataBinding::inflate) {

    lateinit var sv : DataModel
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            type = it.getString(KEY_DATA).toString()
            if (type == INSERT)
            {
                binding.btnUpdate.text = "Them"
            }
            else
            {
                binding.btnUpdate.text= "Sua"
            }

            val s = it.getSerializable(KEY_MODEL)
            if (s==null)
            {
                sv = DataModel(System.currentTimeMillis().toInt(),"","",0f,0f,0f)
            }
            else
            {
                sv = s as DataModel
                binding.edtSbd.setText(sv.sbd)
                binding.edtHoten.setText(sv.hoten)
                binding.edtDiemtoan.setText(sv.toan.toString())
                binding.edtDiemly.setText(sv.ly.toString())
                binding.edtDiemhoa.setText(sv.hoa.toString())
            }
        }





        binding.btnUpdate.setOnClickListener {

            if (type == INSERT)
            {
                getData()
                if (sv.sbd.isEmpty() || sv.hoten.isEmpty())
                {
                    Toast.makeText(this,"Thong tin khong day du",Toast.LENGTH_LONG).show()
                }
                else
                {
                    myDataBase.Insert(sv)
                    setResult(RESULT_OK)
                    finish()
                }
            }
            else
            {
                getData()
                if (sv.sbd.isEmpty() || sv.hoten.isEmpty())
                {
                    Toast.makeText(this,"Thong tin khong day du",Toast.LENGTH_LONG).show()
                }
                else
                {
                    myDataBase.updateSV(sv)
                    setResult(RESULT_OK)
                    finish()
                }
            }

        }

        binding.btnBack.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

    }

    private fun getData() {
        sv.sbd = binding.edtSbd.text.toString()
        sv.hoten = binding.edtHoten.text.toString()

        if (binding.edtDiemtoan.text.toString().isEmpty())
        {
            sv.toan = 0f
        }
        else
        {
            sv.toan = binding.edtDiemtoan.text.toString().toFloat()
        }

        if (binding.edtDiemly.text.toString().isEmpty())
        {
            sv.ly = 0f
        }
        else
        {
            sv.ly = binding.edtDiemly.text.toString().toFloat()
        }

        if (binding.edtDiemhoa.text.toString().isEmpty())
        {
            sv.hoa = 0f
        }
        else
        {
            sv.hoa = binding.edtDiemhoa.text.toString().toFloat()
        }

    }


}