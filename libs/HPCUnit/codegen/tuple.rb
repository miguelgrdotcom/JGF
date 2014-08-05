
num = ARGV[0]
num = num.to_i

str = "public class HUTuple#{num}<"
for i in 0..num-1
  str += "T#{i} extends Comparable<T#{i}>"
  if i != num-1
    str += ", "
  end
end
str +="> "

str +="implements Comparable<HUTuple#{num}<"
for i in 0..num-1
  str += "T#{i}"
  if i != num-1
    str += ", "
  end
end
str += ">>, Serializable"
str += "{"

# field
for i in 0..num-1
  str += "private T#{i} el#{i};"
end

str += "private int hashCode = 0;"

# constructors
# str += "public HUTuple#{num}(){}"

str += "public HUTuple#{num}("
for i in 0..num-1
  str += "T#{i} el#{i}"
  if i != num-1
    str += ", "
  end
end
str += "){"
for i in 0..num-1
  str += "this.el#{i} = el#{i};"
end
str += "}"

# getter
for i in 0..num-1
  str += "public T#{i} el#{i}() {return el#{i};}"
end

# setter
#for i in 0..num-1
#  str += "public void set#{i}(T#{i} el#{i}) {this.el#{i} = el#{i};}"
#end

# comparator
str += "@Override public int compareTo(HUTuple#{num}<"
for i in 0..num-1
  str += "T#{i}"
  if i != num-1
    str += ", "
  end
end
str += "> o) {return "
for i in 0..num-1
  str += "this.el#{i}.compareTo(o.el#{i}) != 0 ? this.el#{i}.compareTo(o.el#{i}) :"
end
str += "0;"
str += "}" 

# equals
str += "@Override public boolean equals(Object o){"
str += "if(this == o){return true;}"
str += "if(!(o instanceof HUTuple#{num})){return false;}"
str += "@SuppressWarnings(\"unchecked\")HUTuple#{num}<"
for i in 0..num-1
  str += "T#{i}"
  if i != num-1
    str += ", "
  end
end
str += ">"
str += "oo = (HUTuple#{num}<"
for i in 0..num-1
  str += "T#{i}"
  if i != num-1
    str += ", "
  end
end
str += ">)o;"
str += "return "
for i in 0..num-1
  str += "this.el#{i}.equals(oo.el#{i})"
  if i != num-1
    str += " && "
  end
end
str += ";"
str += "}"


# hashCode
str += "public int hashCode() {"
str += "if (hashCode == 0) {"
str += "int result = 17;"
for i in 0..num-1
  str += "result = 37 * result + el#{i}.hashCode();"
end
str += "hashCode = result;}"
str += "return hashCode;"
str += "}"

# toString
str += "public String toString(){"
str += "return \"(\" + "
for i in 0..num-1
  str += "el#{i}.toString()"
  if i != num-1
    str += " + \", \" + "
  end
end
str += " + \")\";"
str += "}"

# map
str += "public <"
for i in 0..num-2
  str += "U#{i} extends Comparable<U#{i}>"
  if i != num-2
    str += ", "
  end
end
str += ">"
str += "HUTuple#{num-1}<"
for i in 0..num-2
  str += "U#{i}"
  if i != num-2
    str += ", "
  end
end
str+= "> map(HUMapper<HUTuple#{num}<"
for i in 0..num-1
  str += "T#{i}"
  if i != num-1
    str += ", "
  end
end
str+=">,"
str+="HUTuple#{num-1}<"
for i in 0..num-2
  str += "U#{i}"
  if i != num-2
    str += ", "
  end
end
str+=">"
str+="> mapper) {return mapper.apply(this);}"

str += "}" #class

puts str
