
dim = ARGV[0]
dim = dim.to_i

num = ARGV[1]
num = num.to_i

type="HUTuple#{dim}<"
dim.times do |i|
  type += "Integer"
  type += ", " if i != dim-1
end
type+=">"

capsule="HUTuple2<BoolsT#{dim-1}, Boolean>"

mname="dependPreT#{dim}_#{num}"
sname="dependPreSeedT#{dim}_#{num}"

# dependPreT{dim}_{num}
str = "public static int #{mname}(HUSet<#{type}> set){return set.fold(#{mname}, #{sname}).el1();}\n"

# dependPreT{dim}_{num}
str+= "private static HUFolder<#{capsule}, #{type}> #{mname} = new HUFolder<#{capsule}, #{type}>() {"
str+= "@Override public #{capsule} apply(#{capsule} capsule, #{type} cur){"
str+= "BoolsT#{dim} visited = capsule.el0();"
str+= "boolean flag = capsule.el1();"
str+= "if(!flag) return new #{capsule}(visited, flag);"
str+= "visited.set("
dim.times do |i|
 if i != dim-1
   str += "cur.el#{i+1}(),"
 else
   str += "true);"
 end
end
str+= "return new #{capsule}(visited,visited.get("
(dim-1).times do |i|
  str+= "cur.el#{dim+1}()"
  str+= "-1" if i == num
  str+= ","  if i != dim-2
end
str+= "));"
str+= "}"
str+= "};\n"

#intervalSeedT{N}
str+= "private static #{capsule} intervalSeedT#{dim} = new #{capsule}(0,null);"

puts str
