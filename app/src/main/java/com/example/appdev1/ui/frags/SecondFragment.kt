package com.example.appdev1.ui.frags

import android.view.View
import android.widget.RadioButton
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.appdev1.R
import com.example.appdev1.base.BaseFragment
import com.example.appdev1.data.model.Model
import com.example.appdev1.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment<FragmentSecondBinding>(R.layout.fragment_second) {

    override val binding by viewBinding(FragmentSecondBinding::bind)

    private var currentQuestionIndex = 0
    private var score = 0

    override fun initialize() = with(binding) {
        btnNext.setOnClickListener {
            val selectedAnswerIndex = radioGroup.checkedRadioButtonId
            checkAnswer(selectedAnswerIndex)
        }

        btnReturn.setOnClickListener {
            resetGame()
        }

        displayQuestion()
    }

    private fun displayQuestion() = with(binding) {
        if (currentQuestionIndex < model.size) {
            val model = model[currentQuestionIndex]
            questionText.text = model.question
            radioGroup.removeAllViews()
            model.options.forEachIndexed { index, option ->
                val radioButton = RadioButton(context)
                radioButton.text = option
                radioButton.id = index
                radioGroup.addView(radioButton)
            }
            radioGroup.clearCheck()
        } else {
            fl.visibility = View.VISIBLE
            cvQuestionText.visibility = View.GONE
            endGame()
        }
    }

    private fun checkAnswer(selectedAnswerIndex: Int) = with(binding) {
        val model = model[currentQuestionIndex]
        if (selectedAnswerIndex == model.correctAnswerIndex) {
            score++
        }
        currentQuestionIndex++
        tvScore.text = "Счет: $score"
        displayQuestion()
    }

    private fun endGame() = with(binding) {
        result.text = "Ваш счет: $score из ${model.size}"
        btnReturn.visibility = View.VISIBLE
        radioGroup.visibility = View.GONE
        btnNext.visibility = View.GONE
        tvScore.visibility = View.GONE
    }

    private fun resetGame() = with(binding) {
        fl.visibility = View.GONE
        btnReturn.visibility = View.GONE
        radioGroup.visibility = View.VISIBLE
        btnNext.visibility = View.VISIBLE
        tvScore.visibility = View.VISIBLE
        currentQuestionIndex = 0
        score = 0
        tvScore.text = "Счет: 0"
        displayQuestion()
    }

    private val model = listOf(
        Model(
            "Какая команда НБА выиграла наибольшее количество чемпионатов в истории?",
            listOf(
                "Лос-Анджелес Лейкерс",
                "Бостон Селтикс",
                "Чикаго Буллс",
                "Голден Стейт Уорриорз"
            ),
            correctAnswerIndex = 1
        ),
        Model(
            "Кто удерживает рекорд по наибольшему количеству набранных очков в одной игре НБА?",
            listOf("Кобе Брайант", "Майкл Джордан", "Уилт Чемберлен", "Леброн Джеймс"),
            correctAnswerIndex = 2
        ),
        Model(
            "Какая высота регулярного баскетбольного кольца в НБА?",
            listOf("9 футов", "9.5 футов", "10 футов", "10.5 футов"),
            correctAnswerIndex = 2
        ),
        Model(
            "Какой игрок известен как \"Греческий фрик\"?",
            listOf("Джеймс Харден", "Кавай Леонард", "Лука Дончич", "Яннис Антетокунмпо"),
            correctAnswerIndex = 3
        ),
        Model(
            "Кто является лидером по набранным очкам в истории НБА?",
            listOf("Карим Абдул-Джаббар", "Майкл Джордан", "Леброн Джеймс", "Кобе Брайант"),
            correctAnswerIndex = 0
        ),
        Model(
            "Какова продолжительность игры в НБА, исключая овертайм?",
            listOf("40 минут", "45 минут", "48 минут", "50 минут"),
            correctAnswerIndex = 2
        ),
        Model(
            "Какая команда НБА выбрала Дирика Новицки в 1998 году, прежде чем его обменяли в Даллас Маверикс?",
            listOf("Сан-Антонио Спёрс", "Лос-Анджелес Лейкерс", "Милуоки Бакс", "Бостон Селтикс"),
            correctAnswerIndex = 0
        ),
        Model(
            "Кто удерживает рекорд по наибольшему количеству трипл-даблов в истории НБА?",
            listOf("Мэджик Джонсон", "Оскар Робертсон", "Леброн Джеймс", "Расселл Уэстбрук"),
            correctAnswerIndex = 1
        ),
        Model(
            "Какая страна известна производством множества талантливых баскетболистов, включая Яо Минга и Ий Цзяньляна?",
            listOf("Индия", "Китай", "Бразилия", "Испания"),
            correctAnswerIndex = 1
        ),
        Model(
            "Каков термин, используемый для успешной попытки броска с штрафной линии после фола в НБА?",
            listOf("Трёхочковый", "Данк", "И еще один", "Быстрый брейк"),
            correctAnswerIndex = 2
        ),
        Model(
            "Какой баскетболист известен под именем \"Адмирал\"?",
            listOf("Дэвид Робинсон", "Хаким Олаювон", "Шакилл О\"Нил", "Дуайт Хауард"),
            correctAnswerIndex = 0
        ),
        Model(
            "Сколько игроков находится на площадке в каждой команде во время игры в НБА?",
            listOf("5", "6", "7", "8"),
            correctAnswerIndex = 0
        ),
        Model(
            "Какой баскетболист получил награду \"Лучший новичок НБА\" в сезоне 2020-2021?",
            listOf("Джа Морант", "Зайон Уильямсон", "ЛаМело Болл", "Антони Эдвардс"),
            correctAnswerIndex = 2
        ),
        Model(
            "Кто является лидером НБА по результативным передачам?",
            listOf("Джон Стоктон", "Стив Нэш", "Джейсон Кидд", "Крис Пол"),
            correctAnswerIndex = 0
        ),
        Model(
            "Какая команда выиграла чемпионат НБА в \"пузыре\" в 2020 году?",
            listOf("Лос-Анджелес Лейкерс", "Майами Хит", "Бостон Селтикс", "Лос-Анджелес Клипперс"),
            correctAnswerIndex = 0
        ),
        Model(
            "Кто известен своим сигнатурным броском \"Скай-хук\" в НБА?",
            listOf("Карим Абдул-Джаббар", "Ларри Берд", "Тим Дункан", "Кобе Брайант"),
            correctAnswerIndex = 0
        ),
        Model(
            "С какой командой НБА чаще всего ассоциируется драка \"Мэлис в палас\" в 2004 году?",
            listOf("Детройт Пистонс", "Индиана Пейсерс", "Лос-Анджелес Лейкерс", "Хьюстон Рокетс"),
            correctAnswerIndex = 1
        ),
        Model(
            "Какое максимальное количество очков игрок может набрать в одном атакующем действии в обычной игре НБА?",
            listOf("2 очка", "3 очка", "4 очка", "5 очков"),
            correctAnswerIndex = 2
        ),
        Model(
            "Кто выиграл награду \"Самый ценный игрок\" (MVP) НБА в сезоне 2020-2021?",
            listOf("Леброн Джеймс", "Яннис Антетокунмпо", "Кевин Дюрант", "Стефен Карри"),
            correctAnswerIndex = 1
        )
    )
}