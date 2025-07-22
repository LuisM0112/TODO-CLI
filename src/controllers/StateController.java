package controllers;

import java.util.ArrayList;

import Helpers.Printer;
import Helpers.Messages;
import models.StateModel;
import models.definitions.State;

public class StateController {
  private StateModel stateModel;

  public StateController(StateModel stateModel) {
    this.stateModel = stateModel;
  }

  public void getAll() {
    ArrayList<State> stateList = this.stateModel.getAll();
    Printer.printStatesTable(stateList);
  }

  public void getById(String stateId) {
    State state = this.stateModel.getById(Integer.parseInt(stateId));
    System.out.println(state.id + ": " + state.name);
  }

  public void create(String name) {
    this.stateModel.create(name);
    System.out.println(Messages.State.created);
  }

  public void update(String prevName, String newName) {
    this.stateModel.update(prevName, newName);
    System.out.println(Messages.State.updated);
  }

  public void delete(String name) {
    this.stateModel.delete(name);
    System.out.println(Messages.State.deleted);
  }

}
