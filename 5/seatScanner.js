fs = require('fs');
r = require('ramda')

function letterToBinary(letter) {
    if (letter === 'F' || letter === 'L') return 0
    return 1
}

function binaryToSeat(binaryPass) {
    const binaryColumn = r.take(7, [...binaryPass]).map(letterToBinary).join('')
    const binaryRow = r.drop(7, [...binaryPass]).map(letterToBinary).join('')
    columnRow = [parseInt(binaryColumn, 2), parseInt(binaryRow, 2)]
    // console.log(columnRow)
    return columnRow
}

const seatToId = (row, column) => row * 8 + column

function findTakenSeats() {
    const passes = fs.readFileSync('passes.txt', 'utf-8').split('\n');
    return passes.map(pass => seatToId(...binaryToSeat(pass))).sort((a, b) => a - b)
}

function findFreeSeat(takenSeatIds) {
    return takenSeatIds.find((seat, index) => (takenSeatIds[index + 1] - seat === 2)) + 1
}

console.log(findFreeSeat(findTakenSeats()))