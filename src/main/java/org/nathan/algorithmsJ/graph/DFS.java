package org.nathan.algorithmsJ.graph;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * depth first search
 */
public final class DFS{
  public static <T> void depthFirstSearch(@NotNull LinkGraph<DFSVert<T>> G){
    var vertices = G.allVertices();
    vertices.parallelStream().forEach(v -> {
      v.color = COLOR.WHITE;
      v.parent = null;
    });
    int time = 0;
    for(var v : vertices){
      if(v.color == COLOR.WHITE){
        time = DFSVisit(G, v, time);
      }
    }
  }

  private static <T> int DFSVisit(LinkGraph<DFSVert<T>> G, DFSVert<T> u, int time){
    time++;
    u.discover = time;
    u.color = COLOR.GRAY;
    var u_edges = G.edgesAt(u);
    for(var edge : u_edges){
      var v = edge.another(u);
      if(v.color == COLOR.WHITE){
        v.parent = u;
        time = DFSVisit(G, v, time);
      }
    }
    u.color = COLOR.BLACK;
    time++;
    u.finish = time;
    return time;
  }

  public static <T> List<DFSVert<T>> topologicalSort(@NotNull LinkGraph<DFSVert<T>> G){
    depthFirstSearch(G);
    List<DFSVert<T>> l = new ArrayList<>(G.allVertices());
    l.sort((o1, o2) -> o2.finish - o1.finish); // descend order
    return l;
  }

  public static <T> void stronglyConnectedComponents(@NotNull LinkGraph<DFSVert<T>> G){
    var l = topologicalSort(G);
    var G_T = transposeGraph(G);
    depthFirstSearchOrderly(G_T, l);
  }

  private static <T> void depthFirstSearchOrderly(LinkGraph<DFSVert<T>> G, List<DFSVert<T>> order){
    var vertices = G.allVertices();
    for(var v : vertices){
      v.color = COLOR.WHITE;
      v.parent = null;
    }
    int time = 0;
    for(var v : order){
      if(v.color == COLOR.WHITE){
        time = DFSVisit(G, v, time);
      }
    }
  }

  private static <T> LinkGraph<DFSVert<T>> transposeGraph(LinkGraph<DFSVert<T>> graph){
    var new_graph = new LinkGraph<>(graph.allVertices(), true);
    var vertices = graph.allVertices();
    for(var v : vertices){
      var edges = graph.edgesAt(v);
      for(var edge : edges){
        var n = edge.another(v);
        new_graph.setNeighbor(n, v);
      }
    }
    return new_graph;
  }

  enum COLOR{WHITE, GRAY, BLACK}

}