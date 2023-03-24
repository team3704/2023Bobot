package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSub;

public class AutonomousCmd extends CommandBase {
    private Timer t = new Timer();
    private DriveTrainSub drivetrain;

    public AutonomousCmd(DriveTrainSub drivetrain) {
        t.stop(); t.reset();
        this.drivetrain = drivetrain;
    }

    @Override public void
    initialize() {t.start();}

    @Override public void
    execute() {
        drivetrain.motorOverride(0.3, 0.3);
    }

    @Override public boolean isFinished() {return t.get() > 0.4;}

    @Override public void
    end(boolean interrupted) {
        drivetrain.motorOverride(0, 0);
        t.stop();
        t.reset();
    }
}