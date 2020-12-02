import java.awt.Color
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JPanel

interface View {
    val panel: JPanel
    val buttons : Array<JButton>
    val back: JButton


    fun update(squares: Array<String>, checkedCell: Int?) {
        for ((index, button) in buttons.withIndex()) {
            button.text = squares[index]
            val line = index.div(8)

            if ((index.rem(2) != line.rem(2)))
                button.background = Color.YELLOW
            else
                button.background = Color.WHITE
        }

        checkedCell?.let{
            buttons[it].background = Color.BLUE
        }
    }
}