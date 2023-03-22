package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ElevatorSub;

import static frc.robot.RobotContainer.controller;
/**
 * Ideas:
 * <p>- Stick controlled while holding certain button
 * <p>- Toggle mode that enables simultaneous elevator
 * and wrist control while holding target at same Y level
 * <p>- Toggle precision mode to move at a slower speed     
 */
public class ElevatorCmd extends CommandBase {
    private ElevatorSub elevator;
    private double movement;
    public ElevatorCmd(ElevatorSub elevator, double movement) {
        this.elevator = elevator;
        addRequirements(elevator);
        this.movement = movement;
    }

    @Override public void
    initialize() {
        elevator.pidReset();
    }

    @Override public void
    execute() {
        elevator.setOutput(movement*RobotContainer.testSpeed);
    }

    @Override public boolean
    isFinished() {
        return false;
    }

    @Override public void
    end(boolean interrupted) {elevator.setOutput(0);}
}
