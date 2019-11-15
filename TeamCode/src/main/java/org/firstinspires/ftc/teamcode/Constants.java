package org.firstinspires.ftc.teamcode;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public interface Constants {

    //Drivetrain
    double DRIVE_PPR = 1120; //REV HD HEX 40:1
    double WHEEL_DIAMETER = 2; // 2" Mecanum
    double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; // pi * diameter
    double PPR_TO_INCHES = WHEEL_CIRCUMFERENCE / DRIVE_PPR; // (circumference / pulses in 1 Revolution) = (x / 1 pulse)
    double COUNTS_PER_INCH = 1120 * (1.0/40) / WHEEL_CIRCUMFERENCE;// this number should be 2.2 counts per inch. We basically have to put the number of inches we want to go multiply it by this number to tell the encoder how many counts it needs to go. I think this math is corect.
    double INCHES_PER_SQUARE = 24; //24" length/width
    double STRAFE_SPEED= .7;

    //Elevator
    double UPPER_LIMIT = 1000;
    double ELEVATOR_GAIN = .95;

    //Servo Positions
    double CLAMP_FOUNDATION = 1;
    double RELEASE_FOUNDATION = .0;
    double INTAKE_OPEN = 1;
    double INTAKE_CLOSE = 0;

    //Intake Speeds
    double SHOOT_SPEED = .95;
    double INTAKE_SPEED = -.95;
}
