package org.firstinspires.ftc.teamcode;


import android.text.method.Touch;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.TelemetryMessage;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryInternal;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeServices;

import java.util.concurrent.TimeUnit;

@Autonomous
public class AutoRed extends LinearOpMode implements Constants {
    //Drivetrain Motors
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    //Gamepiece Motors
    private DcMotor stretch = null;
    private DcMotor elevator =  null;
    private DcMotor intakeA = null;
    private DcMotor intakeB = null;

    //servos
    private Servo elevatorTilt = null;
    private Servo clampA = null;
    private Servo clampB = null;

    //sensors
    private TouchSensor upperLimit = null;
    private TouchSensor lowerLimit = null;

    @Override
    public void runOpMode () {
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
        upperLimit = hardwareMap.get(TouchSensor.class, "upperLimit");
        lowerLimit = hardwareMap.get(TouchSensor.class, "lowerLimit");

        //Motor Polarity
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        //Every other motor should be set to run without an encoder. This shouldn't have to be changed.
        rightFrontDrive.setMode((DcMotor.RunMode.RUN_WITHOUT_ENCODER));
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        //Operator presses START here. Everything below waitForStart(); will run.
        waitForStart();
        telemetry.addData("Mode", "running");
        telemetry.update();

        resetStartTime();

        while (opModeIsActive() & getRuntime() < 1) {
            strafeRight(.8);
        }
        resetStartTime();
        while (opModeIsActive() && getRuntime() < 2.5) {
            driveBackward(.85);
        }
        resetStartTime();
        while (opModeIsActive() & getRuntime() < 3) {
            clampFoundation();
        }
        resetStartTime();
        while (opModeIsActive() && getRuntime() < 3 ) {
            driveForward(.85);
        }
        resetStartTime();
        while (opModeIsActive() & getRuntime()< 3) {
            releaseFoundation();
        }
        resetStartTime();
        while (opModeIsActive() & getRuntime()< 2) {
            strafeLeft(.85);
        }

        resetStartTime();
        while (opModeIsActive() & getRuntime()< 3) {
            tiltElevator(1);
        }
        resetStartTime();
        stopDriving();
    }

    private void strafeLeft(double strafeSpeed) {
        leftFrontDrive.setPower(strafeSpeed);
        leftBackDrive.setPower(-strafeSpeed);
        rightFrontDrive.setPower(-strafeSpeed);
        rightBackDrive.setPower(strafeSpeed);
    }

    private void strafeRight(double strafeSpeed) {
        leftFrontDrive.setPower(-strafeSpeed);
        leftBackDrive.setPower(strafeSpeed);
        rightFrontDrive.setPower(strafeSpeed);
        rightBackDrive.setPower(-strafeSpeed);
    }

    private void driveForward(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(power);
    }
    private void driveBackward(double power) {
        leftFrontDrive.setPower(-power);
        rightFrontDrive.setPower(-power);
        leftBackDrive.setPower(-power);
        rightBackDrive.setPower(-power);
    }
    private void stopDriving(){
        driveForward(0);
    }
    private void tiltElevator(double position) {
        elevatorTilt.setPosition(position);
    }

    private void clampFoundation() {
        clampA.setPosition(1);
        clampB.setPosition(1);
    }

    private void releaseFoundation (){
        clampA.setPosition (0);
        clampB.setPosition(0);
    }

    public void turnRight(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(-power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(-power);
    }
    public void turnLeft(double power){
        turnRight(-power);
    }

    public void driveToDistance (double inches, double power){
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setTargetPosition ((int)(inches / PPR_TO_INCHES)); //This converts inches back into pulses. 1 pulse covers about .046 inches of distance.
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveForward(power);

        while (opModeIsActive() && rightBackDrive.isBusy())
        {
            telemetry.addData("Current Encoder Position: ", rightBackDrive.getCurrentPosition() + "  busy = " + rightBackDrive.isBusy());
            telemetry.update();
            idle();
        }
        stopDriving();
    }

}
