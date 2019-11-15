package org.firstinspires.ftc.teamcode;


import android.text.method.Touch;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;




@Autonomous
public class JacobAuto extends LinearOpMode implements Constants {
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
    private Servo elevatorTilt;
    private Servo clampA = null;
    private Servo clampB = null;

    //sensors
    private TouchSensor upperLimit = null;
    private TouchSensor lowerLimit = null;

    @Override
    public void runOpMode ()throws InterruptedException{
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

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);


        //reset encoder count on the motor(s) with encoders
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set leftFrontDrive to run to target encoder position and stop with brakes on.
        rightBackDrive.setTargetPosition(538);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //set the rest of the motors to run without an encoder
        rightFrontDrive.setMode((DcMotor.RunMode.RUN_WITHOUT_ENCODER));
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();
        telemetry.addData("Mode", "running");
        telemetry.update();




        driveForward(.5);

        while (opModeIsActive() && rightBackDrive.isBusy())
        {
            telemetry.addData("encoder-fwd", rightBackDrive.getCurrentPosition() + "  busy=" + rightBackDrive.isBusy());
            telemetry.update();
            idle();
        }

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        resetStartTime();
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive() & getRuntime()< 3)
        {
            turnLeft(3);
        }

        driveForward(0);






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
   /* private void tiltElevator(double position) {
        elevatorTilt.setPosition(position);
    }*/

    public void turnRight(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(-power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(-power);
    }
    public void turnLeft(double power){
        leftFrontDrive.setPower(-power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(-power);
        rightBackDrive.setPower(power);

    }
}
