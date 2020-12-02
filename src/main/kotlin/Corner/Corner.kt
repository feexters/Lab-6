package Corner

import State
import Controller
import View
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JPanel

class Corner(
    white: Array<Int> = arrayOf(0, 1, 2, 8, 9, 10, 16, 17, 18),
    black: Array<Int> = Array(white.size){63-white[it]}
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
    val nextCell = arrayOf(-7, -8, -9, -1, 1, 7, 8, 9)

    override fun step(from: Int, to: Int) =
            if (possibleMoves(squares[from], from).contains(to))
                move(from, to)
            else
                null

    fun possibleMoves(shape: String, from: Int) =
            ArrayList<Int>().apply {
                for (next in nextCell)
                    (checkCell(from + next)
                            ?: checkCell(from + 2 * next))
                            ?.let {
                                add(it)
                            }
            }.toTypedArray()

    fun checkCell(cell: Int) =
            if ((cell >= 0) and (cell < 64))
                if (squares[cell] == " ")
                    cell
                else null
            else null

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