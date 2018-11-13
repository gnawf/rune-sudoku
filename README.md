# Rune Sudoku Solver

Solves [Rune Sudoku](https://oldschool.runescape.wiki/w/Rogue_Trader#Runes) using an image of the initial sudoku board.

## Usage

```
./gradlew run
```

or

```
./gradlew run --args='sudoku.png'
```

If the argument is omitted, then the script uses the most recent Runelite screenshot.

The solution is saved to `output.png` in the current working directory.

## Executable Jar

To build an executable jar run the following command:

```
./gradlew shadowJar
```
