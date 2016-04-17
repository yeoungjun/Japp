input_string ="\
4\n\
1 3\n\
1 2 1\n\
5 5\n\
5 2 5 2 4\n\
1 1 1 1 1\n\
3 2 3 2 3\n\
3 2 3 2 3\n\
5 2 5 2 4\n\
7 7\n\
2 2 2 2 2 3 2\n\
2 1 1 1 2 3 1\n\
2 1 2 1 2 4 2\n\
2 1 1 3 2 4 2\n\
2 2 2 2 2 4 2\n\
2 2 2 2 2 4 2\n\
2 2 2 2 2 4 2\n\
20 10\n\
20 50 40 50 40 50 50 10 35 50\n\
20 50 40 50 40 50 50 10 35 50\n\
20 50 40 50 40 50 50 10 35 50\n\
15 15 15 15 15 15 15 10 15 15\n\
20 50 40 50 40 50 50 10 35 50\n\
20 30 30 30 30 30 30 10 30 30\n\
20 50 40 50 40 50 50 10 35 50\n\
20 25 25 25 25 25 25 10 25 25\n\
20 50 40 50 40 50 50 10 35 50\n\
20 25 25 25 25 25 25 10 25 25\n\
20 50 40 50 40 50 50 10 35 50\n\
15 15 15 15 15 15 15 10 15 15\n\
20 50 40 50 40 50 50 10 35 50\n\
20 10 10 10 10 10 10 10 10 10\n\
20 30 30 30 30 30 30 10 30 30\n\
10 10 10 10 10 10 10 10 10 10\n\
20 50 40 50 40 50 50 10 35 50\n\
20 50 40 50 40 50 50 10 35 50\n\
20 50 40 50 40 50 50 10 35 50\n\
20 40 40 40 40 40 40 10 40 40\n"


def main(n, m, nums):
    if (n <= 1 or m<=1):
        return True

    nums = checkLine(nums)
    if not nums:
        return True

    nums = checkLine(nums)
    if not nums:
        return True

    if ( n == len(nums) and m == len(nums[0]) ):
        return False

    return main(len(nums),len(nums[0]),nums)


def checkLine(nums):
    i =0
    nMin = min(min(nums))
    while(i < len(nums)):
        if (max(nums[i]) == min(nums[i])) and (min(nums[i]) == nMin):
            del nums[i]
            if not nums :
                return False
            nMin = min(min(nums))
            i = 0
        else:
            i +=1
    return inverse(nums)

def inverse(nums):
    tmp = []
    nn = len(nums)
    mm = len(nums[0])

    for i in range(mm):
        tmp.append([0] * nn)

    for i in range(nn):
        for j in range(mm):
            tmp[j][i] = nums[i][j]
    return tmp


line = 1
result = input_string.split('\n')

for i in range(int(result[0])):
    nm = []
    nums = []
    nm = map(int , result[line].split(' '))
    for j in range(nm[0]):
        nums.append(map(int , result[j+line+1].split(' ')))
    line += nm[0] + 1

    if ( main(nm[0],nm[1],nums) ):
        print "Case#%d YES" %(i+1)
    else:
        print "Case#%d NO" %(i+1)