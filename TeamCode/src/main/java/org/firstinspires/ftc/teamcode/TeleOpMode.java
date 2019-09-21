package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//Test

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
@Disabled
public class TeleOpMode extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive= null;
    private DcMotor rightBackDrive = null;

<<<<<<< HEAD
=======
    private DcMotor elevator = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */
>>>>>>> 82fe41a6fa4b9f1dd2c94b76bb7f11cbbdce6b6c
    @Override
    public void init() {

        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftRearMotor");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightRearMotor");
        elevator = hardwareMap.get (DcMotor.class, "elevator") ;

        //telemetry and hardwareMap stuff goes in this method.

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        elevator.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
        public void raiseElevator (double power) {
            elevator.setPower(power);
        }
    }
    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
    }

    @Override
    public void stop() {
    }

}