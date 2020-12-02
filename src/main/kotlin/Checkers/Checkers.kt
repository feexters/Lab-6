package Checkers

import State
import Controller
import View
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JPanel

class Checkers(
    black: Array<Int> = arrayOf(1, 3, 5, 7,
            8, 10, 12, 14,
            17, 19, 21, 23),
    white: Array<Int> = arrayOf(62, 60, 58, 56,
            55, 53, 51, 49,
            46, 44, 42, 40)
) : View, Controller, State(
    Array(64){
        when {
            white.contains(it) -> "W"
            black.contains(it) -> "B"
            else -> " "
        }
    }) {
    override var checkedCell: Int? = null
    override var moves = arrayListOf(squares)

    override fun step(from: Int, to: Int) =
            if (possibleMoves(squares[from], from).contains(to))
                move(from, to)
            else
                null

    fun possibleMoves(shape: String, from: Int) =
        when(shape){
            "W" -> arrayOf(from-7, from-9)
            "B" -> arrayOf(from+7, from+9)
            else -> arrayOf()
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
        }
        panel.add(it)
    }

    init {
        update(squares, checkedCell)
    }
}