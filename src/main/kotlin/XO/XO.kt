package XO

import State
import Controller
import View
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JPanel

class XO() : View, Controller, State() {
    override var checkedCell: Int? = null
    override var moves = arrayListOf(squares)
    var symbols = arrayOf("X", "0") // Символы для расстановки
    var length = 8 // Длина ряда поля
    var scope = length - 5 // Разница между размером поля и необходимым рядом
    var direct = arrayOf(8, 1) // Шаги для прямых
    var diag = arrayOf(9, 7) // Шаги для диагоналей
    var player = 0

    /* Ставит символ на выбранное поле */
    override fun onClick(index: Int, squares: Array<String>) {
        if (possiblePuts(index)) {
            /* Передаем результат хода */
            win(putSymbol(index))
            update(squares, checkedCell)
        }
    }
    override fun step(from: Int, to: Int): Array<String>? {
        return null
    }

    /* Расстановка символов */
    fun putSymbol(to: Int): String {
        squares = squares.clone()
        squares[to] = symbols[player]
        player = if (player == 0) 1
        else 0
        moves.add(squares)

        return checkWinner()
    }

    fun checkWinner(): String {

            var result = " "

            for (i in 0 until length) {
                /* Проверка горизонтальных и вертикальных линий */
                for (j in 0..1) {
                    result = check(direct[j], i * (j * (length - 1) + 1))
                    if (result != " ") return result
                }
                /* Проверка диагоналей */
                if (i < length / 2) {
                    for (j in 0..1) {
                        /* Проверка верхних диагоналей */
                        result = check(diag[j], (length - 1) * j - i * (j * 2 - 1), length - 1 - i)
                        if (result != " ") return result
                        /* Проверка нижних диагоналей */
                        result = check(diag[j], length * i + (length - 1) * j, length - 1 - i)
                        if (result != " ") return result
                    }
                }
            }

            return result
        }

        /* Проверяет один ряд */
        fun check(step: Int, next: Int = 0, all: Int = (length - 1)): String{
            var combo = 1 // Счетчик комбинаций

            /* Проверка на комбинацию */
            for (i in 0 until all) {
                /* В случае совпадения символов */
                if (squares[i * step + next] != " " &&
                        squares[i * step + next] == squares[(i + 1) * step + next]) {
                    combo++
                    /* Победа */
                    if (combo == 5) {
                        return squares[i * step + next]
                    }
                    /* Если нет смысла дальше, т.е. осталось <5 клеток для проверки */
                } else if (i > scope - (length - (all + 1))) {
                    return " "
                    /* Обнуляем счетчик */
                } else {
                    combo = 1
                }
            }

            return " "
        }

    /* Возможность поставить символ */
    fun possiblePuts(to: Int) : Boolean {
        return squares[to] == " "
    }

    override  val panel = JPanel().apply {
        layout = GridLayout(9, 8)
    }

    override val buttons = Array(64) { index ->
        JButton().also { button ->
            button.text = squares[index]
            button.addActionListener {
                onClick(index, squares)
                update(squares, checkedCell)
            }
            panel.add(button)
        }
    }

    override val back = JButton().also{it ->
        it.text = "<"
        it.addActionListener {
            squares = goBack(moves)
            update(squares, checkedCell)
            player = if (player == 0) 1
                else 0
        }
        panel.add(it)
    }

    /* Результат победы */
    val result = JButton().also{it ->
        it.text = " "
        panel.add(it)
    }

    fun win(win: String = " ") {
        /* В случае победы */
        if (win != " ") {
            result.text = win
            result.background = Color.GREEN
    }
}

    init {
        update(squares, checkedCell)
    }
}