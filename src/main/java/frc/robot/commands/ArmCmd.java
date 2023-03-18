package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSub;

public class ArmCmd extends CommandBase {
    private final ArmSub arm;

    public ArmCmd(ArmSub arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override public boolean
    isFinished() {return false;}

    @Override public void
    end(boolean interrupted) {
        arm.setOutput(0);
    }
}