package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSub;
/**
 * Ideas:
 * <p>- Stick controlled while holding certain button
 * <p>- Toggle mode that enables simultaneous elevator
 * and wrist control while holding target at same Y level
 * <p>- Toggle precision mode to move at a slower speed     
 */
public class ElevatorCmd extends CommandBase {
    private ElevatorSub elevator;

    public ElevatorCmd(ElevatorSub elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }

    @Override public void
    execute() {}

    @Override public boolean
    isFinished() {
        return false;
    }

    @Override public void
    end(boolean interrupted) {}
}
