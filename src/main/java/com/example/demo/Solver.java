package com.example.demo;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Stack;

public class Solver {

  public static void main(String[] args) {

//
//    int[][] tiles = {{4, 1, 3}, {5, 7, 6}, {2, 0, 8}};
//    int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    int[][] tiles = {{2, 8, 3}, {1, 6, 4}, {7, 0, 5}};
    int[][] goal = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
    Node init = new Node(tiles, null, null);
    Node des = new Node(goal, null, null);

    Solver solver = new Solver();
    solver.solveGreedyBFS(init, des);


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

  public static int[][] deepCopy(int[][] original) {
    if (original == null) {
      return null;
    }

    final int[][] result = new int[original.length][];
    for (int i = 0; i < original.length; i++) {
      result[i] = Arrays.copyOf(original[i], original[i].length);
    }
    return result;
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
          Node node = new Node(deepCopy(current.value), Actions.Up, current);
          int tempValue = node.value[rowOfZero][columnOfZero];
          node.value[rowOfZero][columnOfZero] = node.value[rowOfZeroMinusOne][columnOfZero];
          node.value[rowOfZeroMinusOne][columnOfZero] = tempValue;
          node.g = current.g + 1;
          node.parentNode = current;
          return node;
        } catch (IndexOutOfBoundsException ie) {
          ie.getMessage();
          break;
        }
      case Down:
        try {
          Node node = new Node(deepCopy(current.value), Actions.Down, current);
          int tempValue = node.value[rowOfZero][columnOfZero];
          node.value[rowOfZero][columnOfZero] = node.value[rowOfZeroPlusOne][columnOfZero];
          node.value[rowOfZeroPlusOne][columnOfZero] = tempValue;
          node.g = current.g + 1;
          node.parentNode = current;
          return node;
        } catch (IndexOutOfBoundsException ie) {
          ie.getMessage();
          break;
        }
      case Left:
        try {
          Node node = new Node(deepCopy(current.value), Actions.Left, current);
          int tempValue = node.value[rowOfZero][columnOfZero];
          node.value[rowOfZero][columnOfZero] = node.value[rowOfZero][columnOfZeroMinusOne];
          node.value[rowOfZero][columnOfZeroMinusOne] = tempValue;

          node.g = current.g + 1;
          node.parentNode = current;
          return node;
        } catch (IndexOutOfBoundsException ie) {
          ie.getMessage();
          break;
        }
      case Right:
        try {
          Node node = new Node(deepCopy(current.value), Actions.Right, current);
          int tempValue = node.value[rowOfZero][columnOfZero];
          node.value[rowOfZero][columnOfZero] = node.value[rowOfZero][columnOfZeroPlusOne];
          node.value[rowOfZero][columnOfZeroPlusOne] = tempValue;
          node.g = current.g + 1;
          node.parentNode = current;
          return node;
        } catch (IndexOutOfBoundsException ie) {
          ie.getMessage();
          break;
        }

      default:
        break;
    }
    return null;
  }



  void printParent(Node goal) {
    if(goal == null)
      return;
    printParent(goal.parentNode);

    printMatrix(goal);
    System.out.println();
  }
  public void solveGreedyBFS(Node initialNode, Node goal) {
    PriorityQueue<Node> open = new PriorityQueue<>(
        Comparator.comparingInt(Node::calculateF));
    HashSet<Node> closed = new HashSet<>();

    initialNode.calculateMissPlaced(goal);
    initialNode.calculateF();
    open.add(initialNode);
    HashMap<Integer, Actions> actions = new HashMap<>();
    actions.put(1, Actions.Up);
    actions.put(2, Actions.Down);
    actions.put(3, Actions.Left);
    actions.put(4, Actions.Right);

    while (!open.isEmpty()) {
      Node current = open.poll();

      if (Arrays.deepEquals(current.value, goal.value)) {
        printParent(current);
        return;
      }
      //check the action
      for (Map.Entry<Integer, Actions> action : actions.entrySet()) {
        //do action
        Node neighbor = neighbors(current, action.getValue());
        // add newNode into neighbors
        if (neighbor != null && !open.contains(neighbor) && !closed.contains(neighbor)) {


          neighbor.calculateMissPlaced(goal);
          neighbor.calculateF();
          current.neighbors.add(neighbor);
        }
      }
      //after create neighbors by action then will find out the one that have minimum  F value
//      Node minF = current.neighbors.stream().min(Comparator.comparing(Node::calculateF))
//          .orElseThrow(NoSuchElementException::new);

      closed.add(current);

      for(Node neighbor : current.neighbors) {
        open.add(neighbor);
      }

    }
  }
}
