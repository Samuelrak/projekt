const val SIZE = 3
var board = Array(SIZE) { CharArray(SIZE) { ' ' }}

fun printBoard() {
    for (row in board) {
        for (cell in row) {
            print("|$cell ")
        }
        println()
    }
    println("--------------")
}

fun makeMove(player: Char) {
    while (true) {
        try {
            val input = readLine()!!.split(' ')
            val row = input[0].toInt()
            val column = input[1].toInt()

            if (row in 0 until SIZE && column in 0 until SIZE && board[row][column] == ' ') {
                board[row][column] = player
                break
            } else {
                println("Invalid input or cell is already taken. Please try again.")
            }
        } catch (e: NumberFormatException) {
            println("Invalid input. Please enter valid integers for row and column.")
        } catch (e: IndexOutOfBoundsException) {
            println("Invalid input. Row and column should be within the range of 0 to ${SIZE - 1}. Please try again.")
        }
    }
}

fun checkWin(player: Char): Boolean {
    for (i in 0 until SIZE) {
        if ((0 until SIZE).all { board[i][it] == player } ||
            (0 until SIZE).all { board[it][i] == player }
        ) {
            return true
        }
    }

    return (0 until SIZE).all { board[it][it] == player } ||
            (0 until SIZE).all { board[it][SIZE - 1 - it] == player }
}

fun onGridButtonClick(row: Int, column: Int, currentPlayer: Char): Boolean {
    if (row in 0 until SIZE && column in 0 until SIZE && board[row][column] == ' ') {
        board[row][column] = currentPlayer
        return true
    }
    return false
}

fun checkGameStatus(moves: Int, currentPlayer: Char): String {
    if (checkWin(currentPlayer)) {
        printBoard()
        return "Player $currentPlayer WON!"
    } else if (moves == SIZE * SIZE) {
        return "It is a draw"
    }
    return ""
}

fun resetGame() {
    board = Array(SIZE) { CharArray(SIZE) { ' ' }}
}

fun main() {
    var moves = 0
    var currentPlayer = 'X'
    while (true) {
        printBoard()
        makeMove(currentPlayer)
        moves++
        val gameStatus = checkGameStatus(moves, currentPlayer)
        if (gameStatus.isNotEmpty()) {
            println(gameStatus)
            break
        }
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
    }
}