package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WristSub extends SubsystemBase {
    private final TalonFX
        motorLeft = new TalonFX(0),
        motorRight = new TalonFX(0);
    
    public WristSub() {
        //new DifferentialDrive(new MotorControllerGroup(new TalonFX(0).f, new Talon(0))
        //.setSafetyEnabled(false);
        motorLeft.follow(motorRight);
        // screw safety;
    }
}
