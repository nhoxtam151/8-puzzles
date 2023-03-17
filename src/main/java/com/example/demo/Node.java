package com.example.demo;

import java.util.List;

public class Node {

  int[][] value;
  List<Node> neighbors;
  List<Actions> actions;
  Actions parentAction;
  Node parentNode;

  int missPlaced;
  int g;
  int f;

  public Node(int[][] value, Actions parentAction, Node parentNode) {
    this.value = value;
    this.parentAction = parentAction;
    this.parentNode = parentNode;
  }

  public int  calculateMissPlaced(Node goal) {
    int n = goal.value.length;
    for(int i = 0; i < n; i++)
    {
      for(int j = 0; j < n; j++) {
        if(value[i][j] != goal.value[i][j] && value[i][j] != 0)
        {
          missPlaced++;
          System.out.print(value[i][j]);
        }
      }
    }
    return missPlaced;
  }

  public int calculateF() {
    f = g + missPlaced;
    return f;
  }



}
