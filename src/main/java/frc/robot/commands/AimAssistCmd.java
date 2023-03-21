package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrainSub;

public class AimAssistCmd extends CommandBase {
    public AimAssistCmd(DriveTrainSub fourTurtles) {
       addRequirements(fourTurtles);
       this.fourTurtles = fourTurtles;
    }

    DriveTrainSub fourTurtles;
    
    double offset = 0;

    @Override public void
    execute() {
        offset = Vision.getXOffset();
        fourTurtles.motorOverride(offset, offset);
        
        if (offset < -5) {
            fourTurtles.motorOverride(-.3, .3);
        }

        if (offset > 5) {
            fourTurtles.motorOverride(.3, -.3);
        }
    }
    
    @Override public boolean
    isFinished() {
        return false;
    }
    // make sure to change this later - from, Past Aldrin //

    @Override public void
    end(boolean interrupted) {
        fourTurtles.motorOverride(0, 0);
    }
}




