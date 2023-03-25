package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSub;
import frc.robot.subsystems.ClawSub;

public class AutonomousArmCmd extends CommandBase {
    private Timer ti = new Timer();
    private ArmSub arm;
    private ClawSub claw;

    public AutonomousArmCmd(ArmSub arm) {
        ti.stop(); ti.reset();
        this.arm = arm; 
    }

    @Override public void
    initialize() {
        ti.start();
    }

    @Override public void
    execute() {
        arm.autoPidMove(0);
    }

    @Override public boolean
    isFinished() {
        return ti.get() > 1.5;
        
        //if (getPosition = desiredPosition )
    }

    @Override public void
    end(boolean interrupted) {
        claw.openClaw();
    }


}
