//package org.firstinspires.ftc.teamcode;
//
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//
//@Autonomous
//public class AutoRed extends LinearOpMode implements Constants {
//    private ElapsedTime runtime = new ElapsedTime();
//    //Drivetrain Motors
//    private DcMotor leftFrontDrive = null;
//    private DcMotor rightFrontDrive = null;
//    private DcMotor leftBackDrive = null;
//    private DcMotor rightBackDrive = null;
//
//    //Gamepiece Motors
//    private Servo arm = null;
//    private DcMotor elevatorA = null;
//    private DcMotor elevatorB = null;
//    private DcMotor elevator = null;
//    private DcMotor intakeA = null;
//    private DcMotor intakeB = null;
//    private Servo arm1 = null;
//    private Servo arm2 = null;
//    private Servo clampA = null;
//    private Servo clampB = null;
////    private TouchSensor upperLimit = null;
////    private TouchSensor lowerLimit = null;
//
//
//
//    @Override
//    public void runOpMode() {
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//        //Initialization
//        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
//        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
//        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
//        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
//        arm1 = hardwareMap.get(Servo.class, "arm1");
//        arm2 = hardwareMap.get(Servo.class, "arm2");
//        elevatorA = hardwareMap.get(DcMotor.class, "elevatorA");
//        elevatorB = hardwareMap.get(DcMotor.class, "elevatorB");
//        elevator = hardwareMap.get(DcMotor.class, "elevatorA");
//        intakeA = hardwareMap.get(DcMotor.class, "intakeA");
//        intakeB = hardwareMap.get(DcMotor.class, "intakeB");
//        clampA = hardwareMap.get(Servo.class, "clampA");
//        clampB = hardwareMap.get(Servo.class, "clamoB");
//        upperLimit = hardwareMap.get(TouchSensor.class, "upperLimit");
//        lowerLimit = hardwareMap.get(TouchSensor.class, "lowerLimit");
//
//        //Default Direction Changed
//        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
//        @ @ -163, 6 + 166, 9 @@private void releaseFoundation () {
//            clampA.setPosition(RELEASE_FOUNDATION);
//            clampB.setPosition(RELEASE_FOUNDATION);
//        }
//        waitForStart();
//        runtime.reset();
//
//        while (opModeIsActive()) {
//            runtime.reset();
//
//            while (getRuntime() < 5) {
//                delatch();
//                setArmPower(.8);
//            }
//
//            while (getRuntime() < 9) {
//                setArmPower(-.5);
//            }
//            while (getRuntime() < 15) {
//                setExtensionPower(-.95);
//                setArmPower(0);
//            }
//            while (getRuntime() < 20) {
//                setExtensionPower(0);
//                strafeLeft();
//            }
////
//        }
//
//        telemetry.addData("Status", "Run Time: " + runtime.toString());
//        telemetry.update();
//
//    }
//}