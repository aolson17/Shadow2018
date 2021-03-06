package org.usfirst.frc.team2526.robot.commands;

import org.usfirst.frc.team2526.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleopDrive extends Command {

    public TeleopDrive() {
    	requires(Robot.driveTrain);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.fL.setSensorPhase(true);
    	Robot.driveTrain.fR.setSensorPhase(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.teleopCraneDrive(Robot.oi.getDriverLeft(), Robot.oi.getDriverRight());
    	SmartDashboard.putNumber("Encoder Left", Robot.driveTrain.fL.getSensorCollection().getQuadraturePosition());
    	SmartDashboard.putNumber("Encoder Right", Robot.driveTrain.fR.getSensorCollection().getQuadraturePosition());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
