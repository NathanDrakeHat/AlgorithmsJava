package graph;

import org.junit.jupiter.api.Test;

import static graph.MinSpanTree.*;
import static graph.LinkedGraph.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MinSpanTreeTest
{
    @Test
    public void algorithmOfKruskalTest()
    {
        var G = buildKruskalExample();
        var t = algorithmOfKruskal(G);
        assertTrue(t.equals(buildKruskalAnswer1()) || t.equals(buildKruskalAnswer2()));
        int i = 0;
        for (var e : t)
        {
            i += e.getWeight();
        }
        assertEquals(37, i);
    }

    static LinkedGraph<KruskalVertex<String>> buildKruskalExample()
    {
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<KruskalVertex<String>>(len);
        for (int i = 0; i < len; i++)
        {
            vertices.add(i, new KruskalVertex<>(names[i]));
        }
        LinkedGraph<KruskalVertex<String>> res = new LinkedGraph<>(vertices, Direction.NON_DIRECTED);
        int[] indexes1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
        int[] indexes2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
        double[] weights = new double[]{4, 8, 7, 9, 10, 2, 1, 8, 11, 2, 7, 6, 4, 14};
        int len_ = indexes1.length;
        for (int i = 0; i < len_; i++)
        {
            res.setNeighbor(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i]);
        }
        return res;
    }

    static Set<Edge<KruskalVertex<String>>> buildKruskalAnswer1()
    {
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<KruskalVertex<String>>(len);
        for (int i = 0; i < len; i++)
        {
            vertices.add(i, new KruskalVertex<>(names[i]));
        }
        int[] indexes1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
        int[] indexes2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
        double[] weights = new double[]{4, 8, 7, 9, 10, 2, 1, 8, 11, 2, 7, 6, 4, 14};
        Set<Edge<KruskalVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 2, 3, 5, 6, 7, 9, 12};
        for (var i : answers)
        {
            res.add(new Edge<>(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i], Direction.NON_DIRECTED));
        }
        return res;
    }

    static Set<Edge<KruskalVertex<String>>> buildKruskalAnswer2()
    {
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<KruskalVertex<String>>(len);
        for (int i = 0; i < len; i++)
        {
            vertices.add(i, new KruskalVertex<>(names[i]));
        }
        int[] indexes1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
        int[] indexes2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
        double[] weights = new double[]{4, 8, 7, 9, 10, 2, 1, 8, 11, 2, 7, 6, 4, 14};
        Set<Edge<KruskalVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 1, 2, 3, 5, 6, 9, 12};
        for (var i : answers)
        {
            res.add(new Edge<>(vertices.get(indexes1[i]), vertices.get(indexes2[i]), weights[i], Direction.NON_DIRECTED));
        }
        return res;
    }


    @Test
    public void algorithmOfPrimTestWithFibonacciHeap()
    {
        var graph = buildPrimExample();
        algorithmOfPrimWithFibonacciHeap(graph, new PrimVertex<>("a"));
        var vertices = graph.getAllVertices();
        Set<Set<PrimVertex<String>>> res = new HashSet<>();
        for (var vertex : vertices)
        {
            if (vertex.parent != null)
            {
                Set<PrimVertex<String>> t = new HashSet<>();
                t.add(vertex);
                t.add(vertex.parent);
                res.add(t);
            }
        }
        assertTrue(res.equals(buildPrimAnswer1()) || res.equals(buildPrimAnswer2()));
    }

    @Test
    public void algorithmOfPrimTestWithMinHeap()
    {
        var graph = buildPrimExample();
        algorithmOfPrimWithMinHeap(graph, new PrimVertex<>("a"));
        var vertices = graph.getAllVertices();
        Set<Set<PrimVertex<String>>> res = new HashSet<>();
        for (var vertex : vertices)
        {
            if (vertex.parent != null)
            {
                Set<PrimVertex<String>> t = new HashSet<>();
                t.add(vertex);
                t.add(vertex.parent);
                res.add(t);
            }
        }
        assertTrue(res.equals(buildPrimAnswer1()) || res.equals(buildPrimAnswer2()));
    }

    static LinkedGraph<PrimVertex<String>> buildPrimExample()
    {
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<PrimVertex<String>>(len);
        for (int i = 0; i < len; i++)
        {
            vertices.add(i, new PrimVertex<>(names[i]));
        }
        LinkedGraph<PrimVertex<String>> res = new LinkedGraph<>(vertices, Direction.NON_DIRECTED);
        int[] indices1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
        int[] indices2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
        double[] weights = new double[]{4, 8, 7, 9, 10, 2, 1, 8, 11, 2, 7, 6, 4, 14};
        int len_ = indices1.length;
        for (int i = 0; i < len_; i++)
        {
            res.setNeighbor(vertices.get(indices1[i]), vertices.get(indices2[i]), weights[i]);
        }
        return res;
    }

    static Set<Set<PrimVertex<String>>> buildPrimAnswer1()
    {
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<PrimVertex<String>>(len);
        for (int i = 0; i < len; i++)
        {
            vertices.add(i, new PrimVertex<>(names[i]));
        }
        int[] indexes1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
        int[] indexes2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
        Set<Set<PrimVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 1, 2, 3, 5, 6, 9, 12};
        for (int answer : answers)
        {
            Set<PrimVertex<String>> t = new HashSet<>();
            t.add(vertices.get(indexes1[answer]));
            t.add(vertices.get(indexes2[answer]));
            res.add(t);
        }
        return res;
    }

    static Set<Set<PrimVertex<String>>> buildPrimAnswer2()
    {
        String n = "a,b,c,d,e,f,g,h,i";
        String[] names = n.split(",");
        int len = names.length;
        var vertices = new ArrayList<PrimVertex<String>>(len);
        for (int i = 0; i < len; i++)
        {
            vertices.add(i, new PrimVertex<>(names[i]));
        }
        int[] indexes1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 1, 2, 8, 8, 2, 3};
        int[] indexes2 = new int[]{1, 2, 3, 4, 5, 6, 7, 0, 7, 8, 7, 6, 5, 5};
        Set<Set<PrimVertex<String>>> res = new HashSet<>();
        int[] answers = new int[]{0, 7, 2, 3, 5, 6, 9, 12};
        for (int answer : answers)
        {
            Set<PrimVertex<String>> t = new HashSet<>();
            t.add(vertices.get(indexes1[answer]));
            t.add(vertices.get(indexes2[answer]));
            res.add(t);
        }
        return res;
    }

}