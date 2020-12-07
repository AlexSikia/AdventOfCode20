const { sum } = require('ramda');

fs = require('fs');
r = require('ramda')

const forms = fs.readFileSync('forms.txt', 'utf-8').split('\n\n').map(group => group.split('\n')); // [[String]]

function countAnswersPerGroup(groupAnswers) {
    const groupSize = groupAnswers.length
    let answersBitMap = (new Array(26)).fill(0)
    groupAnswers.forEach(answers => answers.split('').forEach(letter => answersBitMap[letter.charCodeAt(0) - 97] += 1));
    return answersBitMap.filter(letterCount => letterCount === groupSize).reduce((acc, next) => acc + 1, 0)
}

console.log(sum(forms.map(countAnswersPerGroup)))