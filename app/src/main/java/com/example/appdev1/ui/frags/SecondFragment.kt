package com.example.appdev1.ui.frags

import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.appdev1.R
import com.example.appdev1.base.BaseFragment
import com.example.appdev1.data.model.Model
import com.example.appdev1.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment<FragmentSecondBinding>(R.layout.fragment_second) {

    override val binding by viewBinding(FragmentSecondBinding::bind)

    private var currentQuestionIndex = 0
    private var score = 0

    override fun initialize() {
        binding.btnNext.setOnClickListener {
            val answer = binding.etAnswer.text.toString()
            checkAnswer(answer)
        }

        binding.btnReturn.setOnClickListener {
            resetGame()
        }

        displayQuestion()
    }

    private fun displayQuestion() {
        if (currentQuestionIndex < model.size) {
            val model = model[currentQuestionIndex]
            binding.questionText.text = model.question
            binding.etAnswer.text.clear()
        } else {
            endGame()
        }
    }

    private fun checkAnswer(answer: String) {
        val model = model[currentQuestionIndex]
        if (answer.equals(model.answer, ignoreCase = true)) {
            score++
        }
        currentQuestionIndex++
        binding.tvScore.text = "Счет: $score"
        displayQuestion()
    }

    private fun endGame() {
        binding.questionText.text = "Ваш счет: $score из ${model.size}"
        binding.btnReturn.visibility = View.VISIBLE
        binding.cvAnswer.visibility = View.GONE
        binding.btnNext.visibility = View.GONE
        binding.tvScore.visibility = View.GONE
    }

    private fun resetGame() {
        binding.btnReturn.visibility = View.GONE
        binding.cvAnswer.visibility = View.VISIBLE
        binding.btnNext.visibility = View.VISIBLE
        binding.tvScore.visibility = View.VISIBLE
        currentQuestionIndex = 0
        score = 0
        binding.tvScore.text = "Счет: 0"
        displayQuestion()
    }

    private val model = listOf(
        Model("Какая страна проводила первый чемпионат мира по футболу?", "Уругвай"),
        Model(
            "Сколько игроков находится на поле у каждой команды во время футбольного матча?",
            "11"
        ),
        Model("Какой клуб является рекордным победителем Лиги Чемпионов УЕФА?", "Реаль Мадрид"),
        Model("Какой футболист получил прозвище \"Божья рука\"?", "Диего Марадона"),
        Model(
            "Какая команда является самой титулованной в истории английского футбола?",
            "Ливерпуль"
        ),
        Model("Какой футболист признан лучшим игроком 21 века?", "Лионель Месси"),
        Model("Какой клуб выиграл большинство чемпионатов Англии?", "Манчестер Юнайтед"),
        Model(
            "Какой год считается годом основания ФИФА (Международной федерации футбола)?",
            "1904"
        ),
        Model(
            "Какой стадион является самым большим по вместимости в мире?",
            "Стадион Рунгнади, Пюре, Индия"
        ),
        Model(
            "Какой футболист забил больше всего голов в истории Чемпионатов мира?",
            "Мирослав Клозе"
        ),
        Model(
            "Какая страна является рекордсменом по количеству выигранных Чемпионатов мира?",
            "Бразилия"
        ),
        Model("Какой клуб считается основателем футбола?", "Шеффилд Футбол Клуб"),
        Model("Сколько минут длится обычный тайм в футболе?", "45 минут"),
        Model("Какой футболист получил прозвище \"Король футбола\"?", "Пеле"),
        Model("Какой клуб выиграл больше всего Лиг Чемпионов УЕФА?", "Реаль Мадрид"),
        Model("Какой город считается родиной футбола?", "Манчестер"),
        Model("Какая страна провела первый официальный футбольный матч?", "Англия"),
        Model(
            "Какой футболист имеет рекорд по количеству забитых голов в одном сезоне Лиги Чемпионов УЕФА?",
            "Лионель Месси"
        ),
        Model("Какой клуб является самым успешным в истории Испанской Ла Лиги?", "Барселона"),
        Model(
            "Как называется турнир, на котором сборные команды соревнуются за звание чемпиона мира?",
            "Чемпионат мира по футболу"
        ),
        Model(
            "Какой футболист считается лучшим среди защитников в истории футбола?",
            "Франц Беккенбауэр"
        ),
        Model(
            "Сколько минут составляет одно время дополнительного времени в футбольном матче?",
            "15"
        ),
        Model(
            "Какой футболист является рекордсменом по количеству голов в одном Чемпионате мира?",
            "Жуст Фонтеин"
        ),
        Model(
            "Какой голкипер стал известным благодаря своим \"невероятным ловлям\"?",
            "Рене Хигита"
        ),
        Model(
            "Какой клуб считается \"гордостью Шотландии\" и \"самым титулованным клубом в Шотландии\"?",
            "Селтик"
        )
    )

}