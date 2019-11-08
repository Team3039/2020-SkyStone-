package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="TeleOpMode", group="Iterative Opmode")

public class TeleOpMode extends OpMode implements Constants{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Driivetrain Motors
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    //Gamepiece Motors
<<<<<<< HEAD
//    private Servo arm = null;
//    private DcMotor elevatorA = null;
//    private DcMotor elevatorB = null;
//    private DcMotor intakeA = null;
//    private DcMotor intakeB = null;
=======
    private Servo arm1 = null;
    private Servo arm2 = null;
    private DcMotor elevator = null;
    private DcMotor intakeA = null;
    private DcMotor intakeB = null;
>>>>>>> ebe0ab0e9f3f0ddf48200ecae3dd6de702166016


//    private DcMotor elevator = hardwareMap.get (DcMotor.class, "elevator") ;

    @Override
    public void init() {
        //telemetry and hardwareMap stuff goes in this method.
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
<<<<<<< HEAD
//        arm = hardwareMap.get (Servo.class, "arm");
//        elevatorA = hardwareMap.get(DcMotor.class, "elevatorA");
//        elevatorB = hardwareMap.get(DcMotor.class, "elevatorB");
//               intakeA = hardwareMap.get(DcMotor.class, "intakeA");
//        intakeB = hardwareMap.get(DcMotor.class, "intakeB");
=======
        arm1 = hardwareMap.get (Servo.class, "arm1");
        arm2 = hardwareMap.get(Servo.class, "arm2");
        elevator = hardwareMap.get(DcMotor.class, "elevator");
        intakeA = hardwareMap.get(DcMotor.class, "intakeA");
        intakeB = hardwareMap.get(DcMotor.class, "intakeB");
>>>>>>> ebe0ab0e9f3f0ddf48200ecae3dd6de702166016

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);


//        elevator.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


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
        //Encoder
        getDistance();

       if (gamepad1.left_bumper) {
            strafeLeft(.8);
       }
       else if (gamepad1.right_bumper) {
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
        if (gamepad2.left_bumper) {
            setIntakeSpeed(.8);
        }
        if (gamepad2.right_bumper) {
            setIntakeSpeed(-.8);
        }
        if (gamepad2.dpad_up) {
            moveElevator(ELEVATOR_LEVEL_ONE);
        }
        if (gamepad2.dpad_left) {
            moveElevator(ELEVATOR_LEVEL_TWO);
        }
        if (gamepad2.dpad_right) {
            moveElevator(ELEVATOR_LEVEL_THREE);
        }
        if (gamepad2.dpad_down) {
            openIntake();
        }
            else {
                closeIntake();
        }

        telemetry.addData("position", -getDistance());
        telemetry.update();

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

    private void setIntakeSpeed(double power) {
//        intakeB.setPower(power);
//        intakeA.setPower(power);
    }

    private void moveElevator(double power) {
        elevator.setPower(power);
    }

    private void openIntake() {
        arm1.setPosition(.90);
        arm2.setPosition(.90);
    }

    private void closeIntake() {
        arm1.setPosition(.0);
        arm2.setPosition(.0);
    }

    private double getDistance() {
        return leftFrontDrive.getCurrentPosition();// * PPR_TO_INCHES;
    }

}