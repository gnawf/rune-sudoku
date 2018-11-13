package com.gnawf

import java.util.Arrays

data class Sudoku constructor(val board: Array<IntArray>) {

  init {
    // Ensure the data is 9x9
    assert(board.size == 9)
    board.forEach { assert(it.size == 9) }
  }

  fun display() {

  }

  fun solve(): Boolean {
    for ((y, row) in board.withIndex()) {
      for ((x, value) in row.withIndex()) {
        if (value == 0) {
          for (number in 1..9) {
            if (isOk(x, y, number)) {
              // Place the number then solve the remaining answers
              board[y][x] = number

              if (solve()) {
                // Stop search - solution found
                return true
              } else {
                // Reset the slot if <number> was not the correct answer
                board[y][x] = 0
              }
            }
          }

          // Could not find a value for the current slot
          return false
        }
      }
    }

    return true
  }

  private fun isOk(x: Int, y: Int, value: Int): Boolean {
    // Check rows & column
    for (point in 0..8) {
      if (board[y][point] == value || board[point][x] == value) {
        return false
      }
    }

    // Check current 3x3 square for the value
    val sX = x - x % 3
    val sY = y - y % 3
    for (dy in 0..2) {
      for (dx in 0..2) {
        if (board[sY + dy][sX + dx] == value) {
          return false
        }
      }
    }

    return true
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Sudoku

    if (!Arrays.deepEquals(board, other.board)) return false

    return true
  }

  override fun hashCode(): Int {
    return Arrays.deepHashCode(board)
  }

}
