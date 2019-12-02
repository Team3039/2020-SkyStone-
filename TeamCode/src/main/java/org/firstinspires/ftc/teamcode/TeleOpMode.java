package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
This is TeleOpMode
    -When this OpMode is run, the drivers can control the robot via controllers
    -In it, we assign controls and designate functions
 */


@TeleOp(name="TeleOpMode", group="Iterative Opmode")

public class TeleOpMode extends OpMode implements Constants {

    //This is where we declare the motors and hardware we use. We declare them here so we can use them later
    private ElapsedTime runtime = new ElapsedTime(); //This is the time that has gone by so far in the match. We can use this to say: "Do this at this time in the match"
    //Drivetrain Motors - These are the motors used for driving the robot
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    //Gamepiece Motors
    private DcMotor stretch = null; //This is responsible for opening and closing the intake mechanism
    private DcMotor elevator =  null; //This controls the elevator up and down motion, yes
    private DcMotor intakeA = null; //This is the left set of intake wheels
    private DcMotor intakeB = null; //This is the right set of intake wheels

    //Servos
    private Servo elevatorTilt = null; //This is called a LINEAR ACTUATOR. It's essentially a motor that can only push and pull forward and backward. We use it to push the elevator/INTAKE mechanism up and down
    private Servo clampA = null; //This is the left servo in the back of the bot. It's used for clamping onto the foundation
    private Servo clampB = null; //This is the right servo in the back of the bot. It's used for clamping onto the foundation

    //Sensors
    private TouchSensor lowerLimit = null; //When it's true, the elevator has hit it. We programmed it to stop the elevator when it hits this so it doesn't go too low and break

    @Override
    public void init() {
        //Initialization- Motors
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive"); //The things in quotes are what we named it on the phone configuration
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
    public void loop() { //Everything in this bracket loops in the TeleOp period
        //Driving
        double rightOutput;
        double leftOutput;

        double drive = gamepad1.left_stick_y * .9;
        double turn = -gamepad1.right_stick_x * .5;

        telemetry.addData("Time Elapsed", getRuntime() );
        telemetry.update();

        //Clamping Foundation
        if (gamepad1.x) { //If the X button on gamepad 1 (driver) is hit, the platform is clamped...)
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
        if (gamepad2.x) {
            openIntake();
        }
        if (gamepad2.y) {
            closeIntake();
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
    private void openIntake() {
        stretch.setPower(.9);
    }
    private void closeIntake() {
        stretch.setPower(-.9);
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
    }//I'll go over the functions (orange) in the auto classes but this is pretty much it for TeleOP
} //We're gonna go to auto now bois Red or Blue first?