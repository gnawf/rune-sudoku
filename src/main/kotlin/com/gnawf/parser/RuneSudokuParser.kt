package com.gnawf.parser

import com.gnawf.Sudoku
import com.gnawf.toImage
import java.awt.image.BufferedImage
import java.io.File

object RuneSudokuParser {

  fun write(image: BufferedImage, sudoku: Sudoku) {
    val exitSprite = File("assets/sprites/interface/sudoku-x.png").toImage()

    var rootX: Int = Int.MIN_VALUE
    var rootY: Int = Int.MIN_VALUE

    for (y in 0..(image.width - 1)) {
      pixel@ for (x in 0..(image.height - 1)) {
        if (x + exitSprite.width >= image.width || y + exitSprite.height >= image.height) {
          continue
        }

        for (dx in 0..(exitSprite.width - 1)) {
          for (dy in 0..(exitSprite.height - 1)) {
            val lhs = exitSprite.getRGB(dx, dy)
            val rhs = image.getRGB(x + dx, y + dy)
            if (!(lhs == 0 || lhs == rhs)) {
              continue@pixel
            }
          }
        }

        rootX = x + exitSprite.width
        rootY = y
        break
      }
    }

    val runes: Map<Int, BufferedImage> = mapOf(
      1 to File("assets/sprites/runes/chaos.png").toImage(),
      2 to File("assets/sprites/runes/body.png").toImage(),
      3 to File("assets/sprites/runes/death.png").toImage(),
      4 to File("assets/sprites/runes/earth.png").toImage(),
      5 to File("assets/sprites/runes/fire.png").toImage(),
      6 to File("assets/sprites/runes/law.png").toImage(),
      7 to File("assets/sprites/runes/mind.png").toImage(),
      8 to File("assets/sprites/runes/water.png").toImage(),
      9 to File("assets/sprites/runes/air.png").toImage()
    )

    for ((y, row) in sudoku.board.withIndex()) {
      for ((x, value) in row.withIndex()) {
        val rune = runes[value]
        image.graphics.drawImage(rune, rootX + x * 37 + 1, rootY + y * 37 + 2, null)
      }
    }
  }

  fun parse(image: BufferedImage): Sudoku {
    val runes: Map<Int, BufferedImage> = mapOf(
      0 to File("assets/sprites/interface/sudoku-x.png").toImage(),
      1 to File("assets/sprites/runes/chaos.png").toImage(),
      2 to File("assets/sprites/runes/body.png").toImage(),
      3 to File("assets/sprites/runes/death.png").toImage(),
      4 to File("assets/sprites/runes/earth.png").toImage(),
      5 to File("assets/sprites/runes/fire.png").toImage(),
      6 to File("assets/sprites/runes/law.png").toImage(),
      7 to File("assets/sprites/runes/mind.png").toImage(),
      8 to File("assets/sprites/runes/water.png").toImage(),
      9 to File("assets/sprites/runes/air.png").toImage()
    )

    var rootX: Int = Int.MIN_VALUE
    var rootY: Int = Int.MIN_VALUE

    // Create 9x9 array to store sudoku data
    val data = Array(9) { IntArray(9) }

    for (y in 0..(image.width - 1)) {
      for (x in 0..(image.height - 1)) {
        dto@ for ((id, sprite) in runes) {
          if (x + sprite.width >= image.width || y + sprite.height >= image.height) {
            continue
          }
          for (dx in 0..(sprite.width - 1)) {
            for (dy in 0..(sprite.height - 1)) {
              val lhs = sprite.getRGB(dx, dy)
              val rhs = image.getRGB(x + dx, y + dy)
              if (!(lhs == 0 || lhs == rhs)) {
                continue@dto
              }
            }
          }

          if (id == 0) {
            rootX = x + sprite.width
            rootY = y
            continue
          } else if (rootX < 0 || rootY < 0) {
            throw IllegalStateException("Didn't find interface before runes")
          }

          val xSlot = x.minus(rootX).div(37)
          val ySlot = y.minus(rootY).div(37)

          data[ySlot][xSlot] = id

          println("found $id at ${xSlot + 1}, ${ySlot + 1}")
        }
      }
    }

    return Sudoku(data)
  }

}
