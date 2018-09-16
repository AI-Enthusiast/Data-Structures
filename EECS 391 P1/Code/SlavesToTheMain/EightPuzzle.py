# EightPuzzle.py created for representing the classic puzzle of the same name
# DateCreated: 9/7/18
# Author: Cormac Dacker (cxd289)

import random
import sys

from Code.SlavesToTheMain import AStar

random.seed(13)
goal = ['b', '1', '2', '3', '4', '5', '6', '7', '8']


def error(errorMessage):
    print(">ERROR:\t" + str(errorMessage))


# Checks if the goal has been met
def isGoal(puzzle):
    for i in range(len(puzzle)):  # itterates through State and goal double checking they match
        if goal[i] != puzzle[i]:  # if they dont match
            return False
    return True


class EightPuzzle:

    # Constructor
    # Params: state(as a string of length 9), parent (TEMP TBA), b(location of the b tile)
    def __init__(self, state="random", parent=None, b=None, depth =0):
        self.Parent = parent
        self.B = b
        self.Depth = depth
        if state.lower() == "random":  # default to random
            self.randomizeState()
        else:
            try:
                if self.Parent is None:
                    if self.validState(state):
                        self.State = list(state.replace(' ', ''))  # finally sets the state to State for one dimention
                else:  # we don't need to validate it if it has a parent. it must be originating from a valid state.
                    self.State = list(state.replace(' ', ''))  # finally sets the state to State for one dimention
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
            raise ValueError(
                "Please format the desiered state correctly, e.g. 'b12 345 678'. Must be of length 9. "
                "You entered a string of length", len(state), state)

    def randomizeState(self, range=100):
        self.State = 'b12345678' # sets 1d state
        self.B = 0 # location of the b tile
        # loop moves the blank piece 100 times in random directions
        for i in range(range):
            moves = self.move(0)
            ran = random.randint(0, len(moves) - 1)  # produce a random int between 1 and num of moves
            try:
                self.State = moves[ran] # sets state to chosen random move
                self.findB() # updates the location of the b tile
            except UnboundLocalError:  # if it can't move that way
                i += 1  # don't let it miss a move
                pass

    # creates a string to visually represent the board
    def __str__(self):
        tile = self.State
        out = "\n{0}\t{1}\t{2}\n" \
              "{3}\t{4}\t{5}\n" \
              "{6}\t{7}\t{8}\n".format(
            tile[0], tile[1], tile[2],
            tile[3], tile[4], tile[5],
            tile[6], tile[7], tile[8])
        print(out)

    def generateSolutionPath(self, path=[]):
        if self.Parent is None:
            return path.reverse() # reverse order as they are added in
        else:
            return self.Parent.generateSolutionPath(path.append(self.Parent))
            #TODO figure out weather parent is a list of 8puzzles, states, chars(representing directions)

    # Moves the tile up, down, left, right
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    # the setting in this case is just to pass it along to lower level helpers
    def move(self, setting=1):
        moves = []
        try:
            moves.append(self.moveUp(setting)) # move up
        except UnboundLocalError:  # cant go up
            pass
        try:
            moves.append(self.moveDown(setting)) # move down
        except UnboundLocalError:  # cant go down
            pass
        try:
            moves.append(self.moveLeft(setting)) # move left
        except UnboundLocalError:  # cant go left
            pass
        try:
            moves.append(self.moveRight(setting)) # move right
        except UnboundLocalError:  # cant go right
            pass
        return moves

    # Moves the blank tile up
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def moveUp(self, setting=1):
        if (self.B - 3) < 0:  # check to see if it's in bounds
            raise UnboundLocalError
        else:
            if setting == 1: # set move to current state
                # performs a switch
                temp = self.State[self.B - 3]
                self.State[self.B - 3] = self.State[self.B]
                self.State[self.B] = temp
                # update B's location
                self.B = self.B - 3
            else: # return the state of the move
                probe = EightPuzzle(self.State, b=self.B)
                probe.moveUp()
                return probe.self.oneD

    # Moves the blank tile down
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def moveDown(self, setting=1):
        if (self.B + 3) > 8:  # check to see if it's in bounds
            raise UnboundLocalError
        else:
            if setting == 1: # set move to current state
                # performs a switch
                temp = self.State[self.B + 3]
                self.State[self.B + 3] = self.State[self.B]
                self.State[self.B] = temp
                # update B's location
                self.B = self.B + 3
            else:# return the state of the move
                probe = EightPuzzle(self.State, b=self.B)
                probe.moveDown()
                return probe.self.oneD

    # Moves the blank tile left
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def moveLeft(self, setting=1):
        if (self.B % 3) == 0:  # check to see if it's in bounds
            raise UnboundLocalError
        else:
            if setting == 1: # set move to current state
                # performs a switch
                temp = self.State[self.B - 1]
                self.State[self.B - 1] = self.State[self.B]
                self.State[self.B] = temp
                # update B's location
                self.B = self.B - 1
            else: # return the state of the move
                probe = EightPuzzle(self.State, b=self.B)
                probe.moveLeft()
                return probe.self.oneD

    # Moves the blank tile right
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def moveRight(self, setting=1):
        if (self.B + 1) % 3 == 0:  # check to see if it's in bounds
            raise UnboundLocalError
        else:
            if setting == 1: # set move to current state
                # performs a switch
                temp = self.State[self.B + 1]
                self.State[self.B + 1] = self.State[self.B]
                self.State[self.B] = temp
                # update B's location
                self.B = self.B + 1
            else:  # return the state of the move
                probe = EightPuzzle(self.State, b=self.B)
                probe.moveRight()
                return probe.self.oneD


    def findB(self):
        for i in range(len(self.State)):
            if self.State[i] == 'b':
                self.B = i # new B's location
                break

def solve_Beam():
    print('temp')

def solve_AStar(state):
    AStar.AStar(state)
if __name__ == '__main__':
    if len(sys.argv) > 1:
        uI = sys.argv[1:]
        for i in range(uI):
            print("TEMP")
