package com.cinebook.command;

public interface BookingCommand {
    void execute();
    void undo();
}
