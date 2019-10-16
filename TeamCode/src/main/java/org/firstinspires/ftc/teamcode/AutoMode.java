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
        private DcMotor leftBackDrive= null;
        private DcMotor rightBackDrive = null;



    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();




        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        resetEncoders();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        //Add game-specific stuff here
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            leftBackDrive.setTargetPosition(1120);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);



            drive(.45);

            while (leftFrontDrive.isBusy()){
                telemetry.addData("left position:", getLeftDistance());
                telemetry.update();
                idle();
            }
            drive(0.0);









        }
    }
    public void resetEncoders(){
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void drive(double power) {
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
    private double getLeftDistance() {
        return leftBackDrive.getCurrentPosition() * PPR_TO_INCHES;
    }
}

