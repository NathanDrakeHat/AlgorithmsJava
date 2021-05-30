package org.nathan.algorithmsJava.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DFSVertex<V>{
    @NotNull
    final V id;
    @Nullable
    DFSVertex<V> parent;
    int discover; //d
    int finish; // f
    DFS.COLOR color;

    public DFSVertex(@NotNull V name){
        this.id = name;
    }

    public @NotNull V getId(){
        return id;
    }

    public @Nullable DFSVertex<V> getParent(){
        return parent;
    }

    @Override
    public String toString(){
        return String.format("DFS.Vertex: (%s)", id);
    }
}