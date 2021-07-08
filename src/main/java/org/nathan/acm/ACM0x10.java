package org.nathan.acm;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.tuples.Tuple;

import java.util.*;

public class ACM0x10{
  /**
   * @param floatRects list of width and height of rectangle
   * @return range of largest inner connection of rectangles(left inclusive and right exclusive)
   */
  public static int largestRectInHist(List<Tuple<Integer, Integer>> floatRects){
    Deque<Tuple<Integer, Integer>> rectRecord = new ArrayDeque<>(floatRects.size());
    int max_area = 0;
    for(var fr : floatRects){
      if(!rectRecord.isEmpty() && fr.second() < rectRecord.getLast().second()){
        int acc_width = 0;
        while(!rectRecord.isEmpty() && rectRecord.getLast().second() > fr.second()) {
          var r = rectRecord.removeLast();
          acc_width += r.first();
          max_area = Math.max(max_area, r.second() * acc_width);
        }
        if(!rectRecord.isEmpty()){
          var l = rectRecord.removeLast();
          rectRecord.addLast(new Tuple<>(l.first() + acc_width, l.second()));
        }
      }
      rectRecord.addLast(fr);
    }
    {
      int acc_width = 0;
      while(!rectRecord.isEmpty()) {
        var r = rectRecord.removeLast();
        acc_width += r.first();
        max_area = Math.max(max_area, r.second() * acc_width);
      }
    }
    return max_area;
  }

  /**
   * subarray of max sum
   *
   * @param array array
   * @param M     max length of subarray
   * @return subarray
   */
  public static double monotonousQueue(double[] array, int M){
    double[] s = new double[array.length + 1];
    System.arraycopy(array, 0, s, 1, array.length);
    for(int i = 2; i < s.length; i++){
      s[i] += s[i - 1];
    }
    Deque<Integer> deque = new ArrayDeque<>();
    deque.addFirst(0);
    double ans = Double.NEGATIVE_INFINITY;
    for(int i = 1; i <= array.length; i++){
      while(!deque.isEmpty() && i - deque.getFirst() > M) {
        deque.removeFirst();
      }
      ans = Math.max(ans, s[i] - s[deque.getFirst()]);
      while(!deque.isEmpty() && s[deque.getLast()] >= s[i]) {
        deque.removeLast();
      }
      deque.addLast(i);
    }
    return ans;
  }

  /**
   * min iterate cell and its max iterate count of every prefix txt
   *
   * @param txt string
   * @return prefix string to its min iterate cell and max iterate count
   */
  public static @NotNull Map<String, Tuple<String, Integer>> period(@NotNull String txt){
    var next = org.nathan.algsJ.strMatch.KMP.computePrefixFunction(txt);
    Map<String, Tuple<String, Integer>> ans = new HashMap<>();
    for(int i = 2; i < txt.length(); i++){
      if(i % (i - next[i]) == 0 && i / (i - next[i]) > 1){
        var prefix = txt.substring(0, i);
        var cell = txt.substring(0, i - next[i]);
        var repeat = i / (i - next[i]);
        ans.put(prefix, new Tuple<>(cell, repeat));
      }
    }
    return ans;
  }

  /**
   * <pre>
   * bca -> abc
   * cab -> abc
   * abc -> abc
   * </pre>
   *
   * @param s string
   * @return min cyclic representation
   */
  public static @NotNull String minCyclicShift(@NotNull String s){
    var ss = s + s;
    int i = 0, j = 1, min = -1, s_len = s.length();
    var funcCompare = new Object(){
      int k = 0;

      int apply(int i, int j){
        k = 0;
        while(k < s_len) {
          char i_c = ss.charAt(i), j_c = ss.charAt(j);
          if(i_c > j_c){
            return 1;
          }
          else if(i_c < j_c){
            return -1;
          }
          else{
            i++;
            j++;
            k++;
          }
        }
        return 0;
      }
    };
    while(i < s_len && j < s_len) {
      int compare = funcCompare.apply(i, j);
      switch(compare){
        case 0 -> { return s;}
        case 1 -> {
          i += funcCompare.k + 1;
          if(i == j){
            i++;
          }
          min = j;
        }
        case -1 -> {
          j += funcCompare.k + 1;
          if(i == j){
            j++;
          }
          min = i;
        }
      }
    }
    return ss.substring(min, min + s_len);
  }
}