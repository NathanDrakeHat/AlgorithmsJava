package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;
import org.nathan.algorithmsJ.structures.FibonacciHeap;
import org.nathan.algorithmsJ.structures.MinHeap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * minimum spanning tree
 */
public final class MinSpanTree{
  public static <T>
  @NotNull Set<UnionEdge<KruskalVert<T>>> Kruskal(@NotNull LinkGraph<KruskalVert<T>> graph){
    Set<UnionEdge<KruskalVert<T>>> res = new HashSet<>();
    var edges_set = graph.getAllEdges();
    var edges_list = new ArrayList<>(edges_set);
    edges_list.sort(Comparator.comparingDouble(UnionEdge::weight));
    for(var edge : edges_list){
      var v1 = edge.former();
      var v2 = edge.latter();
      if(v1.findGroupId() != v2.findGroupId()){
        res.add(edge);
        v1.union(v2);
      }
    }
    return res;
  }

  public static <T> void MSTPrimFibonacciHeap(@NotNull LinkGraph<PrimVert<T>> graph,
                                              @NotNull PrimVert<T> r){
    FibonacciHeap<Double, PrimVert<T>> Q = new FibonacciHeap<>(Comparator.comparingDouble(a -> a));
    var vertices = graph.allVertices();
    for(var u : vertices){
      if(u != r){
        u.key = Double.POSITIVE_INFINITY;
      }
      else{
        u.key = 0.0;
      }
      Q.insert(u.key, u);
      u.parent = null;
    }
    while(Q.count() > 0) {
      var u = Q.extractMin();
      var u_edges = graph.edgesAt(u);
      for(var edge : u_edges){
        var v = edge.another(u);
        if(Q.contains(v) && edge.weight() < v.key){
          v.parent = u;
          v.key = edge.weight();
          Q.decreaseKey(v, v.key);
        }
      }
    }
  }

  public static <T> void MSTPrimMinHeap(@NotNull LinkGraph<PrimVert<T>> graph,
                                        @NotNull PrimVert<T> r){
    var vertices = graph.allVertices();
    for(var u : vertices){
      if(u != r){
        u.key = Double.POSITIVE_INFINITY;
      }
      else{
        u.key = 0.0;
      }
      u.parent = null;
    }
    MinHeap<Double, PrimVert<T>> Q = new MinHeap<>(vertices, PrimVert::getKey, Double::compare);
    while(Q.length() > 0) {
      var u = Q.extractMin();
      var u_edges = graph.edgesAt(u);
      for(var edge : u_edges){
        var v = edge.another(u);
        if(Q.contains(v) && edge.weight() < v.key){
          v.parent = u;
          v.key = edge.weight();
          Q.updateKey(v, v.key);
        }
      }
    }
  }

}