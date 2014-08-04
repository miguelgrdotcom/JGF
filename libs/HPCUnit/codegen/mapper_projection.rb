
dim = ARGV[0]
dim = dim.to_i

num = ARGV[1]
num = num.to_i

from = "HUTuple#{dim}<"
for i in 0..dim-1
 from += "T#{i}"
 from += ","
end
from = from[0..from.size-2]
from += ">"

to = "HUTuple#{dim-1}<"
for i in 0..dim-1
  if i != num
    to += "T#{i}"
    to += ","
  end
end
to = to[0..to.size-2]
to += ">"

str = "public static <"

for i in 0..dim-1
 str += "T#{i} extends Comparable<T#{i}>"
 str += ","
end
str = str[0..str.size-2]

str += "> HUMapper<#{from},#{to}>"

str += "t"
for i in 0..dim-1
 str += "#{i}"
end

str += "_t"
for i in 0..dim-1
  if i != num
    str += "#{i}"
  end
end

str += "() { return new HUMapper<#{from}, #{to}> () {"
str += "@Override "
str += "public #{to} apply(#{from} o) {"
str += "return new #{to} ("
for i in 0..dim-1
  if i != num
    str += "o.el#{i}()"
    str += ","
  end
end
str = str[0..str.size-2]

str += ");}};}"


puts str
