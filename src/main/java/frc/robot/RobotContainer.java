package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ArmCmd;
import frc.robot.commands.ElevatorCmd;
import frc.robot.subsystems.ArmSub;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.ElevatorSub;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {
  private final ElevatorSub sub_elevator = new ElevatorSub();
  private final ArmSub      sub_arm      = new ArmSub();
  private final DriveTrainSub actualDrive = new DriveTrainSub();

  private final ClawSub sub_claw = new ClawSub();
  private final Command
    cmd_elevator = new ElevatorCmd(sub_elevator),

    // NOTE: DO NOT EDIT THE CODE BELOW, IT MAY LAG YOUR VSC
    cmd_moveArm  = new ArmCmd(sub_arm, arm -> {
      // specifically this line
      arm.setOutput(MathUtil.applyDeadband(controller.getRightY(), 0.05) * testSpeed);
    }),
    cmd_holdArm = new ArmCmd(sub_arm, arm -> {
      arm.setOutput(
        // and this one
        arm.liftPidController.calculate(arm.getPosition(), arm.getWantedPosition())
      );
    },
    arm -> {arm.positionSnapshot();});
    // NOTE: DO NOT EDIT THE CODE ABOVE, IT MAY LAG YOUR VSC
  
  public static double testSpeed = 0.5;
  public static final CommandXboxController controller = new CommandXboxController(1);
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  // private final CommandXboxController m_driverController =
      // new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link CommandXboxController Xbox} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings() {
    controller.leftBumper().whileTrue(cmd_elevator);
    
    controller.rightBumper().whileTrue(cmd_moveArm);
    
    controller.povUp().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(testSpeed + 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", testSpeed);
    }));
    
    controller.povDown().onTrue(runOnce(() -> {
      testSpeed = MathUtil.clamp(testSpeed - 0.05, 0, 1);
      SmartDashboard.putNumber("Speed", testSpeed);
    }));
    
    controller.rightBumper().onFalse(cmd_holdArm);

    controller.rightTrigger(0.6).onTrue(runOnce(() -> {
      sub_claw.openClaw();
    }));

    controller.rightTrigger(0.6).onFalse(runOnce(() -> {
      sub_claw.closeClaw();
    }));

    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //    .onTrue(new ExampleCommand(m_exampleSubsystem));
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
