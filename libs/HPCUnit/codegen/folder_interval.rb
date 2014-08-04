
dim = ARGV[0]
dim = dim.to_i

type="HUTuple#{dim}<"
dim.times do |i|
  type += "Integer"
  type += ", " if i != dim-1
end
type+=">"

capsule="HUTuple2<Integer,#{type}>"

# intervalT{N}
str = "public static int intervalT#{dim}(HUSet<#{type}> set){return set.fold(intervalT#{dim}, intervalSeedT#{dim}).el0()/set.size();}\n"

# intervalT{N}
str+= "private static HUFolder<#{capsule}, #{type}> intervalT#{dim} = new HUFolder<#{capsule}, #{type}>() {"
str+= "@Override public #{capsule} apply(#{capsule} capsule, #{type} cur){"
str+= "#{type} pre = capsule.el1();"
str+= "int sum = capsule.el0();"
str+= "if(pre != null){sum += "
dim.times do |i|
  if i != 0
    str += "Math.abs(cur.el#{i}()-pre.el#{i}())"
    str += "+" if i != dim-1
  end
end
str+=";}"
str+= "return new #{capsule}(sum, cur);"
str+= "}"
str+= "};\n"

#intervalSeedT{N}
str+= "private static #{capsule} intervalSeedT#{dim} = new #{capsule}(0,null);"

puts str
