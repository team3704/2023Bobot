package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveTrainSub extends SubsystemBase {
    TalonFX frontleftmotor = new TalonFX(Constants.MotorIds.DT_FrontLeft);
    TalonFX frontrightmotor = new TalonFX(Constants.MotorIds.DT_FrontRight);
    TalonFX backleftmotor = new TalonFX(Constants.MotorIds.DT_BackLeft);
    TalonFX backrightmotor = new TalonFX(Constants.MotorIds.DT_BackRight);
    DifferentialDrive CtrlDrive = new DifferentialDrive((MotorController) frontleftmotor, (MotorController) frontrightmotor);
    public DriveTrainSub() {
        backleftmotor.follow(frontleftmotor);
        backrightmotor.follow(frontrightmotor);
    }
    @Override public void periodic() {
        CtrlDrive.arcadeDrive(RobotContainer.controller.getLeftY(), RobotContainer.controller.getLeftX());
    }

}
