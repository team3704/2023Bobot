package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSub extends SubsystemBase {
    TalonFX frontleftmotor = new TalonFX(Constants.MotorIds.DT_FrontLeft);
    TalonFX frontrightmotor = new TalonFX(Constants.MotorIds.DT_FrontRight);
    TalonFX backleftmotor = new TalonFX(Constants.MotorIds.DT_BackLeft);
    TalonFX backrightmotor = new TalonFX(Constants.MotorIds.DT_BackRight);
    public DifDrive CtrlDrive = new DifDrive(frontleftmotor, frontrightmotor);
    public DriveTrainSub() {
        backleftmotor.follow(frontleftmotor);
        backrightmotor.follow(frontrightmotor);
    }

public void motorOverride (double output, double output2) {
    // negative so that both turn in robots (backward apparently) direction
    frontleftmotor.set(ControlMode.PercentOutput, -output);
    frontrightmotor.set(ControlMode.PercentOutput, output2);
}

}
