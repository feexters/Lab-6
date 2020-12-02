abstract class State (
    var squares: Array<String> = Array(64) {" "}
) {
    fun move(from:Int, to:Int): Array<String> {
        squares = squares.clone()
        squares[to] = squares[from]
        squares[from] = " "
        return squares
    }

    operator fun get(index: Int)= squares[index]
}