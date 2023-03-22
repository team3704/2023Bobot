package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AimAssistCmd;
import frc.robot.commands.ArmCmd;
import frc.robot.commands.ClawIntakeCmd;
import frc.robot.commands.ElevatorCmd;
import frc.robot.subsystems.ArmSub;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.ElevatorSub;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;

public class RobotContainer {
  private final DriveTrainSub actualDrive  = new DriveTrainSub();
  private final ElevatorSub   sub_elevator = new ElevatorSub();
  private final ArmSub        sub_arm      = new ArmSub();
  private final ClawSub       sub_claw     = new ClawSub();

  private final Command
    cmd_elevator = new ElevatorCmd(sub_elevator),
    cmd_moveArm  = new ArmCmd(sub_arm, arm -> arm.pidMove(RobotContainer.controller.getLeftTriggerAxis())),
    /*cmd_holdArm = new ArmCmd(sub_arm, arm -> {
        arm.setOutput(
          arm.armPidController.calculate(arm.getPosition(), arm.getWantedPosition())
        );
      }, arm -> arm.positionSnapshot()
    ),*/
    cmd_AimAssist = new AimAssistCmd(actualDrive),
    cmd_clawIntake = new ClawIntakeCmd(sub_claw);
  
  public static double testSpeed = 0.5;
  public static final CommandXboxController controller = new CommandXboxController(1);
  public static final CommandJoystick stickjoy = new CommandJoystick(0);
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  // private final CommandXboxController m_driverController =

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureBindings(); // x = cones y = boxes
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link CommandXboxController Xbox} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings() {
    controller.leftBumper().whileTrue(cmd_elevator);

    controller.povUp().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(testSpeed + 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", testSpeed);
    }));
    
    controller.povDown().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(testSpeed - 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", testSpeed);
    }));
    
    controller.leftStick().whileTrue(cmd_clawIntake);
    //controller.rightBumper().whileFalse(cmd_holdArm);

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
    controller.povLeft().onTrue(runOnce(() -> {sub_arm.writePosition();}));
    controller.b().onTrue(runOnce(() -> {sub_arm.resetEncoder();}));

    controller.x().whileTrue(cmd_AimAssist);
    
    controller.y().whileTrue(cmd_AimAssist);
    // Remind to Change - Past Aldrin. //

    controller.a().onTrue(runOnce(() -> sub_arm.writePosition()));
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
