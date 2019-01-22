package ltseng01.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Uses an Xbox controller to control a differential drive robot with (4) REV Robotics' Brushless NEO Motors
 * and (4) SPARK MAX motor controllers. Encoder position values are sent to SmartDashboard periodically.
 */
public class Robot extends TimedRobot {

    private static CANSparkMax leftFront;
    private static CANSparkMax leftBack;
    private static CANSparkMax rightFront;
    private static CANSparkMax rightBack;

    private static SpeedControllerGroup leftSCG;
    private static SpeedControllerGroup rightSCG;

    private static DifferentialDrive differentialDrive;

    private static XboxController xboxController;


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        leftFront = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftBack = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightFront = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightBack = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftSCG = new SpeedControllerGroup(leftFront, leftBack);
        rightSCG = new SpeedControllerGroup(rightFront, rightBack);

        differentialDrive = new DifferentialDrive(leftSCG, rightSCG);

        xboxController = new XboxController(0);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("LF NEO Encoder", leftFront.getEncoder().getPosition());
        SmartDashboard.putNumber("LB NEO Encoder", leftBack.getEncoder().getPosition());
        SmartDashboard.putNumber("RF NEO Encoder", rightFront.getEncoder().getPosition());
        SmartDashboard.putNumber("RB NEO Encoder", rightBack.getEncoder().getPosition());

        SmartDashboard.putNumber("LF NEO Temp", leftFront.getMotorTemperature());
        SmartDashboard.putNumber("LB NEO Temp", leftBack.getMotorTemperature());
        SmartDashboard.putNumber("RF NEO Temp", rightFront.getMotorTemperature());
        SmartDashboard.putNumber("RB NEO Temp", rightBack.getMotorTemperature());

        SmartDashboard.putNumber("LF NEO Current", leftFront.getOutputCurrent());
        SmartDashboard.putNumber("LB NEO Current", leftBack.getOutputCurrent());
        SmartDashboard.putNumber("RF NEO Current", rightFront.getOutputCurrent());
        SmartDashboard.putNumber("RB NEO Current", rightBack.getOutputCurrent());

    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        drive(xboxController.getY(GenericHID.Hand.kLeft),
                xboxController.getX(GenericHID.Hand.kRight),
                false);
    }

    public static void drive(double speed, double rotation, boolean squared) {
        differentialDrive.arcadeDrive(speed, rotation, squared);
    }


}
