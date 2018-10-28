import copy

x = [2, 0, 0, 0, 1, 1, 1, 2, 2];
y = [2, 0, 1, 2, 0, 1, 2, 0, 1];

class Node:
    def __init__(self, state, parent = None):
        self.state = state
        self.parent = parent
        self.h = self.h()
        if parent != None:
            self.g = parent.g + 1
        else:
            self.g = 0

    def g(self):
        return self.g

    def h(self):
        cost = 0
        for i in range(3):
            for j in range(3):
                cost += abs(x[self.state[i][j]] - i) + abs(y[self.state[i][j]] - j)
        return cost

    def score(self):
        return self.g + self.h

Open = []
Closed = []

def pos_zero(mat):
    for i in range(3):
        for j in range(3):
            if mat[i][j] == 0:
                return (i, j)

start_state = [[1, 2, 3], [5, 6, 0], [7, 8, 4]]
goal_state = [[1, 2, 3], [5, 8, 6], [0, 7, 4]]

start_node = Node(state = start_state)
goal_node = Node(state = goal_state)

Open.append(start_node)

while Open:
    min = 1000

    for node in Open:
        if node.score() < min:
            min = node.score()
            best_node = node

    temp = []

    for node in Open:
        if node.state != best_node.state:
            temp.append(node)

    Open = []

    for node in temp:
        Open.append(node)

    if best_node.state == goal_node.state:
        print("Found")
        ans = []

        while best_node != start_node:
            ans.append(best_node)
            best_node = best_node.parent

        for rows in start_state:
            print(rows)
        print('')

        for its in reversed(ans):
            for rows in its.state:
                print(rows)
            print('')

        break;

    else:
        succ = []
        zx, zy = pos_zero(best_node.state)

        if zx - 1 > -1:
            temp = copy.deepcopy(best_node.state)
            temp[zx][zy] = temp[zx - 1][zy]
            temp[zx - 1][zy] = 0
            succ_node = Node(state = temp, parent = best_node)
            succ.append(succ_node)

        if zx + 1 < 3:
            temp = copy.deepcopy(best_node.state)
            temp[zx][zy] = temp[zx + 1][zy]
            temp[zx + 1][zy] = 0
            succ_node = Node(state = temp, parent = best_node)
            succ.append(succ_node)

        if zy - 1  > -1:
            temp = copy.deepcopy(best_node.state)
            temp[zx][zy] = temp[zx][zy - 1]
            temp[zx][zy - 1] = 0
            succ_node = Node(state = temp, parent = best_node)
            succ.append(succ_node)

        if zy + 1  < 3:
            temp = copy.deepcopy(best_node.state)
            temp[zx][zy] = temp[zx][zy + 1]
            temp[zx][zy + 1] = 0
            succ_node = Node(state = temp, parent = best_node)
            succ.append(succ_node)

        for it in succ:
            ofl = 0
            cfl = 0

            for node in Open:
                if node.state == it.state:
                    if node.score() <= it.score():
                        ofl = 1

            for node in Closed:
                if node.state == it.state:
                    if node.score() <= it.score():
                        cfl = 1

            if ofl == 0 and cfl ==0:
                Open.append(it)

        Closed.append(best_node)
