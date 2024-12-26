import javax.swing.JOptionPane
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

// Function to read the word list from the file
def readWordList(filePath) {
    def wordList = []
    new File(filePath).eachLine { line ->
        def parts = line.split(';')
        if (parts.size() == 2) {
            wordList << [chinese: parts[0], english: parts[1]]
        }
    }
    return wordList
}

// Function to display the popup window and ask for user input
def showPopup() {
	def filePath = 'word_list.txt'  // Path to the word list file
    def wordList = readWordList(filePath)
	
    def randomWord = wordList[new Random().nextInt(wordList.size())]
    def chineseWord = randomWord.chinese
    def englishWord = randomWord.english
    def attempts = 0

    while (true) {
        def prompt = attempts >= 3 ? "${englishWord} - : ${chineseWord}" : "${chineseWord}"
        def userInput = JOptionPane.showInputDialog(null, prompt, "Word Quiz", JOptionPane.QUESTION_MESSAGE)
        if (userInput == englishWord) {
            break
        } else {
            attempts += 1
        }
    }
}

// Main function
def main() {
    

    def scheduler = Executors.newScheduledThreadPool(1)
    scheduler.scheduleAtFixedRate({ showPopup() }, 0, 5, TimeUnit.MINUTES)
}

main()