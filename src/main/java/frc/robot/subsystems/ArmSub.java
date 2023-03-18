package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.ctre.phoenix.motorcontrol.TalonFXControlMode.*;

public class ArmSub extends SubsystemBase {
    private final PIDController liftPidController = new PIDController(0.000001, 0, 0);
    private final TalonFX
        motorLeft = new TalonFX(0),
        motorRight = new TalonFX(0);

    public ArmSub() {
        motorLeft.follow(motorRight);
        motorLeft.setInverted(TalonFXInvertType.OpposeMaster);
    }

    @Override public void
    periodic() {
        SmartDashboard.putNumber("Left Motor Encoder", motorLeft.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right Motor Encoder", motorRight.getSelectedSensorPosition());
    }

    public void
    setOutput(double output) {
        motorRight.set(PercentOutput, output);
        // motorRight.set(TalonFXControlMode.Position, output);
        // HOLY HECK IS THAT POSITION CONTROL WITHOUT PID???
    }
}
