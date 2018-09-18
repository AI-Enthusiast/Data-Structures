import timeit

from Code.SlavesToTheMain import AStar
from Code.SlavesToTheMain import EightPuzzle as ep


def error(errorMessage):
    print(">ERROR:\t" + str(errorMessage))


# main's main
if __name__ == "__main__":
    print('> Welcome')
    print("> Please enter a starting state 'state <state>' or type 'state' for a random state")
    newGame = True
    puzzle = None
    while True:

        # TODO read instructions from file
        userIn = input('>>').lower().split()  # splits the input at every space

        if len(userIn) == 0:  # used for testing
            puzzle = ep.EightPuzzle(state='random', random=65)  # create the puzzle
            start = timeit.default_timer()  # start timer
            ep.solve_AStar(puzzle)  # solve with A*
            stop = timeit.default_timer()  # stop the timer
            print("Time to solve:", stop - start, "seconds")  # print time
            newGame = False
        elif userIn[0] == 'state' or userIn[0] == 'setState':
            uI = ' '.join(userIn[1:]).lower().replace(' ', '')
            try:
                if len(uI) > 0: # if the user provided the state
                    puzzle = ep.EightPuzzle(state=uI)
                else: # create a random puzzle
                    puzzle = ep.EightPuzzle()
                newGame = False

            except ValueError:
                continue
        elif not newGame:
            if userIn[0] == 'move':
                uI = ' '.join(userIn[1:]).lower()
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
                if ep.isGoal(puzzle.State):
                    print("> You Win! Play again y/n?")
                    userIn = input('>>').lower()  # splits the input at every space
                    if userIn == 'n' or 'no':
                        quit()
                    else:
                        newGame = True
                        print("> Please enter a starting state 'state <state>' or type 'state' for a random state")
                        continue  # goto next iteration in the loop
            elif userIn[0] == "solve":
                if userIn[1] == "a-star":
                    AStar.AStar(puzzle.State)
                elif userIn[1] == "beam":
                    ep.solve_Beam(puzzle.State)
                else:
                    AStar.AStar(puzzle)
            elif userIn[0] == "printState" or userIn[0] == 'print':
                puzzle.__str__()
            elif userIn[0] == 'help':
                print("> Valid commands include 'move <direction>', 'solve <algorithm>', 'print' or 'printState'")
            else:
                print("> Please enter a valid command or type 'help'")
        elif userIn[0] == 'help':
            print("> Valid commands include 'state <state>' or 'setState <state>', the '<state>' is optional and if not"
                  " inputted will create a random ")
        else:
            print("> Please enter a valid command or type 'help'")
