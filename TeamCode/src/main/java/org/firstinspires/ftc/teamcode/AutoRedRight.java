package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;


    @Autonomous
    public class AutoRedRight extends LinearOpMode implements Constants  {

        private ElapsedTime runtime = new ElapsedTime();

        //Drivetrain Motors
        private DcMotor leftFrontDrive = null;
        private DcMotor rightFrontDrive = null;
        private DcMotor leftBackDrive = null;
        private DcMotor rightBackDrive = null;

        //Gamepiece Motors
        private DcMotor intakeA = null;
        private DcMotor intakeB = null;
        private DcMotor elevator = null;
        private Servo arm1 = null;
        private Servo arm2 = null;
        private Servo clampA = null;
        private Servo clampB = null;
        private TouchSensor upperLimit = null;
        private TouchSensor lowerLimit = null;

        //Switches



        @Override
        public void runOpMode() {
            telemetry.addData("Status", "Initialized");
            telemetry.update();

            //Initialization
            leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
            rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
            leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
            rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
            elevator = hardwareMap.get(DcMotor.class, "elevatorA");
            arm1 = hardwareMap.get(Servo.class, "arm1");
            arm2 = hardwareMap.get(Servo.class, "arm2");
            intakeA = hardwareMap.get(DcMotor.class, "intakeA");
            intakeB = hardwareMap.get(DcMotor.class, "intakeB");
            clampA = hardwareMap.get(Servo.class, "clampA");
            clampB = hardwareMap.get(Servo.class, "clamoB");
            upperLimit = hardwareMap.get(TouchSensor.class, "upperLimit");
            lowerLimit = hardwareMap.get(TouchSensor.class, "lowerLimit");

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

//        Ready to Run to Position
//        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Sequencing
            waitForStart();
            runtime.reset();

            //Start of Auto Code
            while (opModeIsActive()) {
                cycleStone1();
                cycleStone2();
                cycleStone3();
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
        private void setIntakeSpeed(double power) {
            intakeB.setPower(power);
            intakeA.setPower(power);
        }
        private void cycleStone1() {
            driveToDistance(INCHES_PER_SQUARE * 2);
            turnLeft(NINETY_TURN);
            driveToDistance(INCHES_PER_SQUARE * 1.5);
            turnRight(NINETY_TURN);
            driveToDistance(INCHES_PER_SQUARE/4);
            setIntakeSpeed(.75);
         //limit switch stuff   setIntakeSpeed(0);
            driveToDistance(-INCHES_PER_SQUARE/4);
            turnRight(NINETY_TURN);
            driveToDistance(INCHES_PER_SQUARE * 3);
            setIntakeSpeed(-.75);
            //limit switch stuff    setIntakeSpeed(0);
            turnLeft(NINETY_TURN);
            driveToDistance(-INCHES_PER_SQUARE * 2);
        }
        private void openIntake() {
            arm1.setPosition(.90);
            arm2.setPosition(.90);
        }
        private void closeIntake() {
            arm1.setPosition(.0);
            arm2.setPosition(.0);
        }
        private void cycleStone2() {
            driveToDistance(INCHES_PER_SQUARE * 2);
            turnLeft(NINETY_TURN);
            driveToDistance(INCHES_PER_SQUARE * 2);
            turnRight(NINETY_TURN);
            driveToDistance(INCHES_PER_SQUARE/4);
            setIntakeSpeed(.75);
            //limit switch stuff   setIntakeSpeed(0);
            driveToDistance(-12);
            turnLeft(NINETY_TURN);
            driveToDistance(INCHES_PER_SQUARE * 3.5);
            setIntakeSpeed(-.75);
            //limit switch stuff    setIntakeSpeed(0);
            turnLeft(NINETY_TURN);
            driveToDistance(-INCHES_PER_SQUARE * 2);
        }
        private void cycleStone3() {
            driveToDistance(INCHES_PER_SQUARE * 2);
            turnLeft(NINETY_TURN);
            driveToDistance(INCHES_PER_SQUARE * 2.5);
            turnRight(NINETY_TURN);
            driveToDistance(INCHES_PER_SQUARE/4);
            setIntakeSpeed(.75);
            //limit switch stuff   setIntakeSpeed(0);
            driveToDistance(-12);
            turnRight(NINETY_TURN);
            driveToDistance(INCHES_PER_SQUARE * 3);
            setIntakeSpeed(-.75);
            //limit switch stuff    setIntakeSpeed(0);
            turnLeft(NINETY_TURN);
            driveToDistance(-INCHES_PER_SQUARE * 2);
        }
        private void clampFoundation() {
            clampA.setPosition(CLAMP_FOUNDATION);
            clampB.setPosition(CLAMP_FOUNDATION);
        }
        private void releaseFoundation() {
            clampA.setPosition(RELEASE_FOUNDATION);
            clampB.setPosition(RELEASE_FOUNDATION);
        }
        private void moveElevator(double power) {
            elevator.setPower(power);
        }


        //Collects Distance
        public double getDistance() {
            return leftBackDrive.getCurrentPosition() * PPR_TO_INCHES * -1; // Multiplying it by -1 isn't doing anything.
        }

        //Main movement method to be called nearly  every time we want to move linearly
        public void driveToDistance(double distance) {
            resetEncoder();
            int distanceForMotor = (int)(distance * COUNTS_PER_INCH); //This should convert the distance we collect back to what the motor reads

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
            driveRaw(0.0);


//        while(getDistance()!=distance){
//            leftBackDrive.setTargetPosition(distanceForMotor);
//            leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            telemetry.addData("Target Position: ", leftBackDrive.getTargetPosition());
//            telemetry.update();
//        }

//        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        while (getDistance()<=distance){
//                driveRaw(.3);
//        }
//
//        driveRaw(0);

        }

        //Sets Encoder position back to 0
        public void resetEncoder() {
            leftBackDrive.setTargetPosition(0);
        }
    }



