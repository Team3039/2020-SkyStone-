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

    private DcMotor leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontMotor");
    private DcMotor rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontMotor");
    private DcMotor leftBackDrive= hardwareMap.get(DcMotor.class, "leftRearMotor");
    private DcMotor rightBackDrive = hardwareMap.get(DcMotor.class, "rightRearMotor");

    private DcMotor elevator = hardwareMap.get (DcMotor.class, "elevator") ;
    //hi how are you

    @Override
    public void init() {



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