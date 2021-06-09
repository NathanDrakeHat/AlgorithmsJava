package org.nathan.algorithmsJ.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DFSTest{


  static LinkedGraph<DFSVertex<String>> makeStronglyConnectedComponentsDemo(){
    String t = "a,b,c,d,e,f,g,h";
    var names = t.split(",");
    var A = new ArrayList<DFSVertex<String>>(names.length);
    for(int i = 0; i < names.length; i++){
      A.add(i, new DFSVertex<>(names[i]));
    }
    LinkedGraph<DFSVertex<String>> G = new LinkedGraph<>(A, true);
    G.setNeighbor(A.get(0), A.get(1));

    G.setNeighbor(A.get(1), A.get(2));
    G.setNeighbor(A.get(1), A.get(4));
    G.setNeighbor(A.get(1), A.get(5));

    G.setNeighbor(A.get(2), A.get(3));
    G.setNeighbor(A.get(2), A.get(6));

    G.setNeighbor(A.get(3), A.get(2));
    G.setNeighbor(A.get(3), A.get(7));

    G.setNeighbor(A.get(4), A.get(0));
    G.setNeighbor(A.get(4), A.get(5));

    G.setNeighbor(A.get(5), A.get(6));

    G.setNeighbor(A.get(6), A.get(5));
    G.setNeighbor(A.get(6), A.get(7));

    G.setNeighbor(A.get(7), A.get(7));

    return G;
  }

  static LinkedGraph<DFSVertex<String>> makeGraph(){
    String names = "uvwxyz";
    var vs = new ArrayList<DFSVertex<String>>(6);

    for(int i = 0; i < 6; i++){
      vs.add(i, new DFSVertex<>(String.valueOf(names.charAt(i))));
    }
    var G = new LinkedGraph<>(vs, true);
    G.setNeighbor(vs.get(0), vs.get(1));
    G.setNeighbor(vs.get(0), vs.get(3));

    G.setNeighbor(vs.get(1), vs.get(4));

    G.setNeighbor(vs.get(2), vs.get(4));
    G.setNeighbor(vs.get(2), vs.get(5));

    G.setNeighbor(vs.get(3), vs.get(1));

    G.setNeighbor(vs.get(4), vs.get(3));

    G.setNeighbor(vs.get(5), vs.get(5));

    return G;
  }

  LinkedGraph<DFSVertex<String>> dfsGraph = makeGraph();

  @Test
  void depthFirstSearchTest(){
    DFS.depthFirstSearch(dfsGraph);
    var vertices = dfsGraph.allVertices();
    List<DFSVertex<String>> l = new ArrayList<>(vertices);
    l.sort(Comparator.comparing(DFSVertex::getId));
    assertEquals(1, l.get(0).discover);
    assertEquals(8, l.get(0).finish);

    if(2 == l.get(1).discover){
      assertEquals(7, l.get(1).finish);
      if(l.get(3).discover == 4){
        assertEquals(5, l.get(3).finish);
        assertEquals(3, l.get(4).discover);
        assertEquals(6, l.get(4).finish);
      }
      else{
        assertEquals(3, l.get(3).discover);
        assertEquals(6, l.get(3).finish);
        assertEquals(4, l.get(4).discover);
        assertEquals(5, l.get(4).finish);
      }
    }
    else{
      assertEquals(2, l.get(3).discover);
      assertEquals(7, l.get(3).finish);
      if(3 == l.get(4).discover){
        assertEquals(6, l.get(4).finish);
        assertEquals(4, l.get(1).discover);
        assertEquals(5, l.get(1).finish);
      }
      else{
        assertEquals(4, l.get(4).discover);
        assertEquals(5, l.get(4).finish);
        assertEquals(3, l.get(1).discover);
        assertEquals(6, l.get(1).finish);
      }

    }

    if(l.get(2).discover == 9){
      assertEquals(12, l.get(2).finish);
      assertEquals(10, l.get(5).discover);
      assertEquals(11, l.get(5).finish);
    }
    else{
      assertEquals(11, l.get(2).discover);
      assertEquals(12, l.get(2).finish);
      assertEquals(9, l.get(5).discover);
      assertEquals(10, l.get(5).finish);
    }


  }

  static LinkedGraph<DFSVertex<String>> makeTopographicalDemo(){
    var A = new ArrayList<DFSVertex<String>>(9);
    String t = "undershorts,pants,belt,shirt,tie,jacket,socks,shoes,watch";
    var names = t.split(",");
    for(int i = 0; i < 9; i++){
      A.add(i, new DFSVertex<>(names[i]));
    }
    LinkedGraph<DFSVertex<String>> G = new LinkedGraph<>(A, true);
    G.setNeighbor(A.get(0), A.get(1));
    G.setNeighbor(A.get(0), A.get(6));

    G.setNeighbor(A.get(1), A.get(2));
    G.setNeighbor(A.get(1), A.get(6));

    G.setNeighbor(A.get(2), A.get(5));

    G.setNeighbor(A.get(3), A.get(2));
    G.setNeighbor(A.get(3), A.get(4));

    G.setNeighbor(A.get(4), A.get(5));

    G.setNeighbor(A.get(6), A.get(7));

    return G;
  }


  LinkedGraph<DFSVertex<String>> topologicalGraph = makeTopographicalDemo();

  @Test
  void topologicalSortTest(){

    var l = DFS.topologicalSort(topologicalGraph);
    boolean flag = true;
    for(int i = 1; i < l.size(); i++){
      for(int j = 0; j < i; j++){
        flag = topologicalSorted(l.get(j), l.get(i), topologicalGraph);
        if(!flag){
          break;
        }
      }
      if(!flag){
        break;
      }
    }
    assertTrue(flag);
  }

  boolean topologicalSorted(DFSVertex<String> target, DFSVertex<String> current, LinkedGraph<DFSVertex<String>> G){
    if(current.equals(target)){
      return false;
    }
    var edges = G.edgesAt(current);
    if(edges.isEmpty()){
      return true;
    }
    else{
      boolean t = true;
      for(var edge : edges){
        var i = edge.another(current);
        t = topologicalSorted(target, i, G);
        if(!t){
          break;
        }
      }
      return t;
    }
  }

  LinkedGraph<DFSVertex<String>> sortedTestGraph = makeTopographicalDemo();

  @Test
  void topologicalSortedTest(){

    boolean flag = true;
    List<DFSVertex<String>> t = new ArrayList<>(sortedTestGraph.allVertices());
    for(int i = 1; i < t.size(); i++){
      for(int j = 0; j < i; j++){
        flag = topologicalSorted(t.get(j), t.get(i), sortedTestGraph);
        if(!flag){
          break;
        }
      }
      if(!flag){
        break;
      }
    }
    assertFalse(flag);
  }

  @Test
  void stronglyConnectedComponentsTest(){
    var G = makeStronglyConnectedComponentsDemo();
    DFS.stronglyConnectedComponents(G);
    var vertices = G.allVertices();
    List<DFSVertex<String>> vs = new ArrayList<>(vertices);
    assertTrue((getRoot(vs.get(0)) == getRoot(vs.get(1))) & (getRoot(vs.get(1)) == getRoot(vs.get(4))));
    assertSame(getRoot(vs.get(2)), getRoot(vs.get(3)));
    assertSame(getRoot(vs.get(5)), getRoot(vs.get(6)));
    assertSame(getRoot(vs.get(7)), vs.get(7));

  }

  DFSVertex<String> getRoot(DFSVertex<String> v){
    var t = v;
    while(t.parent != null) {
      t = t.parent;
    }
    return t;
  }
}