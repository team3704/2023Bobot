package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.VisionSub;

public class AimAssistCmd extends CommandBase {
    public AimAssistCmd(DriveTrainSub fourTurtles, VisionSub vision, String pipeline) {
       this.vision = vision;
       this.fourTurtles = fourTurtles;
       this.pipeline = pipeline;
       addRequirements(fourTurtles, vision);
    }

    DriveTrainSub fourTurtles;
    VisionSub vision;
    String pipeline;
    
    double offset = 0;

    @Override public void
    initialize() {
        vision.setEntry("pipeline", pipeline);
    }

    @Override public void
    execute() {
        offset = vision.getXOffset();
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




