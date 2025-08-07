package com.todo.cli.controllers;

import java.util.ArrayList;

import com.todo.cli.helpers.Messages;
import com.todo.cli.helpers.Printer;
import com.todo.cli.models.StateModel;
import com.todo.cli.models.definitions.State;

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
    int resultStateId = this.stateModel.create(name);
    if (resultStateId == 0) {
      return;
    }
    System.out.println(Messages.State.created);
  }

  public void update(String prevName, String newName) {
    int resultStateId = this.stateModel.update(prevName, newName);
    if (resultStateId == 0) {
      return;
    }
    System.out.println(Messages.State.updated);
  }

  public void delete(String name) {
    int resultStateId = this.stateModel.delete(name);
    if (resultStateId == 0) {
      return;
    }
    System.out.println(Messages.State.deleted);
  }

}
