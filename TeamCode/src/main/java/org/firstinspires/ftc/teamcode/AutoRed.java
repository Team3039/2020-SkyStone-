package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous
public class AutoRed extends LinearOpMode implements Constants {
    private ElapsedTime runtime = new ElapsedTime();
    //Drivetrain Motors
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    //Gamepiece Motors
    private Servo elevatorTilt;
    private DcMotor elevatorA = null;
    private DcMotor elevatorB = null;
    private DcMotor elevator = null;
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
        elevatorTilt = hardwareMap.get(Servo.class, "elevatorTilt");
        arm1 = hardwareMap.get(Servo.class, "arm1");
        arm2 = hardwareMap.get(Servo.class, "arm2");
        elevatorA = hardwareMap.get(DcMotor.class, "elevatorA");
        elevatorB = hardwareMap.get(DcMotor.class, "elevatorB");
        elevator = hardwareMap.get(DcMotor.class, "elevatorA");
        intakeA = hardwareMap.get(DcMotor.class, "intakeA");
        intakeB = hardwareMap.get(DcMotor.class, "intakeB");
        clampA = hardwareMap.get(Servo.class, "clampA");
        clampB = hardwareMap.get(Servo.class, "clamoB");
        lowerLimit = hardwareMap.get(TouchSensor.class, "lowerLimit");

        //Default Direction Changed
       /* leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        @ @ -163, 6 + 166, 9 @@private void releaseFoundation () {
            clampA.setPosition(RELEASE_FOUNDATION);
            clampB.setPosition(RELEASE_FOUNDATION);
        }*/
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            runtime.reset();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            while (getRuntime() <= 7) {
                tiltElevator(1);
            }
//
//            while (getRuntime() < 1) {
//                strafeRight(.8);
//            }
//            while (getRuntime() == 1.1) {
//                stop();
//            }

//            while (getRuntime() == 2.5)  {
//                stop();
//            }
//            while (getRuntime() < 3.5) {
//                stop();
//            }
//            while (getRuntime()< 4.4) {
//                driveForward(.8);
//            }
//            while (getRuntime() < 4.4) {
//                stop();
//            }

        }

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
        leftFrontDrive.setPower(-power);
        rightFrontDrive.setPower(-power);
        leftBackDrive.setPower(-power);
        rightBackDrive.setPower(-power);
    }
    private void tiltElevator(double position) {
        elevatorTilt.setPosition(position);
    }

    public void turnLeft(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(-power);
        leftBackDrive.setPower(power);
        rightBackDrive.setPower(-power);
    }
    private void stopDriving(){
        driveForward(0);
    }

    public void driveToDistance (double inches){
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setTargetPosition ((int)(inches / PPR_TO_INCHES)); //This converts inches back into pulses. 1 pulse covers about .046 inches of distance.
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveForward(.5);

        while (opModeIsActive() && rightBackDrive.isBusy())
        {
            telemetry.addData("Current Encoder Position: ", rightBackDrive.getCurrentPosition() + "  busy = " + rightBackDrive.isBusy());
            telemetry.update();
            idle();
        }
        stopDriving();
    }

}