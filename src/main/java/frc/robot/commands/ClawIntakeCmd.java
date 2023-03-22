package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSub;

public class ClawIntakeCmd extends CommandBase {
    private ClawSub claw;

    public ClawIntakeCmd(ClawSub claw) {
        this.claw = claw;
        addRequirements(claw);
    }

    @Override public void 
    initialize() {claw.setIntake(0.5);}

    @Override public boolean
    isFinished() {return false;}

    @Override public void end(boolean interrupted) {claw.setIntake(0);}
}
