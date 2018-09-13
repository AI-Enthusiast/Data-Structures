# AStar.py created for solving the 8-puzzle problem, implemented in Eight_Puzzle.py
# DateCreated: 9/7/18
# Author: Cormac Dacker (cxd289)
import heapq

from Code.SlavesToTheMain import EightPuzzle as ep


def error(errorMessage):
    print(">ERROR:\t" + str(errorMessage))


class AStar:

    def __init__(self, puzzle, maxNodes=27):
        self.maxNodes = maxNodes
        self.H1 = self.h1(puzzle)
        self.H2 = self.h2(puzzle)
        self.aStar(puzzle)

    # First Heuristic is based on the number of tiles out of place
    # the less the better
    def h1(self, puzzle):
        numOutOfPlace = 0
        for i in range(len(ep.goal)):
            if ep.goal[i] != puzzle[i]:
                numOutOfPlace += 1
        return numOutOfPlace

    # Second heuristic is based on the distance of tiles to goal state
    # the less the better
    def h2(self, puzzle):
        distTot = 0  # var for total distance
        for i in range(len(ep.goal)):
            if puzzle[i] == ep.goal[i]:  # if it's already on the right one
                continue
            else:
                tilePos = 0
                for j in range(len(puzzle)):
                    if puzzle[j] == ep.goal[i]:  # if you find the current possion of the tile
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

    #TODO crossrefrence direction and state
    # Chooses a branch based off 3 heaps representing h1,h2,and depth
    def chooseBranch(self, tree):
        state = "" #default direction
        if len(tree) > 0:
            heap1, heap2, heap3 = [], [], []
            for branch in range(len(tree)):  # build the heaps
                h1, h2, deapth = list(tree[branch]), list(tree[branch]), list(tree[branch])
                h1.remove(h1[1])
                h1.remove(h1[1])
                h2.remove(h2[0])
                h2.remove(h2[1])
                deapth.remove(deapth[1])
                deapth.remove(deapth[0])
                heapq.heappush(heap1, h1)  # heap for h1
                heapq.heappush(heap2, h2)  # heap for h2
                heapq.heappush(heap3, deapth)  # heap for depth

            directions = {"up": '0', 'down': "1", 'left': "2", 'right': "3"}  # to determine where we are comming from
            score = [[0, "up"], [0, "down"], [0, "left"], [0, "right"]]
            a, b, c = heapq.heappop(heap1), heapq.heappop(heap2), heapq.heappop(heap3)
            x, y, z = heapq.heappop(heap1), heapq.heappop(heap2), heapq.heappop(heap3)
            if a[0] < x[0]:
                score[int(directions.get(a[1]))][0] +=1
            if a[0] > x[0]:
                score[int(directions.get(x[1]))][0] =+1
            if b[0] < y[0]:
                score[int(directions.get(b[1]))][0] +=1
            if b[0] > y[0]:
                score[int(directions.get(y[1]))][0] +=1
            if c[0] < z[0]:
                score[int(directions.get(c[1]))][0] +=1
            if c[0] > z[0]:
                score[int(directions.get(z[1]))][0] +=1
            finalHeap = []
            for i in range(len(score)):
                heapq.heappush(finalHeap, score[i])
            heapq._heapify_max(finalHeap)
            direction = heapq._heappop_max(finalHeap)[1]

            if a[1] == direction:
                state = a[2]
            elif b[1] == direction:
                state = b[2]
            elif c[1] == direction:
                state = c[2]
            elif x[1] == direction:
                state = x[2]
            elif y[1] == direction:
                state = y[2]
            elif z[1] == direction:
                state = z[2]
            if state == '': # if there was no lead, pick b
                state = b[2]
            return state, direction

    def aStar(self, puzzle, depth=0, pathTaken=[]):
        open, closed = [], [] #nodes to visit, nodes to not visit
        open.append("TEMP") #TEMP
        print(open)
        while open is not None:
            self.chooseBranch(puzzle,closed)
            if ep.isGoal(puzzle):
                print("Solution found")
                break





















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
