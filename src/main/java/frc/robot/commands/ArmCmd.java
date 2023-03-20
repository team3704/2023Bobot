package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSub;

import static frc.robot.RobotContainer.controller;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class ArmCmd extends CommandBase {
    public static interface Function {
        public void execute(ArmSub arm);
    }
    private final ArmSub arm;
    private final Function test;
    public ArmCmd(ArmSub arm, Function test) {
        this.arm = arm;
        this.test = test;
        addRequirements(arm);
    }

    @Override public void
    execute() {
        test.execute(arm);
    }

    @Override public boolean
    isFinished() {return false;}

    @Override public void
    end(boolean interrupted) {
        arm.setOutput(0);
    }
}