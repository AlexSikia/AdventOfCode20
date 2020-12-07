const { sum } = require('ramda');

fs = require('fs');
r = require('ramda')

const forms = fs.readFileSync('forms.txt', 'utf-8').split('\n\n').map(group => group.split('\n')); // [[String]]

function countAnswersPerGroup(groupAnswers) {
    let answersBitMap = new Array(26)
    groupAnswers.forEach(answers => answers.split('').forEach(letter => answersBitMap[letter.charCodeAt(0) - 97] = 1));
    return answersBitMap.reduce((acc, next) => acc + next)
}

console.log(sum(forms.map(countAnswersPerGroup)))