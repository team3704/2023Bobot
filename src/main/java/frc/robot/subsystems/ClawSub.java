package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSub extends SubsystemBase {
   DoubleSolenoid hormones = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 0);
   public void openClaw () {
         hormones.set(Value.kForward);
      }
   public void closeClaw () {
         hormones.set(Value.kReverse);
      }

   public void setIntake(double output) {}
   
}