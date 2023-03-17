package com.example.demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class Solver {

  public static void main(String[] args) {

    int[][] tiles = {{4, 1, 3}, {5, 7, 6}, {2, 0, 8}};
    int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    Node init = new Node(tiles, null, null);
    Node des = new Node(goal, null, null);

    Solver solver = new Solver();
    Node testNeighbor = solver.neighbors(init, Actions.Right);
    solver.printMatrix(testNeighbor);
    System.out.println();


  }

  public void printMatrix(Node node) {
    int n = node.value.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(node.value[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void printPositionOfZero(int[] position) {
    int n = position.length;
    for (int i = 0; i < n; i++) {
      System.out.print(position[i] + " ");
    }
  }


  public int[] findPositionOfZero(Node node) {
    int[] positionOfZero = new int[2];
    for (int i = 0; i < node.value.length; i++) {
      for (int j = 0; j < node.value.length; j++) {
        if (node.value[i][j] == 0) {
          positionOfZero[0] = i;
          positionOfZero[1] = j;
          return positionOfZero;
        }
      }
    }
    return positionOfZero;
  }


  //create neighbors
  public Node neighbors(Node current, Actions action) {
    int rowOfZero = findPositionOfZero(current)[0];
    int columnOfZero = findPositionOfZero(current)[1];
    final int rowOfZeroMinusOne = rowOfZero - 1;
    final int rowOfZeroPlusOne = rowOfZero + 1;
    final int columnOfZeroMinusOne = columnOfZero - 1;
    final int columnOfZeroPlusOne = columnOfZero + 1;

    switch (action) {
      case Up:
        try {
          int tempValue = current.value[rowOfZero][columnOfZero];
          current.value[rowOfZero][columnOfZero] = current.value[rowOfZeroMinusOne][columnOfZero];
          current.value[rowOfZeroMinusOne][columnOfZero] = tempValue;
          return new Node(current.value, Actions.Up, current);
        } catch (IndexOutOfBoundsException ie) {
          ie.getMessage();
        }
      case Down:
        try {
          int tempValue = current.value[rowOfZero][columnOfZero];
          current.value[rowOfZero][columnOfZero] = current.value[rowOfZeroPlusOne][columnOfZero];
          current.value[rowOfZeroPlusOne][columnOfZero] = tempValue;
          return new Node(current.value, Actions.Down, current);
        } catch (IndexOutOfBoundsException ie) {
          ie.getMessage();
        }
      case Left:
        try {
          int tempValue = current.value[rowOfZero][columnOfZero];
          current.value[rowOfZero][columnOfZero] = current.value[rowOfZero][columnOfZeroMinusOne];
          current.value[rowOfZero][columnOfZeroMinusOne] = tempValue;
          return new Node(current.value, Actions.Down, current);
        } catch (IndexOutOfBoundsException ie) {
          ie.getMessage();
        }
      case Right:
        try {
          int tempValue = current.value[rowOfZero][columnOfZero];
          current.value[rowOfZero][columnOfZero] = current.value[rowOfZero][columnOfZeroPlusOne];
          current.value[rowOfZero][columnOfZeroPlusOne] = tempValue;
          return new Node(current.value, Actions.Down, current);
        } catch (IndexOutOfBoundsException ie) {
          ie.getMessage();
        }

      default:
        break;
    }
    return null;
  }

  public void solveGreedyBFS(Node initialNode, Node goal) {
    PriorityQueue<Node> open = new PriorityQueue<>();
    HashSet<Node> closed = new HashSet<>();
    open.add(initialNode);
    HashMap<Integer, Actions> actions = new HashMap<>();
    actions.put(1, Actions.Up);
    actions.put(2, Actions.Down);
    actions.put(3, Actions.Left);
    actions.put(4, Actions.Right);
    while (!open.isEmpty()) {
      Node current = open.poll();

      if (Arrays.deepEquals(current.value, goal.value)) {
        return;
      }
      //check the action
      for (Map.Entry<Integer, Actions> action : actions.entrySet()) {
        //do action
        Node neighbor = neighbors(current, action.getValue());
        // add newNode into neighbors
        if(neighbor != null) {
          current.neighbors.add(neighbor);
          neighbor.g += current.g;
        }
      }
      //after create neighbors by action then will find out the one that have minimum  F value
      Node minF = current.neighbors.stream().min(Comparator.comparing(node -> node.calculateF()))
          .get();
      minF.parentNode = current;
      if(!closed.contains(minF)) {

      }
      closed.add(current);
      open.add(minF);

    }
  }
}
