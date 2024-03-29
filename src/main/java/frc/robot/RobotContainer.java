package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.commands.auto.AutonomousArmCmd;
import frc.robot.subsystems.*;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;
import static frc.robot.commands.auto.AutonomousDriveCmd.autoDrive;

public class RobotContainer {
  public static final CommandXboxController
    driveController = new CommandXboxController(1),
    armController   = new CommandXboxController(2);
  //public static final CommandJoystick stickjoy = new CommandJoystick(0);

  private final DriveTrainSub sub_drive  = new DriveTrainSub();
  private final ElevatorSub   sub_elevator = new ElevatorSub();
  private final ArmSub        sub_arm      = new ArmSub();
  private final ClawSub       sub_claw     = new ClawSub();
  private final VisionSub     sub_vision   = new VisionSub();

  private final Command
    cmd_elevatorUp   = new ElevatorCmd(sub_elevator, 1),
    cmd_elevatorDown = new ElevatorCmd(sub_elevator, -1),
    cmd_moveArm      = new ArmCmd(sub_arm, arm -> arm.pidMove(-armController.getLeftY())),
    cmd_AimCones     = new AimAssistCmd(sub_drive, sub_vision, "RetroReflective"),
    cmd_AimCubes     = new AimAssistCmd(sub_drive, sub_vision, "Fiducial Markers"),
    cmd_ArmAuto      = new AutonomousArmCmd(sub_arm, sub_claw, -64500),
    cmd_Drive        = new DriveCmd(sub_drive);
  
  public static double testSpeed = 0.55;
  public static double elevatorTest = .5;
  
  // The robot's subsystems and commands are defined here...

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    DriverStation.silenceJoystickConnectionWarning(true);
    DifDrive.init(); // accept this in merge, do not discard

    configureBindings(); // x = cones y = boxes
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link CommandXboxController Xbox} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings() {
    //stickjoy.button(6).whileTrue(cmd_elevatorUp);
    //stickjoy.button(7).whileTrue(cmd_elevatorDown);
    //controller.leftStick().whileTrue(cmd_clawIntake);

    armController.x().onTrue(runOnce(() -> { // stick button 1
      sub_claw.openClaw();
    }));

    armController.x().onFalse(runOnce(() -> {
      sub_claw.closeClaw();
    }));
    /*stickjoy.axisGreaterThan(2, 0)
      .onTrue(runOnce(()->{
        sub_arm.lockingmethod();
    }));
    stickjoy.axisLessThan(2, 0)
      .onTrue(runOnce(()->{
        sub_arm.unlockingmethod();
    }));*/

    //stickjoy.button(11).whileTrue(run(() -> sub_arm.offsetEncoder(-1000)));
    //stickjoy.button(10).whileTrue(run(() -> sub_arm.offsetEncoder(1000)));

    driveController.povUpRight().onTrue(runOnce(() -> {sub_arm.writePosition();}));
    driveController.x().onTrue(runOnce(() -> {
      //sub_elevator.resetEncoder();
      sub_arm.resetEncoder();
    }));
    //stickjoy.button(3).whileTrue(run(() -> sub_arm.maxHeight -= -stickjoy.getY() * 1500));
    driveController.a().whileTrue(cmd_AimCones);
    driveController.b().whileTrue(cmd_AimCubes);
    driveController.leftTrigger(0.6).whileTrue(cmd_elevatorDown);
    driveController.rightTrigger(0.6).whileTrue(cmd_elevatorUp);

    driveController.povUp().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(testSpeed + 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", testSpeed);
    }));
    driveController.povDown().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(testSpeed - 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", testSpeed);
    }));
    
    driveController.povRight().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(elevatorTest + 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", elevatorTest);
    }));
    driveController.povLeft().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(elevatorTest - 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", elevatorTest);
    }));
    
    
    // Remind to Change - Past Aldrin. //

    driveController.y().onTrue(runOnce(() -> sub_arm.writePosition()));
  }
  public void scheduleTeleop() {
    CommandScheduler.getInstance().schedule(cmd_moveArm, cmd_Drive);
  }
  public void descheduleTeleop() {
    CommandScheduler.getInstance().cancelAll();
  }

  public void initialize() {
    sub_arm.resetEncoder();
  }

  public Command getAutonomousSequence() {
    //Left Hand Start
    //return autoDrive(sub_drive, 3, .3).andThen(
      //autoDrive(sub_drive, 1, -.3, .3)).andThen(
        //autoDrive(sub_drive, 1, .3)).andThen(
          //autoDrive(sub_drive, 1, -.3, .3)).andThen(
      //autoDrive(sub_drive, 1.5, .3));


      //Right Hand Start
    //return autoDrive(sub_drive, 3, .3).andThen(
      //autoDrive(sub_drive, 1, .3, -.3)).andThen(
        //autoDrive(sub_drive, 1, .3)).andThen(
          //autoDrive(sub_drive, 1, .3, -.3)).andThen(
          //autoDrive(sub_drive, 1.5, .3));

    //Taxi
    return cmd_ArmAuto
      .andThen(autoDrive(sub_drive, 3.1, .3))
      //.andThen(autoDrive(sub_drive, 1.5, -.3))
      .andThen(runOnce(() -> sub_claw.closeClaw()));

    // return cmd_ArmAutonomous.andThen(cmd_AutonomousDriveCmd);
    
    // with autoDrive i just made
    /*
     * return autoDrive(sub_drive, 3.5, 0.3); // drives straight for 3.5 seconds at 0.3 output
     * // this is the ame as returning cmd_Autonomous except
     * // you don't hae to make multiple classes or explicit instances
     * 
     * return autoDrive(sub_drive, 3.5, 0.3).andThen(autoDrive(sub_drive, 100, -1, 1)); // two outputs for left and right are optional. Using only one like the one above is to set both of them at that value
     * // this one will make it spin at full power after driving straight,
     * // without needed to change the autonomous class :D
     */
    
    /*
    new SequentialCommandGroup(null).andThen(null)
    Commands.runOnce(() -> {});
    Autos.exampleAuto(m_exampleSubsystem);
    */
  }
}
