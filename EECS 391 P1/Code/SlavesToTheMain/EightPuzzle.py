# EightPuzzle.py created for representing the classic puzzle of the same name
# DateCreated: 9/7/18
# Author: Cormac Dacker (cxd289)

from Code.SlavesToTheMain import AStar
import random
import sys

random.seed(13)
goal = ['b', '1', '2', '3', '4', '5', '6', '7', '8']


def error(errorMessage):
    print(">ERROR:\t" + str(errorMessage))


# Checks if the goal has been met
def isGoal(puzzle):
    for i in range(len(puzzle)):  # itterates through oneD and goal double checking they match
        if goal[i] != puzzle[i]:  # if they dont match
            return False
    return True


class EightPuzzle:

    # Constructor
    def __init__(self, state="random"):
        if state.lower() == "random":  # default to random
            self.randomizeState()
        else:
            try:
                self.setState(state)  # sets state
            except ValueError and TypeError as e:  # if you gave a screwy state
                error(e)
                # TODO double check user inputed states are solvable
                print('Setting state to random instead')
                self.randomizeState()

    def validState(self, state):
        # double checks it's of the right length(you can never trust the user)
        if len(state) == 9:
            # loops validates that entered state has all the right tiles
            for i in range(len(state)):
                for j in range(len(goal)):
                    if goal[j] == state[i]:
                        if j == 0:  # ie if the blank tile has been found
                            self.B = i  # location of the blank tile
                        break
                    elif j == 8:
                        raise ValueError("State", state[i],
                                         "not found. Please enter state correctly, 1->8 and a 'b'.")
                continue
            return True
        else:
            raise ValueError("Please format the desiered state correctly, e.g. 'b12 345 678'. Must be of length 9. You entered a string of length",len(state),state)

    # Sets the puzzle state (e.g. "b12 345 678")
    def setState(self, state):
        if self.validState(state):
            self.oneD = list(state.replace(' ', ''))  # finally sets the state to oneD for one dimention

    def randomizeState(self):
        self.setState("b12345678")  # set it as end goal then shuffle to ensure it can be solved
        # loop moves the blank piece 100 times in random directions
        for i in range(100):
            ran = random.randint(1, 5)  # produce a random int between 1 and 4
            try:
                if ran == 1:
                    self.moveUp()
                elif ran == 2:
                    self.moveDown()
                elif ran == 3:
                    self.moveLeft()
                elif ran == 4:
                    self.moveRight()
            except UnboundLocalError:  # if it can't move that way
                i += 1  # don't let it miss a move
                pass

    # creates a string to visually represent the board
    def __str__(self):
        ds = self.oneD
        out = "\n{0}\t{1}\t{2}\n" \
              "{3}\t{4}\t{5}\n" \
              "{6}\t{7}\t{8}\n".format(
            ds[0], ds[1], ds[2],
            ds[3], ds[4], ds[5],
            ds[6], ds[7], ds[8])
        print(out)

    # Prints a 1D string of length 9
    def printState(self):
        st = ''.join(self.oneD)
        return st

    # Moves the tile up, down, left, right
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def move(self, setting=1):
        up, down, left, right = None, None, None, None

        try:
            up = self.moveUp(setting)

        except UnboundLocalError:  # cant go up
            pass
        try:
            down = self.moveDown(setting)
        except UnboundLocalError:  # cant go down
            pass
        try:
            left = self.moveLeft(setting)
        except UnboundLocalError:  # cant go left
            pass
        try:
            right = self.moveRight(setting)
        except UnboundLocalError:  # cant go right
            pass
        if setting == 0:
            return [up, down, left, right]

    # Moves the blank tile up
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def moveUp(self, setting=1):
        if (self.B - 3) < 0:  # check to see if it's in bounds
            raise UnboundLocalError
        else:
            if setting == 1:
                # performs a switch
                temp = self.oneD[self.B - 3]
                self.oneD[self.B - 3] = self.oneD[self.B]
                self.oneD[self.B] = temp
                # update B's location
                self.B = self.B - 3
            else:
                probe = EightPuzzle(self.printState())
                probe.moveUp()
                return probe.printState()

    # Moves the blank tile down
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def moveDown(self, setting=1):
        if (self.B + 3) > 8:  # check to see if it's in bounds
            raise UnboundLocalError
        else:
            if setting == 1:
                # performs a switch
                temp = self.oneD[self.B + 3]
                self.oneD[self.B + 3] = self.oneD[self.B]
                self.oneD[self.B] = temp
                # update B's location
                self.B = self.B + 3
            else:
                probe = EightPuzzle(self.printState())
                probe.moveDown()
                return probe.printState()

    # Moves the blank tile left
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def moveLeft(self, setting=1):
        if (self.B % 3) == 0:  # check to see if it's in bounds
            raise UnboundLocalError
        else:
            if setting == 1:
                # performs a switch
                temp = self.oneD[self.B - 1]
                self.oneD[self.B - 1] = self.oneD[self.B]
                self.oneD[self.B] = temp
                # update B's location
                self.B = self.B - 1
            else:
                probe = EightPuzzle(self.printState())
                probe.moveLeft()
                return probe.printState()

    # Moves the blank tile right
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def moveRight(self, setting=1):
        if (self.B + 1) % 3 == 0:  # check to see if it's in bounds
            raise UnboundLocalError
        else:
            if setting == 1:
                # performs a switch
                temp = self.oneD[self.B + 1]
                self.oneD[self.B + 1] = self.oneD[self.B]
                self.oneD[self.B] = temp
                # update B's location
                self.B = self.B + 1
            else:
                probe = EightPuzzle(self.printState())
                probe.moveRight()
                return probe.printState()

    def solve_AStar(self):
        AStar.AStar(self.printState())

    def solve_Beam(self):
        print('temp')




if __name__ == '__main__':
    if len(sys.argv) > 1:
        uI = sys.argv[1:]
        for i in range(uI):
            print("TEMP")
