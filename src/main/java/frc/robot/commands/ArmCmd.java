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
    private final Function test, init;
    public ArmCmd(ArmSub arm, Function test) {
        this.arm = arm;
        this.test = test;
        init = null;
        addRequirements(arm);
    }
    public ArmCmd(ArmSub arm, Function test, Function init) {
        this.arm = arm;
        this.test = test;
        this.init = init;
        addRequirements(arm);
    }

    @Override public void
    initialize() {
        if(init != null) init.execute(arm);
    }

    @Override public void
    execute() {
        arm.pidMove(RobotContainer.controller.getLeftTriggerAxis());
    }

    @Override public boolean
    isFinished() {return false;}

    @Override public void
    end(boolean interrupted) {
        arm.setOutput(0);
    }
}