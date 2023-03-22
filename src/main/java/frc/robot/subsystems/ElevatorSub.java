package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static com.ctre.phoenix.motorcontrol.TalonSRXControlMode.*;;

public class ElevatorSub extends SubsystemBase {
    private final TalonSRX liftMotor = new TalonSRX(Constants.MotorIds.Elevator);
    private final PIDController liftPidController = new PIDController(0.0000001, 0, 0);

    @Override public void
    periodic() {
        SmartDashboard.putNumber("Elevator Encoder", liftMotor.getSelectedSensorPosition());
    }

    public void setTargetPosition(double position) {
        liftMotor.set(PercentOutput, liftPidController.calculate(
            liftMotor.getSelectedSensorPosition(), position
        ));
    }

    public void setOutput(double output) {liftMotor.set(PercentOutput, output);}

    public void pidReset() {
        liftPidController.reset();
    }
}
