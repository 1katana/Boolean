package com.example.aboolean



import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.aboolean.databinding.ActivityMainBinding
import com.ezylang.evalex.Expression
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.math.pow



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val dataModel: data by viewModels()
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        


    }

    override fun onResume() {
        super.onResume()
        binding.calculate.setOnClickListener(){



            if (binding.editExpression.text.toString().isNotEmpty()){
                val variableChar=(binding.editExpression.text.toString().filter(Char::isLetter))
                if (variableChar.isNotEmpty()){
                    val inputBoolean:String=binding.editExpression.text.toString()

                    try {
                        dataModel.expression.value=binding.editExpression.text.toString()
                        val thread=Thread{
                            Log.d("thread","${Thread.currentThread().name }}")
                            BottomFragment().show(supportFragmentManager, "tag")
                        }
                        thread.start()

                    }
                    catch (e:Exception){
                        Toast.makeText(applicationContext, R.string.Incorrect, Toast.LENGTH_SHORT).show()
                    }


                }
                else{
                    Toast.makeText(applicationContext, R.string.IncorrectInput, Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(applicationContext, R.string.noExpression, Toast.LENGTH_SHORT).show()
            }
        }
    }




}





fun BooleanFunc(inputBoolean: String): MutableList<MutableList<Char>> {


    val variableChar = (inputBoolean.filter(Char::isLetter))
    var name = mutableListOf<Char>()

    for (i in variableChar) {
        if (i !in name) {
            name.add(i)
        }
    }

    val count = (2.0.pow(name.size)).toInt()
    val variables = MutableList(count) { mutableListOf<Char>() }

    name.add('R')
    variables.add(0, name)

    for (i in 0..count - 1) {
        var replacedInput = inputBoolean
        val BooleanValue = (Integer.toBinaryString(i))


        var b = mutableListOf<Char>()

        for (j in BooleanValue) {
            b.add(j)
        }

        while (b.size != name.size - 1) {
            b.add(0, '0')
        }

        for (z in 0..name.size - 2) {

            replacedInput = replacedInput.replace(variables[0][z], b[z])
        }

        val R = eval(replacedInput)

        b.add(R)
        variables[i + 1] = b
    }

    Log.d("answer","${variables.joinToString()}")

    return variables
}



fun eval(txt:String): Char {

    val expression = Expression(txt)

    if(expression.evaluate().getBooleanValue()==true){
        return '1'
    }
    else{
        return '0'
    }
}

fun SknfAndSdnf(truthTable: MutableList<MutableList<Char>>): List<String> {
    val with=truthTable[0].size
    val height=truthTable.size
    var SKNF=""
    var SDNF=""
    Log.d("SKNF","${truthTable.joinToString()}")
    for (i in 0..height-1){
        if (truthTable[i][with-1]=='0'){
            if (SKNF.length!=0){
                SKNF +="∧"
            }
            SKNF+="("
            for (j in 0..with-2){
                if (truthTable[i][j] == '1'){
                    SKNF +="¬"
                }
                SKNF += "${truthTable[0][j]}"

                if (j != with - 2){
                    SKNF += "∨"
                }



            }
            SKNF+=")"
        }
        else if (truthTable[i][with-1]=='1'){
            if (SDNF.length!=0){
                SDNF += "∨"
            }
            SDNF+="("
            for (j in 0..with-2){
                if (truthTable[i][j] == '0'){
                    SDNF+= "¬"
                }
                SDNF += "${truthTable[0][j]}"

                if (j != with - 2){
                    SDNF+= "∧"
                }



            }
            SDNF +=")"
        }
    }
    Log.d("SKNF","${SKNF}")

    return listOf<String>(SKNF,SDNF)

}



