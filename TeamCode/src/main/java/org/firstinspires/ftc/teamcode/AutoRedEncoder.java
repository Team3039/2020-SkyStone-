package org.firstinspires.ftc.teamcode;

import android.text.method.Touch;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
/*
FIELD MAP:

//////////////////////////////

    //////          //////
    /    /          /    /
    /    /          /    /
    //////          //////


    \\\\\\\\\\\\\\\\\\\\\\

        /\          /\
        /\          /\
        /\          /\
        /\          /\
//////////////////////////////
 */
/*
    Conditions for this auto to work optimally:
        -We start on the BUILDING ZONE (The side with the foundations (the big plate things that we need to drag)
        -The encoders are plugged in, wired correctly, working correctly, and coded correctly.
        -We start on the RED side of the field (The side of the field that has the red part of the central gate)
    Notes:
        -It is always preferable to use this auto but it has a severe lack of testing
    Priorities:
        -Test, Test, Test!
        -Get the encoder code working
 */

@Autonomous
public class AutoRedEncoder extends LinearOpMode implements Constants {

    //Drivetrain Motors
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    //Gamepiece Motors
    private DcMotor stretch = null; //Open & Close Intake
    private DcMotor elevator =  null; //Move the elevator up and down (Tied to gamepad2 right joystick)
    private DcMotor intakeA = null; //Left Intake side (Spins those wheels)
    private DcMotor intakeB = null; //Right Intake side (Spins those wheels)

    //Servos
    private Servo elevatorTilt = null; //LINEAR ACTUATOR (Brings elevator/intake mechanism down)
    private Servo clampA = null; //Left Foundation Servo (Clamps & Releases foundation)
    private Servo clampB = null; //Right Foundation Servo (Clamps & Releases Foundation)

    //Sensors
    private TouchSensor lowerLimit = null; //Prevents elevator from going too far down

    @Override
    public void runOpMode () throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Initialization- Motors
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        stretch = hardwareMap.get(DcMotor.class, "stretch");
        elevator = hardwareMap.get(DcMotor.class, "elevator");
        intakeA = hardwareMap.get(DcMotor.class, "intakeA");
        intakeB = hardwareMap.get(DcMotor.class, "intakeB");

        //Initialization - Servos
        elevatorTilt = hardwareMap.get(Servo.class , "elevatorTilt");
        clampA = hardwareMap.get(Servo.class , "clampA");
        clampB = hardwareMap.get(Servo.class , "clampB");

        //Initialization - Sensors
        lowerLimit = hardwareMap.get(TouchSensor.class, "lowerLimit");

        //Setting Initial Motor Directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        //Motor/Encoder Settings
        rightFrontDrive.setMode((DcMotor.RunMode.RUN_WITHOUT_ENCODER));
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        //Operator presses START here. Everything below waitForStart(); will run.
        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();
        while (opModeIsActive()) {

              while (getRuntime() < 4) {
                  driveForward(.9);
              }
              stopDriving();
            //Start of Auto
            //Initial Strafe into Foundation Grabbing Zone
            while (getRuntime() <= 2) {
                strafeLeft(.65);
            }
            //Backing Into Contact With the Foundation
            reverseToDistance(32);
            //Locking onto the Foundation
            while (getRuntime() < 3 && getRuntime() > 2) {
                clampFoundation();
            }
            //Bringing the Foundation to the Scoring Zone
            driveToDistance(32);
            //Releasing the Foundation
            while (getRuntime() < 4 && getRuntime() > 3) {
                releaseFoundation();
            }
            //Strafing Into the Center Line
            while (getRuntime() < 7 && getRuntime() > 4) {
                strafeRight(.85);
            }
            reverseToDistance(15); //Backing up for the elevator tilt
            //Bringing down the Elevator/Intake Mechanism
            while (getRuntime() < 10 && getRuntime() > 7) {
                tiltElevator(1);
            }
            //Security Stop All Driving
            stopDriving();
        }
    }
    //Strafes Right
    public void strafeRight (double strafeSpeed) {
        leftFrontDrive.setPower(strafeSpeed);
        leftBackDrive.setPower(-strafeSpeed);
        rightFrontDrive.setPower(-strafeSpeed);
        rightBackDrive.setPower(strafeSpeed);
    }
    //Strafes Left
    private void strafeLeft(double strafeSpeed) {
        leftFrontDrive.setPower(-strafeSpeed);
        leftBackDrive.setPower(strafeSpeed);
        rightFrontDrive.setPower(strafeSpeed);
        rightBackDrive.setPower(-strafeSpeed);
    }
    //Drives Forward
    private void driveForward(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(power);
    }
    //Drives Backward
    private void driveBackward(double power){
        driveForward(-power);
    }
    //Stops all Driving
    private void stopDriving(){
        driveForward(0);
    }
    //Tilts Elevator
    private void tiltElevator(double position) {
        elevatorTilt.setPosition(position);
    }
    //Clamps Foundation
    private void clampFoundation() {
        clampA.setPosition(1);
        clampB.setPosition(1);
    }
    //Releases Foundation
    private void releaseFoundation (){
        clampA.setPosition (0);
        clampB.setPosition(0);
    }
    //Turns right to a set power
    public void turnRight(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(-power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(-power);
    }
    //Turns left to a set power
    public void turnLeft(double power){
        turnRight(-power);
    }
    //Drives forward to a certain distance in inches
    public void driveToDistance (double inches){
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setTargetPosition ((int)(inches / PPR_TO_INCHES)); //This converts inches back into pulses. 1 pulse covers about .046 inches of distance.
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveForward(.85);

        while (rightBackDrive.isBusy())
        {
            telemetry.addData("Current Encoder Position: ", rightBackDrive.getCurrentPosition() );
            telemetry.update();
        }
        stopDriving();
    }
    //Drives backward to a certain distance in inches
    public void reverseToDistance (double inches){
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setTargetPosition ((int)(inches / PPR_TO_INCHES)); //This converts inches back into pulses. 1 pulse covers about .046 inches of distance.
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackward(.85);

        while (rightBackDrive.isBusy())
        {
            telemetry.addData("Current Encoder Position: ", rightBackDrive.getCurrentPosition());
            telemetry.update();
        }
        stopDriving();
    }
    //Strafes Left to a certain distance in inches
//    public void strafeLeftToDistance(double inches) {
//        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rightBackDrive.setTargetPosition((int) (inches / PPR_TO_INCHES)); //This converts inches back into pulses. 1 pulse covers about .046 inches of distance.
//        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        strafeLeft(.85);
//
//        while (rightBackDrive.isBusy()) {
//            telemetry.addData("Current Encoder Position: ", rightBackDrive.getCurrentPosition());
//            telemetry.update();
//        }
//        stopDriving();
//    }

    //Strafes Right to a certain distance in inches
//    public void strafeRightToDistance(double inches) {
//        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rightBackDrive.setTargetPosition((int) (inches / PPR_TO_INCHES)); //This converts inches back into pulses. 1 pulse covers about .046 inches of distance.
//        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        strafeRight(.85);
//
//        while (rightBackDrive.isBusy()) {
//            telemetry.addData("Current Encoder Position: ", rightBackDrive.getCurrentPosition());
//            telemetry.update();
//        }
//        stopDriving();
//    }
}


