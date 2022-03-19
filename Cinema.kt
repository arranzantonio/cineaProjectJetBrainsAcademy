package cinema

fun menu(): Int {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    print("> ")
    return readln().toInt()
}

fun showEstatistics(data: MutableList<Int>) {
    val percentage = data[0].toDouble()/data[3] * 100
    println()
    println("Number of purchased tickets: ${data[0]}")
    println("Percentage: ${String.format("%.2f", percentage)}%")
    println("Current income: $${data[1]}")
    println("Total income: $${data[2]}")
}

fun buyTicket(room: MutableList<MutableList<Char>>, data: MutableList<Int>) {
    val rows = room.size
    val columns = room[0].size
    var price = 0
    println()
    do {
        var wrongChoose = false
        println("Enter a row number:")
        print("> ")
        val row = readln().toInt()
        println("Enter a seat number in that row:")
        print("> ")
        val column = readln().toInt()
        when {
            row > rows || column > columns -> println("Wrong input!\n")
            room[row-1][column-1] == 'B' -> println("That ticket has already been purchased!\n")
            else -> {
                price = getPrice(row,room.size,room[0].size)
                room[row-1][column-1] = 'B'
                wrongChoose = true
            }
        }
    } while (!wrongChoose)

    println("Ticket price: $$price")

    ++data[0]
    data[1] += price
}

fun showRoom(room: MutableList<MutableList<Char>>) {
    val rows = room.size
    val columns = room[0].size
    println()
    println("Cinema:")
    print(" ")
    for (i in 1..columns) print(" $i")
    println()
    for (i in 0 until rows) {
        print(i + 1)
        for (j in 0 until columns) {
            print(" ${room[i][j]}")
        }
        println()
    }
}

fun getPrice(r: Int, rows: Int, columns: Int): Int {
    return if (rows * columns <= 60)
        10
    else {
        if (r <= rows/2) 10 else 8
    }
}

fun main() {
    println("Enter the number of rows:")
    print("> ")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    print("> ")
    val columns = readln().toInt()

    // Creating a MutableList to hold data
    val data = MutableList(4) {0}
    data[2] = if (rows * columns <= 60)
        rows * columns * 10
    else {
        rows/2 * columns * 10 + (rows - rows/2) * columns * 8
    }
    data[3] = rows * columns
    // Creating a MutableList of MutableList to represent al the seats.
    val room = MutableList(rows) {MutableList(columns) {'S'}}

    var repeat = 2
    while (repeat != 0) {
        when (menu()) {
            1 -> showRoom(room)
            2 -> buyTicket(room, data)
            3 -> showEstatistics(data)
            0 -> repeat = 0
            else -> println("Invalid option!")
        }
    }
}