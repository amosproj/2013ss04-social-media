package com.amos.project4.views;
import java.util.Vector;


public class TreeNodeVector<E> extends Vector<E> {
	  String name;

	  TreeNodeVector(String name) {
	    this.name = name;
	  }

	  TreeNodeVector(String name, E elements[]) {
	    this.name = name;
	    for (int i = 0, n = elements.length; i < n; i++) {
	      add(elements[i]);
	    }
	  }

	  public String toString() {
	    return "[" + name + "]";
	  }
}
