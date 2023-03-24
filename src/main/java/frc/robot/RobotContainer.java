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
import frc.robot.subsystems.*;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;

public class RobotContainer {
  public static final CommandXboxController controller = new CommandXboxController(1);
  public static final CommandJoystick stickjoy = new CommandJoystick(0);

  private final DriveTrainSub actualDrive  = new DriveTrainSub();
  private final ElevatorSub   sub_elevator = new ElevatorSub();
  private final ArmSub        sub_arm      = new ArmSub();
  private final ClawSub       sub_claw     = new ClawSub();
  private final VisionSub     sub_vision   = new VisionSub();

  private final Command
    cmd_elevatorUp   = new ElevatorCmd(sub_elevator, 1),
    cmd_elevatorDown = new ElevatorCmd(sub_elevator, -1),
    cmd_moveArm      = new ArmCmd(sub_arm, arm -> arm.pidMove(-RobotContainer.stickjoy.getY())),
    cmd_AimCones     = new AimAssistCmd(actualDrive, sub_vision, "RetroReflective"),
    cmd_AimCubes     = new AimAssistCmd(actualDrive, sub_vision, "Fiducial Markers"),
    cmd_clawIntake   = new ClawIntakeCmd(sub_claw);
  
  public static double testSpeed = 0.5;
  
  // The robot's subsystems and commands are defined here...

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    DriverStation.silenceJoystickConnectionWarning(true);

    configureBindings(); // x = cones y = boxes
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link CommandXboxController Xbox} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings() {
    stickjoy.button(6).whileTrue(cmd_elevatorUp);
    stickjoy.button(7).whileTrue(cmd_elevatorDown);
    //controller.leftStick().whileTrue(cmd_clawIntake);

    stickjoy.button(1).onTrue(runOnce(() -> {
      sub_claw.openClaw();
    }));

    stickjoy.button(1).onFalse(runOnce(() -> {
      sub_claw.closeClaw();
    }));
    stickjoy.axisGreaterThan(2, 0)
      .onTrue(runOnce(()->{
        sub_arm.lockingmethod();
    }));
    stickjoy.axisLessThan(2, 0)
      .onTrue(runOnce(()->{
        sub_arm.unlockingmethod();
    }));

    stickjoy.button(11).onTrue(runOnce(() -> sub_arm.offsetEncoder(-3000)));
    stickjoy.button(10).onTrue(runOnce(() -> sub_arm.offsetEncoder(3000)));

    controller.povLeft().onTrue(runOnce(() -> {sub_arm.writePosition();}));
    controller.x().onTrue(runOnce(() -> {sub_arm.resetEncoder();}));
    controller.a().whileTrue(cmd_AimCones);
    controller.b().whileTrue(cmd_AimCubes);
    controller.leftTrigger(0.6).whileTrue(cmd_elevatorDown);
    controller.rightTrigger(0.6).whileTrue(cmd_elevatorUp);

    controller.povUp().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(testSpeed + 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", testSpeed);
    }));
    controller.povDown().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(testSpeed - 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", testSpeed);
    }));
    // Remind to Change - Past Aldrin. //

    controller.y().onTrue(runOnce(() -> sub_arm.writePosition()));
  }
  public void scheduleTeleop() {
    CommandScheduler.getInstance().schedule(cmd_moveArm);
  }
  public void descheduleTeleop() {
    CommandScheduler.getInstance().cancelAll();
  }

  public void initialize() {
    sub_arm.resetEncoder();
  }

  public Command getAutonomousSequence() {
    return null;
    /*
    new SequentialCommandGroup(null).andThen(null)
    Commands.runOnce(() -> {});
    Autos.exampleAuto(m_exampleSubsystem);
    */
  }
}
