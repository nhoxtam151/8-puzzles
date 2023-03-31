package com.example.demo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Node implements Comparable<Node> {

  int id;
  int[][] value;
  List<Node> neighbors = new LinkedList<>();
  Actions parentAction;
  Node parentNode;

  int missPlaced;
  int g = 0;
  int f;

  public Node(int[][] value, Actions parentAction, Node parentNode) {
    this.value = value;
    this.parentAction = parentAction;
    this.parentNode = parentNode;

  }

  public void calculateMissPlaced(Node goal) {
    int n = goal.value.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (value[i][j] != goal.value[i][j] && value[i][j] != 0) {
          missPlaced++;
        }
      }
    }

  }

  public int calculateF() {
    f = g + missPlaced;
    return f;
  }


  @Override
  public int compareTo(Node o) {
    return this.calculateF() - o.calculateF();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Node)) {
      return false;
    }
    Node node = (Node) o;
    return Arrays.deepEquals(value, node.value);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(value);
  }
}
