package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class AutoMode extends LinearOpMode implements Constants  {

        private ElapsedTime runtime = new ElapsedTime();
        //leah was here again//

        private DcMotor leftFrontDrive = null;
        private DcMotor rightFrontDrive = null;
        private DcMotor leftBackDrive = null;
        private DcMotor rightBackDrive = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Initialization
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftRearMotor");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightRearMotor");

        //Default Direction Changed
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        //Change Motor Mode
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Ready to Run to Position
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Sequencing
        waitForStart();
        runtime.reset();

        //Start of Auto Code
        while (opModeIsActive()) {
            double position = getDistance();
            telemetry.addData("Position", position);

            //TODO: Call the Drive to Distance method

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
    public void turnRight (double power){
            leftFrontDrive.setPower (-power);
            rightFrontDrive.setPower (power);
            leftBackDrive.setPower (-power);
            rightBackDrive.setPower (power);
    }

    //Collects Distance
    public double getDistance() {
        return leftBackDrive.getCurrentPosition() * PPR_TO_INCHES * 1; //Multiply by -1 if in wrong direction
    }

    //Main movement method to be called nearly every time we want to move linearly
    public void driveToDistance(double distance) {
        resetEncoder();
        double distanceForMotor = distance / PPR_TO_INCHES; //This should convert the distance we collect back to what the motor reads

        /*
            TODO: Set each motor to drive to the distance variable declared earlier. Might have to get each motor to act as a follower the motor that has the encoder on it
         */
    }

    //Sets Encoder position back to 0
    public void resetEncoder() {
        leftBackDrive.setTargetPosition(0);
    }
}

