package minesweeper

import kotlin.math.abs
import kotlin.random.Random
import kotlin.system.exitProcess

class Map(mines:Int)
{
    val mineCount = mines
    private val minePositions = MutableList<Int>(0) {0}

    val playerMap:  MutableList<MutableList<Char>> = MutableList(9) { MutableList<Char>(9) {'.'} }
    val map: MutableList<MutableList<Char>> = MutableList(9) { MutableList<Char>(9) {'/'} }


    init {
        var iter = 0
        var posA = 0
        var posB = 0

        for (i in mines downTo 1) {
            do {
                posA = Random.nextInt(0, 9)
                posB = Random.nextInt(0, 9)
            }while (minePositions.contains((posA * 10) + posB))


            val coord = (posA * 10) + posB
            minePositions += coord
        }
        minePositions.forEach()
        {
            val a = it/10
            val b = it%10
            setNumbers(map, a, b)
        }
        minePositions.forEach()
        {
            val a = it/10
            val b = it%10
            map[a][b] = 'X'
        }
    }


    fun printMap()
    {
        var numCounter = 1
        println(" |123456789|\n-|---------|")
        playerMap.forEach { println("${numCounter++}|${it.joinToString("")}|") }
        println("-|---------|")

    }


    private fun numCheck(a: MutableList<MutableList<Char>>, x: Int, y: Int): Char {

        val target = abs((x)*10)+y


            if(!minePositions.contains(target))
            {
                if (a[x][y] == '/' && !minePositions.contains(target))
                {
                    return '1'
                }
                else if (a[x][y].isDigit()) {
                    var f = a[x][y].digitToInt()
                    f+= 1
                    if(f > 8) {f = 9}
                    return f.digitToChar()
                }
                else
                {
                    return a[x][y]
                }
            }
            else
            {
                return a[x][y]
            }
    }


    private fun setNumbers(a: MutableList<MutableList<Char>>, x: Int, y: Int) {

        getArea(a, x, y).forEach()
        {
            val xCord: Int = it / 10
            val yCord: Int = it % 10
            println("x: $xCord y:$yCord")
            map[xCord][yCord] = numCheck(a,xCord,yCord)
        }
    }

    private fun getArea(a: MutableList<MutableList<Char>>, x: Int, y: Int) : List<Int> {
        val xList = mutableMapOf<Int,Int>(
            0 to x-1,
            1 to x  ,
            2 to x+1,
            3 to x-1,
            4 to x+1,
            5 to x-1,
            6 to x  ,
            7 to x+1
        ).filter { (key, value) -> value in 0..8 }
        val yList = mutableMapOf<Int,Int>(
            0 to y+1,
            1 to y+1,
            2 to y+1,
            3 to y  ,
            4 to y  ,
            5 to y-1,
            6 to y-1,
            7 to y-1
        ).filter { (key, value) -> value in 0..8 }
        val returnList = mutableListOf<Int>()
        val listSize: Int = if(xList.size > yList.size) xList.size else yList.size
        repeat(8)
        {
            if(xList.containsKey(it)&&yList.containsKey(it))
            {
                returnList += xList.getValue(it) * 10 + (yList.getValue(it))
            }
        }
        return returnList

    }

    private fun clearOpenSpaces(x: Int, y: Int)
    {
        getArea(map, x, y).forEach()
        {
            if(map[(it/10)][it%10].isDigit())  playerMap[(it/10)][(it%10)] = map[(it/10)][it%10]
        }

        playerMap[x][y] = '/'
        getArea(map, x, y).filter { map[(it/10)][(it%10)] == '/' && playerMap[(it/10)][(it%10)] != '/' }.forEach()
        {
            playerMap[(it/10)][(it%10)] = '/'
            clearOpenSpaces((it/10),(it%10))
        }

    }

    fun playerFree(x: Int, y: Int) {
        when{
            map[x][y] == '/' -> {
                clearOpenSpaces(x,y)
            }
            map[x][y].isDigit() -> {
                playerMap[x][y] = map[x][y]
            }
            map[x][y] == 'X' -> {
                playerMap[x][y] = map[x][y]
                printMap()
                println("You stepped on a mine and failed!")
                exitProcess(976)
            }
        }
    }
    var finalCount = 0
    fun playerMine(x: Int, y: Int)
    {
        val target = (x*10) + y

        if(playerMap[x][y] != '*') {
            playerMap[x][y] = '*'
            if(minePositions.contains(target)) finalCount++
        }
        else {
            playerMap[x][y] = '.'
            if (minePositions.contains(target)) finalCount--
        }



    }
}



fun main()
{
    var PlayerWon = false

    print("How many mines do you want on the field? ")
    val MineMap = Map(readln().toInt())
    MineMap.printMap()

    while(MineMap.finalCount != MineMap.mineCount) {

        print("Set/unset mines marks or claim a cell as free: >")
        var xy = List<String>(3){""}
        do {
            xy = readln().split(" ")
        } while (xy[0].toIntOrNull() == null || xy[1].toIntOrNull() == null)

        if (xy[2] == "free") {
            MineMap.playerFree(xy[1].toInt()-1, xy[0].toInt()-1)
            MineMap.printMap()
        }else if (xy[2] == "mine") {
            MineMap.playerMine(xy[1].toInt()-1, xy[0].toInt()-1)
            MineMap.printMap()
        }
        if(MineMap.finalCount == MineMap.mineCount)
        {
            println("Congratulations! You found all the mines!")
            PlayerWon = true
        }
    }
}






