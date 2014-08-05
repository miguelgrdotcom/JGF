
dim = ARGV[0]
dim = dim.to_i

type="HUTuple#{dim}<"
dim.times do |i|
  type += "Integer"
  type += ", " if i != dim-1
end
type+=">"

str = "public static HUSet<#{type}> make#{dim}(int[] arr) { "
str+= "HUSet<#{type}> set = new HUSet<#{type}>();"

dim.times do |i|
  str += "for(int i#{i} = arr[#{i*2}]; i#{i} <= arr[#{i*2+1}]; i#{i}++)"
end

str += "set.add(new #{type}("

dim.times do |i|
  str += "i#{i}"
  str += ", " if i != dim-1
end

str += ")); return set;"

str+="}"

puts str
