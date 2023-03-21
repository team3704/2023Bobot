package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveTrainSub extends SubsystemBase {
    TalonFX frontleftmotor = new TalonFX(Constants.MotorIds.DT_FrontLeft);
    TalonFX frontrightmotor = new TalonFX(Constants.MotorIds.DT_FrontRight);
    TalonFX backleftmotor = new TalonFX(Constants.MotorIds.DT_BackLeft);
    TalonFX backrightmotor = new TalonFX(Constants.MotorIds.DT_BackRight);
    DifDrive CtrlDrive = new DifDrive(frontleftmotor, frontrightmotor);
    public DriveTrainSub() {

        backleftmotor.follow(frontleftmotor);
        backrightmotor.follow(frontrightmotor);
    }
    @Override public void periodic() {
        CtrlDrive.arcadeDrive(
            RobotContainer.controller.getRightX() * RobotContainer.testSpeed,
            RobotContainer.controller.getLeftY() * RobotContainer.testSpeed
            );
    }

public void motorOverride (double output, double output2) {
    frontleftmotor.set(ControlMode.PercentOutput, output);
    frontrightmotor.set(ControlMode.PercentOutput, -output2);
}

}
