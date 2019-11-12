import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Iterator;
import java.lang.Cloneable;

public class Thesaurus {

//Method that turns an Arraylist of chars to a String
  public String toStr(ArrayList<Character> list){
    char[] arr = new char[list.size()];
    for (int i = 0; i < list.size(); i++){
      arr[i] = list.get(i);
    }
    String string = new String(arr);
    return string;
  }

//Method that splits a string whenever hits a " " and sorts it and removed the duplicates
  public Set<String> splitStr(String str){
    Set<String> splitted = new TreeSet<String>();

    ArrayList<Character> list = new ArrayList<Character>();

    for(int i = 0; i < str.length(); i++){

      if (str.charAt(i) != ' '){
        list.add(str.charAt(i));
        if (i == str.length()-1){
          String dummy = toStr(list);
          splitted.add(dummy);
          list.clear();
        }
      }
      else {
        String dummy = toStr(list);
        splitted.add(dummy);
        list.clear();
      }
    }
    return splitted;
  }

//Method that makes an arraylist of Set<String> from the initial String Array
  public ArrayList<Set<String>> arrToList(String[] array){

    ArrayList<Set<String>> listOut = new ArrayList<Set<String>>();

    for (int i = 0; i < array.length; i++){
      listOut.add(splitStr(array[i]));
    }
    return listOut;
  }

//Method that turns a Set<String> to String
  public String setToString(Set<String> set){
  ArrayList<String> arr = new ArrayList<String>();
  for (String str : set){
    arr.add(str);
  }

  String finalString = arr.get(0);
  for (int i = 1; i < arr.size(); i++){
    finalString = finalString + " " + arr.get(i);
  }
  return finalString.toLowerCase();
}

//Method that removes first element
  public ArrayList<Set<String>> removeLast(ArrayList<Set<String>> input){
    ArrayList<Set<String>> finalList = new ArrayList<Set<String>>();
    for(int i = 0; i < input.size() - 1; i++){
      finalList.add(input.get(i));
    }
    return finalList;
  }

//Method that returns true/false if the intersection has 2 or more elements
  public Boolean intersection(Set<String> set1, Set<String> set2){
    Set<String> dummySet = new TreeSet<String>();
    dummySet.addAll(set1);
    dummySet.retainAll(set2);

    if (dummySet.size() >= 2){
      return true;
    }
    else {
      return false;
    }
  }

//Method that returns the index of the first set that wants to merge.
  public int merge(Set<String> entry, ArrayList<Set<String>> list){
    int answer = -1;
    for (int i = 0; i < list.size(); i++){
      if (entry.equals(list.get(i)) == true){
        continue;
      }
      else {
        if (intersection(entry, list.get(i)) == true){
          answer = i;
          break;
        }
      }
    }
    return answer;
  }

//An attempt to use RECURSION
   public ArrayList<Set<String>> cleanUp(ArrayList<Set<String>> entries){
     ArrayList<Set<String>> outList = new ArrayList<Set<String>>();

     if (entries.size() <= 1){
     outList.addAll(entries);
   }

     else {
       Set<String> lastEntry = entries.get(entries.size() - 1);
       ArrayList<Set<String>> listWithoutlastEntry = removeLast(entries);
       ArrayList<Set<String>> cleanedUpList = cleanUp(listWithoutlastEntry);

       if (merge(lastEntry, cleanedUpList) == -1){
         cleanedUpList.add(lastEntry);
         outList.addAll(cleanedUpList);
       }
       else {
         int num = merge(lastEntry, cleanedUpList);
         cleanedUpList.get(num).addAll(lastEntry);
         outList.addAll(cleanUp(cleanedUpList));
       }
     }
     return outList;
 }

 //ORDER
 public ArrayList<String> orderList(ArrayList<Set<String>> list){
   ArrayList<String> setOut = new ArrayList<String>();
   for (Set<String> set : list){
     setOut.add(setToString(set));
   }
   Collections.sort(setOut);
   return setOut;
 }


 //method that does what the problem needs
     public String[] edit(String[] entry) {
       ArrayList<String> set = orderList(cleanUp(arrToList(entry)));
       String[] arrayOut = new String[set.size()];
       int i = 0;
       for (String str : set){
         arrayOut[i] = str;
         i = i + 1;
       }
       return arrayOut;
     }

//MAIN
  public static void main(String[] args){
    Thesaurus obj = new Thesaurus();
    String[] test = {"a b c d e f g h i j k l m n o p q r s t u v w x y", "a z", "b c bob", "tom x k", "a z"};

    //testing
    ArrayList<Set<String>> answerFin = obj.arrToList(test);
    //System.out.println(answerFin);
    //int ans = obj.merge(answerFin.get(0), answerFin);
    //System.out.println(ans);

    //testing removeFirst
    ArrayList<Set<String>> test1 = obj.cleanUp(answerFin);
    ArrayList<String> test2 = obj.orderList(test1);
    System.out.println(test1);
    //for (String s : obj.edit(test)){
    //  System.out.println(s);
    //}



  }

}
//method that does what the problem wants
//     public ArrayList<Set<String>> cleanUp(ArrayList<Set<String>> entries){
//       for(int i = 0; i < entries.size(); i++){
//         for (int j = i + 1; j < entries.size(); j++){
//           if (intersection(entries.get(i), entries.get(j)) == true) {
//             entries.get(i).addAll(entries.get(j));
//             entries.remove(entries.get(j));
//             System.out.println(entries)
//             i = 0;
//
//           }
//         }
//       }
//       return entries;
//     }
