# EightPuzzle.py created for representing the classic puzzle of the same name
# DateCreated: 9/7/18
# Author: Cormac Dacker (cxd289)

import random
import sys
from typing import List

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
    State = ...  # type: str

    # Constructor
    # Params: state(as a string of length 9), parent (TEMP TBA), b(location of the b tile)
    def __init__(self, state="random", random=100, parent=None, b=None, depth=0):
        self.Parent = parent
        self.B = b
        self.Depth = depth
        if str(state).lower() == "random":  # default to random
            self.randomizeState(random)
            self.__str__()
        else:
            try:
                if self.Parent is None:
                    if self.validState(state):
                        self.State = str(state).replace(' ', '')  # finally sets the state to State for one dimention
                        self.__str__()
                else:  # we don't need to validate it if it has a parent. it must be originating from a valid state.
                    self.State = str(state).replace(' ', '')  # finally sets the state to State for one dimention
            except ValueError and TypeError as e:  # if you gave a screwy state
                error(e)
                # TODO double check user inputed states are solvable
                print('Setting state to random instead')
                self.randomizeState()

    # allows for EightPuzzle() < EightPuzzle() conparison
    def __lt__(self, other):
        return self.__hash__() < other.__hash__()

    def validState(self, state):
        # double checks it's of the right length(you can never trust the user)
        if len(state) == 9:
            # loops validates that entered state has all the right tiles
            for i in range(len(state)):
                for j in range(len(goal)):
                    if goal[j] == state[i]:  # if the state has a correct tile
                        if j == 0:  # ie if the blank tile has been found
                            self.B = i  # location of the blank tile
                        break  # break causes jump to the continue 4 lines down
                    elif j == 8:  # a tile could not be found
                        raise ValueError("State", state[i],
                                         "not found. Please enter state correctly, 1->8 and a 'b'.")
                continue  # continue to next iteration
            return True
        else:
            raise ValueError(
                "Please format the desiered state correctly, e.g. 'b12 345 678'. Must be of length 9. "
                "You entered a string of length", len(state), state)

    # Creates a random starting state
    def randomizeState(self, r=100):
        self.State = 'b12345678'  # sets 1d state
        self.B = 0  # location of the b tile
        # loop moves the blank piece 100 times in random directions
        for i in range(0, r):
            moves = move(self, 0)  # type: List[EightPuzzle]
            ran = random.randint(0, len(moves) - 1)  # produce a random int between 1 and num of moves
            try:
                self.State = moves[ran].State  # sets state to chosen random move
                self.findB()  # updates the location of the b tile
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

    # Creates the path to the parent from the current node (this assumes that node is the goal)
    def generateSolutionPath(self, path: List = []):
        if self.Parent is None:
            path.reverse()
            return ' --> '.join(path)  # reverse order as they are added in
        else:
            path.append(self.Parent[0])
            return self.Parent[1].generateSolutionPath(path)  # recursively self call for path
            # TODO figure out weather parent is a list of 8puzzles, states, chars(representing directions)

    # Finds the location of the b tile
    def findB(self):
        for i in range(len(self.State)):
            if self.State[i] == 'b':
                self.B = i  # new B's location
                break

    # Swaps the location of the b tile with y
    # setting ( 0 = return states instead of setting them, 1 = set states at each move)
    def swap(self, b, y, direction=None, setting=1):
        state = list(self.State)
        # performs a switch
        temp = state[y]
        state[y] = state[b]
        state[b] = temp
        # update B's location
        if setting == 1:
            self.B = y
            self.State = str(''.join(state))
        else:
            return EightPuzzle(state=str(''.join(state)), parent=(direction, self), b=y, depth=self.Depth + 1)


# Moves the tile up, down, left, right
# setting ( 0 = return states instead of setting them, 1 = set states at each move)
# the setting in this case is just to pass it along to lower level helpers
def move(puzzle: EightPuzzle, setting=1) -> List[EightPuzzle]:
    moves = []
    try:
        moves.append(moveUp(puzzle, setting))  # move up
    except UnboundLocalError:  # cant go up
        pass
    try:
        moves.append(moveDown(puzzle, setting))  # move down
    except UnboundLocalError:  # cant go down
        pass
    try:
        moves.append(moveLeft(puzzle, setting))  # move left
    except UnboundLocalError:  # cant go left
        pass
    try:
        moves.append(moveRight(puzzle, setting))  # move right
    except UnboundLocalError:  # cant go right
        pass
    return moves


# Moves the blank tile up
# setting ( 0 = return states instead of setting them, 1 = set states at each move)
def moveUp(puzzle, setting=1):
    if (puzzle.B - 3) < 0:  # check to see if it's in bounds
        raise UnboundLocalError
    else:
        if setting == 1:  # set move to current state
            puzzle.swap(puzzle.B, puzzle.B - 3, setting)

        else:  # return the node made from moving
            return puzzle.swap(puzzle.B, puzzle.B - 3, "Up", setting)


# Moves the blank tile down
# setting ( 0 = return states instead of setting them, 1 = set states at each move)
def moveDown(puzzle, setting=1):
    if (puzzle.B + 3) > 8:  # check to see if it's in bounds
        raise UnboundLocalError
    else:
        if setting == 1:  # set move to current state
            puzzle.swap(puzzle.B, puzzle.B + 3, setting)
        else:  # return the node made from moving
            return puzzle.swap(puzzle.B, puzzle.B + 3, "Down", setting)


# Moves the blank tile left
# setting ( 0 = return states instead of setting them, 1 = set states at each move)
def moveLeft(puzzle, setting=1):
    if (puzzle.B % 3) == 0:  # check to see if it's in bounds
        raise UnboundLocalError
    else:
        if setting == 1:  # set move to current state
            puzzle.swap(puzzle.B, puzzle.B - 1, setting)
        else:  # return the node made from moving
            return puzzle.swap(puzzle.B, puzzle.B - 1, "Left", setting)


# Moves the blank tile right
# setting ( 0 = return states instead of setting them, 1 = set states at each move)
def moveRight(puzzle, setting=1):
    if (puzzle.B + 1) % 3 == 0:  # check to see if it's in bounds
        raise UnboundLocalError
    else:
        if setting == 1:  # set move to current state
            puzzle.swap(puzzle.B, puzzle.B + 1, setting)
        else:  # return the node made from moving
            return puzzle.swap(puzzle.B, puzzle.B + 1, "Right", setting)


def solve_Beam():
    print('temp')


def solve_AStar(state):
    AStar.AStar(state)


if __name__ == '__main__':
    if len(sys.argv) > 1:
        uI = sys.argv[1:]
        for i in range(uI):
            print("TEMP")
