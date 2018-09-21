import argparse
import csv
import os
import timeit

from Code.SlavesToTheMain import AStar
from Code.SlavesToTheMain import EightPuzzle as ep


# Reports an error
def error(errorMessage):
    print(">ERROR:\t" + str(errorMessage))


# Creates a "Command.csv" file
def createFile():
    with open("Command.csv", 'w', newline='\n', encoding='utf8') as csvfile:
        csvfile.close()


# Reads commands from "Command.csv"
def readFile():
    with open("Command.csv", "r", newline='', encoding='utf8') as csvfile:
        DataReader = csv.reader(csvfile, delimiter=",", quotechar=" ")
        out = []  # you're beautiful (shhh it's a secrete)
        for Item in DataReader:
            out.append(Item)
        csvfile.close()
        return out[0]


# main's main ;)
if __name__ == "__main__":
    newGame = True
    puzzle = None

    print('> Welcome')

    # Commandline interface
    parser = argparse.ArgumentParser(description="Initialize a game and play or watch algorithms solve it")
    parser.add_argument("-setState", help="e.g. 'b12 345 678' or 'b12345678' or 'random'", dest="state", type=str,
                        default="random")
    parser.add_argument("-rand", help="Sets number of random moves to create the random board", dest='random', type=int)
    parser.add_argument("-aStar", help="Solves the puzzle A* style", dest="a_star", type=bool)
    parser.add_argument("-beam", help="Solves the puzzle Beam style", dest="beam", type=bool)
    parser.add_argument("-print", help="Prints the current state of the puzzle", dest="print", type=bool)

    if not os.path.isfile("Command.csv"):  # looks for the commands
        error("Command.csv could not be found. Creating file now")
        createFile()

    commands = readFile()  # read commands

    # This is all one big loop for user commands
    for cmd in range(len(commands)):
        userIn = str(commands[cmd]).split(' ')
        print("\n>>", ' '.join(userIn))

        if userIn[0] == 'state' or userIn[0] == 'setState':  # if the user commands a state
            uI = ' '.join(userIn[1:]).lower().replace(' ', '')
            try:
                if len(uI) > 0:  # if the user provided the state
                    puzzle = ep.EightPuzzle(state=uI)
                else:  # create a random puzzle
                    puzzle = ep.EightPuzzle()
                newGame = False
            except ValueError:
                continue

        elif not newGame:  # There is an incomplete game in place (no way out but to win)

            if userIn[0] == 'move':  # user commands a move in a direction
                uI = ' '.join(userIn[1:]).lower()  # unite all from 1 onward
                try:
                    if uI == 'up':
                        ep.moveUp(puzzle)
                    elif uI == 'down':
                        ep.moveDown(puzzle)
                    elif uI == 'left':
                        ep.moveLeft(puzzle)
                    elif uI == 'right':
                        ep.moveRight(puzzle)
                    else:
                        print("> Please enter a valid move, eg: 'move left'")
                except UnboundLocalError:
                    error("You can't go that way")
                puzzle.__str__()

                if ep.isGoal(puzzle.State):  # WIN! NEW GAME
                    print("> You Win! Play again y/n?")
                    userIn = input('>>').lower()  # splits the input at every space

                    if userIn == 'n' or 'no':
                        quit()
                    else:
                        newGame = True
                        print("> Please enter a starting state 'state <state>' or type 'state' for a random state")
                        continue  # goto next iteration in the loop

            elif userIn[0] == "solve":  # user commands to solve
                start = timeit.default_timer()  # start timer
                if userIn[1] == "a-star" or userIn[1] == "aStar" or userIn[1] == "astar":  # A* style
                    AStar.AStar(puzzle)
                elif userIn[1] == "beam":  # Beam style
                    print("TEMP, Solve beam you FUcK")  # TODO BEAM
                else:
                    error("Please enter a valid command or type 'help'")
                stop = timeit.default_timer()  # stop the timer
                print("Time to solve:", stop - start, "seconds")  # print time

            elif userIn[0] == "printState" or userIn[0] == 'print':  # user commands a print
                puzzle.__str__()

            elif userIn[0] == 'help':  # user needs help with commands
                print("> Valid commands include 'move <direction>', 'solve <algorithm>', 'print' or 'printState'")
            else:
                error("Please enter a valid command or type 'help'")

        elif userIn[0] == 'help':  # user needs help with commands
            print("> Valid commands include 'state <state>' or 'setState <state>', the '<state>' is optional and if not"
                  " inputted will create a random state. format the <state> = 'b12345678' or 'b12 345 678'")
        else:
            error("Please enter a valid command or type 'help'")
