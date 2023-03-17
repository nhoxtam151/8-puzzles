package com.example.demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {

  public static void main(String[] args) {

    int[][] tiles = {{4, 1, 3}, {5, 7, 6}, {2, 0, 8}};
    int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    for (int i = 0; i < goal.length; i++) {
      for (int j = 0; j < goal.length; j++) {
        System.out.print(goal[i][j] + " ");
      }
      System.out.println();
    }

    Node init = new Node(tiles,null,null);
    Node des = new Node(goal, null, null);
    System.out.println(init.calculateMissPlaced(des));
  }

  Node swap(Node node, int i, int j, Actions action) {
    final int plusOne = 1;
    final int minusOne = -1;
    if (action == Actions.Up) {
      int temp = node.value[i + minusOne][j];
      node.value[i + minusOne][j] = node.value[i][j];
      node.value[i][j] = temp;
    }
    //else
    return new Node(node.value,action, node);
  }

  public void solve(Node node, Actions action) {
    node.actions.add(Actions.Up);
    for (int i = 0; i < node.value.length; i++) {
      for (int j = 0; j < node.value.length; j++) {
        if (node.value[i][j] == 0) {

        }
      }
    }
  }

  public void solveGreedyBFS(Node inittialNode, Node goal) {
    PriorityQueue<Node> open = new PriorityQueue<>();
    HashSet<Node> closed = new HashSet<>();
    open.add(inittialNode);

    while (!open.isEmpty()) {
      Node current = open.poll();
      if (Arrays.deepEquals(current.value, goal.value)) {
        return;
      }
      //check the action

      //after create neighbors by action then will find out the one that have minimum  F value
      Node minF = current.neighbors.stream().min(Comparator.comparing(node -> node.calculateF()))
          .get();
      minF.parentNode = current;
      closed.add(current);
      open.add(minF);



    }
  }
}
