package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous
public class AutoBlue extends LinearOpMode implements Constants {
    private ElapsedTime runtime = new ElapsedTime();
    //Drivetrain Motors
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    //Gamepiece Motors
    private Servo arm = null;
    private DcMotor elevatorA = null;
    private DcMotor elevatorB = null;
    private Servo elevatorTilt;
    private DcMotor intakeA = null;
    private DcMotor intakeB = null;
    private Servo arm1 = null;
    private Servo arm2 = null;
    private Servo clampA = null;
    private Servo clampB = null;
    private TouchSensor upperLimit = null;
    private TouchSensor lowerLimit = null;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Initialization
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        arm1 = hardwareMap.get(Servo.class, "arm1");
        arm2 = hardwareMap.get(Servo.class, "arm2");
        elevatorA = hardwareMap.get(DcMotor.class, "elevatorA");
        elevatorB = hardwareMap.get(DcMotor.class, "elevatorB");
        elevatorTilt = hardwareMap.get(Servo.class, "elevatorTilt");//
        intakeA = hardwareMap.get(DcMotor.class, "intakeA");
        intakeB = hardwareMap.get(DcMotor.class, "intakeB");
        clampA = hardwareMap.get(Servo.class, "clampA");
        clampB = hardwareMap.get(Servo.class, "clamoB");
        lowerLimit = hardwareMap.get(TouchSensor.class, "lowerLimit");

        //Default Direction Changed
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            runtime.reset();
//            while (getRuntime() < 1) {
//                strafeLeft(.8);
//            }
//            while (getRuntime() == 1.1) {
//                stop();
        }
        while (getRuntime() <= 7) {
            tiltElevator(1);
        }
//            while (getRuntime() == 2.5)  {
//                stop();
//            }


        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.update();

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
    public void resetEncoder() {
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void driveSetSpeed(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(power);
    }
    public void driveSquare(double power) {
        driveToDistance(INCHES_PER_SQUARE);
    }

    private void tiltElevator(double position) {
        elevatorTilt.setPosition(position);
    }
        public void driveToDistance ( double distance){
            resetEncoder();
            int distanceForMotor = (int) (distance * 2); //This should convert the distance we collect back to what the motor reads
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
        }

}

