package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.MotorIds.*;
import static com.ctre.phoenix.motorcontrol.TalonFXControlMode.*;

public class ArmSub extends SubsystemBase {
    public final PIDController armPidController = new PIDController(0.000028, 0.0000001, 0.000004);
    private double position = 0;
    public double maxHeight = -100000;
    public final TalonFX // change both to private final when done testing
        motorLeft = new TalonFX(Arm_Left),
        motorRight = new TalonFX(Arm_Right);
        double wantedPosition = -500;
        boolean locked = false;
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
        if(!locked){
            //wantedPosition = (triggerValue * maxHeight) -500;
            wantedPosition = MathUtil.clamp(
                wantedPosition + MathUtil.applyDeadband(triggerValue, 0.05) * -1500, maxHeight, -500
            );
        }
        
        SmartDashboard.putNumber("desired", wantedPosition);
        motorRight.set(TalonFXControlMode.PercentOutput, armPidController.calculate(getPosition(), wantedPosition));
    }
    public void lockingmethod(){
        locked = true;
    }
    public void unlockingmethod(){
        locked = false;
    }
    public void offsetEncoder(double offset) {
        motorRight.setSelectedSensorPosition(getPosition() + offset);
        // Warning, could break physical bits and bobs
    }
}