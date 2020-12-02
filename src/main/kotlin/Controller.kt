interface Controller {
    var checkedCell: Int?
    var moves: ArrayList<Array<String>>

    fun step(from: Int, to: Int): Array<String>?

    fun onClick(index: Int, squares: Array<String>) {
        var newSquares = squares
        if (checkedCell == null)
            checkedCell = index
        else {
            step(checkedCell!!, index)?.let {
                newSquares = it
                moves.add(squares)
            }
            checkedCell = null
        }
    }


    fun goBack(moves: ArrayList<Array<String>>): Array<String> {
        var newSquares = moves[moves.size - 1]
        if (moves.size > 1) {
            newSquares = moves[moves.size - 2]
            moves.removeAt(moves.size - 1)
            return newSquares
        }

        return newSquares
    }
}