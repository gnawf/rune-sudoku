package com.gnawf

import com.gnawf.parser.RuneSudokuParser
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(vararg args: String) {
  val path = args.getOrElse(0) default@{ index ->
    // Attempt to use the latest screenshot from RuneLite
    val home = System.getProperty("user.home")
    val screenshots = File(home, ".runelite/screenshots/")

    if (!screenshots.exists()) {
      error("No input image")
    }

    return@default screenshots.listFiles()
      .asSequence()
      // Each directory holds the screenshots for a specific account
      // Get a list of all the files & unwrap the list for traversal
      .filter { it.isDirectory }
      .mapNotNull(File::listFiles)
      .flatMap(Array<File>::asSequence)
      // Only check pictures
      .filter { it.extension == "png" }
      // Grab the most recently modified image
      .sortedBy(File::lastModified)
      .last()
      .absolutePath
  }

  println("Parsing $path")

  val image = File(path).toImage()

  // Parse the incomplete board and solve it
  val sudoku = RuneSudokuParser.parse(image)
  sudoku.solve()

  // Write the solved sudoku board back to the image
  RuneSudokuParser.write(image, sudoku)

  // Write the image back out
  val output = File("output.png")
  ImageIO.write(image, "png", output)
}

fun File.toImage(): BufferedImage {
  return ImageIO.read(this@toImage)
}
