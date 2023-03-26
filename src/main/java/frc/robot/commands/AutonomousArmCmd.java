package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSub;
import frc.robot.subsystems.ClawSub;

public class AutonomousArmCmd extends CommandBase {
    private Timer ti = new Timer();
    private ArmSub arm;
    private ClawSub claw;
    private double desiredPosition;

    public AutonomousArmCmd(ArmSub arm, ClawSub claw, double desiredPos) {
        ti.stop(); ti.reset();
        this.arm = arm; 
        this.claw = claw;
        desiredPosition = desiredPos;
        addRequirements(arm, claw);
    }

    @Override public void
    initialize() {
        ti.start();
    }

    double increment = 0;
    @Override public void
    execute() {
        increment = MathUtil.clamp(
            increment + -1280, -64000, -500
        );
        arm.autoPidMove(increment);
    }

    @Override public boolean
    isFinished() {
        return ti.get() > 3 || arm.getPosition() >= desiredPosition - 2000;
    }

    @Override public void
    end(boolean interrupted) {
        claw.openClaw();
    }


}
