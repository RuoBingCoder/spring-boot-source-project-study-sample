print("hello lua")
function add(x, y)
    return x + y;
end
local sum = add(12, 34)
print(sum)

local number_1 = 102
local number_2 = 2
if number_1 % number_2 == 0 then
    print("===>", number_1 / number_2)
else
    print("error !")
end

