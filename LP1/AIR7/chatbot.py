import re
import math
from collections import Counter

def word2vec(words):
    vec = re.findall(r'\w+', words)
    return Counter(vec)

def get_cosine(vec1, vec2):
    intersection = set(vec1.keys() & vec2.keys())
    num = sum(vec1[x]*vec2[x] for x in intersection)
    den1 = sum(vec1[x]**2 for x in vec1.keys())
    den2 = sum(vec2[x]**2 for x in vec2.keys())
    den = math.sqrt(den1*den2)
    if den == 0:
        return
    else:
        return num/den

user_data = ['hi', 'I would like to invest', 'suggest me investment', 'budget is', 'bye', 'my stocks are']
bot_data = ['Hello', 'What is your Budget?', 'What is your Budget?', 'These are the investment options: ', 'Bye', 'Your stocks are: ']
stocks = []

while True:
    que = input("U: ").lower()
    response = "B: "
    vec1 = word2vec(que)
    max_cos = -1
    resultindex = 0
    for i in range(len(user_data)):
        cos = get_cosine(vec1, word2vec(user_data[i].lower()))
        if cos >= max_cos:
            max_cos = cos
            resultindex = i

    if max_cos < 0.6:
        print("Sorry, I dont understand")
    else:
        if 'budget' in str(user_data[resultindex].lower()):
            bud = re.findall('\d+', str(que.lower()))
            if(int(bud[0]) < 30000):
                print(bot_data[resultindex] + "A\nB\nC")
                c = input("Enter your choice")
                stocks.append(c);
            else:
                print(bot_data[resultindex] + "D\nE\nF")
                c = input("Enter your choice")
                stocks.append(c);

        elif 'stocks' in str(user_data[resultindex].lower()):
            print(bot_data[resultindex]+ str(stocks))

        elif resultindex == 4:
            print(bot_data[resultindex])
            break

        else:
            print(bot_data[resultindex])
