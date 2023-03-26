package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.RobotContainer.*;
import frc.robot.subsystems.DriveTrainSub;

public class DriveCmd extends CommandBase {
    private final DriveTrainSub drivetrain;

    public DriveCmd(DriveTrainSub drivesub) {
        this.drivetrain = drivesub;
        addRequirements(drivesub);
    }

    @Override public void
    execute() {
        drivetrain.CtrlDrive.arcadeDrive(
            controller.getRightX() * testSpeed,
            controller.getLeftY() * testSpeed
        );
    }

    @Override public boolean isFinished() {return false;}

    @Override public void
    end(boolean isFinished) {drivetrain.CtrlDrive.arcadeDrive(0, 0);}
}
