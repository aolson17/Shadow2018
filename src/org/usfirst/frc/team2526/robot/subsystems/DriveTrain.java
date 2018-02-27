package org.usfirst.frc.team2526.robot.subsystems;



import org.usfirst.frc.team2526.robot.commands.TeleopDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {
	private WPI_TalonSRX fL; //Front Left Motor 
	private WPI_TalonSRX bL; //Back Left Motor
	private WPI_TalonSRX fR; //Front Right Motor
	private WPI_TalonSRX bR; //Back Right Motor
	private Double fPID = 19.2;
	private Double pPid = 0.0;
	private Double dPid = 0.0;
	DifferentialDrive drive; //RobotDrive instance to control motors during teleop
	//private PID gainsLeft; //PID Gains for left side.
	//private PID gainsRight; //PID Gains for right side.
	SpeedControllerGroup leftDrive;
	SpeedControllerGroup rightDrive;
	private double inchesPerMeter = 39.37;
	private double wheelInches =4.0;
	//public WPI_TalonSRX.TalonControlMode currentMode;
	
	//If this drivetrain is not using PID invoke this constructor
	public DriveTrain(int fLID, int bLID, int fRID, int bRID){
		fL = new WPI_TalonSRX(fLID);
		bL = new WPI_TalonSRX(bLID);
		fR = new WPI_TalonSRX(fRID);
		bR = new WPI_TalonSRX(bRID);
		fL.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder.RemoteSensor1, 0, 0);
		fR.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder.RemoteSensor1, 0, 0);
		leftDrive = new SpeedControllerGroup(fL, bL);
	    rightDrive = new SpeedControllerGroup(fR, bR);
	    bL.follow(fL);//fL leads bL follows
		bR.follow(fR);//fR leads bR follows
		
	//setFollowerOf(fR, bR);
		drive = new DifferentialDrive(this.leftDrive, this.rightDrive);
		pidInit();
	}
	public int getSpeed() {
		System.out.println(fL.getSelectedSensorVelocity(0));
		return fR.getSelectedSensorVelocity(0);	
	}
	public void pidInit(){
		
		//set pid for Left Side
		//fL.setPID(gainsLeft.p, gainsLeft.i, gainsLeft.d, gainsLeft.f, gainsLeft.iZone, gainsLeft.rampRate, gainsLeft.profile);
		fL.config_kF(0,fPID,0); 
		fL.config_kP(0,pPid,0);
		fL.config_kI(0,0,0);
		fL.config_kD(0,dPid,0);
		fL.selectProfileSlot(0,0);
	
		//Set pid for Right Side
		//fR.setPID(gainsRight.p, gainsRight.i, gainsRight.d, gainsRight.f, gainsRight.iZone, gainsRight.rampRate, gainsRight.profile);
		fR.config_kF(0,fPID,0); 
		fR.config_kP(0,pPid,0);
		fR.config_kI(0,0,0);
		fR.config_kD(0,dPid,0);
		fR.selectProfileSlot(0,0);
		
		//fL.setFeedbacxkDevice(CANTalon.FeedbackDevice.QuadEncoder);
		fL.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		//fL.configEncoderCodesPerRev(256);ALWAYS 100 as of 2018
		
		//fR.setFeedbacxkDevice(CANTalon.FeedbackDevice.QuadEncoder);
		fR.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		//fR.configEncoderCodesPerRev(256); ALWAYS 100 as of 2018
		
		//fL.reverseSensor(false);
		fL.setSensorPhase(false);
		//fR.reverseSensor(true);
		fR.setSensorPhase(true);
		
		//disable safety
		fL.setSafetyEnabled(false);
		fR.setSafetyEnabled(false);
		bL.setSafetyEnabled(false);
		bR.setSafetyEnabled(false);
	}
	
	public void pidSpeedInit(){
		fL.set(ControlMode.Position, 0);
		fR.set(ControlMode.Position, 0);
		adjustRampRate(0);
		/*bL.setInverted(false);
		bR.setInverted(false);
		fR.setInverted(false);
		fL.setInverted(true);*/
	}
	
	private void adjustRampRate(double rate){
		fL.configOpenloopRamp(rate,0);
		fR.configOpenloopRamp(rate,0);
		bL.configOpenloopRamp(rate,0);
		bR.configOpenloopRamp(rate,0);
	}
	
	public void teleopCraneDrive(Joystick left, Joystick right){
			drive.arcadeDrive(left.getY(), right.getX(), true);
	}
	
	public double calculateRPM(double velocity){
		double inchesPerSecond = velocity * inchesPerMeter;
		double inchesPerRevolution = wheelInches * Math.PI;
		double rpm = (inchesPerSecond * 60) / inchesPerRevolution;
		return rpm;
	}
	
	public void speedDrive(double speedRight,double speedLeft){
		fL.set(speedLeft);
		fR.set(speedRight);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new TeleopDrive());
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

 
}

