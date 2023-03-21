package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.MotorIds.*;
import static com.ctre.phoenix.motorcontrol.TalonFXControlMode.*;

public class ArmSub extends SubsystemBase {
    public final PIDController liftPidController = new PIDController(0.000008, 0.0000005, 0.0000005);
    private double position = 0;
    private double maxHeight = -89500;
    public final TalonFX // change both to private final when done testing
        motorLeft = new TalonFX(Arm_Left),
        motorRight = new TalonFX(Arm_Right);

    public ArmSub() {
        motorLeft.follow(motorRight);
        motorLeft.setInverted(TalonFXInvertType.OpposeMaster);
        motorRight.setSelectedSensorPosition(0);
    }

    @Override public void
    periodic() {
        SmartDashboard.putNumber("Right Motor Encoder", motorRight.getSelectedSensorPosition());
    }

    public void
    setOutput(double output) {
        motorRight.set(PercentOutput, output);
        // motorRight.set(TalonFXControlMode.Position, output);
        // HOLY HECK IS THAT POSITION CONTROL WITHOUT PID???
    }

    public double getPosition() {return motorRight.getSelectedSensorPosition();}
    public void positionSnapshot() {position = getPosition();}
    public double getWantedPosition() {return position;}
    public void writePosition() {
        SmartDashboard.putNumber("Arm snapshot", motorRight.getSelectedSensorPosition());
    }
    public void resetEncoder() {
        motorRight.setSelectedSensorPosition(0);
    }
    public void pidMove(double triggerValue) {
        motorRight.set(TalonFXControlMode.PercentOutput, (triggerValue * maxHeight) - 500);
    }
}
