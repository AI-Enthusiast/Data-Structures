from Code.SlavesToTheMain import EightPuzzle as ep
from Code.SlavesToTheMain import AStar
def error(errorMessage):
    print(">ERROR:\t" + str(errorMessage))

# main's main
if __name__ == "__main__":
    print('> Welcome')
    print("> Please enter a starting state or type 'random'")
    print('> 123 4b5 678')
    newGame = True
    puzzle = None
    while True:


        userIn = input('>>').lower().split()  # splits the input at every space

        if len(userIn) == 0:
            puzzle = ep.EightPuzzle('random')
            puzzle.solve_AStar()
            newGame = False
        elif userIn[0] == ('state' or 'setState' or 'random'):
            uI = ' '.join(userIn[1:]).lower().replace(' ', '' )
            try:
                puzzle = ep.EightPuzzle(state=uI)
                newGame = False
            except ValueError:
                continue
            puzzle.__str__()
        elif userIn[0] == 'help':
            print("TEMP")
        elif (not newGame):
            if userIn[0] == 'move':
                if len(userIn) == 1:
                    puzzle.move()
                else:
                    uI = ' '.join(userIn[1:]).lower()
                    try:
                        if uI == 'up':
                            puzzle.moveUp()
                        elif uI == 'down':
                            puzzle.moveDown()
                        elif uI == 'left':
                            puzzle.moveLeft()
                        elif uI == 'right':
                            puzzle.moveRight()
                    except UnboundLocalError:
                        error("You can't go that way")
                    puzzle.__str__()
                if (puzzle.isGoal()):
                    print("> You Win! Play again y/n?")
                    userIn = input('>>').lower()  # splits the input at every space
                    if (userIn == ('n' or 'no')):
                        quit()
                    else:
                        newGame = True
                        print("> Please enter a starting state or type 'random'")
                        continue  # goto next iteration in the loop
            elif(userIn[0]) == "solve":
                if userIn[1] == "a-star":
                    AStar.AStar(puzzle.State)
                elif userIn[1] == "beam":
                    ep.solve_Beam(puzzle.State)
                else:
                    AStar.AStar(puzzle.State)

            elif userIn[0] == ("printState" or 'print'):
                puzzle.__str__()
            else:
                print("> Please enter a valid command or type 'help'")
        else:
            print("> Please enter a valid command or type 'help'")