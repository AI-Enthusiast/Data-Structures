# AStar.py created for solving the 8-puzzle problem, implemented in Eight_Puzzle.py
# DateCreated: 9/7/18
# Author: Cormac Dacker (cxd289)
from typing import List

from Code.SlavesToTheMain import EightPuzzle as ep


def error(errorMessage):
    print(">ERROR:\t" + str(errorMessage))


class AStar:

    def __init__(self, puzzle, maxNodes=31):
        self.MaxNodes = maxNodes
        self.aStar(puzzle)  # print aStar solution

    # the heuristic function
    def f(self, puzzle):
        return ep.h1(puzzle) + ep.h2(puzzle) + puzzle.Depth

    # Chooses a branch based off a priority queue representing the heuristic function of the puzzles
    def chooseBranch(self, open, closed):
        funcHeap = []
        for branch in range(len(open)):  # build the queue
            if open[branch].State not in closed:
                funcHeap.append((self.f(open[branch]), open[branch]))  # pushes info to the queue
        funcHeap.sort(reverse=True)
        theChosenOne = funcHeap.pop()  # picks top branch
        return theChosenOne[1]  # returns the puzzle on top

    # The one and only A* it'self! Say hello A*! <3
    def aStar(self, puzzle):
        goal = False
        open, closed = [], {}  # nodes to visit, nodes to not visit
        if ep.isGoal(puzzle.State):  # if the current puzzle is the goal state
            puzzle.generateSolutionPath()  # goal has been achieved
        else:  # work toward the goal
            open.append(puzzle)  # add the starting puzzle to open
            while open is not None:  # while there are still nodes to expand in open
                branch = self.chooseBranch(open, closed)  # type: ep.EightPuzzle #choose a branch from open
                closed[branch.State] = branch  # add the chosen branch to the closed table
                open.remove(branch)  # remove the branch from open
                newBranches = ep.move(branch, 0)  # type: List[ep.EightPuzzle] # get childern of that branch
                for stem in range(len(newBranches)):  # for each expantion of the branch
                    if ep.isGoal(newBranches[stem].State):  # if it's the goal
                        newBranches[stem].generateSolutionPath([])
                        goal = True
                        break  # goal has been achieved
                    if newBranches[stem].State not in closed and newBranches[stem].Depth < self.MaxNodes:
                        # if not in closed
                        # if its not exceeded depth
                        open.append(newBranches[stem])  # add stem to open nodes
                if goal:
                    break
