package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        claw.closeClaw();
        ti.reset();
        ti.start();
    }

    double increment = 0;
    @Override public void
    execute() {
        increment = MathUtil.clamp(
            increment + -520, -60500, -500
        );
        SmartDashboard.putNumber("desired pos for auto", increment);
        arm.autoPidMove(increment);
    }

    @Override public boolean
    isFinished() {
        SmartDashboard.putNumber("time", ti.get());
        return ti.get() > 4 || arm.getPosition() <= desiredPosition - 2000;
    }

    @Override public void
    end(boolean interrupted) {
        claw.openClaw();
        arm.setOutput(0);
    }


}
