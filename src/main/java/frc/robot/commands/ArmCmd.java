package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSub;

import static frc.robot.RobotContainer.controller;

public class ArmCmd extends CommandBase {
    private final ArmSub arm;

    public ArmCmd(ArmSub arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override public void
    execute() {
        arm.setOutput(MathUtil.applyDeadband(controller.getRightY(), 0.05) * RobotContainer.testSpeed);
    }

    @Override public boolean
    isFinished() {return false;}

    @Override public void
    end(boolean interrupted) {
        arm.setOutput(0);
    }
}