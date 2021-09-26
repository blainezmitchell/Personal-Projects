import requests
import json
import random

# get songs from backend
response = requests.get("http://127.0.0.1:5000/songs")

# transform data from string to dictionary
response = json.loads(response.text)['data']

class Song:

    '''
    Song class to store all data related to an instance of a song in one place
    @param position - position of song in Hot 100
    @param title - title of song
    @param artist - artist of song
    '''
    def __init__(self, position, title, artist):
        self.position = position
        self.title = title
        self.artist = artist

    '''
    Str method to easily print a song
    @return a string containing the title and artist of a song in a nicely formatted way
    '''
    def __str__(self):
        return 'Title: ' + str(self.title) + ', Artist: ' + str(self.artist)

# create the list of songs using the data from the backend and the Song class
songs_list = [Song(response['Position'][str(i)], response['Title'][str(i)], response['Artist'][str(i)]) for i in range(0, 100)]
correct = 0
incorrect = 0

# keep going until the user wants to stop
# emulates a do while loop by putting the exit condition at the end with a break statement
# therefore, the user will always play one game, and can keep playing as many times as they want
while True:

    # randomly choose three songs from the list of songs
    # to do this, first choose three random numbers between 0 and 99
    random_choices = random.sample(range(0, 100), 3)
    # then use those random numbers as indices in the songs_list array and get three random songs
    songs_to_guess = [songs_list[i] for i in random_choices]

    # get guesses from the user
    guesses = []
    print('\nGuess the order of the following three songs:\n')
    # first print the list of songs
    for i in range(0, len(songs_to_guess)):
        print(str(i + 1) + " - " + str(songs_to_guess[i]))
    # then allow the user to guess
    for i in range(0, len(songs_to_guess)):
        print('\nGuess the song that is #' + str(i + 1))
        # add their guess to the list of guesses
        guesses.append(int(input()))
    # convert the user's guesses to actual songs
    guesses = [songs_to_guess[i - 1] for i in guesses]
    
    # figure out if the user is correct, and print output accordingly
    # sort the songs_to_guess list by position to obtain the correct ordering of the songs
    correct_answer = sorted(songs_to_guess, key=lambda Song: Song.position)
    # if the user's guess is correct, tell them, or else let them know the correct answer
    if guesses == correct_answer:
        print("\nThat's correct!")
        correct += 1
    else:
        print("\nThat is incorrect. The correct answer was:\n")
        for i in range(0, len(correct_answer)):
            print("#" + str(i + 1) + " - " + str(correct_answer[i]))
        incorrect += 1
    
    # exit logic allows for the user to decide when the game ends
    print('\nWould you like to play again? (y/n)')
    if input() != 'y':
        break

# once the user is done, display how many games they played and how often they were correct/incorrect
print('\nYou played a total of ' + str(correct + incorrect) + ' games.')
print('Of those games, you were correct ' + str(correct) + ' times and incorrect ' + str(incorrect) + ' times.')
print('Thanks for playing!')