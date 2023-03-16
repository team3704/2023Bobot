package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSub extends SubsystemBase {
    private final TalonFX liftMotor = new TalonFX(0);
    private final PIDController liftPidController = new PIDController(0.0000001, 0, 0);
}
