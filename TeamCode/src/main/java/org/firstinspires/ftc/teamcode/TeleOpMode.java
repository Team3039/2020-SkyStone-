package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="TeleOpMode", group="Iterative Opmode")

public class TeleOpMode extends OpMode implements Constants {

    private ElapsedTime runtime = new ElapsedTime();
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
    private TouchSensor lowerLimit = null;

    @Override
    public void init() {
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

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);




        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");



        telemetry.update();
    }

    @Override
    public void init_loop() {
        //public void raiseElevator (double power) {
        //   elevator.setPower(power);
        //}
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        //Driving
        double rightOutput;
        double leftOutput;

        double drive = gamepad1.left_stick_y * .9;
        double turn = -gamepad1.right_stick_x * .5;

        //Clamping Foundation
        if (gamepad1.x) {
            clampPlatform();
        }
        if (gamepad1.y) {
            releasePlatform();
        }

        //Driving
        if (gamepad1.left_trigger > .1) {
            strafeLeft(.8);
        }
        else if (gamepad1.right_trigger > .1) {
            strafeRight(.8);
        }
        else {
            leftOutput = Range.clip(drive + turn, -.95, .95);
            rightOutput = Range.clip(drive - turn, -.95, .95);

            leftFrontDrive.setPower(leftOutput);
            rightFrontDrive.setPower(rightOutput);
            leftBackDrive.setPower(leftOutput);
            rightBackDrive.setPower(rightOutput);
        }
        if (gamepad1.a) {
            drive(.85);
        }
        if (gamepad1.y) {
            drive(-.85);
        }

        //Intake
        if (gamepad2.left_trigger > .1) {
            intakeA.setPower(Constants.SHOOT_SPEED * -1);
            intakeB.setPower(Constants.SHOOT_SPEED);
        }
        else if (gamepad2.right_trigger > .1) {
            intakeA.setPower(Constants.INTAKE_SPEED * -1);
            intakeB.setPower(Constants.INTAKE_SPEED);
        }
        else {
            intakeA.setPower(0);
            intakeB.setPower(0);
        }

        //Elevator
        if (lowerLimit.isPressed()) {
            moveElevator(gamepad2.right_stick_y * -1 * Constants.SLOWED_ELEVATOR_GAIN);
        }
        else {
            moveElevator(gamepad2.right_stick_y * -1 * Constants.ELEVATOR_GAIN);
        }

        tiltElevator(Range.clip(gamepad2.left_stick_y * -1, 0, 1));



    }

    @Override
    public void stop() {
    }

    private void strafeRight(double strafeSpeed) {
        leftFrontDrive.setPower(-strafeSpeed);
        leftBackDrive.setPower(strafeSpeed);
        rightFrontDrive.setPower(strafeSpeed);
        rightBackDrive.setPower(-strafeSpeed);
    }

    private void strafeLeft(double strafeSpeed) {
        leftFrontDrive.setPower(strafeSpeed);
        leftBackDrive.setPower(-strafeSpeed);
        rightFrontDrive.setPower(-strafeSpeed);
        rightBackDrive.setPower(strafeSpeed);
    }
    private void drive (double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(power);
    }


    private void moveElevator(double power) {
        elevator.setPower(power);
    }

    private void tiltElevator(double position) {
        elevatorTilt.setPosition(position);
    }

    private void clampPlatform() {
        clampA.setPosition(-1);
        clampB.setPosition(-1);
    }

    private void releasePlatform() {
        clampA.setPosition(0);
        clampB.setPosition(0);
    }

}