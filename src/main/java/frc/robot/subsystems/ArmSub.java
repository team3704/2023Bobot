package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSub extends SubsystemBase {
    private final TalonFX
        motorLeft = new TalonFX(0),
        motorRight = new TalonFX(0);
    private final PIDController liftPidController = new PIDController(0.000001, 0, 0);

    @Override public void
    periodic() {
        SmartDashboard.putNumber("Left Motor Encoder", motorLeft.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right Motor Encoder", motorRight.getSelectedSensorPosition());
    }
}
