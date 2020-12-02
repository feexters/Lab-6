import Fox.Fox
import javax.swing.JFrame

fun main(){
//    val game = Fox()
//    val game = Corner.Corner()
//    val game = Checkers.Checkers()
    val game = XO.XO()

    val frame = JFrame().apply {
        add(game.panel)
        setSize(500, 500)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }
}