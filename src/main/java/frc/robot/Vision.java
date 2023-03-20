package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {    
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry
        tx = table.getEntry("tx"),
        ty = table.getEntry("ty"),
        ta = table.getEntry("ta");
    
    // sets limelight settings to defaults
    static {
        table.getEntry("ledMode").setValue(1);
        table.getEntry("camMode").setValue(0);
    }

    private static boolean lights = true;
    public static void toggleLight() {
        table.getEntry("ledMode").setValue((lights = !lights) ? 3 : 1);
    }

    public static void light(boolean lightSetting) {
        lights = lightSetting;
        table.getEntry("ledMode").setValue(lightSetting ? 3 : 1);
    }

    private static boolean visionProcessing = true;
    public static void toggleCamMode() {
        table.getEntry("camMode").setValue((visionProcessing = !visionProcessing) ? 0 : 1);
    }

    public double getXPercent() {
        return Math.abs(getXOffset() / 60) + 0.35;
    }

    public double getXOffset() {return tx.getDouble(0.0);}
    public double getYOffset() {return ty.getDouble(0.0);}
    public double getArea() {return ta.getDouble(0.0);}
}