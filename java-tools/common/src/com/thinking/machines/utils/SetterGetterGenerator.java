package com.thinking.machines.utils;
import java.lang.reflect.*;
import java.io.*;
public class SetterGetterGenerator
{
public static void main(String gg[])
{
if(gg.length!=1 && gg.length!=2)
{
System.out.println("Usage: java -classpath path_to_jar_file;. com.thinking.machines.utils.SetterGetterGenerator class_name constructor=true/false");
return;
}

if(gg.length==2 && gg[1].equalsIgnoreCase("constructor=true")==false && gg[1].equalsIgnoreCase("constructor=false")==false)
{
System.out.println("Usage: java -classpath path_to_jar_file;. com.thinking.machines.utils.SetterGetterGenerator class_name constructor=true/false");
return;
}

String className=gg[0];
try
{
Class c=Class.forName(className);
Field fields[]=c.getDeclaredFields();
Class fieldType=null;
TMList<String> list=new TMArrayList<String>();
String fieldName="";
String setterName;
String getterName;
String tmp="";
String line;
System.out.println("Number of fields:"+fields.length);

//GENERATING DEFAULT CONSTRUCTOR
if(gg.length==1 ||(gg.length==2 && gg[1].equalsIgnoreCase("constructor=true")))
{
line="public"+" "+className+"()";
list.add(line);
list.add("{");
for(int e=0;e<fields.length;e++)
{
line="this."+fields[e].getName()+"="+getDefaultValue(fields[e].getType())+";";
list.add(line);
}
list.add("}");
}

for(int e=0;e<fields.length;e++)
{
fieldName=fields[e].getName();
fieldType=fields[e].getType();
if(fieldName.charAt(0)<=122 && fieldName.charAt(0)>=97)
{
tmp=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
}
else
{
tmp=fieldName;
}
setterName="set"+tmp;
getterName="get"+tmp;

//GENERATING SETTER
line="public void "+setterName+"("+fieldType.getName()+" "+fieldName+")";
list.add(line);
list.add("{");
line="this."+fieldName+"="+fieldName+";";
list.add(line);
list.add("}");

//GENERATING GETTER
line="public "+fieldType.getName()+" "+getterName+"()";
list.add(line);
list.add("{");
line="return this."+fieldName+";";
list.add(line);
list.add("}");
}

// TRAVERSING IN ARRAYLIST & PUTTING THE DATA IN FILE NAMED AS tmp.tmp
File file=new File("tmp.tmp");
if(file.exists()) file.delete();
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
TMIterator <String>iterator=list.iterator();
while(iterator.hasNext())
{
line=iterator.next();
randomAccessFile.writeBytes(line+"\r\n");
}
randomAccessFile.close();
System.out.println("setters/getters for:"+c.getName()+" generated in file named as tmp.tmp");
}catch(ClassNotFoundException cnfe)
{
System.out.println("Unable to load class, classpath missing");
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}//main() Ends

//INTERNAL FUNCTIONS
//getDefaultValue()
private static String getDefaultValue(Class c)
{
String className=c.getName();
if(className.equalsIgnoreCase("java.lang.Long") || className.equalsIgnoreCase("long")) return "0";
if(className.equalsIgnoreCase("java.lang.Integer") || className.equalsIgnoreCase("int")) return "0";
if(className.equalsIgnoreCase("java.lang.Short") || className.equalsIgnoreCase("short")) return "0";
if(className.equalsIgnoreCase("java.lang.Byte") || className.equalsIgnoreCase("byte")) return "0";
if(className.equalsIgnoreCase("java.lang.Double") || className.equalsIgnoreCase("double")) return "0.0";
if(className.equalsIgnoreCase("java.lang.Float") || className.equalsIgnoreCase("float")) return "0.0f";
if(className.equalsIgnoreCase("java.lang.Character") || className.equalsIgnoreCase("char")) return "' '";
if(className.equalsIgnoreCase("java.lang.Boolean") || className.equalsIgnoreCase("boolean")) return "false";
if(className.equalsIgnoreCase("java.lang.String")) return "\"\"";
return null;
}
} //Class SetterGetterGenerator Ends