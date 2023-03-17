package com.example.demo;

import java.util.*;


public class HanoiTower {
  private static int numRings;
  private static int[][] goalState;

  public static void main(String[] args) {
    numRings = 3; // number of rings in the problem

    // initialize goal state
    goalState = new int[numRings][3];
    for (int i = 0; i < numRings; i++) {
      goalState[i][2] = i + 1;
    }

    // solve the problem
    int[][] startState = new int[numRings][3];
    for (int i = 0; i < numRings; i++) {
      startState[i][0] = i + 1;
    }
    List<int[][]> solution = solve(startState);

    // print the solution
    for (int i = 0; i < solution.size(); i++) {
      System.out.println("Move " + (i + 1) + ":");
      printState(solution.get(i));
    }
  }

  private static List<int[][]> solve(int[][] startState) {
    PriorityQueue<Node> openList = new PriorityQueue<>();
    HashSet<String> closedList = new HashSet<>();
    Node startNode = new Node(startState, 0, manhattanDistance(startState), null);
    openList.add(startNode);

    while (!openList.isEmpty()) {
      Node currentNode = openList.poll();
      if (isGoalState(currentNode.getState())) {
        return reconstructPath(currentNode);
      }

      closedList.add(Arrays.deepToString(currentNode.getState()));
      List<Node> nextNodes = generateStates(currentNode);
      for (Node nextNode : nextNodes) {
        if (!closedList.contains(Arrays.deepToString(nextNode.getState()))) {
          openList.add(nextNode);
        }
      }
    }

    return null; // no solution found
  }

  private static List<Node> generateStates(Node node) {
    List<Node> nextNodes = new ArrayList<>();
    int[][] currentState = node.getState();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (currentState[i][j] != 0) {
          for (int k = 0; k < 3; k++) {
            if (k != i) {
              int[][] nextState = new int[3][3];
              for (int l = 0; l < 3; l++) {
                nextState[l] = Arrays.copyOf(currentState[l], 3);
              }
              int ring = nextState[i][j];
              nextState[i][j] = 0;
              int row = -1;
              for (int l = 0; l < 3; l++) {
                if (nextState[k][l] == 0) {
                  row = l;
                  break;
                }
              }
              nextState[k][row] = ring;
              int g = node.getG() + 1;
              int h = manhattanDistance(nextState);
              nextNodes.add(new Node(nextState, g, h, node));
            }
          }
        }
      }
    }
    return nextNodes;
  }


  private static List<int[][]> reconstructPath(Node node) {
    List<int[][]> path = new ArrayList<>();
    while (node != null) {
      path.add(0, node.getState());
      node = node.getParent();
    }
    return path;
  }

  private static int manhattanDistance(int[][] state) {
    int distance = 0;
    for (int i = 0; i < numRings; i++) {
      for (int j = 0; j < 3; j++) {
        if (state[i][j] != 0) {
          int[] goalPosition = findRing(goalState, state[i][j]);
          distance += Math.abs(i - goalPosition[0]) + Math.abs(j - goalPosition[1]);
        }
      }
    }
    return distance;
  }


  private static int[] findRing(int[][] state, int ring) {
    int[] position = new int[2];
    for (int i = 0; i < numRings; i++) {
      for (int j = 0; j < 3; j++) {
        if (state[i][j] == ring) {
          position[0] = i;
          position[1] = j;
          return position;
        }
      }
    }
    return null;
  }

  private static boolean isGoalState(int[][] state) {
    for (int i = 0; i < numRings; i++) {
      for (int j = 0; j < 3; j++) {
        if (state[i][j] != goalState[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  private static boolean arraysEqual(int[][] arr1, int[][] arr2) {
    if (arr1.length != arr2.length || arr1[0].length != arr2[0].length) {
      return false;
    }
    for (int i = 0; i < arr1.length; i++) {
      for (int j = 0; j < arr1[0].length; j++) {
        if (arr1[i][j] != arr2[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  private static void printState(int[][] state) {
    for (int i = numRings - 1; i >= 0; i--) {
      for (int j = 0; j < 3; j++) {
        System.out.print(state[i][j] + " ");
      }
      System.out.println();
    }
  }

  private static class Node implements Comparable<Node> {
    private int[][] state;
    private int g;
    private int h;
    private Node parent;

    public Node(int[][] state, int g, int h, Node parent) {
      this.state = state;
      this.g = g;
      this.h = h;
      this.parent = parent;
    }

    public int[][] getState() {
      return state;
    }

    public int getG() {
      return g;
    }

    public int getH() {
      return h;
    }

    public Node getParent() {
      return parent;
    }

    public int getF() {
      return g + h;
    }

    @Override
    public int compareTo(Node other) {
      return Integer.compare(getF(), other.getF());
    }
  }
}

