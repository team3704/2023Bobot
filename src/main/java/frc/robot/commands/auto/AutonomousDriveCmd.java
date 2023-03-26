package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSub;

public class AutonomousDriveCmd extends CommandBase {
    private Timer t = new Timer();
    private DriveTrainSub drivetrain;
    private double duration;
    private double[] motorOutputs = new double[2];

    public AutonomousDriveCmd(DriveTrainSub drivetrain, double duration, double output) {
        t.stop(); t.reset();
        this.drivetrain = drivetrain;
        this.duration = duration;
        motorOutputs[0] = output; motorOutputs[1] = output;
        addRequirements(drivetrain);
    }
    public AutonomousDriveCmd(DriveTrainSub drivetrain, double duration, double leftOutput, double rightOutput) {
        t.stop(); t.reset();
        this.drivetrain = drivetrain;
        this.duration = duration;
        motorOutputs[0] = leftOutput; motorOutputs[1] = rightOutput;
        addRequirements(drivetrain);
    }

    @Override public void
    initialize() {t.start();}

    @Override public void
    execute() {
        // 0 for left output, 1 for right output
        drivetrain.motorOverride(motorOutputs[0], motorOutputs[1]);
    }

    @Override public boolean isFinished() {
        return t.get() > duration;
    }

    @Override public void
    end(boolean interrupted) {
        drivetrain.motorOverride(0, 0);
        t.stop();
        t.reset();
    }

    // made for easy to use static imports
    public static AutonomousDriveCmd autoDrive(DriveTrainSub drivetrain, double duration, double output) {
        return new AutonomousDriveCmd(drivetrain, duration, output);
    }

    public static AutonomousDriveCmd autoDrive(DriveTrainSub drivetrain, double duration, double leftOutput, double rightOutput) {
        return new AutonomousDriveCmd(drivetrain, duration, leftOutput, rightOutput);
    }
}
