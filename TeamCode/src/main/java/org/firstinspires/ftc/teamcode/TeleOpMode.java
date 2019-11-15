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
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Drivetrain Motors
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor leftIntake = null;
    //private DcMotor rightIntake = null;

    //Gamepiece Motors

    private Servo elevatorTilt;
    private Servo clampA;
    private Servo clampB;
    private DcMotor elevator = null;
    private DcMotor intakeA = null;
    private DcMotor intakeB = null;
    private TouchSensor lowerLimit = null;


//    private DcMotor elevator = hardwareMap.get (DcMotor.class, "elevator") ;

    @Override
    public void init() {
        //telemetry and hardwareMap stuff goes in this method.
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        leftIntake = hardwareMap.get(DcMotor.class, "leftIntake");
        //rightIntake = hardwareMap.get(DcMotor.class, "rightIntake");


        elevator = hardwareMap.get(DcMotor.class, "elevator");
        intakeA = hardwareMap.get(DcMotor.class, "intakeA");
        intakeB = hardwareMap.get(DcMotor.class, "intakeB");
        elevatorTilt = hardwareMap.get(Servo.class, "elevatorTilt");
        clampA = hardwareMap.get(Servo.class, "clampA");
        clampB = hardwareMap.get(Servo.class, "clampB");
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
        //   getDistance();
        //   telemetry.addData("elevator_position", elevator.getCurrentPosition());
        //   telemetry.update();

        if (gamepad1.x) {
            clampPlatform();
        } else if (gamepad1.y) {
            releasePlatform();
        }
        if (gamepad1.left_trigger > .1) {
            strafeLeft(.8);
        } else if (gamepad1.right_trigger > .1) {
            strafeRight(.8);
        } else {
            leftOutput = Range.clip(drive + turn, -.95, .95);
            rightOutput = Range.clip(drive - turn, -.95, .95);

            leftFrontDrive.setPower(leftOutput);
            rightFrontDrive.setPower(rightOutput);
            leftBackDrive.setPower(leftOutput);
            rightBackDrive.setPower(rightOutput);
        }
        if (gamepad2.left_trigger > .1) {
            intakeA.setPower(Constants.SHOOT_SPEED * -1);
            intakeB.setPower(Constants.SHOOT_SPEED);
        } else if (gamepad2.right_trigger > .1) {
            intakeA.setPower(Constants.INTAKE_SPEED * -1);
            intakeB.setPower(Constants.INTAKE_SPEED);
        } else {
            intakeA.setPower(0);
            intakeB.setPower(0);
        }
        if (gamepad2.x) {
            openIntake();
            System.out.println("Open Intake");
        }
        if (gamepad2.y) {
            closeIntake();
            System.out.println("Close Intake");
        }
        if (lowerLimit.isPressed()) {
            elevator.setPower(0.2);
        } else {

            moveElevator(gamepad2.right_stick_y * -1 * Constants.ELEVATOR_GAIN);

            tiltElevator(Range.clip(gamepad2.left_stick_y * -1, 0, 1));
        }

//        if ((lowerLimit.isPressed()) && (gamepad2.right_stick_y < 0)) {
//            moveElevator(0);
//        }
//        else if((elevator.getCurrentPosition() > Constants.UPPER_LIMIT) && (gamepad2.right_stick_y >0)) {
//            moveElevator(0);
//        }
//        else {
//             moveElevator(gamepad2.right_stick_y);
//        }

        telemetry.addData("position", getDistance());
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
        intakeB.setPower(power);
        intakeA.setPower(power);
    }

    private void moveElevator(double power) {
        elevator.setPower(power);
    }

    private void openIntake() {
        leftIntake.setPower(.9);
        //rightIntake.setPower(1);
    }

    private void closeIntake() {
        leftIntake.setPower(-.9);
        //rightIntake.setPower(0);
    }

    private void tiltElevator(double position) {
        elevatorTilt.setPosition(position);
    }


    /* private void shootStone() {*/
    /*     setIntakeSpeed(Constants.SHOOT_SPEED);*/
    /* }*/
    /**/
    /* private void intakeStone() {*/
    /*     setIntakeSpeed(Constants.INTAKE_SPEED);*/
    /* }*/

    private void clampPlatform() {
        clampA.setPosition(-1);
        clampB.setPosition(-1);
    }

    private void releasePlatform() {
        clampA.setPosition(0);
        clampB.setPosition(0);
    }

    private double getDistance() {
        return rightBackDrive.getCurrentPosition() * PPR_TO_INCHES;
    }

    public void resetEncoder() {
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void driveToDistance(double distance) {
        resetEncoder();
        int distanceForMotor = (int) (distance * COUNTS_PER_INCH); //This should convert the distance we collect back to what the motor reads
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setTargetPosition(distanceForMotor);

        driveSetSpeed(.5);

        while (rightBackDrive.isBusy()) {
            telemetry.addData("Current Position", leftBackDrive.getCurrentPosition());
            telemetry.addData("Target Position: ", leftBackDrive.getTargetPosition());
            telemetry.update();
        }

        driveSetSpeed(0);

    }

    //Raw Driving Methods
    public void driveSetSpeed(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(power);
    }

    public void turnLeft(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(-power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(-power);
    }

    public void turnRight(double power) {
        leftFrontDrive.setPower(-power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(-power);
        rightBackDrive.setPower(power);
    }


}