package controllers;

import java.util.ArrayList;

import models.StateModel;
import models.definitions.State;

public class StateController {
  private StateModel stateModel;

  public StateController(StateModel stateModel) {
    this.stateModel = stateModel;
  }

  public void getAll() {
    ArrayList<State> stateList = this.stateModel.getAll();
    System.out.println("State List");
    System.out.println("==========");
    for (State state : stateList) {
      System.out.println(state.id + ": " + state.name);
    }
  }

  public void create(String name) {
    this.stateModel.create(name);
    System.out.println("State added: " + name);
  }

  public void update(String prevName, String newName) {
    this.stateModel.update(prevName, newName);
    System.out.println("State updated: " + prevName + " to "+ newName);
  }

  public void delete(String name) {
    this.stateModel.delete(name);
    System.out.println("State deleted: " + name);
  }

}
