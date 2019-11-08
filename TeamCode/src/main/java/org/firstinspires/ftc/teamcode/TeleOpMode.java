package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
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
    private Servo leftIntake = null;
    private Servo rightIntake = null;
    private Servo elevatorTilt = null;
    private DcMotor elevator = null;
    private DcMotor intakeA = null;
    private DcMotor intakeB = null;
    private TouchSensor upperLimit = null;
    private TouchSensor lowerLimit = null;


//    private DcMotor elevator = hardwareMap.get (DcMotor.class, "elevator") ;

    @Override
    public void init() {
        //telemetry and hardwareMap stuff goes in this method.
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");

        leftIntake = hardwareMap.get (Servo.class, "arm1");
        rightIntake = hardwareMap.get(Servo.class, "arm2");

        elevator = hardwareMap.get(DcMotor.class, "elevator");
        intakeA = hardwareMap.get(DcMotor.class, "intakeA");
        intakeB = hardwareMap.get(DcMotor.class, "intakeB");
        elevatorTilt = hardwareMap.get(Servo.class, "elevatorTilt");
        upperLimit = hardwareMap.get(TouchSensor.class, "upperLimit");
        lowerLimit = hardwareMap.get(TouchSensor.class, "lowerLimit");

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
        if (gamepad2.y) {
            openIntake();
        }
        else {
            closeIntake();
        }

        moveElevator(gamepad2.right_stick_y);
        dropIntake(gamepad2.left_stick_y);

       // elevatorTilt.setPosition(gamepad2.left_stick_y);
       // dropIntake(gamepad2.left_stick_y);

         if (lowerLimit.isPressed() && (gamepad2.right_stick_y < 0)) {
             moveElevator(0);
        }
         else {
             moveElevator(gamepad2.right_stick_y);
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
        leftIntake.setPosition(180);
        rightIntake.setPosition(180);
    }
    private void closeIntake() {
        leftIntake.setPosition(0);
        rightIntake.setPosition(0);
    }
    private void dropIntake(double position) {
        elevatorTilt.setPosition(position);
    }
    private double getDistance() {
        return leftFrontDrive.getCurrentPosition();// * PPR_TO_INCHES;
    }
    //Main movement method to be called nearly  every time we want to move linearly

    public void resetEncoder() {
        leftBackDrive.setTargetPosition(0);
    }

    public void driveToDistance(double distance) {
        resetEncoder();
        int distanceForMotor = (int) (distance * COUNTS_PER_INCH); //This should convert the distance we collect back to what the motor reads

        leftBackDrive.setTargetPosition(distanceForMotor);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveRaw(.99);

        while (leftBackDrive.isBusy()) {
            telemetry.addData("Current Position", leftBackDrive.getCurrentPosition());
            telemetry.addData("Target Position: ", leftBackDrive.getTargetPosition());
            telemetry.update();
        }
    }
    //Raw Driving Methods
    public void driveRaw(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(power);
    }
    public void turnLeft (double power){
        leftFrontDrive.setPower (power);
        rightFrontDrive.setPower (-power);
        leftBackDrive.setPower (power);
        rightBackDrive.setPower (-power);
    }
    public void turnRight (double power) {
        leftFrontDrive.setPower(-power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(-power);
        rightBackDrive.setPower(power);
    }


}