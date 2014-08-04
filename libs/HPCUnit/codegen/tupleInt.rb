
num = ARGV[0]
num = num.to_i

str = "public class HUTupleInt#{num} "
str +="implements Comparable<HUTupleInt#{num}>"
str +=", Serializable"
str +="{"

# field
for i in 0..num-1
  str += "private int el#{i};"
end
str += "private int hashCode = 0;"

# constructors
str += "public HUTupleInt#{num}(){}"

str += "public HUTupleInt#{num}("
for i in 0..num-1
  str += "int el#{i}"
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
  str += "public int get#{i}() {return el#{i};}"
end

# setter
for i in 0..num-1
  str += "public void set#{i}(int el#{i}) {this.el#{i} = el#{i};}"
end

# comparator
str += "@Override public int compareTo(HUTupleInt#{num} o) {return "
for i in 0..num-1
  str += "this.el#{i}-o.el#{i} != 0 ? this.el#{i}-o.el#{i} :"
end
str += "0;"
str += "}" 

# equals
str += "@Override public boolean equals(Object o){"
str += "if(this == o){return true;}"
str += "if(!(o instanceof HUTupleInt#{num})){return false;}"
str += "HUTupleInt#{num}"
str += " oo = (HUTupleInt#{num})o;"
str += "return "
for i in 0..num-1
  str += "this.el#{i}==oo.el#{i}"
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
  str += "result = 37 * result + Integer.valueOf(el#{i}).hashCode();"
end
str += "hashCode = result;}"
str += "return hashCode;"
str += "}"

# toString
str += "public String toString(){"
str += "return \"(\" + "
for i in 0..num-1
  str += "el#{i}"
  if i != num-1
    str += " + \", \" + "
  end
end
str += " + \")\";"
str += "}"

# map
str += "public "
str += "HUTupleInt#{num-1}"
str+= " map(HUMapper<HUTupleInt#{num},HUTupleInt#{num-1}> mapper) {return mapper.calc(this);}"
str += "}" #class

puts str
