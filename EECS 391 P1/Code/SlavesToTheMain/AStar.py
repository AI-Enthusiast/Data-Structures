# AStar.py created for solving the 8-puzzle problem, implemented in Eight_Puzzle.py
# DateCreated: 9/7/18
# Author: Cormac Dacker (cxd289)
import heapq
from typing import List

from Code.SlavesToTheMain import EightPuzzle as ep


def error(errorMessage):
    print(">ERROR:\t" + str(errorMessage))


class AStar:

    def __init__(self, puzzle, maxNodes=27):
        self.MaxNodes = maxNodes
        print(self.aStar(puzzle)) #print aStar solution

    # First Heuristic is based on the number of tiles out of place
    # the less the better
    def h1(self, puzzle):
        numOutOfPlace = 0
        for i in range(len(ep.goal)):
            if ep.goal[i] != puzzle.State[i]:
                numOutOfPlace += 1
        return numOutOfPlace

    # Second heuristic is based on the distance of tiles to goal state
    # the less the better
    def h2(self, puzzle):
        distTot = 0  # var for total distance
        for i in range(len(ep.goal)):
            if puzzle.State[i] == ep.goal[i]:  # if it's already on the right one
                continue
            else:
                tilePos = 0
                for j in range(len(puzzle.State)):
                    if puzzle.State[j] == ep.goal[i]:  # if you find the current possion of the tile
                        tilePos = j
                        break  # break the loop cb tile found
                while tilePos != i:
                    if i < tilePos:  # if the tile needs to be moved up
                        if tilePos - 3 >= 0 and tilePos - 3 >= i:  # if it needs to be moved up not left
                            tilePos -= 3  # move the tile up
                            distTot += 1
                        elif tilePos - i < 3:  # if the tile needs to be moved left
                            dist = tilePos - i
                            tilePos -= dist
                            distTot += dist
                    elif i > tilePos:  # if the tile needs to be moved down
                        if tilePos + 3 <= 8 and tilePos + 3 <= i:  # if it needs to be moved down not right
                            tilePos += 3
                            distTot += 1
                        elif i - tilePos < 3:  # if the tile needs to be moved right
                            dist = i - tilePos
                            tilePos += dist
                            distTot += dist
        return distTot

    # the heuristic function
    def f(self, puzzle):
        return (self.h1(puzzle) + self.h2(puzzle))

    #TODO crossrefrence direction and state
    # Chooses a branch based off a heaps representing the heuristic function of the puzzles
    def chooseBranch(self, tree):
        funcHeap = []
        for branch in range(len(tree)):  # build the heaps
            heapq.heappush(funcHeap, [self.f(tree[branch]), tree[branch]])
        return heapq.heappop(funcHeap)[1] # returns the puzzle on top

    def aStar(self, puzzle):
        open, closed = [], {} #nodes to visit, nodes to not visit
        if ep.isGoal(puzzle.State): #if the current puzzle is the goal state
            return puzzle.generateSolutionPath()
        else: # work toward the goal
            # add the starting puzzle to open
            open.append(puzzle)
            while open is not None: #while there are still nodes to expand in open
                print('open',open)
                branch = self.chooseBranch(open)  # type: ep.EightPuzzle #choose a branch from open
                newBranches = branch.move(0)  # type: List[ep.EightPuzzle]
                print('moves',newBranches)
                for stem in range(len(newBranches)): #for each expantion of the branch
                    if ep.isGoal(puzzle.State): # if it's the goal
                        return puzzle.generateSolutionPath()
                    if newBranches[stem] not in closed: #if not in closed
                        open.append(newBranches[stem]) # add stem to open nodes
                closed[branch.State] = branch



















        # directions = {'0': "up", '1': "down", '2': "left", '3': "right"}  # to determine where we are comming from
        # if depth <= self.maxNodes:
        #     P = ep.EightPuzzle(str(puzzle))
        #     if ep.isGoal(puzzle):  # fist consider starting position
        #         return pathTaken
        #     else:  # work toward goal
        #         branches = P.move(0)  # set to return possible nodes
        #         arr = []
        #         for branch in range(len(branches)):  # choose one to explore
        #             if branches[branch] is not None:  # if there is an available path to take
        #                 st = branches[branch]  # set the puzzel string
        #                 arr.append([self.h1(st), self.h2(st), depth, directions.get(str(branch)), st])
        #         choice = self.chooseBranch(arr)  # the choice is made
        #         pathTaken.append(choice[1])
        #
        #         dpth = depth + 1
        #         print(choice)
        #         return self.aStar(str(choice[0]), depth=dpth, pathTaken=list(pathTaken))  # explore in further depth
