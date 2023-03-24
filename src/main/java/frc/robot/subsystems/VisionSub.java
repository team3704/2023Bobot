package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSub extends SubsystemBase {    
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry
        tx = table.getEntry("tx"),
        ty = table.getEntry("ty"),
        ta = table.getEntry("ta");
    
    // sets limelight settings to defaults
    public VisionSub() {
        table.getEntry("ledMode").setValue(1);
        table.getEntry("camMode").setValue(0);
    }

    private boolean lights = true;
    public void toggleLight() {
        table.getEntry("ledMode").setValue((lights = !lights) ? 3 : 1);
    }

    public void light(boolean lightSetting) {
        lights = lightSetting;
        table.getEntry("ledMode").setValue(lightSetting ? 3 : 1);
    }

    private boolean visionProcessing = true;
    public void toggleCamMode() {
        table.getEntry("camMode").setValue((visionProcessing = !visionProcessing) ? 0 : 1);
    }

    public double getXPercent() {
        return Math.abs(getXOffset() / 60) + 0.35;
    }

    public double getXOffset() {return tx.getDouble(0.0);}
    public double getYOffset() {return ty.getDouble(0.0);}
    public double getArea() {return ta.getDouble(0.0);}
    public void setEntry(String entry, Object value) {
        table.getEntry(entry).setValue(entry);
    }
}