package com.example.aboolean

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aboolean.databinding.ResponseBinding

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

private const val COLLAPSED_HEIGHT = 230

class BottomFragment : BottomSheetDialogFragment() {
    private val adapter=truthTableAdapter()
    private val dataModel: data by activityViewModels()
    lateinit var binding: ResponseBinding
    private lateinit var inputBoolean:String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = ResponseBinding.bind(inflater.inflate(R.layout.response, container))

        dataModel.expression.observe(activity as LifecycleOwner,{
            inputBoolean=it
        })

        return binding.root
    }




    override fun onStart(){

        super.onStart()


        lateinit var answerBoolean:MutableList<MutableList<Char>>


        answerBoolean= BooleanFunc(inputBoolean)













        val (sknf,sdnf)= SknfAndSdnf(answerBoolean)
        val width=answerBoolean[0].size

        val height=answerBoolean.size
        adapter.width=width
        adapter.allRemove()
        binding.apply {
            textSDNF.isVisible=true
            textSKNF.isVisible=true
            horizant.isVisible=true
            textTable.isVisible=true
            SKNF.text=sknf
            SDNF.text=sdnf

            truthTable.layoutManager= GridLayoutManager(truthTable.context,width)
            truthTable.adapter=adapter
            for (i in 0..height-1){
                for (j in 0..width-1){
                    adapter.addTruth(answerBoolean[i][j].toString())
                }
            }
        }

        val density = requireContext().resources.displayMetrics.density

        dialog?.let {

            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED



        }

    }
}

